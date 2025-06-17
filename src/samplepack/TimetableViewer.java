// TimetableViewer.java
package samplepack;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimetableViewer {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public String showArrivalInfo(String Route, String Stop) {
        LocalTime now = LocalTime.now();

        if (Route.equals("연구협력관행")) {
            if (now.isAfter(LocalTime.of(7, 50)) && now.isBefore(LocalTime.of(11, 50))) {
                return nextBusTime(now, 10) + " 도착 예정";
            } else if (now.isAfter(LocalTime.of(13, 0)) && now.isBefore(LocalTime.of(21, 0))) {
                return nextBusTime(now, 10) + " 도착 예정";
            }
        } else if (Route.equals("한우리집행")) {
            if (now.isAfter(LocalTime.of(8, 25)) && now.isBefore(LocalTime.of(10, 45))) {
                return nextBusTime(now, 20) + " 도착 예정";
            } else if (now.isAfter(LocalTime.of(13, 25)) && now.isBefore(LocalTime.of(16, 5))) {
                return nextBusTime(now, 20) + " 도착 예정";
            }
        } else if (Route.contains("기숙사")) {
            return nextBusTime(now, 10) + " 도착 예정";
        }

        return "운행 시간 아님";
    }

    private String nextBusTime(LocalTime now, int intervalMinutes) {
        int minute = now.getMinute();
        int nextInterval = ((minute / intervalMinutes) + 1) * intervalMinutes;
        LocalTime next = now.withMinute(0).withSecond(0).withNano(0).plusMinutes(nextInterval);
        return next.format(formatter);
    }

    public void showTimetable(String Route, String Stop) {
        System.out.println("\n[전체 시간표 안내]");
        if (Route.contains("연구협력관행")) {
            System.out.println("■ 운행시간: 오전 07:50 ~ 11:50 / 오후 13:00 ~ 21:00");
            System.out.println("■ 미운행시간: 12:00 ~ 13:00");
            System.out.println("■ 배차간격: 10분");
        } else if (Route.contains("한우리집행")) {
            System.out.println("■ 운행시간: 오전 08:25 ~ 10:45 / 오후 13:25 ~ 16:05");
            System.out.println("■ 미운행시간: 12:00 ~ 13:00");
            System.out.println("■ 배차간격: 20분");
        } else if (Route.contains("기숙사")) {
            System.out.println("■ 평일 운행시간: 21:10 ~ 23:40");
            System.out.println("■ 토요일 운행시간: 19:10 ~ 23:40");
            System.out.println("■ 배차간격: 10분");
        } else {
            System.out.println("해당 노선의 시간표 정보가 없습니다.");
        }
        System.out.println();
    }
}