package com.synergy.sports_agency.repository;

import com.synergy.sports_agency.aggregate.Grade;
import com.synergy.sports_agency.aggregate.Player;

import java.io.*;
import java.util.ArrayList;

public class PlayerRepository {

    private final ArrayList<Player> playerList = new ArrayList<>();
    private static final String FILE_PATH = "src/main/java/com/synergy/sports_agency/db/playerDB.dat";

    public PlayerRepository() {

        File file = new File(FILE_PATH);

        if(!file.exists()) {
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
}
