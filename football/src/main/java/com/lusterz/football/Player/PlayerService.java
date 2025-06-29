package com.lusterz.football.Player;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers(){
        return playerRepository.findAll();
    }

    public Player addPlayer(Player player){
        return playerRepository.save(player);
    }

    @Transactional
    public void deletePlayer(String playerName){
        playerRepository.deleteByName(playerName);
    }

    public Player updatePlayer(Player updatedPlayer){
        Optional<Player> optionalPlayer = playerRepository.findByName(updatedPlayer.getName());
        if(optionalPlayer.isPresent()) {
            Player existingPlayer = optionalPlayer.get();
            existingPlayer.setName(updatedPlayer.getName());
            existingPlayer.setTeam(updatedPlayer.getTeam());
            existingPlayer.setPosition(updatedPlayer.getPosition());
            existingPlayer.setNation(updatedPlayer.getNation());
            return playerRepository.save(existingPlayer);
        } else {
            throw new EntityNotFoundException("Player not found with name: " + updatedPlayer.getName());
        }
    }

    public List<Player> getPlayersByTeam(String teamName){
        return playerRepository.findAll()
                                .stream()
                                .filter(player -> player.getTeam() != null && player.getTeam().equalsIgnoreCase(teamName))
                                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByName(String playerName){
        return playerRepository.findAll()
                                .stream()
                                .filter(player -> player.getName() != null && player.getName().toLowerCase().contains(playerName.toLowerCase()))
                                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByPosition(String playerPosition){
        return playerRepository.findAll()
                .stream()
                .filter(player -> player.getPosition() != null && player.getPosition().toLowerCase().contains(playerPosition.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByNation(String playerNation){
        return playerRepository.findAll()
                .stream()
                .filter(player -> player.getNation() != null && player.getNation().toLowerCase().contains(playerNation.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> searchPlayer(String name, String nation, String position, String team){
        Specification<Player> specs = PlayerSpecification.filter(name,
                                                                nation,
                                                                position,
                                                                team);
        return playerRepository.findAll(specs);
    }
}
