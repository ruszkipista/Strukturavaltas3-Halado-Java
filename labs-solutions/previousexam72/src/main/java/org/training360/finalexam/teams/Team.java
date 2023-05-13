package org.training360.finalexam.teams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.training360.finalexam.players.Player;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team",cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<Player> players = new HashSet<>();

    public Team(String name) {
        this.name = name;
    }

    public void addPlayer(Player player){
        players.add(player);
        player.setTeam(this);
    }
}
