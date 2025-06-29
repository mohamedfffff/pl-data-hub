package com.lusterz.football.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/pl")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<Player>> getPlayers() {
        return ResponseEntity.ok(playerService.getPlayers());
    }

    @GetMapping("/team/{teamName}")
    public ResponseEntity<List<Player>> getPlayersByTeam(@PathVariable String teamName) {
        return ResponseEntity.ok(playerService.getPlayersByTeam(teamName));
    }

    @GetMapping("/player/{playerName}")
    public ResponseEntity<List<Player>> getPlayersByName(@PathVariable String playerName) {
        return ResponseEntity.ok(playerService.getPlayersByName(playerName));
    }

    @GetMapping("/position/{playerPosition}")
    public ResponseEntity<List<Player>> getPlayersByPosition(@PathVariable String playerPosition) {
        return ResponseEntity.ok(playerService.getPlayersByPosition(playerPosition));
    }

    @GetMapping("/nation/{playerNation}")
    public ResponseEntity<List<Player>> getPlayersByNation(@PathVariable String playerNation) {
        return ResponseEntity.ok(playerService.getPlayersByNation(playerNation));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Player>> searchPlayer(@RequestParam(required = false) String name,
                                                     @RequestParam(required = false) String nation,
                                                     @RequestParam(required = false) String position,
                                                     @RequestParam(required = false) String team) {
        return ResponseEntity.ok(playerService.searchPlayer(name, nation, position, team));
    }

    @DeleteMapping("/{playerName}")
    public ResponseEntity<Void> deletePlayer(@PathVariable String playerName) {
        playerService.deletePlayer(playerName);
        return  ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        return ResponseEntity.ok(playerService.addPlayer(player));
    }

    @PutMapping
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) {
        return ResponseEntity.ok(playerService.updatePlayer(player));
    }
}
