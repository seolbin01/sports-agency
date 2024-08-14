package com.synergy.sports_agency.service;

import com.synergy.sports_agency.aggregate.Player;
import com.synergy.sports_agency.repository.PlayerRepository;

import java.util.ArrayList;

public class PlayerService {

    private final PlayerRepository playRepository = new PlayerRepository();

    public void findAllPlayer() {
        ArrayList<Player> findPlayers = playRepository.selectAllPlayers();

        for(Player player : findPlayers) {
            System.out.println("Player : " + player);
        }
    }
}
