package edu.bbte.idde.jgim2241.repository.mem;

import edu.bbte.idde.jgim2241.repository.ContactDao;
import edu.bbte.idde.jgim2241.repository.DaoFactory;

public class MemDaoFactory extends DaoFactory {
    @Override
    public ContactDao getContactDao() {
        return new ContactMemDao();
    }
}
