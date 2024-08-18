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

    public void salaryOfNextYear() {
        ArrayList<Player> findPlayers = playRepository.selectAllPlayers();

        Map<String, Integer> salaryOfNextYear = findPlayers.stream()
                .collect(Collectors.toMap(
                        Player::getName,
                        player -> {
                            if(player.getGrade().equals(Grade.S) ||
                                    player.getGrade().equals(Grade.A)) player.setSalary(player.getSalary() + 500000);
                            else if(player.getGrade().equals(Grade.C) ||
                                    player.getGrade().equals(Grade.D) ||
                                    player.getGrade().equals(Grade.E)) {
                                if(player.getSalary() >= 500000) player.setSalary(player.getSalary() - 500000);
                                else player.setSalary(0);
                            }
                            return player.getSalary();
                        }
                        ));

        salaryOfNextYear.forEach((playerName, nextSalary) ->
                System.out.println("선수 이름: " + playerName + ", 내년 연봉: " + nextSalary + "원"));

      public void checkBMIAndChangeSalary(int no) {
          Player selectedPlayer = playRepository.selectPlayerByNo(no);

          System.out.print("선수 이름: " + selectedPlayer.getName());
          System.out.print(", 이전 연봉: " + selectedPlayer.getSalary() + "원");

          double BMI = selectedPlayer.getWeight() / Math.pow((selectedPlayer.getHeight() / 100), 2);
          BMI = Math.round(BMI*100)/100.0;   // 소숫점 세번째 자리에서 반올림

          System.out.print(", BMI: " + BMI);

          if(BMI >= 25) {
              if(selectedPlayer.getSalary() >= 100000) {
                  selectedPlayer.setSalary(selectedPlayer.getSalary() - 100000);
              } else {
                  selectedPlayer.setSalary(0);
              }
          }

          System.out.print(", 조정 후 연봉: " + selectedPlayer.getSalary() + "원");
          System.out.println();
    }
}