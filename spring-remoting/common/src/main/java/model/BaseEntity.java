package model;

import java.io.Serializable;

public class BaseEntity<ID> implements Serializable {

    private ID id;

    public BaseEntity() {
    }

    public BaseEntity(ID _id) {
        id=_id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}
