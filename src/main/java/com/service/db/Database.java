package com.service.db;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.model.Item;
import com.model.PriceQuotation;
import com.model.PriceQuotationsReport;
import com.model.PurchaseRequest;
import com.model.User;

public class Database {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {

                Properties settings = new Properties();
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MariaDBDialect");
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost/test");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "");
                settings.put(Environment.HBM2DDL_AUTO, "update");
                // settings.put(Environment.HBM2DDL_AUTO, "create");
                settings.put(Environment.SHOW_SQL, true);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(settings)
                        .build();

                Configuration config = new Configuration();
                config.addAnnotatedClass(User.class);
                config.addAnnotatedClass(PurchaseRequest.class);
                config.addAnnotatedClass(Item.class);
                config.addAnnotatedClass(PriceQuotationsReport.class);
                config.addAnnotatedClass(PriceQuotation.class);

                sessionFactory = config.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sessionFactory;

    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
