package com.example.medcenter.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role", schema = "public", catalog = "medcenter")
public class RoleEntity {

    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "role")
    private String role;
//    private UsersEntity usersById;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity that = (RoleEntity) o;
        return id == that.id &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }

//    @OneToOne(mappedBy = "roleById")
//    public UsersEntity getUsersById() {
//        return usersById;
//    }
//
//    public void setUsersById(UsersEntity usersById) {
//        this.usersById = usersById;
//    }
}
