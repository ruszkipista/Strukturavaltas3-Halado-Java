package org.training360.finalexam.players;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/players")
@AllArgsConstructor
public class PlayerController {

    private PlayerService playerService;

    @GetMapping
    public List<PlayerDTO> getPlayers(){
        return playerService.getPlayers();
    }

    @PostMapping
    public PlayerDTO createPlayer(@RequestBody @Valid CreatePlayerCommand command){
        return playerService.createPlayer(command);

    }

    @DeleteMapping("/{id}")
    public void deletePlayerById(@PathVariable("id") long id){
        playerService.deletePlayerById(id);
    }

}
