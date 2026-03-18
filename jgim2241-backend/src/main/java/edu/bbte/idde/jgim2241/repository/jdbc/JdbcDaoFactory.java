package edu.bbte.idde.jgim2241.repository.jdbc;

import edu.bbte.idde.jgim2241.repository.ContactDao;
import edu.bbte.idde.jgim2241.repository.DaoFactory;

public class JdbcDaoFactory extends DaoFactory {
    @Override
    public ContactDao getContactDao() {
        return new ContactJdbcDao();
    }
}
