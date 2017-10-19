package notice.controller;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import notice.model.LoginDao;
import notice.model.Notice;
import notice.model.NoticeDao;

@Component("controller")
@Scope("prototype")
public class Controller {
	
	@Resource
	private LoginDao loginDao;
	
	@Resource
	private NoticeDao noticeDao;
	
	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}
	
	public Controller() {
//		System.out.println("Controller default constructor");
	}
	
	public Controller(LoginDao loginDao, NoticeDao noticeDao) {
		this.loginDao = loginDao;
		this.noticeDao = noticeDao;
	}
	
	public String loginAction(String user, String pass) {
		String msg = null;

		// boolean check=new LoginDAO().loginCheck(user,pass);
		boolean check = loginDao.loginCheck(user, pass);
		if (check) {
			msg = "로그인 되었습니다.";
		} else {
			msg= "아이디와 비밀번호를 다시 확인해주세요.";
		}
		return msg;
	}
	
	public ArrayList<Notice> listAction() {
		return noticeDao.noticeList();
	}
	
	public String deleteAction(int num) {
		String msg = null;
			int n = noticeDao.noticeDelete(num);
            if(n!=0) {msg = "삭제처리 되었습니다.";}
            else {msg="Error : 공지삭제오류";}
		return msg;
	}
	
	public String saveAction(Notice notice) {
		int n = noticeDao.noticeInsert(notice);
		if(n!=0) {return "정상적으로 등록되었습니다.";}
		return "Error : 공지등록오류";
	}
	
	public String updateAction(Notice notice) {
		Notice nb = null;
		if (1 == noticeDao.noticeUpdate(notice)) {
			return "정상적으로 수정되었습니다.";
		}
		return "Error : 공지수정오류";
	}
	
	public Notice viewAction(int num) {
		return noticeDao.noticeView(num);
	}
}
