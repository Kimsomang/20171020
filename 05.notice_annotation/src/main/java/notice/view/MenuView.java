package notice.view;

import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import notice.controller.Controller;
import notice.model.Notice;

@Component("menu")
@Scope("prototype")
public class MenuView {
	Scanner scan = new Scanner(System.in);
	
	@Autowired
	Controller controller;
	
	public int mainMenu() {
		System.out.println("********************");
		System.out.println("1. 로그인");
		System.out.println("2. 공지조회");
		System.out.println("3. 종료");
		System.out.println("********************");
		System.out.print(">> ");
		int num = scan.nextInt();
		scan.nextLine();
		return num;
	}
	
	public String loginMenu() {
		System.out.println("********************");
		System.out.print("아이디 : ");
		String user = scan.nextLine();
		System.out.print("비밀번호 : ");
		String pass = scan.nextLine();
		String msg = controller.loginAction(user, pass);
		if(msg.contains("확인")) {
			System.out.println(msg);
			return null;
		} else {
			System.out.println(msg);
			return user;
		}
	}
	
	public void selectNotices() {
		System.out.println("********************");
		ArrayList<Notice> notices = controller.listAction();
		for(int i = 0; i < notices.size(); i++) {
			System.out.println(notices.get(i));
		}
	}
	
	public int resultMenu(String user) {
		System.out.println("********************");
		System.out.println(user + " 님 환영합니다.");
		System.out.println("********************");
		System.out.println("1. 공지글쓰기");
		System.out.println("2. 공지글조회");
		System.out.println("3. 공지상세조회");
		System.out.println("4. 공지수정");
		System.out.println("5. 공지삭제");
		System.out.println("6. 로그아웃");
		System.out.println("********************");
		System.out.print(">> ");
		int n = scan.nextInt();
		scan.nextLine();
		return n;
	}
	
	public void insertNotice(String user) {
		System.out.println("********************");
		System.out.println("작성자 : " + user);
		System.out.println("********************");
		System.out.print("제목 : ");
		String title = scan.nextLine();
		System.out.println("********************");
		System.out.print("내용 : ");
		String cont = scan.nextLine();
		System.out.println("********************");
		Notice notice = new Notice(0, user, null, title, cont);
		System.out.println(controller.saveAction(notice));
	}
	
	public void selectNotice() {
		System.out.println("********************");
		System.out.print("조회할 공지글번호 : ");
		int n = scan.nextInt();
		System.out.println("********************");
		Notice notice = controller.viewAction(n);
		System.out.println(notice+" : "+notice.getContent());
	}
	
	public void updateNotice() {
		System.out.println("********************");
		System.out.print("수정할 공지글번호 : ");
		int n = scan.nextInt();
		scan.nextLine();
		Notice notice = controller.viewAction(n);
		System.out.println("********************");
		System.out.println("제목 : " + notice.getTitle());
		System.out.println("수정할 제목을 입력하세요(미입력시 기존제목으로 저장됩니다)");
		System.out.print("제목 : ");
		String title = scan.nextLine();
		System.out.println("********************");
		System.out.println("내용 : " + notice.getContent());
		System.out.println("수정할 내용을 입력하세요(미입력시 기존내용으로 저장됩니다)");
		System.out.print("내용 : ");
		String cont = scan.nextLine();
		Notice newNotice = new Notice(n, null, null, title, cont);
		if(title == null || title.trim().length() == 0) {
			newNotice.setTitle(notice.getTitle());
		}
		if(cont == null || cont.trim().length() == 0) {
			newNotice.setContent(notice.getContent());
		}
		System.out.println(controller.updateAction(newNotice));
	}
	
	public void deleteNotice() {
		System.out.println("********************");
		System.out.print("삭제할 공지글번호 : ");
		int n = scan.nextInt();
		System.out.println(controller.deleteAction(n));
	}
}
