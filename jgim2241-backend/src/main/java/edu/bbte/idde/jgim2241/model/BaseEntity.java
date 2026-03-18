package edu.bbte.idde.jgim2241.model;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable {
    protected Long id;

    public BaseEntity(Long id) {
        this.id = id;
    }

    public BaseEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{"
                + "id=" + id + '}';
    }
}
