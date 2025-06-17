package samplepack;

import java.util.Scanner;

public class CLIApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Login login = new Login();
        BusRoutePage busRoutePage = new BusRoutePage();
        NotificationPage notificationPage = new NotificationPage();

        while (true) {
            System.out.println("\n====== 셔틀버스 안내 시스템 ======");
            System.out.println("1: 로그인");
            System.out.println("2: 노선 선택");
            System.out.println("3: 알림 확인");
            System.out.println("4: 종료");
            System.out.println("=============================");
            System.out.print("\n메뉴 번호를 입력하세요>> ");

            int num;
            try {
                num = sc.nextInt();
            } catch (Exception e) {
                System.out.println("⚠ 숫자를 입력해주세요.\n");
                sc.nextLine(); // 버퍼 비우기
                continue;
            }

            System.out.println("\n=============================");

            switch (num) {
                case 1 -> login.selectMethod(sc);
                case 2 -> busRoutePage.selectRoute(sc);
                case 3 -> notificationPage.showAllNotifications();
                case 4 -> {
                    System.out.println("프로그램을 종료합니다. -ALGAMZAZ");
                    return;
                }
                default -> System.out.println("⚠ 올바른 메뉴 번호를 입력해주세요.\n");
            }
        }
    }
}
