// BusRoutePage.java
package samplepack;

import java.time.*;
import java.util.*;

public class BusRoutePage {
    private String Route;
    private String Stop;

    private final TimetableViewer timetableViewer;
    private final CongestionService congestionService;
    private final FavoritesManager favoritesManager;

    public BusRoutePage(TimetableViewer timetableViewer, CongestionService congestionService, FavoritesManager favoritesManager) {
        this.timetableViewer = timetableViewer;
        this.congestionService = congestionService;
        this.favoritesManager = favoritesManager;
    }

    private static final Map<Integer, String> routeMap = Map.of(
            1, "연구협력관행",
            2, "한우리집행",
            3, "기숙사(야간)"
    );

    private static final Map<Integer, String> stopMap = Map.ofEntries(
            Map.entry(1, "정문"), Map.entry(2, "포스코관(상행)"), Map.entry(3, "공대삼거리(상행)"),
            Map.entry(4, "한우리집(상행)"), Map.entry(5, "기숙사삼거리(상행)"), Map.entry(6, "연구협력관"),
            Map.entry(7, "기숙사삼거리(하행)"), Map.entry(8, "한우리집(하행)"), Map.entry(9, "공대삼거리(하행)"),
            Map.entry(10, "포스코관(하행)"), Map.entry(11, "E-HOUSE"), Map.entry(12, "한우리집(주차장)"),
            Map.entry(13, "포스코관(야간)"), Map.entry(14, "조형대삼거리")
    );

    private boolean isDormNightRouteInServiceNow() {
        LocalTime now = LocalTime.now();
        DayOfWeek day = LocalDate.now().getDayOfWeek();
        return (day == DayOfWeek.SATURDAY && isBetween(now, LocalTime.of(19, 10), LocalTime.of(23, 40))) ||
                (day != DayOfWeek.SUNDAY && isBetween(now, LocalTime.of(21, 10), LocalTime.of(23, 40)));
    }

    private boolean isBetween(LocalTime now, LocalTime start, LocalTime end) {
        return !now.isBefore(start) && now.isBefore(end);
    }

    public void selectRoute(Scanner sc) {
        routeMap.forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.print("노선을 선택하세요>> ");
        int input = sc.nextInt();
        sc.nextLine();

        if (!routeMap.containsKey(input)) {
            System.out.println("⚠ 잘못된 노선 번호입니다.\n");
            return;
        }

        Route = routeMap.get(input);
        boolean isNightRoute = false;

        if (Route.equals("연구협력관행")) {
            System.out.println("💡 현재는 " + (isDormNightRouteInServiceNow() ? "야간" : "주간") + " 운행 시간대입니다.");
            System.out.print("야간 노선인가요? (y/n) >> ");
            isNightRoute = sc.nextLine().trim().equalsIgnoreCase("y");
        }

        if (!HolidayChecker.isOperatingDay(LocalDate.now(), isNightRoute)) {
            System.out.println("\n🚫 오늘은 해당 노선이 운행되지 않습니다. (운휴일)");
            System.out.printf("▶ 노선: %s | 날짜: %s\n\n", Route, LocalDate.now());
            return;
        }

        selectStop(sc);
    }

    public void selectStop(Scanner sc) {
        System.out.println("\n[정류장 목록]");
        stopMap.forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.print("정류장 번호를 입력하세요>> ");
        int input = sc.nextInt();
        sc.nextLine();

        Stop = stopMap.getOrDefault(input, null);
        if (Stop == null) {
            System.out.println("⚠ 존재하지 않는 정류장입니다.");
            return;
        }

        handleStopMenu(sc);
    }

    public void selectRouteByManual(int menuNum, Scanner sc) {
        if (!routeMap.containsKey(menuNum)) {
            System.out.println("⚠ 존재하지 않는 노선입니다.");
            return;
        }

        Route = routeMap.get(menuNum);
        System.out.println("노선: " + Route);
        System.out.println("해당 노선으로 이동합니다...\n");

        boolean isNightRoute = false;
        if (Route.equals("연구협력관행")) {
            System.out.println("💡 현재는 " + (isDormNightRouteInServiceNow() ? "야간" : "주간") + " 운행 시간대입니다.");
            System.out.print("야간 노선인가요? (y/n) >> ");
            isNightRoute = sc.nextLine().trim().equalsIgnoreCase("y");
        }

        if (!HolidayChecker.isOperatingDay(LocalDate.now(), isNightRoute)) {
            System.out.println("\n🚫 오늘은 해당 노선이 운행되지 않습니다. (운휴일)");
            System.out.printf("▶ 노선: %s | 날짜: %s\n\n", Route, LocalDate.now());
            return;
        }

        selectStop(sc);
    }

    public void handleStopMenu(Scanner sc) {
        System.out.println("==============");
        System.out.println("정류장: " + Stop);
        System.out.println("[시간표] " + timetableViewer.showArrivalInfo(Route, Stop));
        System.out.println("[혼잡도] " + congestionService.showcurrentCongestion(Route, Stop));

        while (true) {
            System.out.println("\n1: 전체 시간표 보기");
            System.out.println("2: 예상 혼잡도 보기");
            System.out.println("3: 혼잡도 제보하기");
            System.out.println("4: 즐겨찾기 추가하기");
            System.out.println("5: 메인화면으로 가기");
            System.out.print("번호를 선택하세요>> ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> timetableViewer.showTimetable(Route, Stop);
                case 2 -> congestionService.predict(Route, Stop);
                case 3 -> congestionService.reportWithInput(sc);
                case 4 -> favoritesManager.addFavorite(sc);  // ✅ 즐겨찾기 추가 (setFavoriteTimetable 제거)
                case 5 -> { return; }
                default -> System.out.println("⚠ 올바른 번호를 선택해주세요.");
            }
        }
    }
}
