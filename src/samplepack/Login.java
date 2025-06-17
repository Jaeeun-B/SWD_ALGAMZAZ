// Login.java
package samplepack;

import java.util.Scanner;

public class Login {
    String ID;
    String Password;

    public void selectMethod(Scanner sc) {
        System.out.println("\n========== [LOGIN] ==========");
        System.out.println("1: 유레카 로그인");
        System.out.println("2: 외부인 로그인");
        System.out.print("로그인 방법을 선택하세요>> ");
        int choice = sc.nextInt();
        doLogin(sc);
    }

    public void doLogin(Scanner sc) {
        System.out.print("\n아이디: ");
        ID = sc.next();
        System.out.print("비밀번호: ");
        Password = sc.next();
        System.out.println("로그인 완료\n");
    }
}