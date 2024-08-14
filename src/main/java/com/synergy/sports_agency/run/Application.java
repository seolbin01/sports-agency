package com.synergy.sports_agency.run;

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
                case 3: break;
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

    private static int chooseNo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("선수 번호 입력 : ");
        return scanner.nextInt();
    }
}