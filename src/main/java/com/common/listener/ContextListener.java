package com.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.common.AESCryptor;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {
//tomcat이 실행 중 지정 돼 특정순간에 지정된 메소드를 실행하게 하는 이벤트같은 것
//ServletContext클래스가 생성됐을 때, 소멸됐을 때,
//ServletContext클래스에 data대입, 수정, 삭제했을 때
//HttpSession 클래스가 생성,소멸,....
//HttrpServletRequest클래스가 생성, 소멸...data대입, 수정, 삭제
    /**
     * Default constructor. 
     */
    public ContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         new AESCryptor();
    }
	
}
