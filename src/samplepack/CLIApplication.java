// CLIApplication.java
package samplepack;

import java.util.Scanner;

public class CLIApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 로그인 수행
        Login login = new Login();
        login.selectMethod(sc); // 로그인 방식 선택 및 로그인 실행

        // 로그인 성공 후 나머지 기능 진입
        NotificationPage notificationPage = new NotificationPage();
        BusRoutePage busRoutePage = new BusRoutePage();
        FavoritesManager favoritesManager = new FavoritesManager();

        while (true) {
            printMainScreen();

            int menu;
            try {
                System.out.print("\n➡ 메뉴 번호를 입력하세요: ");
                menu = sc.nextInt();
                sc.nextLine(); // 개행 처리
            } catch (Exception e) {
                System.out.println("⚠ 숫자를 입력해주세요.");
                sc.nextLine();
                continue;
            }

            System.out.println("\n--------------------------------------------------");

            switch (menu) {
                case 1, 2, 3 -> busRoutePage.selectRouteByManual(menu, sc);
                case 4 -> notificationPage.showAllNotifications();
                case 5 -> {
                    System.out.print("추가할 수시 공지를 입력하세요: ");
                    String newNotice = sc.nextLine();
                    notificationPage.addOccasionalNotification(newNotice);
                }
                case 6 -> favoritesManager.showFavorites();
                case 7 -> System.out.println("📌 학생서비스 페이지로 이동합니다. (모의 기능)");
                case 8 -> {
                    System.out.println("👋 프로그램을 종료합니다. - ALGAMZAZ");
                    return;
                }
                default -> System.out.println("⚠ 올바른 메뉴 번호를 입력해주세요.");
            }
        }
    }

    private static void printMainScreen() {
        System.out.println("\n========= 🚌 ALGAMJABUS 셔틀 안내 시스템 =========");
        System.out.println("📢 [공지] 일요일 기숙사행 노선은 운영하지 않습니다.");
        System.out.println("--------------------------------------------------");

        System.out.println("\n[노선 선택]");
        System.out.println("1. 연구협력관행");
        System.out.println("2. 한우리집행");
        System.out.println("3. 기숙사 (야간)");

        System.out.println("\n[기타 기능]");
        System.out.println("4. 안내사항 전체 보기");
        System.out.println("5. 수시 공지 추가하기");
        System.out.println("6. 즐겨찾기한 시간표");
        System.out.println("7. 학생서비스 바로가기");
        System.out.println("8. 종료");
    }
}
