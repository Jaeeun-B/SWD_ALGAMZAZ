// FavoritesManager.java
package samplepack;

import java.util.ArrayList;
import java.util.List;

public class FavoritesManager {
    private final List<String> favorites = new ArrayList<>();

    public void setFavoriteTimetable(String Route, String Stop) {
        favorites.add(Route + " > " + Stop);
        System.out.println("\n" + Route + " > " + Stop + " 시간표를 즐겨찾기로 설정했습니다.\n==============");
    }
}
