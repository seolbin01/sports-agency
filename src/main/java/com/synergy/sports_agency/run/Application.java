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
            System.out.println();
            System.out.println("===== 스포츠 에이전시 =====");
            System.out.println("1. 선수 관리");
            System.out.println("2. 연봉 관리");
            System.out.println("3. 건강 관리");
            System.out.println("4. 통계 및 분석");
            System.out.println("9. 프로그램 종료");
            System.out.print("메뉴 선택 : ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1: managePlayer(); break;
                case 2: manageSalary(); break;
                case 3: manageEtc(); break;
                case 4: statistics(); break;
                case 9:
                    System.out.println("선수 관리 프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("번호를 잘못 입력했습니다.");
            }
        }
    }

    private static void managePlayer() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("===== 선수 관리 프로그램 =====");
            System.out.println("1. 모든 선수 정보 보기");
            System.out.println("2. 선수 찾기");
            System.out.println("3. 선수 등록");
            System.out.println("4. 선수 정보 수정");
            System.out.println("5. 선수 방출");
            System.out.println("6. 특정 년도에 따른 성인 선수 선별");
            System.out.println("9. 돌아가기");
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
                case 5: playerService.removePlayer(chooseNo()); break;
                case 6: playerService.findAdultPlayerByYear(chooseYear()); break;
                case 9:
                    System.out.println("이전으로 돌아갑니다.");
                    return;
                default:
                    System.out.println("번호를 잘못 입력했습니다.");
            }
        }
    }

    private static void manageSalary() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("===== 연봉 관리 프로그램 =====");
            System.out.println("1. 모든 선수 평균 연봉 보기");
            System.out.println("2. 내년 연봉 미리보기");
            System.out.println("3. 특정 선수 BMI 체크 후 연봉 조정");
            System.out.println("4. 미성년자 선수 연봉 제한 실행");
            System.out.println("9. 돌아가기");
            System.out.print("메뉴 선택 : ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1: playerService.avgSalaryPlayer(); break;
                case 2: playerService.salaryOfNextYear(); break;
                case 3: playerService.checkBMIAndChangeSalary(chooseNo()); break;
                case 4: playerService.limitSalaryYoungPlayer(); break;
                case 9:
                    System.out.println("이전으로 돌아갑니다.");
                    return;
                default:
                    System.out.println("번호를 잘못 입력했습니다.");
            }
        }
    }

    private static void manageEtc() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("===== 건강 관리 프로그램 =====");
            System.out.println("1. 사고 발생");
            System.out.println("2. 부상 치료");
            System.out.println("3. 부상 유무에 따른 선수 등급 조정");
            System.out.println("9. 돌아가기");
            System.out.print("메뉴 선택 : ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1: playerService.addInjury(chooseCategory()); break;
                case 2: playerService.treatInjury(chooseName());break;
                case 3: playerService.manageInjury(); break;
                case 9:
                    System.out.println("이전으로 돌아갑니다.");
                    return;
                default:
                    System.out.println("번호를 잘못 입력했습니다.");
            }
        }
    }

    private static void statistics() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("===== 통계 및 분석 프로그램 =====");
            System.out.println("1. 종목별 최고 등급 선수");
            System.out.println("2. 종목별 평균 신장");
            System.out.println("3. MZ세대 선수 중 가장 가벼운 선수 찾기");
            System.out.println("4. 모든 선수 목록(선수명)");
            System.out.println("9. 돌아가기");
            System.out.print("메뉴 선택 : ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1: playerService.bestPlayer(); break;
                case 2: playerService.avgHeightPlayer(); break;
                case 3: playerService.lightWeightMZPlayer(); break;
                case 4: playerService.findNamePlayer(); break;
                case 9:
                    System.out.println("이전으로 돌아갑니다.");
                    return;
                default:
                    System.out.println("번호를 잘못 입력했습니다.");
            }
        }
    }

    private static int chooseNo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("선수 번호 입력 : ");
        return scanner.nextInt();
    }

    private static int chooseYear() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("년도 입력 : ");
        return scanner.nextInt();
    }

    private static String chooseName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("선수 이름 입력 : ");
        return scanner.nextLine();
    }

    private static String chooseCategory() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("종목 입력 : ");
        return scanner.nextLine();
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
        System.out.print("선수명을 입력하세요: ");
        String name = sc.nextLine();

        System.out.print("신장을 입력하세요: ");
        double height = sc.nextDouble();

        System.out.print("체중을 입력하세요: ");
        double weight = sc.nextDouble();

        System.out.print("나이을 입력하세요: ");
        int age = sc.nextInt();

        sc.nextLine();

        System.out.print("종목을 입력하세요: ");
        String category = sc.nextLine();

        System.out.print("부상을 입력하세요: ");
        String injury = sc.nextLine();

        System.out.print("연봉을 입력하세요: ");
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

        newPlayer = new Player(name, height, weight, age, category, injury, salary, gt);

        return newPlayer;
    }

}