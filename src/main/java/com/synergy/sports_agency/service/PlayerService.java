package com.synergy.sports_agency.service;

import com.synergy.sports_agency.aggregate.Grade;
import com.synergy.sports_agency.aggregate.Player;
import com.synergy.sports_agency.repository.PlayerRepository;

import java.time.LocalDate;
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
            System.out.println(player.getName() + "님 선수 등록이 완료 되었습니다.");
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
            System.out.println("선수 방출이 완료 되었습니다");
        } else {
            System.out.println("입력하신 선수 번호에 해당하는 선수가 없습니다");
        }

    }

    public Map<String, String> selectBestPlayers() {
        List<Player> players = playRepository.selectAllPlayers();
        // Player의 Grade를 ordinal 값으로 비교
        Comparator<Player> gradeComparator = Comparator.comparingInt(player -> player.getGrade().ordinal());

        return players.stream()
                .collect(Collectors.groupingBy(
                        Player::getCategory,
                        Collectors.collectingAndThen(
                                // 각 카테고리별로 가장 높은 등급 선수 찾기
                                Collectors.maxBy(gradeComparator.reversed()),
                                optionalPlayer -> optionalPlayer
                                        .map(player -> player.getName() + " (실력: " + player.getGrade() + ")")
                                        .orElse("선수가 존재하지 않습니다")
                        )
                ));
    }

    public void bestPlayer() {
        Map<String, String> bestPlayersByCategory = selectBestPlayers();

        bestPlayersByCategory.forEach((category, playerDetails) ->
                System.out.println("종목: " + category + ", 선수명: " + playerDetails));
    }

    public void avgSalaryPlayer() {
        // 1. 기존 selectAllPlayer에서 List 가져어기
        ArrayList<Player> playerList = playRepository.selectAllPlayers();

        // 선수 리스트를 스트림으로 변환하고 , 이제 평균을 계산한다
        double averageSalary = playerList.stream()
                .mapToDouble(Player::getSalary)
                .average()
                .orElse(0.0);

        System.out.println("※ 소수점 셋째 자리에서 반올림 ※");
        System.out.println("모든 선수의 평균 연봉: " + Math.round(averageSalary * 100) / 100.0 + "원");
    }

    public void avgHeightPlayer() {
        // 평균 연봉 계산과 같이 기존 select 사용
        ArrayList<Player> playerList = playRepository.selectAllPlayers();

        // 선수 리스트를 스트림으로 변환하기
        Map<String, Double> averageHeights = playerList.stream()
                .collect(Collectors.groupingBy(
                        Player::getCategory,  // 종목별로 그루핑하기
                        Collectors.averagingDouble(Player::getHeight)  // 평균 키 계산
                ));

        averageHeights.forEach((category, avgHeight) ->
                System.out.println("종목: " + category + ", 평균 키: " + avgHeight + "cm"));
    }


    public void lightWeightMZPlayer() {
        List<Player> playerList = playRepository.selectAllPlayers();

        // MZ 세대 필터링 해준다(20~40살로 기준잡기)
        List<Player> mzPlayers = playerList.stream()
                .filter(player -> player.getAge() >= 20 && player.getAge() <= 40) // MZ 세대 필터링
                .collect(Collectors.toList());

        // MZ 세대 선수가 없는 경우
        if (mzPlayers.isEmpty()) {
            System.out.println("MZ 세대 선수가 없습니다.");
            return;
        }

        Player lightestPlayer = mzPlayers.stream()
                .min(Comparator.comparingDouble(Player::getWeight)) // 몸무게 기준으로 최소값으로 찾는다
                .orElse(null);

        if (lightestPlayer != null) {
            System.out.println("※ 몸무게가 가장 가벼운 MZ 세대 선수 ※");
            System.out.println("이름: " + lightestPlayer.getName());
            System.out.println("나이: " + lightestPlayer.getAge());
            System.out.println("몸무게: " + lightestPlayer.getWeight() + "kg");
        } else {
            System.out.println("MZ 세대 선수 정보가 없습니다.");
        }
    }

    public void findNamePlayer() {
        ArrayList<Player> playerList = playRepository.selectAllPlayers();

        //선수 이름만 추출하여 문자열로 변환
        String nameList = playerList.stream()
                .map(Player::getName) // 선수 이름 추출
                .collect(Collectors.joining(", ")); // 쉼표로 구분된 문자열로 변환

        System.out.println("모든 선수 목록(이름) : " + nameList);
    }

    public void salaryOfNextYear() {
        ArrayList<Player> findPlayers = playRepository.selectAllPlayers();

        Map<String, Integer> salaryOfNextYear = findPlayers.stream()
                .collect(Collectors.toMap(
                        Player::getName,
                        player -> {
                            if (player.getGrade().equals(Grade.S) ||
                                    player.getGrade().equals(Grade.A)) return player.getSalary() + 500000;
                            else if (player.getGrade().equals(Grade.C) ||
                                    player.getGrade().equals(Grade.D) ||
                                    player.getGrade().equals(Grade.E)) {
                                if (player.getSalary() >= 500000) return player.getSalary() - 500000;
                            }
                            return 0;
                        }
                ));

        System.out.println("※ A이상 500000 인상 / B 동결 / C이하 500000 삭감 예정 ※");
        salaryOfNextYear.forEach((playerName, nextSalary) ->
                System.out.println("선수 이름: " + playerName + ", 내년 연봉: " + nextSalary + "원"));

    }

    public void checkBMIAndChangeSalary(int no) {
        Player selectedPlayer = playRepository.selectPlayerByNo(no);

        if (selectedPlayer != null) {
            System.out.println("※ BMI 25 이상이면 연봉 100000 삭감 ※");
            System.out.println("※ 소수점 셋째 자리에서 반올림 ※");

            System.out.print("선수 이름: " + selectedPlayer.getName());
            System.out.print(", 이전 연봉: " + selectedPlayer.getSalary() + "원");

            double BMI = selectedPlayer.getWeight() / Math.pow((selectedPlayer.getHeight() / 100), 2);
            BMI = Math.round(BMI * 100) / 100.0;   // 소수점 셋째 자리에서 반올림

            System.out.print(", BMI: " + BMI);

            if (BMI >= 25) {
                if (selectedPlayer.getSalary() >= 100000) {
                    selectedPlayer.setSalary(selectedPlayer.getSalary() - 100000);
                } else {
                    selectedPlayer.setSalary(0);
                }
            }

            System.out.print(", 조정 후 연봉: " + selectedPlayer.getSalary() + "원");
            System.out.println();
        } else {
            System.out.println("해당 번호를 가진 선수는 없습니다");
        }

    }

    public void manageInjury() {
        ArrayList<Player> findPlayers = playRepository.selectAllPlayers();

        System.out.println("※ 현재 부상 중이면 선수 등급이 한 단계 하락합니다. ※");

        Map<String, Grade> salaryOfNextYear = findPlayers.stream()
                .collect(Collectors.toMap(
                        Player::getName,
                        player -> {
                            if (player.getInjury().isEmpty()) {
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

    public void limitSalaryYoungPlayer() {
        ArrayList<Player> findPlayers = playRepository.selectAllPlayers();

        Map<String, Integer> limitSalary = findPlayers.stream()
                .filter(player -> player.getAge() < 20)
                .collect(Collectors.toMap(
                        Player::getName,
                        player -> {
                            if (player.getSalary() > 1000000) {
                                player.setSalary(1000000);
                            }
                            return player.getSalary();
                        }
                ));

        System.out.println("※ 미성년자 선수의 경우, 최대 백만원으로 연봉이 제한됩니다. ※");
        limitSalary.forEach((playerName, salary) ->
                System.out.println("선수 이름: " + playerName + ", 연봉: " + salary + "원"));
    }

    public void findAdultPlayerByYear(int year) {
        int yeardiff = year - LocalDate.now().getYear();

        ArrayList<Player> findPlayers = playRepository.selectAllPlayers();

        System.out.println(year + "년 기준 성인 선수를 선별합니다.");
        System.out.println("※ 나이순으로 내림차순 정렬하여 출력 ※");
        List<String> adultPlayer = findPlayers.stream()
                .filter(player -> player.getAge() + yeardiff >= 20)
                .sorted(Comparator.comparingInt(Player::getAge).reversed())
                .map(Player::getName)
                .toList();

        System.out.println(adultPlayer);

    }

    public void treatInjury(String name) {
        Player selectedPlayer = playRepository.selectPlayerByName(name);

        if (selectedPlayer != null) {
            if (!selectedPlayer.getInjury().isEmpty()) {
                selectedPlayer.setInjury("");
                System.out.println(name + " 선수의 부상을 치료했습니다.");
            } else {
                System.out.println(name + " 선수는 부상이 없어 치료할 필요가 없습니다.");
            }
        } else {
            System.out.println("해당 이름을 가진 선수는 없습니다");
        }
    }

    public void addInjury(String category) {
        ArrayList<Player> selectedPlaryers = playRepository.selectPlayerByCategory(category);

        if (!selectedPlaryers.isEmpty()) {
            for (Player player : selectedPlaryers) {
                player.setInjury("다리");
            }
            System.out.println(category + " 종목의 선수들이 부상을 입었습니다.");
        } else {
            System.out.println("해당 종목의 선수가 없습니다.");
        }
    }
}
