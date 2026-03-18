package edu.bbte.idde.jgim2241.repository;

import edu.bbte.idde.jgim2241.util.PropertyProvider;
import edu.bbte.idde.jgim2241.repository.mem.MemDaoFactory;
import edu.bbte.idde.jgim2241.repository.jdbc.JdbcDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory instance;

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            if ("mem".equals(PropertyProvider.getProperty("daoFactory.impl"))) {
                instance = new MemDaoFactory();
            } else {
                instance = new JdbcDaoFactory();
            }
        }
        return instance;
    }

    public abstract ContactDao getContactDao();
}
