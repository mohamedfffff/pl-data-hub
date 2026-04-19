package com.lusterz.football;

import com.lusterz.football.Player.Player;
import com.lusterz.football.Player.PlayerRepository;
import com.lusterz.football.Player.PlayerService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceUnitTests {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
        player.setName("Lionel Messi");
        player.setTeam("Inter Miami");
        player.setPosition("RWF");
        player.setNation("ARG");
    }

    @Test
    void getPlayers_shouldReturnAllPlayers() {
        when(playerRepository.findAll()).thenReturn(List.of(player));

        List<Player> result = playerService.getPlayers();

        assertEquals(1, result.size());
        assertTrue(result.contains(player));
    }

    @Test
    void getPlayers_shouldReturnEmptyListWhenNoPlayers() {
        when(playerRepository.findAll()).thenReturn(Collections.emptyList());

        List<Player> result = playerService.getPlayers();

        assertTrue(result.isEmpty());
    }

    @Test
    void addPlayer_shouldSaveAndReturnPlayer() {
        when(playerRepository.save(player)).thenReturn(player);

        Player result = playerService.addPlayer(player);

        assertEquals(player, result);
        verify(playerRepository).save(player);
    }

    @Test
    void deletePlayer_shouldDeletePlayer() {
        playerService.deletePlayer("Lionel Messi");

        verify(playerRepository).deleteByName("Lionel Messi");
    }

    @Test
    void updatePlayer_shouldUpdateAndReturnPlayer_whenPlayerExists() {
        when(playerRepository.findByName("Lionel Messi")).thenReturn(Optional.of(player));
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        Player result = playerService.updatePlayer(player);

        assertEquals(player, result);
        verify(playerRepository).save(player);
    }

    @Test
    void updatePlayer_shouldThrowEntityNotFoundException_whenPlayerNotFound() {
        when(playerRepository.findByName("Unknown Player")).thenReturn(Optional.empty());

        Player unknown = new Player();
        unknown.setName("Unknown Player");

        assertThrows(EntityNotFoundException.class, () -> playerService.updatePlayer(unknown));
    }

    @Test
    void getPlayersByTeam_shouldReturnMatchingPlayers() {
        when(playerRepository.findAll()).thenReturn(List.of(player));

        List<Player> result = playerService.getPlayersByTeam("Inter Miami");

        assertEquals(1, result.size());
        assertTrue(result.contains(player));
    }

    @Test
    void getPlayersByTeam_shouldReturnEmpty_whenNoMatch() {
        when(playerRepository.findAll()).thenReturn(List.of(player));

        List<Player> result = playerService.getPlayersByTeam("Real Madrid");

        assertTrue(result.isEmpty());
    }

    @Test
    void getPlayersByName_shouldReturnMatchingPlayers() {
        when(playerRepository.findAll()).thenReturn(List.of(player));

        List<Player> result = playerService.getPlayersByName("messi");

        assertEquals(1, result.size());
        assertTrue(result.contains(player));
    }

    @Test
    void getPlayersByName_shouldReturnEmpty_whenNoMatch() {
        when(playerRepository.findAll()).thenReturn(List.of(player));

        List<Player> result = playerService.getPlayersByName("ronaldo");

        assertTrue(result.isEmpty());
    }

    @Test
    void getPlayersByPosition_shouldReturnMatchingPlayers() {
        when(playerRepository.findAll()).thenReturn(List.of(player));

        List<Player> result = playerService.getPlayersByPosition("RWF");

        assertEquals(1, result.size());
        assertTrue(result.contains(player));
    }

    @Test
    void getPlayersByPosition_shouldReturnEmpty_whenNoMatch() {
        when(playerRepository.findAll()).thenReturn(List.of(player));

        List<Player> result = playerService.getPlayersByPosition("GK");

        assertTrue(result.isEmpty());
    }

    @Test
    void getPlayersByNation_shouldReturnMatchingPlayers() {
        when(playerRepository.findAll()).thenReturn(List.of(player));

        List<Player> result = playerService.getPlayersByNation("ARG");

        assertEquals(1, result.size());
        assertTrue(result.contains(player));
    }

    @Test
    void getPlayersByNation_shouldReturnEmpty_whenNoMatch() {
        when(playerRepository.findAll()).thenReturn(List.of(player));

        List<Player> result = playerService.getPlayersByNation("BRZ");

        assertTrue(result.isEmpty());
    }

    @Test
    void searchPlayer_shouldReturnMatchingPlayers() {
        when(playerRepository.findAll(any(Specification.class))).thenReturn(List.of(player));

        List<Player> result = playerService.searchPlayer("Messi", "ARG", "RWF", "Inter Miami");

        assertEquals(1, result.size());
        assertTrue(result.contains(player));
    }

    @Test
    void searchPlayer_shouldReturnEmpty_whenNoMatch() {
        when(playerRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

        List<Player> result = playerService.searchPlayer("Unknown", null, null, null);

        assertTrue(result.isEmpty());
    }
}
