package pro.pie.me.corm.model;


import pro.pie.me.corm.annotation.Column;

import java.util.Objects;

/**
 * Entity -ID
 */
public abstract class IdEntity {

    /**
     * "ID" 属性名称
     */
    public static final String ID_PN = "id";

    protected Long id;

    @Column(value = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdEntity idEntity = (IdEntity) o;
        return Objects.equals(id, idEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "IdEntity{" +
                "id=" + id +
                '}';
    }
}
