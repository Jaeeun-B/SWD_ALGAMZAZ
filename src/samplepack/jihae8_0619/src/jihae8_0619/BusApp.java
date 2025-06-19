package jihae8_0619;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class BusApp extends JFrame {
	CardLayout cards;
	JPanel mainPanel;
	JPanel homePanel, detailPanel;
	String currentRoute = "";

	public BusApp() {
		setTitle("노선 안내 기능");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 800);
		setLocationRelativeTo(null);

		cards = new CardLayout();
		mainPanel = new JPanel(cards);

		// 홈 화면
		homePanel = new JPanel();
		homePanel.setLayout(new BorderLayout());

		JLabel titleLabel = new JLabel("버스 노선도");
		titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// 홈 이미지
		ImageIcon icon = null;
		URL imageUrl = getClass().getResource("/resources/images/busApp.png");
		if (imageUrl != null) {
			ImageIcon originalIcon = new ImageIcon(imageUrl);
			Image originalImage = originalIcon.getImage();
			Image resizedImage = originalImage.getScaledInstance(500, 480, Image.SCALE_SMOOTH);
			icon = new ImageIcon(resizedImage);
		} else {
			System.err.println("이미지 파일을 찾을 수 없습니다: /images/busApp.png");
			icon = new ImageIcon(); // 빈 이미지 방지용
		}
		JLabel imageLabel = new JLabel(icon);
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// 노선 선택 버튼
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
		JButton btn1 = new JButton("한우리집행");
		JButton btn2 = new JButton("연구협력관행");
		JButton btn3 = new JButton("기숙사야간행");

		btn1.addActionListener(e -> showDetail("한우리집행", "정문 → 포스코관 → 공대삼거리 → 한우리집"));
		btn2.addActionListener(e -> showDetail("연구협력관행", "정문 → 포스코관 → 공대삼거리 → 기숙사삼거리 → 연구협력관"));
		btn3.addActionListener(e -> showDetail("기숙사야간행", "정문 → 포스코관 → 한우리집 → 이하우스"));

		buttonPanel.add(btn1);
		buttonPanel.add(btn2);
		buttonPanel.add(btn3);

		homePanel.add(titleLabel, BorderLayout.NORTH);
		homePanel.add(imageLabel, BorderLayout.CENTER);
		homePanel.add(buttonPanel, BorderLayout.SOUTH);

		// 상세 정보 화면
		detailPanel = new JPanel();
		detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
		detailPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		mainPanel.add(homePanel, "HOME");
		mainPanel.add(detailPanel, "DETAIL");

		add(mainPanel);
		setVisible(true);
	}

	private void showDetail(String routeName, String routeInfo) {
		currentRoute = routeName;
		detailPanel.removeAll();

		JLabel title = new JLabel(routeName + " 노선 안내");
		title.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		JTextArea text = new JTextArea(routeInfo);
		text.setEditable(false);
		text.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		text.setLineWrap(true);
		text.setWrapStyleWord(true);

		detailPanel.add(title);
		detailPanel.add(Box.createVerticalStrut(10));
		detailPanel.add(text);

		// 🔽 한우리집행 노선 이미지 추가
		if (routeName.equals("연구협력관행")) {
			URL hwImageUrl = getClass().getResource("/resources/images/hanuri.png");
			if (hwImageUrl != null) {
				ImageIcon hwOriginal = new ImageIcon(hwImageUrl);
				Image resized = hwOriginal.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
				ImageIcon resizedIcon = new ImageIcon(resized);
				JLabel hwImageLabel = new JLabel(resizedIcon);
				hwImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				detailPanel.add(Box.createVerticalStrut(10));
				detailPanel.add(hwImageLabel);
			} else {
				System.err.println("한우리집행 이미지가 없습니다.");
			}
		}
		
		if (routeName.equals("한우리집행")) {
			URL hwImageUrl = getClass().getResource("/resources/images/yeon.png");
			if (hwImageUrl != null) {
				ImageIcon hwOriginal = new ImageIcon(hwImageUrl);
				Image resized = hwOriginal.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
				ImageIcon resizedIcon = new ImageIcon(resized);
				JLabel hwImageLabel = new JLabel(resizedIcon);
				hwImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				detailPanel.add(Box.createVerticalStrut(10));
				detailPanel.add(hwImageLabel);
			} else {
				System.err.println("한우리집행 이미지가 없습니다.");
			}
		}
		
		if (routeName.equals("기숙사야간행")) {
			URL hwImageUrl = getClass().getResource("/resources/images/night.png");
			if (hwImageUrl != null) {
				ImageIcon hwOriginal = new ImageIcon(hwImageUrl);
				Image resized = hwOriginal.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
				ImageIcon resizedIcon = new ImageIcon(resized);
				JLabel hwImageLabel = new JLabel(resizedIcon);
				hwImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				detailPanel.add(Box.createVerticalStrut(10));
				detailPanel.add(hwImageLabel);
			} else {
				System.err.println("한우리집행 이미지가 없습니다.");
			}
		}

		detailPanel.add(Box.createVerticalStrut(20));

		JButton btnJungmun = new JButton("정문 시간표 보기");
		JButton btnPosco = new JButton("포스코관 시간표 보기");
		JButton btnSamgeori = new JButton("공대삼거리 시간표 보기");
		JButton btnGisuksa = new JButton("기숙사삼거리 시간표 보기");
		JButton btnHanwoori = new JButton("한우리집 시간표 보기");
		JButton backBtn = new JButton("뒤로가기");

		btnJungmun.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPosco.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSamgeori.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnGisuksa.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnHanwoori.setAlignmentX(Component.CENTER_ALIGNMENT);
		backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

		btnJungmun.addActionListener(e -> showStopTime("정문"));
		btnPosco.addActionListener(e -> showStopTime("포스코관"));
		btnSamgeori.addActionListener(e -> showStopTime("공대삼거리"));
		btnGisuksa.addActionListener(e -> showStopTime("기숙사삼거리"));
		btnHanwoori.addActionListener(e -> showStopTime("한우리집"));
		backBtn.addActionListener(e -> cards.show(mainPanel, "HOME"));

		detailPanel.add(btnJungmun);
		detailPanel.add(btnPosco);
		detailPanel.add(btnSamgeori);
		detailPanel.add(btnGisuksa);
		detailPanel.add(btnHanwoori);
		detailPanel.add(Box.createVerticalStrut(20));
		detailPanel.add(backBtn);

		detailPanel.revalidate();
		detailPanel.repaint();

		cards.show(mainPanel, "DETAIL");
	}

	private void showStopTime(String stopName) {
		String message = "";

		switch (currentRoute) {
			case "한우리집행":
				message = switch (stopName) {
					case "정문" -> "08:25~10:45: 05,25,45\n11~13:20 : 점심시간 운휴\n13:25~16:05 : 05,25,45 ";
					case "포스코관" -> "08:27~10:47: 07,27,47\n11:05~13:20 : 점심시간 운휴\n13:27~16:07 : 07,27,47";
					case "공대삼거리" -> "08:29~10:49 : 09,29,49\n11:05~13:20 : 점심시간 운휴\n13:29~16:09 : 09,29,49";
					case "한우리집" -> "08:35~10:55 : 15,35,55\n11:05~13:20 : 점심시간 운휴\n13:35~16:15 : 15,35,55";
					case "기숙사삼거리" -> "해당 노선은 기숙사삼거리를 경유하지 않습니다.";
					default -> "해당 정류장의 정보가 없습니다.";
				};
				break;
			case "연구협력관행":
				message = switch (stopName) {
					case "정문" -> "07:50~11:00 : 00,10,15,20,30,35,40,50,55\n11:10~11:50 : 00,10,20,30,40,50\n11:50~12:59 : 점심시간 운휴\n13:00 ~ 15:40 : 00,10,20,30,40,50\n15:50~21:00 : 00,01,15,20,30,35,40,50,55 ";
					case "포스코관" -> "07:52~11:52 : 02,12,17,22,32,37,42,52,57\n11:12~11:52 : 02,12,22,32,42,52\n11:53~13:01 : 점심시간 운휴\n13:02~15:42 : 02,12,22,32,42,52\n15:52~19:02 : 02,12,17,22,32,37,42,52,57";
					case "공대삼거리" -> "07:54~11:04 : 04,14,19,24,34,44,54,59\n11:14~11:54 : 04,14,24,34,44,54\n12:00~12:59 : 점심시간 운휴\n13:04~15:44 : 04,14,24,34,44,54\n15:54~19:04 : 04,14,19,24,34,39,44,54,59";
					case "기숙사삼거리" -> "08:01~11:11 : 01,06,11,21,26,31,41,46,51\n11:21~21:11 : 01,11,21,31,41,51\n12:12~13:10 : 점심시간 운휴\n13:11~15:51 : 01,11,21,31,41,51\n16:01~19:11 : 01,06,11,21,26,31,41,46,51";
					case "한우리집" -> "해당 노선은 한우리집을 경유하지 않습니다.";
					default -> "해당 정류장의 정보가 없습니다.";
				};
				break;
			case "기숙사야간행":
				message = switch (stopName) {
					case "정문" -> "월~금 : 21:10~23:40(10분 간격)";
					case "포스코관" -> "월~금 : 21:12~23:42(10분 간격)";
					case "공대삼거리" -> "해당 노선은 공대삼거리를 경유하지 않습니다.";
					case "기숙사삼거리" -> "해당 노선은 기숙사삼거리를 경유하지 않습니다.";
					case "한우리집" -> "월~금 : 21:23~23:33(10분 간격)\n토 : 21:23~23:44(10분 간격)";
					default -> "해당 정류장의 정보가 없습니다.";
				};
				break;
			default:
				message = "노선 정보가 없습니다.";
		}

		JOptionPane.showMessageDialog(this, stopName + " 시간표\n\n" + message, "정류장 시간표", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void main(String[] args) {
		new BusApp();
	}
}