// FavoritesManager.java
package samplepack;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FavoritesManager {
    private static final int MAX_FAVORITES = 3;
    private final CongestionService congestionService;
    private final List<Favorite> favorites = new ArrayList<>();

    // 생성자에서 CongestionService 주입
    public FavoritesManager(CongestionService congestionService) {
        this.congestionService = congestionService;
    }

    // 내부 클래스: 즐겨찾기 항목
    private static class Favorite {
        String routeName;
        String stopName;
        String direction;

        public Favorite(String routeName, String stopName, String direction) {
            this.routeName = routeName;
            this.stopName = stopName;
            this.direction = direction;
        }

        @Override
        public String toString() {
            return routeName + " - " + stopName + " (" + direction + ")";
        }
    }

    // 즐겨찾기 추가
    public void addFavorite(Scanner sc) {
        if (favorites.size() >= MAX_FAVORITES) {
            System.out.println("⚠ 즐겨찾기는 최대 3개까지 등록할 수 있습니다.");
            return;
        }

        System.out.print("노선 이름을 입력하세요: ");
        String route = sc.nextLine();

        System.out.print("정류장 이름을 입력하세요: ");
        String stop = sc.nextLine();

        System.out.print("방향을 입력하세요 (상행/하행/야간): ");
        String direction = sc.nextLine();

        favorites.add(new Favorite(route, stop, direction));
        System.out.println("✅ 즐겨찾기에 추가되었습니다.");
    }

    // 즐겨찾기 조회 및 혼잡도 출력
    public void showFavorites() {
        if (favorites.isEmpty()) {
            System.out.println("⭐ 즐겨찾기 항목이 없습니다.");
            return;
        }

        System.out.println("⭐ 등록된 즐겨찾기:");
        for (int i = 0; i < favorites.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, favorites.get(i));
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("➡ 확인할 즐겨찾기 번호를 선택하세요 (0: 취소): ");
        int selection = sc.nextInt();
        sc.nextLine();

        if (selection <= 0 || selection > favorites.size()) {
            System.out.println("⛔ 선택을 취소했습니다.");
            return;
        }

        Favorite selected = favorites.get(selection - 1);
        LocalDateTime now = LocalDateTime.now();

        // 1️⃣ 혼잡도 계산
        String congestion = congestionService.showcurrentCongestion(selected.routeName, selected.stopName);
        String symbol = getCongestionSymbol(congestion);

        // 2️⃣ 시간표에서 도착 시간 계산
        TimetableViewer timetableViewer = new TimetableViewer();
        String arrivalInfo = timetableViewer.showArrivalInfo(selected.routeName, selected.stopName);

        // 3️⃣ 출력
        System.out.println("\n정류장: " + selected.stopName);
        System.out.println("[도착 예정 시간] " + arrivalInfo);
        System.out.println("[예상 혼잡도] " + symbol + " " + congestion);
    }


    // 혼잡도 레벨을 이모지로 변환
    private String getCongestionSymbol(String level) {
        return switch (level) {
            case "여유" -> "\uD83D\uDFE2";
            case "보통" -> "\uD83D\uDFE1";
            case "혼잡" -> "\uD83D\uDD34";
            default -> "⚪";
        };
    }

    // (현재 미사용) 향후 시간표 기능 필요 시 구현
    public void setFavoriteTimetable(String route, String stop) {
        // TODO: 시간표 연동이 필요할 경우 구현
    }
}

