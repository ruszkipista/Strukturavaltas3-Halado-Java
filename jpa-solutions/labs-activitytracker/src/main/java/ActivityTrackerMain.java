import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ActivityTrackerMain {
    static EntityManagerFactory entityManagerFactory;

    public static void main(String[] args) {
        entityManagerFactory = Persistence.createEntityManagerFactory("PU");
        ActivityDao repo = new ActivityDao(entityManagerFactory);

        // Save single
        Activity activity = new Activity(LocalDateTime.parse("2023-02-21T23:00"),"programming labs", Activity.ActivityType.PROGRAMMING);
        repo.saveActivity(activity);
        assert null != activity.getId();

        // Read single
        Activity activityCopy = repo.findActivityById(activity.getId());
        assert activityCopy.getStartTime().equals(activity.getStartTime());

        // Save single
        activity = new Activity(LocalDateTime.parse("2023-02-20T19:00"),"playing basketball", Activity.ActivityType.BIKING);
        repo.saveActivity(activity);

        // Read multiple
        List<Activity> activities = repo.findAllActivities();
        assert 2 == activities.size();

        // Modify single
        repo.updateTypeOfActivityById(activity.getId(), Activity.ActivityType.BASKETBALL);
        activityCopy = repo.findActivityById(activity.getId());
        assert Activity.ActivityType.BASKETBALL.equals(activityCopy.getType());

        // Delete single
        repo.deleteActivityById(activity.getId());
        activities = repo.findAllActivities();
        assert 1 == activities.size();

        entityManagerFactory.close();
    }

}
