package edu.bbte.idde.jgim2241.repository.mem;

import edu.bbte.idde.jgim2241.model.BaseEntity;
import edu.bbte.idde.jgim2241.repository.Dao;
import edu.bbte.idde.jgim2241.repository.RepositoryException;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class MemDao<T extends BaseEntity> implements Dao<T> {
    protected Map<Long, T> entities = new ConcurrentHashMap<>();
    private Long nextId = 0L;

    @Override
    public Collection<T> findAll() {
        return entities.values();
    }

    @Override
    public T findByID(Long id) {
        return entities.get(id);
    }

    @Override
    public Long getCount() {
        return (long) entities.size();
    }

    public Long getNextId() {
        return nextId;
    }

    @Override
    public T create(T entity) {
        entity.setId(getNextId());
        entities.put(nextId, entity);
        nextId++;
        return findByID(entity.getId());
    }

    @Override
    public T update(T entity) throws RepositoryException {
        if (entity == null || findByID(entity.getId()) == null) {
            log.info("Contact not found at update");
            throw new RepositoryException("Entity not found");
        }
        entities.put(entity.getId(), entity);
        return findByID(entity.getId());
    }

    @Override
    public void delete(Long id) {
        entities.remove(id);
    }
}
