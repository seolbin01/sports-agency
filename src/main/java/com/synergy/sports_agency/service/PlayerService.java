package com.synergy.sports_agency.service;

import com.synergy.sports_agency.aggregate.Player;
import com.synergy.sports_agency.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

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

    public Player findPlayerForModify(int no) {
        Player selectedPlayer = playRepository.selectPlayerByNo(no);

        if (selectedPlayer != null) {
            Player newInstance = new Player();
            newInstance.setNo(selectedPlayer.getNo());
            newInstance.setName(selectedPlayer.getName());
            newInstance.setHeight(selectedPlayer.getHeight());
            newInstance.setWeight(selectedPlayer.getWeight());
            newInstance.setAge(selectedPlayer.getAge());
            newInstance.setCategory(selectedPlayer.getCategory());
            newInstance.setInjury(selectedPlayer.getInjury());
            newInstance.setSalary(selectedPlayer.getSalary());
            newInstance.setGrade(selectedPlayer.getGrade());
            return newInstance;

        }
        System.out.println("입력하신 선수 번호에 해당하는 선수 없습니다");
        return null;
    }

    public void modifyPlayer(Player player) {
        int result = playRepository.updatePlayer(player);

        if (result == 1) {
            System.out.println("선수 정보 수정이 완료되었습니다 ");
        } else {
            System.out.println("입력하신 선수 번호에 해당하는 선수가 없습니다  ");
        }

    }

    public void removePlayer(int no) {
        int result = playRepository.deletePlayer(no);

        if (result == 1) {
            System.out.println("회원 탈퇴가 완료 되었습니다");
        } else {
            System.out.println("입력하신 회원 번호에 해당하는 회원이 없습니다");
        }

    }

    public void bestPlayer() {
        Map<String, String> bestPlayersByCategory = playRepository.selectBestPlayers();

        bestPlayersByCategory.forEach((category, playerDetails) ->
                System.out.println("종목: " + category + ", 선수명: " + playerDetails));
    }

    public void avgSalaryPlayer() {
        // 모든 선수의 연봉을 DoubleStream으로 생성
        DoubleStream salaryStream = playRepository.avgSalaryAllPlayer();

        // 평균 연봉을 직접 계산
        double averageSalary = salaryStream
                .reduce((a, b) -> a + b) // 연봉을 모두 더함
                .orElse(0.0) / playRepository.selectAllPlayers().size(); // 선수 수로 나눔

        // 평균 연봉 출력
        System.out.println("모든 선수의 평균 연봉: " + averageSalary + "원");
    }

}