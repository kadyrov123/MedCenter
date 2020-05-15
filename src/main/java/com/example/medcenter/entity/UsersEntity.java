package com.example.medcenter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "public", catalog = "medcenter")
public class UsersEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "surname")
    private String surname;
    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "pin")
    private long pin;

    @JsonIgnore
    @OneToMany(mappedBy = "usersByPatientId")
    private Collection<DiseaseEntity> diseasesById;

    @JsonIgnore
    @OneToMany(mappedBy = "usersByDoctorId")
    private Collection<DoctorsFeaturesEntity> doctorsFeaturesById;

//    @JsonIgnore
//    @OneToMany(mappedBy = "usersByDoctorId")
//    private Collection<QueueEntity> queuesById;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<RoleEntity> roles;

//    @OneToOne
//    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
//    private RoleEntity roleById;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPin() {
        return pin;
    }

    public void setPin(long pin) {
        this.pin = pin;
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
                Objects.equals(pin, that.pin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, username, password, email, pin);
    }


    public Collection<DiseaseEntity> getDiseasesById() {
        return diseasesById;
    }

    public void setDiseasesById(Collection<DiseaseEntity> diseasesById) {
        this.diseasesById = diseasesById;
    }


    public Collection<DoctorsFeaturesEntity> getDoctorsFeaturesById() {
        return doctorsFeaturesById;
    }

    public void setDoctorsFeaturesById(Collection<DoctorsFeaturesEntity> doctorsFeaturesById) {
        this.doctorsFeaturesById = doctorsFeaturesById;
    }


//    public Collection<QueueEntity> getQueuesById() {
//        return queuesById;
//    }
//
//    public void setQueuesById(Collection<QueueEntity> queuesById) {
//        this.queuesById = queuesById;
//    }


    public Collection<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleEntity> roles) {
        this.roles = roles;
    }


//    public RoleEntity getRoleById() {
//        return roleById;
//    }
//
//    public void setRoleById(RoleEntity roleById) {
//        this.roleById = roleById;
//    }


    @Override
    public String toString() {
        return "UsersEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", pin=" + pin +

                '}';
    }
}
