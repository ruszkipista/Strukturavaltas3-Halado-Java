package training360.questions.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean enabled = true;

    @OneToMany(mappedBy = "member")
    private List<Question> questions;

    public Member(String name) {
        this.name = name;
    }
}
