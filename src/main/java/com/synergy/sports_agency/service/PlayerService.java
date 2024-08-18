package com.synergy.sports_agency.service;

import com.synergy.sports_agency.aggregate.Grade;
import com.synergy.sports_agency.aggregate.Player;
import com.synergy.sports_agency.repository.PlayerRepository;

import java.util.*;
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
        DoubleStream salaryStream = playRepository.avgSalaryAllPlayer();

        // OptionalDouble로 평균 연봉을 계산
        OptionalDouble averageSalaryOpt = salaryStream.average();

        if (averageSalaryOpt.isPresent()) {
            // 평균 연봉이 존재할 때
            double averageSalary = averageSalaryOpt.getAsDouble();
            System.out.println("모든 선수의 평균 연봉: " + averageSalary + "원");
        } else {
            // 평균 연봉이 없을 때 (선수가 없는 경우 등)
            System.out.println("선수가 없습니다. 평균 연봉을 계산할 수 없습니다.");
        }
    }

    public void avgHeightPlayer() {
        Map<String, Double> averageHeights = playRepository.avgHeightByCategory();

        averageHeights.forEach((category, avgHeight) ->
                System.out.println("종목: " + category + ", 평균 키: " + avgHeight + "cm"));
    }

    public void lightWeightMZPlayer() {
        Player player = playRepository.findLightWeightMZPlayer();

        if (player != null) {
            System.out.println("몸무게가 가장 가벼운 MZ 세대 선수:");
            System.out.println("이름: " + player.getName());
            System.out.println("몸무게: " + player.getWeight() + "kg");
        } else {
            System.out.println("MZ 세대 선수 정보가 없습니다.");
        }
    }

    public void findNamePlayer() {
        List<String> namePlayers = playRepository.selectAllNamePlayers();

        String nameList = namePlayers.stream()
                .collect(Collectors.joining(", "));

        System.out.println("모든 선수 목록(이름) : " + nameList);
    }

    public void manageInjury() {
        ArrayList<Player> findPlayers = playRepository.selectAllPlayers();

        Map<String, Grade> salaryOfNextYear = findPlayers.stream()
                .collect(Collectors.toMap(
                        Player::getName,
                        player -> {
                            if(player.getInjury().isEmpty()) {
                                System.out.println(player.getName() + " 선수는 부상이 없어 등급이 하락하지 않았습니다.");
                            } else {
                                player.setGrade(decrementGrade(player.getGrade()));
                            }
                            return player.getGrade();
                        }
                ));

        salaryOfNextYear.forEach((playerName, grade) ->
                System.out.println("선수 이름: " + playerName + ", 실력(등급): " + grade));

    }

    public Grade decrementGrade(Grade currentGrade) {
        Grade[] grades = Grade.values(); // S, A, B, C, D, E
        int ordinal = currentGrade.ordinal(); // 현재 등급의 순서
        if (ordinal < grades.length - 1) { // 감소할 수 있는 경우
            return grades[ordinal + 1];    // 다음 등급을 반환
        } else {
            return grades[ordinal];        // 이미 최소 등급이면 그대로 반환
        }
    }

}