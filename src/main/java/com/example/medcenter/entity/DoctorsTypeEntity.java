package com.example.medcenter.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "doctors_type", schema = "public", catalog = "medcenter")
public class DoctorsTypeEntity {
    private int id;
    private String type;
//    private Collection<DoctorsFeaturesTypesEntity> doctorsFeaturesTypesById;



    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DoctorsFeaturesEntity> doctorsFeaturesEntities;



    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorsTypeEntity that = (DoctorsTypeEntity) o;
        return id == that.id &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

//    @OneToMany(mappedBy = "doctorsTypeByTypeId")
//    public Collection<DoctorsFeaturesTypesEntity> getDoctorsFeaturesTypesById() {
//        return doctorsFeaturesTypesById;
//    }
//
//    public void setDoctorsFeaturesTypesById(Collection<DoctorsFeaturesTypesEntity> doctorsFeaturesTypesById) {
//        this.doctorsFeaturesTypesById = doctorsFeaturesTypesById;
//    }
}
