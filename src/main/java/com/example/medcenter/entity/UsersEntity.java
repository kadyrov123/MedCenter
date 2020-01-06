package com.example.medcenter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "public", catalog = "medcenter")
public class UsersEntity {
    private long id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private long roleId;
    private Collection<DiseaseEntity> diseasesById;
    private Collection<DoctorsFeaturesEntity> doctorsFeaturesById;
    private Collection<QueueEntity> queuesById;
    private RoleEntity roleById;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "role_id")
    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(email, that.email) &&
                Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, username, password, email, roleId);
    }

    @JsonIgnore
    @OneToMany(mappedBy = "usersByPatientId")
    public Collection<DiseaseEntity> getDiseasesById() {
        return diseasesById;
    }

    public void setDiseasesById(Collection<DiseaseEntity> diseasesById) {
        this.diseasesById = diseasesById;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "usersByDoctorId")
    public Collection<DoctorsFeaturesEntity> getDoctorsFeaturesById() {
        return doctorsFeaturesById;
    }

    public void setDoctorsFeaturesById(Collection<DoctorsFeaturesEntity> doctorsFeaturesById) {
        this.doctorsFeaturesById = doctorsFeaturesById;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "usersByDoctorId")
    public Collection<QueueEntity> getQueuesById() {
        return queuesById;
    }

    public void setQueuesById(Collection<QueueEntity> queuesById) {
        this.queuesById = queuesById;
    }

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    public RoleEntity getRoleById() {
        return roleById;
    }

    public void setRoleById(RoleEntity roleById) {
        this.roleById = roleById;
    }
}
