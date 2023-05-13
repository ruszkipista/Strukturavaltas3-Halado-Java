import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActivityDaoIT {
    EntityManagerFactory entityManagerFactory;
    ActivityDao repoAct;
    Activity activity;
    TrackPointDao repoTr;

    @BeforeEach
    void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("PU");
        repoAct = new ActivityDao(entityManagerFactory);
        repoTr = new TrackPointDao(entityManagerFactory);
        activity = new Activity(LocalDateTime.parse("2023-02-21T23:00"), "programming labs",
                Activity.ActivityType.PROGRAMMING);
    }

    @AfterEach
    void close() {
        entityManagerFactory.close();
    }

    @Test
    void saveOneActivity() {
        repoAct.saveActivity(activity);
        assertNotNull(activity.getId());
    }

    @Test
    void readOneActivity() {
        repoAct.saveActivity(activity);
        Activity activityCopy = repoAct.findActivityById(activity.getId());
        assertEquals(activityCopy.getStartTime(), activity.getStartTime());
    }

    @Test
    void readMultipleActivities() {
        repoAct.saveActivity(activity);
        activity = new Activity(LocalDateTime.parse("2023-02-20T19:00"), "playing basketball",
                Activity.ActivityType.BIKING);
        repoAct.saveActivity(activity);

        List<Activity> activities = repoAct.findAllActivities();
        assertEquals(2, activities.size());
    }

    @Test
    void modifyOneActivity() {
        repoAct.saveActivity(activity);
        repoAct.updateTypeOfActivityById(activity.getId(), Activity.ActivityType.BASKETBALL);

        Activity activityCopy = repoAct.findActivityById(activity.getId());
        assertEquals(Activity.ActivityType.BASKETBALL, activityCopy.getType());
    }

    @Test
    void deleteOneActivity() {
        repoAct.saveActivity(activity);
        List<Activity> activities = repoAct.findAllActivities();
        assertEquals(1, activities.size());

        repoAct.deleteActivityById(activity.getId());

        activities = repoAct.findAllActivities();
        assertEquals(0, activities.size());
    }

    @Test
    void descriptionNotNullable() {
        activity = new Activity(LocalDateTime.parse("2023-02-20T19:00"), null, Activity.ActivityType.BIKING);
        assertThrows(PersistenceException.class, () -> repoAct.saveActivity(activity));
    }

    @Test
    void automaticUpdateOfCreatedAt() {
        assertNull(activity.getUpdatedAt());
        assertNull(activity.getCreatedAt());

        repoAct.saveActivity(activity);
        activity = repoAct.findActivityById(activity.getId());

        assertNotNull(activity.getCreatedAt());
        assertNull(activity.getUpdatedAt());
    }

    @Test
    void automaticUpdateOfUpdatedAt() {
        repoAct.saveActivity(activity);
        assertNull(activity.getUpdatedAt());

        repoAct.updateDescriptionOfActivityById(activity.getId(), "New Description");

        Activity activityCopy = repoAct.findActivityById(activity.getId());
        assertNotNull(activityCopy.getUpdatedAt());
        assertTrue(activityCopy.getUpdatedAt().isAfter(activityCopy.getCreatedAt()));
    }

    @Test
    void saveLabelsInActivity(){
        activity.addLabel("L1");
        activity.addLabel("L2");
        repoAct.saveActivity(activity);

        Activity activityCopy = repoAct.findActivityByIdWithLabels(activity.getId());
        Assertions.assertThat(activityCopy.getLabels())
            .hasSize(2)
            .containsExactlyInAnyOrder("L1","L2");
    }

    @Test
    void saveTrackPointsInActivity(){
        repoAct.saveActivity(activity);

        repoTr.saveTrackPoint(new TrackPoint(LocalDateTime.parse("2023-03-01T12:15"), 40.0, 20.0, activity));
        repoTr.saveTrackPoint(new TrackPoint(LocalDateTime.parse("2023-03-01T12:00"), 41.0, 21.0, activity));

        Activity activityCopy = repoAct.findActivityByIdWithTrackPoints(activity.getId());
        Assertions.assertThat(activityCopy.getTrackPoints())
            .hasSize(2)
            .extracting(TrackPoint::getTime)
            .containsExactly(LocalDateTime.parse("2023-03-01T12:00"), LocalDateTime.parse("2023-03-01T12:15"));
    }

    @Test
    void findCoordinatesOfActivitiesAfterTime(){
        repoAct.saveActivity(activity);

        repoTr.saveTrackPoint(new TrackPoint(LocalDateTime.parse("2023-03-01T12:15"), 40.0, 20.0, activity));
        repoTr.saveTrackPoint(new TrackPoint(LocalDateTime.parse("2023-03-01T12:00"), 41.0, 21.0, activity));
        repoTr.saveTrackPoint(new TrackPoint(LocalDateTime.parse("2023-03-01T12:30"), 42.0, 22.0, activity));
        repoTr.saveTrackPoint(new TrackPoint(LocalDateTime.parse("2023-03-01T12:25"), 43.0, 23.0, activity));
        repoTr.saveTrackPoint(new TrackPoint(LocalDateTime.parse("2023-03-01T12:40"), 44.0, 24.0, activity));
        repoTr.saveTrackPoint(new TrackPoint(LocalDateTime.parse("2023-03-01T12:35"), 45.0, 25.0, activity));

        List<Coordinate> coordinates = repoTr.findTrackPointCoordinatesAfterDateTime(LocalDateTime.parse("2023-03-01T12:15"), 1, 2);
        Assertions.assertThat(coordinates)
            .hasSize(2)
            .extracting(Coordinate::getLongitude)
            .contains(23.0,24.0);
    }

    @Test
    void testGetTrackPointCountByActivity() {
        activity = new Activity(LocalDateTime.of(2022, 4, 11, 6, 0), "morning running", Activity.ActivityType.RUNNING);
        repoAct.saveActivity(activity);
        repoTr.saveTrackPoint(new TrackPoint(LocalDateTime.of(2021, 2, 3, 6, 3), 47.497912, 19.040235, activity));
        repoTr.saveTrackPoint(new TrackPoint(LocalDateTime.of(2021, 4, 5, 7, 4), -33.88223, 151.33140, activity));
        repoTr.saveTrackPoint(new TrackPoint(LocalDateTime.of(2021, 3, 4, 8, 5), 48.87376, 2.25120, activity));
        
        activity = new Activity(LocalDateTime.of(2022, 3, 14, 17, 30), "evening running", Activity.ActivityType.RUNNING);
        repoAct.saveActivity(activity);
        repoTr.saveTrackPoint(new TrackPoint(LocalDateTime.of(2021, 2, 3, 6, 3), 47.497912, 19.040235, activity));
        repoTr.saveTrackPoint(new TrackPoint(LocalDateTime.of(2021, 4, 5, 7, 4), -33.88223, 151.33140, activity));

        activity = new Activity(LocalDateTime.of(2022, 4, 9, 10, 15), "hiking near Budapest", Activity.ActivityType.HIKING);
        repoAct.saveActivity(activity);
        repoTr.saveTrackPoint(new TrackPoint(LocalDateTime.of(2021, 2, 3, 6, 3), 47.497912, 19.040235, activity));

        List<Object[]> expected = repoAct.getTrackPointCountByActivity();

        assertEquals(3, expected.size());
        assertArrayEquals(new Object[]{"morning running", 3}, expected.get(2));
        assertArrayEquals(new Object[]{"evening running", 2}, expected.get(0));
        assertArrayEquals(new Object[]{"hiking near Budapest", 1}, expected.get(1));
    }
}
