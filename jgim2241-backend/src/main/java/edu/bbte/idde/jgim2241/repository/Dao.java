package edu.bbte.idde.jgim2241.repository;

import edu.bbte.idde.jgim2241.model.BaseEntity;

import java.util.Collection;

public interface Dao<T extends BaseEntity> {
    Collection<T> findAll() throws RepositoryException;

    T findByID(Long id) throws RepositoryException;

    Long getCount() throws RepositoryException;

    T create(T entity) throws RepositoryException;

    T update(T entity) throws RepositoryException;

    void delete(Long id) throws RepositoryException;
}
