import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "trackpoints")
@NamedQuery(name = "coordinates", query = "SELECT new Coordinate(t.latitude, t.longitude) FROM TrackPoint t WHERE t.time > :afterThis")
public class TrackPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime time;

    private double latitude;
    private double longitude;

    @ManyToOne
    private Activity activity;
    
    public TrackPoint() {
    }

    public TrackPoint(LocalDateTime time, double latitude, double longitude, Activity activity) {
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.activity = activity;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

}
