package mk.ukim.finki.wp.kol2023.g1.service.impl;

import mk.ukim.finki.wp.kol2023.g1.model.Player;
import mk.ukim.finki.wp.kol2023.g1.model.PlayerPosition;
import mk.ukim.finki.wp.kol2023.g1.model.Team;
import mk.ukim.finki.wp.kol2023.g1.model.exceptions.InvalidPlayerIdException;
import mk.ukim.finki.wp.kol2023.g1.repository.PlayerRepository;
import mk.ukim.finki.wp.kol2023.g1.service.PlayerService;
import mk.ukim.finki.wp.kol2023.g1.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamService teamService;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamService teamService) {
        this.playerRepository = playerRepository;
        this.teamService = teamService;
    }

    @Override
    public List<Player> listAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player findById(Long id) {
        return playerRepository.findById(id).orElseThrow(InvalidPlayerIdException::new);
    }

    @Override
    public Player create(String name, String bio, Double pointsPerGame, PlayerPosition position, Long team) {
        Team t = teamService.findById(team);
        Player player = new Player(name, bio, pointsPerGame, position, t);
        return playerRepository.save(player);
    }

    @Override
    public Player update(Long id, String name, String bio, Double pointsPerGame, PlayerPosition position, Long team) {
        Player player = this.findById(id);
        player.setName(name);
        player.setBio(bio);
        player.setPointsPerGame(pointsPerGame);
        player.setPosition(position);
        player.setTeam(teamService.findById(team));
        return playerRepository.save(player);
    }

    @Override
    public Player delete(Long id) {
        Player player = this.findById(id);
        playerRepository.delete(player);
        return player;
    }

    @Override
    public Player vote(Long id) {
        Player player = this.findById(id);
        player.setVotes(player.getVotes() + 1);
        return playerRepository.save(player);
    }

    @Override
    public List<Player> listPlayersWithPointsLessThanAndPosition(Double pointsPerGame, PlayerPosition position) {
        if (pointsPerGame != null && position != null) {
            return playerRepository.findAllByPointsPerGameLessThanAndPosition(pointsPerGame, position);
        } else if (pointsPerGame != null) {
            return playerRepository.findAllByPointsPerGameLessThan(pointsPerGame);
        } else if (position != null) {
            return playerRepository.findAllByPosition(position);
        } else return playerRepository.findAll();
    }
}
