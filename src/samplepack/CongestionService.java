// CongestionService.java
package samplepack;

import java.util.*;
import java.time.*;

public class CongestionService {
    private String lastReportedCongestion = "~";
    private LocalDateTime lastReportedTime = null;

    private static final List<LocalTime[]> CLASS_PERIODS = List.of(
            new LocalTime[]{LocalTime.of(8, 0), LocalTime.of(9, 15)},
            new LocalTime[]{LocalTime.of(9, 30), LocalTime.of(10, 45)},
            new LocalTime[]{LocalTime.of(11, 0), LocalTime.of(12, 15)},
            new LocalTime[]{LocalTime.of(12, 30), LocalTime.of(13, 45)},
            new LocalTime[]{LocalTime.of(14, 0), LocalTime.of(15, 15)},
            new LocalTime[]{LocalTime.of(15, 30), LocalTime.of(16, 45)},
            new LocalTime[]{LocalTime.of(17, 0), LocalTime.of(18, 15)}
    );

    // 1. 실시간 혼잡도 출력
    public String showcurrentCongestion(String Route, String Stop) {
        if (lastReportedTime != null &&
                LocalDateTime.now().isBefore(lastReportedTime.plusMinutes(10))) {
            return lastReportedCongestion + " (제보 기준)";
        }

        int nowHour = LocalTime.now().getHour();
        return predictCongestion(Route, Stop, nowHour);
    }

    // 2. 예정된 시간에 따른 예상 혼잡도 안내
    public void predict(String Route, String Stop) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n예정된 탑승 시간(0~23시)을 입력하세요 >> ");

        int hour;
        try {
            hour = sc.nextInt();
            if (hour < 0 || hour > 23) {
                System.out.println("잘못된 시간입니다. 0~23 사이의 정수를 입력해주세요.\n");
                return;
            }
        } catch (Exception e) {
            System.out.println("숫자를 입력해주세요.\n");
            sc.nextLine(); // 버퍼 비우기
            return;
        }

        String predicted = predictCongestion(Route, Stop, hour);

        System.out.println("\n[예상 혼잡도 안내]");
        System.out.println("노선: " + Route + " | 정류장: " + Stop);
        System.out.println(hour + "시 기준 예상 혼잡도: " + predicted + "\n");
    }

    // 3. 사용자 제보 기반 혼잡도 기록 (10분 유효)
    public void report(String Route, String Stop) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n[혼잡도 제보]");
        System.out.println("- 여유 : 아무 입력 없이 Enter");
        System.out.println("- 보통 : y 입력 후 Enter 1회");
        System.out.println("- 혼잡 : y 입력 후 Enter 2회\n");

        int pressCount = 0;
        for (int i = 1; i <= 2; i++) {
            System.out.print("버튼 입력 " + i + " >> ");
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("y")) {
                pressCount++;
            }
        }

        lastReportedCongestion = switch (pressCount) {
            case 0 -> "여유";
            case 1 -> "보통";
            default -> "혼잡";
        };

        lastReportedTime = LocalDateTime.now();

        System.out.println("\n[제보 완료]");
        System.out.println("노선: " + Route + " | 정류장: " + Stop);
        System.out.println("제보된 혼잡도: " + lastReportedCongestion + "\n(해당 정보는 10분간 유지됩니다.)");
    }

    // 가라데이터 기반 예측 혼잡도
    private String predictCongestion(String route, String stop, int hour) {
        LocalTime time = LocalTime.of(hour, 0);
        DayOfWeek today = LocalDate.now().getDayOfWeek();

        if (route.equals("연구협력관행")) {
            if (today == DayOfWeek.SATURDAY || today == DayOfWeek.SUNDAY)
                return "운휴일";
            if ((time.isBefore(LocalTime.of(7, 50))) ||
                    (time.isAfter(LocalTime.of(11, 50)) && time.isBefore(LocalTime.of(13, 0))) ||
                    (time.isAfter(LocalTime.of(21, 0))))
                return "운휴시간";
            if (isInCongestionZone(time)) return "혼잡";
            if (hour >= 8 && hour <= 19) return "보통";
            return "여유";
        } else if (route.equals("한우리집행")) {
            if (today == DayOfWeek.SATURDAY || today == DayOfWeek.SUNDAY)
                return "운휴일";
            if ((time.isBefore(LocalTime.of(8, 25))) ||
                    (time.isAfter(LocalTime.of(10, 45)) && time.isBefore(LocalTime.of(13, 25))) ||
                    (time.isAfter(LocalTime.of(16, 5))))
                return "운휴시간";
            if (isInCongestionZone(time)) return "혼잡";
            if (hour >= 8 && hour <= 19) return "보통";
            return "여유";
        } else if (route.contains("기숙사") && route.contains("야간")) {
            if (today == DayOfWeek.SUNDAY) return "운휴일";
            if (today == DayOfWeek.SATURDAY) return "보통";
            if (hour == 21) {
                if (stop.equals("조형대삼거리")) return "혼잡";
                if (stop.equals("포스코관(야간)")) return "보통";
                if (stop.equals("한우리집(주차장)")) return "여유";
                return "여유";
            } else if (hour >= 22 && hour <= 23) {
                return "혼잡";
            } else {
                return "운휴시간";
            }
        }

        return "정보 없음";
    }

    private boolean isInCongestionZone(LocalTime time) {
        for (LocalTime[] period : CLASS_PERIODS) {
            LocalTime before = period[0].minusMinutes(15);
            LocalTime after = period[1].plusMinutes(15);
            if (!time.isBefore(before) && !time.isAfter(after)) {
                return true;
            }
        }
        return false;
    }
}