package notice.view;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component("main")
@Scope("prototype")
public class MainView {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("jdbc-beans-config.xml");
		MenuView menu = context.getBean("menu", MenuView.class);
		int num;
		String user;
		
		do {
			num = menu.mainMenu();
			switch(num) {
			case 1 :
				user = menu.loginMenu();
				if(user != null) {
					int n;
					do {
						n = menu.resultMenu(user);
						switch(n) {
						case 1 :
							menu.insertNotice(user);
							break;
						case 2 :
							menu.selectNotices();
							break;
						case 3 :
							menu.selectNotice();
							break;
						case 4 :
							menu.updateNotice();
							break;
						case 5 :
							menu.deleteNotice();
						}
					} while(n != 6);
				}
				break;
			case 2 :
				menu.selectNotices();
				break;
			}
		} while (num != 3);
	}
}
