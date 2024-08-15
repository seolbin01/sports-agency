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
                case 4:
                    Player selected = playerService.findPlayerForModify(chooseNo());
                    if (selected == null) continue;
                    playerService.modifyPlayer(reform(selected));
                    break;
                case 5: break;
                case 9:
                    System.out.println("회원 관리 프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("번호를 잘못 입력했습니다.");
            }
        }
    }
    private static int chooseNo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("선수 번호 입력 : ");
        return scanner.nextInt();
    }

    private static Player reform(Player selected) {
        Player modifiedPlayer = selected;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("==== 수정 서브 메뉴 ====");
            System.out.println("1. 선수명");
            System.out.println("2. 신장");
            System.out.println("3. 체중");
            System.out.println("4. 나이");
            System.out.println("5. 종목");
            System.out.println("6. 부상");
            System.out.println("7. 연봉");
            System.out.println("8. 실력");
            System.out.println("9. 메인 메뉴로 돌아가기");
            System.out.print("내용을 선택하세요: ");
            int chooseNo = sc.nextInt();
            sc.nextLine();
            switch (chooseNo) {
                case 1:
                    System.out.print("수정 할 선수명을 입력하세요: ");
                    modifiedPlayer.setName(sc.nextLine());
                    break;
                case 2:
                    System.out.print("수정 할 신장을 입력하세요: ");
                    modifiedPlayer.setHeight(sc.nextDouble());
                    sc.nextLine(); // Consume newline
                    break;
                case 3:
                    System.out.print("수정 할 체중을 입력하세요: ");
                    modifiedPlayer.setWeight(sc.nextDouble());
                    sc.nextLine(); // Consume newline
                    break;
                case 4:
                    System.out.print("수정 할 나이를 입력하세요: ");
                    modifiedPlayer.setAge(sc.nextInt());
                    sc.nextLine(); // Consume newline
                    break;
                case 5:
                    System.out.print("수정 할 종목을 입력하세요: ");
                    modifiedPlayer.setCategory(sc.nextLine());
                    sc.nextLine(); // Consume newline

                    break;
                case 6:
                    System.out.print("수정 할 부상을 입력하세요: ");
                    modifiedPlayer.setInjury(sc.nextLine());
                    sc.nextLine(); // Consume newline

                    break;
                case 7:
                    System.out.print("수정 할 연봉을 입력하세요: ");
                    modifiedPlayer.setSalary(sc.nextInt());
                    sc.nextLine(); // Consume newline

                    break;
                case 8:
                    modifiedPlayer.setGrade(resetGradeType());

                    break;
                case 9:
                    System.out.println("메인 메뉴로 돌아갑니다.");
                    return selected;
                default:
                    System.out.println("번호를 다시 제대로 입력해 주세요: ");
            }

            return modifiedPlayer;
        }
    }

    private static Grade resetGradeType() {
        Scanner sc = new Scanner(System.in);
        System.out.print("수정 할 실력을 입력하세요(S, A, B, C, D, E): ");
        String gradetype = sc.nextLine();
        Grade gt = switch (gradetype) {
            case "S" -> Grade.S;
            case "A" -> Grade.A;
            case "B" -> Grade.B;
            case "C" -> Grade.C;
            case "D" -> Grade.D;
            case "E" -> Grade.E;
            default -> null;
        };

        return gt;
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

}