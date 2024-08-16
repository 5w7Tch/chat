package models.listeners;


import models.DAO.Dao;
import models.DAO.dbCredentials;
import models.DAO.mySqlDb;
import models.USER.User;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;

public class contextListener implements ServletContextListener  {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        BasicDataSource source = new BasicDataSource();
        source.setUrl(dbCredentials.url);
        source.setUsername(dbCredentials.userName);
        source.setPassword(dbCredentials.password);
        Dao db = new mySqlDb(source);
        servletContextEvent.getServletContext().setAttribute(Dao.DBID,db);
        ArrayList<User> users = new ArrayList<>();
        servletContextEvent.getServletContext().setAttribute(User.ONLINE, users);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ((Dao) servletContextEvent.getServletContext().getAttribute(mySqlDb.DBID)).closeDbConnection();
    }
}
