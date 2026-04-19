package com.lusterz.football;

import com.lusterz.football.Player.Player;
import com.lusterz.football.Player.PlayerRepository;
import com.lusterz.football.Player.PlayerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
class PlayerServiceIntegrationTests {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerService playerService;

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
        player.setName("Lionel Messi");
        player.setTeam("Inter Miami");
        player.setPosition("RWF");
        player.setNation("ARG");
        playerRepository.save(player);
    }

    @Test
    void getPlayers_shouldReturnAllPlayers() {
        List<Player> result = playerService.getPlayers();

        assertEquals(1, result.size());
        assertTrue(result.contains(player));
    }

    @Test
    void getPlayers_shouldReturnEmptyListWhenNoPlayers() {
        playerRepository.deleteAll();

        List<Player> result = playerService.getPlayers();

        assertTrue(result.isEmpty());
    }

    @Test
    void addPlayer_shouldSaveAndReturnPlayer() {
        Player result = playerService.addPlayer(player);

        assertEquals(player, result);

        Optional<Player> dbPlayer = playerRepository.findByName(player.getName());
        assertTrue(dbPlayer.isPresent());
    }

    @Test
    void deletePlayer_shouldDeletePlayer() {
        playerService.deletePlayer("Lionel Messi");

        Optional<Player> dbPlayer = playerRepository.findByName(player.getName());
        assertFalse(dbPlayer.isPresent());
    }

    @Test
    void updatePlayer_shouldUpdateAndReturnPlayer_whenPlayerExists() {
        Player result = playerService.updatePlayer(player);

        assertEquals(player, result);
    }

    @Test
    void updatePlayer_shouldThrowEntityNotFoundException_whenPlayerNotFound() {
        playerRepository.deleteAll();

        Player unknown = new Player();
        unknown.setName("Unknown Player");

        assertThrows(EntityNotFoundException.class, () -> playerService.updatePlayer(unknown));
    }

    @Test
    void getPlayersByTeam_shouldReturnMatchingPlayers() {
        List<Player> result = playerService.getPlayersByTeam("Inter Miami");

        assertEquals(1, result.size());
        assertTrue(result.contains(player));
    }

    @Test
    void getPlayersByTeam_shouldReturnEmpty_whenNoMatch() {
        playerRepository.deleteAll();

        List<Player> result = playerService.getPlayersByTeam("Real Madrid");

        assertTrue(result.isEmpty());
    }

    @Test
    void getPlayersByName_shouldReturnMatchingPlayers() {
        List<Player> result = playerService.getPlayersByName("messi");

        assertEquals(1, result.size());
        assertTrue(result.contains(player));
    }

    @Test
    void getPlayersByName_shouldReturnEmpty_whenNoMatch() {
        playerRepository.deleteAll();

        List<Player> result = playerService.getPlayersByName("ronaldo");

        assertTrue(result.isEmpty());
    }

    @Test
    void getPlayersByPosition_shouldReturnMatchingPlayers() {
        List<Player> result = playerService.getPlayersByPosition("RWF");

        assertEquals(1, result.size());
        assertTrue(result.contains(player));
    }

    @Test
    void getPlayersByPosition_shouldReturnEmpty_whenNoMatch() {
        playerRepository.deleteAll();

        List<Player> result = playerService.getPlayersByPosition("GK");

        assertTrue(result.isEmpty());
    }

    @Test
    void getPlayersByNation_shouldReturnMatchingPlayers() {
        List<Player> result = playerService.getPlayersByNation("ARG");

        assertEquals(1, result.size());
        assertTrue(result.contains(player));
    }

    @Test
    void getPlayersByNation_shouldReturnEmpty_whenNoMatch() {
        playerRepository.deleteAll();

        List<Player> result = playerService.getPlayersByNation("BRZ");

        assertTrue(result.isEmpty());
    }

    @Test
    void searchPlayer_shouldReturnMatchingPlayers() {
        List<Player> result = playerService.searchPlayer("Messi", "ARG", "RWF", "Inter Miami");

        assertEquals(1, result.size());
        assertTrue(result.contains(player));
    }

    @Test
    void searchPlayer_shouldReturnEmpty_whenNoMatch() {
        playerRepository.deleteAll();

        List<Player> result = playerService.searchPlayer("Unknown", null, null, null);

        assertTrue(result.isEmpty());
    }
}
