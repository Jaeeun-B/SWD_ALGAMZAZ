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

    private static final Map<String, List<String>> STOP_MAP_BY_DIRECTION = Map.of(
            "상행", List.of("정문", "포스코관(상행)", "공대삼거리(상행)", "한우리집(상행)", "기숙사삼거리(상행)", "연구협력관"),
            "하행", List.of("연구협력관", "기숙사삼거리(하행)", "한우리집(하행)", "공대삼거리(하행)", "포스코관(하행)", "정문", "E-HOUSE"),
            "야간", List.of("한우리집(주차장)", "포스코관(야간)", "조형대삼거리", "기숙사삼거리(하행)")
    );

    public void printCongestionLegend() {
        System.out.println("\n[혼잡도 기준]");
        System.out.println("\uD83D\uDFE2 여유 : 좌석에 여유가 있어 앉을 수 있어요.");
        System.out.println("\uD83D\uDFE1 보통 : 모든 좌석이 찼지만 서서 탑승할 수 있어요.");
        System.out.println("\uD83D\uDD34 혼잡 : 차량이 많이 붐벼 탑승이 어려울 수 있어요.\n");
    }

    public String showcurrentCongestion(String route, String stop) {
        if (lastReportedTime != null && LocalDateTime.now().isBefore(lastReportedTime.plusMinutes(10))) {
            return lastReportedCongestion + " (제보 기준)";
        }
        LocalTime now = LocalTime.now();
        return predictCongestion(route, stop, now.getHour(), now.getMinute(), LocalDate.now().getDayOfWeek());
    }

    public void predict(String route, String stop) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n[예상 혼잡도 조회]");
        printCongestionLegend();

        DayOfWeek day = null;
        while (day == null) {
            System.out.print("요일을 입력하세요 (예: 월/화/수/목/금/토/일): ");
            day = parseDay(sc.nextLine().trim());
            if (day == null) System.out.println("⚠ 올바른 요일을 입력해주세요.");
        }

        boolean isAM = false;
        while (true) {
            System.out.print("오전 또는 오후? (am/pm): ");
            String meridiem = sc.nextLine().trim().toLowerCase();
            if (meridiem.equals("am")) { isAM = true; break; }
            if (meridiem.equals("pm")) { break; }
            System.out.println("⚠ 'am' 또는 'pm' 중 하나를 입력해주세요.");
        }

        System.out.print("시(hour)를 입력하세요 (1~12): ");
        int hour = sc.nextInt();
        System.out.print("분(minute)을 입력하세요 (0~59): ");
        int minute = sc.nextInt();
        sc.nextLine();

        if (hour < 1 || hour > 12 || minute < 0 || minute > 59) {
            System.out.println("⚠ 유효한 시간(1~12)과 분(0~59)을 입력해주세요.");
            return;
        }

        int hour24 = isAM ? (hour == 12 ? 0 : hour) : (hour == 12 ? 12 : hour + 12);
        String predicted = predictCongestion(route, stop, hour24, minute, day);
        System.out.println("\n[예상 혼잡도 안내]");
        System.out.printf("노선: %s | 정류장: %s\n", route, stop);
        System.out.printf("요일: %s | 시간: %s %02d:%02d\n", day, (isAM ? "오전" : "오후"), hour, minute);
        System.out.println("예상 혼잡도: " + congestionToSymbol(predicted) + " " + predicted + "\n");
    }

    public void report(String route, String stop) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n[혼잡도 제보]");
        System.out.println("- 여유 : 아무 입력 없이 Enter");
        System.out.println("- 보통 : y 입력 후 Enter 1회");
        System.out.println("- 혼잡 : y 입력 후 Enter 2회\n");

        int pressCount = 0;
        for (int i = 1; i <= 2; i++) {
            System.out.print("버튼 입력 " + i + " >> ");
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("y")) pressCount++;
        }

        lastReportedCongestion = switch (pressCount) {
            case 0 -> "여유";
            case 1 -> "보통";
            default -> "혼잡";
        };
        lastReportedTime = LocalDateTime.now();

        System.out.println("\n[제보 완료]");
        System.out.println("노선: " + route + " | 정류장: " + stop);
        System.out.println("제보된 혼잡도: " + lastReportedCongestion + "\n(해당 정보는 10분간 유지됩니다.)\n");
    }

    public void reportWithInput(Scanner sc) {
        System.out.println("\n[혼잡도 제보하기]");
        System.out.println("방향을 선택하세요:");
        System.out.println("1. 상행\n2. 하행\n3. 야간");

        String direction = null;
        while (direction == null) {
            System.out.print("번호 입력 >> ");
            direction = switch (sc.nextLine().trim()) {
                case "1" -> "상행";
                case "2" -> "하행";
                case "3" -> "야간";
                default -> {
                    System.out.println("⚠ 잘못된 입력입니다.");
                    yield null;
                }
            };
        }

        List<String> stops = STOP_MAP_BY_DIRECTION.getOrDefault(direction, List.of());
        if (stops.isEmpty()) {
            System.out.println("⚠ 정류장 정보가 없습니다.");
            return;
        }

        System.out.println("\n[정류장 목록]");
        for (int i = 0; i < stops.size(); i++) {
            System.out.println((i + 1) + ". " + stops.get(i));
        }

        String stop = null;
        while (stop == null) {
            System.out.print("정류장 번호 입력 >> ");
            try {
                int idx = Integer.parseInt(sc.nextLine()) - 1;
                if (idx >= 0 && idx < stops.size()) stop = stops.get(idx);
                else System.out.println("⚠ 번호가 범위를 벗어났습니다.");
            } catch (Exception e) {
                System.out.println("⚠ 숫자를 입력해주세요.");
            }
        }

        System.out.print("노선 이름을 입력하세요 (예: 연구협력관행, 한우리집행, 기숙사(야간)) >> ");
        String route = sc.nextLine().trim();
        this.report(route, stop);
    }

    private String predictCongestion(String route, String stop, int hour, int minute, DayOfWeek today) {
        LocalTime time = LocalTime.of(hour, minute);

        if (route.equals("기숙사(야간)")) {
            if (today == DayOfWeek.SUNDAY) return "운휴일";
            if (today == DayOfWeek.SATURDAY && time.isAfter(LocalTime.of(19, 9)) && time.isBefore(LocalTime.of(23, 41))) return "보통";
            if (today.getValue() >= 1 && today.getValue() <= 5) {
                if (time.isAfter(LocalTime.of(21, 29)) && time.isBefore(LocalTime.of(23, 41))) {
                    if (stop.equals("조형대삼거리")) return "혼잡";
                    if (stop.equals("포스코관(야간)")) return "보통";
                    return "여유";
                }
                return "운휴시간";
            }
        }

        if (route.equals("연구협력관행")) {
            if (today == DayOfWeek.SATURDAY || today == DayOfWeek.SUNDAY) return "운휴일";
            if (time.isBefore(LocalTime.of(7, 50)) ||
                    (time.isAfter(LocalTime.of(11, 50)) && time.isBefore(LocalTime.of(13, 0))) ||
                    time.isAfter(LocalTime.of(21, 0))) return "운휴시간";
            if (isInCongestionZone(time)) return "혼잡";
            if (hour >= 8 && hour <= 19) return "보통";
            return "여유";
        }

        if (route.equals("한우리집행")) {
            if (today == DayOfWeek.SATURDAY || today == DayOfWeek.SUNDAY) return "운휴일";
            if (time.isBefore(LocalTime.of(8, 25)) ||
                    (time.isAfter(LocalTime.of(10, 45)) && time.isBefore(LocalTime.of(13, 25))) ||
                    time.isAfter(LocalTime.of(16, 5))) return "운휴시간";
            if (isInCongestionZone(time)) return "혼잡";
            if (hour >= 8 && hour <= 19) return "보통";
            return "여유";
        }

        return "정보 없음";
    }

    private boolean isInCongestionZone(LocalTime time) {
        for (LocalTime[] period : CLASS_PERIODS) {
            if (!time.isBefore(period[0].minusMinutes(15)) && !time.isAfter(period[1].plusMinutes(15))) return true;
        }
        return false;
    }

    private DayOfWeek parseDay(String input) {
        return switch (input) {
            case "월" -> DayOfWeek.MONDAY;
            case "화" -> DayOfWeek.TUESDAY;
            case "수" -> DayOfWeek.WEDNESDAY;
            case "목" -> DayOfWeek.THURSDAY;
            case "금" -> DayOfWeek.FRIDAY;
            case "토" -> DayOfWeek.SATURDAY;
            case "일" -> DayOfWeek.SUNDAY;
            default -> null;
        };
    }

    private String congestionToSymbol(String level) {
        return switch (level) {
            case "여유" -> "\uD83D\uDFE2";
            case "보통" -> "\uD83D\uDFE1";
            case "혼잡" -> "\uD83D\uDD34";
            default -> "⚪";
        };
    }
}
