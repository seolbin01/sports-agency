package com.synergy.sports_agency.run;

import com.synergy.sports_agency.aggregate.Grade;
import com.synergy.sports_agency.aggregate.Player;
import com.synergy.sports_agency.service.PlayerService;

import java.util.Scanner;

public class Application {

    private static final PlayerService playerService = new PlayerService();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("===== 선수 관리 프로그램 =====");
            System.out.println("1. 모든 선수 정보 보기");
            System.out.println("2. 선수 찾기");
            System.out.println("3. 선수 등록");
            System.out.println("4. 선수 정보 수정");
            System.out.println("5. 선수 방출");
            System.out.println("9. 프로그램 종료");
            System.out.print("메뉴 선택 : ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1: playerService.findAllPlayer();break;
                case 2: playerService.findPlayerByNo(chooseNo()); break;
                case 3: playerService.registPlayer(signUp()); break;
                case 4: break;
                case 5: break;
                case 9:
                    System.out.println("회원 관리 프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("번호를 잘못 입력했습니다.");
            }
        }
    }

    private static Player signUp() {
        Player newPlayer = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("선수명을 입력하세요: ");
        String name = sc.nextLine();

        System.out.println("신장을 입력하세요: ");
        double height = sc.nextDouble();

        System.out.println("체중을 입력하세요: ");
        double weight = sc.nextDouble();

        System.out.println("나이을 입력하세요: ");
        int age = sc.nextInt();

        sc.nextLine();

        System.out.println("종목을 입력하세요: ");
        String category = sc.nextLine();

        System.out.println("부상을 입력하세요: ");
        String injury = sc.nextLine();

        System.out.println("연봉을 입력하세요: ");
        int salary = sc.nextInt();

        sc.nextLine();

        System.out.print("실력을 입력하세요(S, A, B, C, D, E): ");
        String gradeType = sc.nextLine();
        Grade gt = switch (gradeType) {
            case "S" -> Grade.S;
            case "A" -> Grade.A;
            case "B" -> Grade.B;
            case "C" -> Grade.C;
            case "D" -> Grade.D;
            case "E" -> Grade.E;

            default -> null;
        };

        newPlayer = new Player(name, height, weight, age, category, injury, salary);

        newPlayer.setGrade(gt);

        return newPlayer;
    }

    private static int chooseNo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("선수 번호 입력 : ");
        return scanner.nextInt();
    }
}