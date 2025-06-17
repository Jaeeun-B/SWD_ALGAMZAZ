// BusRoutePage.java
package samplepack;

import java.time.*;
import java.util.*;

public class BusRoutePage {
    String Route;
    String Stop;

    static Map<Integer, String> routeMap = new HashMap<>();
    static Map<Integer, String> stopMap = new HashMap<>();

    static {
        routeMap.put(1, "연구협력관행");
        routeMap.put(2, "한우리집행");

        stopMap.put(1, "정문");
        stopMap.put(2, "포스코관(상행)");
        stopMap.put(3, "공대삼거리(상행)");
        stopMap.put(4, "한우리집(상행)");
        stopMap.put(5, "기숙사삼거리(상행)");
        stopMap.put(6, "연구협력관");
        stopMap.put(7, "기숙사삼거리(하행)");
        stopMap.put(8, "한우리집(하행)");
        stopMap.put(9, "공대삼거리(하행)");
        stopMap.put(10, "포스코관(하행)");
        stopMap.put(11, "E-HOUSE");
        stopMap.put(12, "한우리집(주차장)");
        stopMap.put(13, "포스코관(야간)");
        stopMap.put(14, "조형대삼거리");
    }

    private boolean isDormNightRouteInServiceNow() {
        LocalTime now = LocalTime.now();
        DayOfWeek day = LocalDate.now().getDayOfWeek();
        return (day == DayOfWeek.SATURDAY && now.isAfter(LocalTime.of(19, 10)) && now.isBefore(LocalTime.of(23, 40))) ||
                ((day != DayOfWeek.SUNDAY) && now.isAfter(LocalTime.of(21, 10)) && now.isBefore(LocalTime.of(23, 40)));
    }

    public void selectRoute(Scanner sc) {
        System.out.println("1: 연구협력관행");
        System.out.println("2: 한우리집행");
        System.out.print("노선을 선택하세요>> ");
        int input = sc.nextInt();

        if (!routeMap.containsKey(input)) {
            System.out.println("잘못된 노선 번호입니다.\n");
            return;
        }

        Route = routeMap.get(input);
        boolean isNightRoute = false;

        if (Route.equals("연구협력관행")) {
            if (isDormNightRouteInServiceNow()) {
                System.out.println("💡 현재는 야간 운행 시간대입니다.");
            } else {
                System.out.println("💡 현재는 주간 운행 시간대입니다.");
            }

            System.out.print("야간 노선인가요? (y/n) >> ");
            sc.nextLine(); // buffer clear
            isNightRoute = sc.nextLine().trim().equalsIgnoreCase("y");
        }

        LocalDate today = LocalDate.now();
        if (!HolidayChecker.isOperatingDay(today, isNightRoute)) {
            System.out.println("\n🚫 오늘은 해당 노선이 운행되지 않습니다. (운휴일)");
            System.out.println("▶ 노선: " + Route + " | 날짜: " + today + "\n");
            return;
        }

        selectStop(sc);
    }

    public void selectStop(Scanner sc) {
        System.out.println("\n[정류장 목록]");
        stopMap.forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.print("정류장 번호를 입력하세요>> ");
        int input = sc.nextInt();
        Stop = stopMap.get(input);

        handleStopMenu(sc);
    }

    public void handleStopMenu(Scanner sc) {
        TimetableViewer timetableViewer = new TimetableViewer();
        CongestionService congestionService = new CongestionService();

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
            switch (choice) {
                case 1 -> timetableViewer.showTimetable(Route, Stop);
                case 2 -> congestionService.predict(Route, Stop);
                case 3 -> congestionService.report(Route, Stop);
                case 4 -> new FavoritesManager().setFavoriteTimetable(Route, Stop);
                case 5 -> { return; }
            }
        }
    }
}
