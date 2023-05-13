import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "activities")
public class Activity {
    public enum ActivityType {
        BIKING, HIKING, RUNNING, BASKETBALL, PROGRAMMING
    };

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "id_gen")
    @TableGenerator(name = "id_gen", table = "act_id_gen", pkColumnName = "table_name", valueColumnName = "id_val", initialValue = 13, allocationSize = 7)
    private Long id;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(length = 200, nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ActivityType type;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ElementCollection
    @CollectionTable(name = "labels", joinColumns = @JoinColumn(name = "label_id"))
    @Column(name = "label")
    private List<String> labels = new ArrayList<>();

    @OneToMany(mappedBy = "activity", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<TrackPoint> trackPoints = new ArrayList<>();

    @ManyToMany
    @JoinTable(name="actv_area",
           joinColumns=@JoinColumn(name="activity_id"),
           inverseJoinColumns=@JoinColumn(name="area_id")) 
    private Set<Area> areas = new HashSet<>();

    public Activity() {
    }

    public Activity(Long id, LocalDateTime startTime, String description, ActivityType type) {
        this.id = id;
        this.startTime = startTime;
        this.description = description;
        this.type = type;
    }

    public Activity(LocalDateTime startTime, String description, ActivityType type) {
        this(null, startTime, description, type);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActivityType getType() {
        return this.type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + getId() +
                ", startTime=" + getStartTime() +
                ", description=" + getDescription() +
                ", type=" + getType() +
                "}";
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    public List<String> getLabels() {
        return this.labels;
    }

    public void addLabel(String label) {
        this.labels.add(label);
    }

    public List<TrackPoint> getTrackPoints() {
        return this.trackPoints;
    }

    public void addTrackPoint(TrackPoint trackPoint) {
        this.trackPoints.add(trackPoint);
    }

}
