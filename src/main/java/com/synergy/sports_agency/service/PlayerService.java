package com.synergy.sports_agency.service;

import com.synergy.sports_agency.aggregate.Player;
import com.synergy.sports_agency.repository.PlayerRepository;

import java.util.ArrayList;

public class PlayerService {

    private final PlayerRepository playRepository = new PlayerRepository();

    public void findAllPlayer() {
        ArrayList<Player> findPlayers = playRepository.selectAllPlayers();

        for (Player player : findPlayers) {
            System.out.println("Player : " + player);
        }
    }

    public void findPlayerByNo(int no) {
        Player selectedPlayer = playRepository.selectPlayerByNo(no);

        if (selectedPlayer != null) {
            System.out.println(selectedPlayer);
        } else {
            System.out.println("해당 번호를 가진 선수는 없습니다");
        }
    }


    public void registPlayer(Player player) {
        int lastPlayerNo = playRepository.selectLastPlayerNo();
        player.setNo(lastPlayerNo + 1);

        int result = playRepository.insertPlayer(player);

        if (result == 1) {
            System.out.println(player.getName() + "님 회원 가입이 완료 되었습니다.");
            /* DBMS와의 연동을 할 경우 commit, rollback 등의 처리 */
        }


    }
}