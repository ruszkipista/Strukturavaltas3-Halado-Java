package org.training360.finalexam.teams;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.training360.finalexam.players.CreatePlayerCommand;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
@AllArgsConstructor
public class TeamController {

    private TeamService teamService;

    @GetMapping
    public List<TeamDTO> getTeams(){
        return teamService.getTeams();
    }

    @PostMapping
    public TeamDTO createTeam(@RequestBody @Valid CreateTeamCommand command){
        return teamService.createTeam(command);
    }

    @PostMapping("/{id}/players")
    public TeamDTO addNewPlayerToTeam(@PathVariable("id") long id, @RequestBody @Valid CreatePlayerCommand command){
        return teamService.addNewPlayerToTeam(id,command);
    }

    @PutMapping("/{id}/players")
    public TeamDTO getFreeAgentPlayer(@PathVariable("id") long id, @RequestBody UpdateWithExistingPlayerCommand command){
        return teamService.addNewFreeAgentToTeam(id, command);
    }

}
