// FavoritesManager.java
package samplepack;

import java.util.ArrayList;
import java.util.List;

public class FavoritesManager {
    private final List<String> favorites = new ArrayList<>();

    // 즐겨찾기 추가
    public void setFavoriteTimetable(String Route, String Stop) {
        favorites.add(Route + " > " + Stop);
        System.out.println("\n✅ " + Route + " > " + Stop + " 시간표를 즐겨찾기로 설정했습니다.\n==============");
    }

    // 즐겨찾기 목록 출력
    public void showFavorites() {
        System.out.println("\n⭐ 즐겨찾기한 시간표 목록");
        if (favorites.isEmpty()) {
            System.out.println("- 등록된 즐겨찾기가 없습니다.");
        } else {
            for (int i = 0; i < favorites.size(); i++) {
                System.out.println((i + 1) + ". " + favorites.get(i));
            }
        }
        System.out.println("==============\n");
    }
}
