package com.synergy.sports_agency.repository;

import com.synergy.sports_agency.aggregate.Grade;
import com.synergy.sports_agency.aggregate.Player;
import com.synergy.sports_agency.stream.MyObjectOutPutStream;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class PlayerRepository {

    private final ArrayList<Player> playerList = new ArrayList<>();
    private static final String FILE_PATH = "src/main/java/com/synergy/sports_agency/db/playerDB.dat";

    public PlayerRepository() {

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            ArrayList<Player> players = new ArrayList<>();
            players.add(new Player(1, "설빈", 182.5, 50.8, 26, "요가", "어깨", 8500000, Grade.S));
            players.add(new Player(2, "재훈", 151.6, 120.6, 18, "댄스스포츠", "발목", 4500000, Grade.S));
            players.add(new Player(3, "민주", 193.1, 64.2, 33, "씨름", "새끼손가락", 3000000, Grade.B));
            players.add(new Player(4, "형미", 177.3, 80.3, 11, "클라이밍", "엄지발가락", 1000, Grade.C));
            players.add(new Player(5, "윤후", 158.7, 30.9, 20, "필라테스", "팔꿈치", 700000, Grade.A));
            players.add(new Player(6, "영기", 149.9, 130.4, 44, "딱지치기", "손목", 20000, Grade.D));
            players.add(new Player(7, "가람", 193.8, 100.1, 37, "골프", "허리", 320000, Grade.E));

            savePlayers(file, players);
        }

        loadPlayers(file);

    }

    public Player selectPlayerByNo(int no) {
        for (Player player : playerList) {
            if (player.getNo() == no) {
                return player;
            }
        }
        return null;
    }

    private void loadPlayers(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

            while (true) {
                playerList.add((Player) ois.readObject());
            }

        } catch (EOFException e) {
            System.out.println("선수 정보를 모두 로딩하였습니다.");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void savePlayers(File file, ArrayList<Player> players) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {

            for (Player player : players) {
                oos.writeObject(player);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Player> selectAllPlayers() {
        return playerList;
    }

    public int selectLastPlayerNo() {
        Player lastPlayer = playerList.get(playerList.size() - 1);
        return lastPlayer.getNo();
    }

    public int insertPlayer(Player player) {
        int result = 0;

        try (MyObjectOutPutStream moos = new MyObjectOutPutStream(new FileOutputStream(FILE_PATH, true))) {

            moos.writeObject(player);
            playerList.add(player);
            result = 1;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public int updatePlayer(Player player) {
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getNo() == player.getNo()) {
                playerList.set(i, player);

                File file = new File(FILE_PATH);
                savePlayers(file, playerList);

                return 1;
            }
        }

        return 0;
    }

    public int deletePlayer(int no) {
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getNo() == no) {
                playerList.remove(i);

                File file = new File(FILE_PATH);
                savePlayers(file, playerList);

                return 1;
            }
        }
        return 0;

    }

    public Map<String, Double> avgHeightByCategory() {
        return playerList.stream()
                .collect(Collectors.groupingBy(
                        Player::getCategory,  // 종목별로 그룹화
                        Collectors.averagingDouble(Player::getHeight)  // 평균 키 계산
                ));
    }
    public Player findLightWeightMZPlayer() {
        List<Player> mzPlayers = playerList.stream()
                .filter(player -> player.getAge() >= 20 && player.getAge() <= 40) // MZ 세대를 20살 부터 40살까지
                .collect(Collectors.toList());

        if (mzPlayers.isEmpty()) {
            return null; // MZ 세대 선수가 없을 경우 null 반환
        }

        // 몸무게가 가장 가벼운 선수 찾기
        Player lightestPlayer = null;
        double minWeight = Double.MAX_VALUE; // 초기값을 최대값으로 설정

        for (Player player : mzPlayers) {
            if (player.getWeight() < minWeight) {
                minWeight = player.getWeight();
                lightestPlayer = player;
            }
        }

        return lightestPlayer;
    }

    public List<String> selectAllNamePlayers() {
        return playerList.stream()
                .map(Player::getName) // 선수명 map으로 받기
                .collect(Collectors.toList());
    }
}

