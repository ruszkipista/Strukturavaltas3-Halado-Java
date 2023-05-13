package org.training360.finalexam.teams;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.training360.finalexam.NotFoundException;
import org.training360.finalexam.players.CreatePlayerCommand;
import org.training360.finalexam.players.Player;
import org.training360.finalexam.players.PlayerDTO;
import org.training360.finalexam.players.PlayerRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeamService {

    private TeamRepository teamRepository;

    private PlayerRepository playerRepository;

    private ModelMapper modelMapper;

    public List<TeamDTO> getTeams(){
        java.lang.reflect.Type targetListType = new TypeToken<List<TeamDTO>>() {}.getType();
        return modelMapper.map(teamRepository.findAll(), targetListType);
    }

    public TeamDTO createTeam(CreateTeamCommand command){
        Team team = new Team(command.getName());
        teamRepository.save(team);
        return modelMapper.map(team, TeamDTO.class);
    }

    @Transactional
    public TeamDTO addNewPlayerToTeam(long id, CreatePlayerCommand command) {
        Team team = teamRepository.findById(id).orElseThrow(()->new NotFoundException(id));
        Player player = new Player(command.getName(),command.getBirthDate(),command.getPosition());
        team.addPlayer(player);
        return modelMapper.map(team,TeamDTO.class);
    }

    @Transactional
    public TeamDTO addNewFreeAgentToTeam(long id, UpdateWithExistingPlayerCommand command) {
        Team team = teamRepository.findById(id).orElseThrow(()->new NotFoundException(id));
        Player player = playerRepository.findById(command.getPlayerId()).orElseThrow(()->new NotFoundException(command.getPlayerId()));

        if (player.hasNoTeam() && hasEmptyPosition(team,player) ){
            team.addPlayer(player);
        }

        return modelMapper.map(team,TeamDTO.class);
    }


    private boolean hasEmptyPosition(Team team, Player player){
        return team.getPlayers().stream()
                .filter(p->p.getPosition()==player.getPosition())
                .collect(Collectors.toList()).size() < 2;
    }
}
