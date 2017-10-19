package notice.view;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import notice.controller.Controller;
import notice.model.FactoryDao;
import notice.model.LoginDao;
import notice.model.NoticeDao;
import notice.model.Service;

public class UnitTest {

	public static void main(String[] args) throws SQLException {
		// bean configuration(beans-config.xml) 설정 기반으로 spring f/w 에서 객체생성 인젝션
		// IoC(DI) 컨테이너 : ApplicationContext
		ApplicationContext context = new ClassPathXmlApplicationContext("jdbc-beans-config.xml");
		
		FactoryDao factory = context.getBean("factoryDao", FactoryDao.class);
		System.out.println(factory);

		System.out.println();
		System.out.println(context.getBean("loginDao", LoginDao.class));
		System.out.println(context.getBean("loginDao", LoginDao.class));
		
		System.out.println();
		System.out.println(context.getBean("noticeDao", NoticeDao.class));
		System.out.println(context.getBean("noticeDao", NoticeDao.class));
		
		System.out.println();
		System.out.println(context.getBean("controller", Controller.class));
		System.out.println(context.getBean("controller", Controller.class));
		System.out.println(context.getBean("controller", Controller.class));
		
		System.out.println();
		System.out.println(context.getBean("service", Service.class));
		System.out.println(context.getBean("service", Service.class));
		System.out.println(context.getBean("service", Service.class));

	}
}
