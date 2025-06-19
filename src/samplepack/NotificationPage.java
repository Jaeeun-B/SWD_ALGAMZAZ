// NotificationPage.java
package samplepack;

import java.util.ArrayList;
import java.util.List;

public class NotificationPage {
    private final List<String> fixedNotices = new ArrayList<>();
    private final List<String> occasionalNotices = new ArrayList<>();

    public NotificationPage() {
        loadFixedNotices();
    }

    private void loadFixedNotices() {
        fixedNotices.add("1. 연구협력관행 노선(주간), 한우리집행 노선은 평일에만 운행합니다.");
        fixedNotices.add("2. 연구협력관행 노선(야간) 미운행일:\n   - 일요일, 신정, 성탄절, 근로자의 날, 본교 창립기념일(5/31), 추석연휴, 여름/겨울방학 정기운휴\n   * 기타 법정 공휴일과 대체 공휴일은 운행");
        fixedNotices.add("3. 캠퍼스 내 도로 및 차량 사정에 따라 정류장 시간표와 실제 운행시간의 차이가 있을 수 있습니다.");
        fixedNotices.add("4. 교내외 교통상황, 긴급 방역, 폭우/폭설 등의 사유로 인해 운행 지연 및 긴급 중단될 수 있으며,\n   중단 시에는 홈페이지에 공지됩니다.");
    }

    public void addOccasionalNotification(String notification) {
        occasionalNotices.add(notification);
        System.out.println("✅ 수시 공지가 추가되었습니다.");
    }

    public void showAllNotifications() {
        System.out.println("\n📌 [상시 안내사항]");
        for (String notice : fixedNotices) {
            System.out.println(notice);
        }

        System.out.println("\n📢 [수시 공지]");
        if (occasionalNotices.isEmpty()) {
            System.out.println("- 현재 등록된 수시 공지가 없습니다.");
        } else {
            for (String occ : occasionalNotices) {
                System.out.println("- " + occ);
            }
        }

        System.out.println("\n※ 담당: 총무팀 (T. 3277-3300)");
        System.out.println("========================================\n");
    }
}
