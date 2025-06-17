// NotificationPage.java
package samplepack;

import java.util.ArrayList;
import java.util.List;

public class NotificationPage {
    private final List<String> notificationList = new ArrayList<>();

    public void showAllNotifications() {
        if (notificationList.isEmpty()) {
            System.out.println("\n현재 알림이 없습니다.\n");
        } else {
            System.out.println("\n[알림 목록]");
            for (String note : notificationList) {
                System.out.println("- " + note);
            }
        }
    }
}
