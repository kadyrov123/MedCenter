package com.example.medcenter.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "doctors_type", schema = "public", catalog = "medcenter")
public class DoctorsTypeEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic
    @Column(name = "type")
    private String type;
//    private Collection<DoctorsFeaturesTypesEntity> doctorsFeaturesTypesById;



    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "doctorsTypeEntities")
    private Set<DoctorsFeaturesEntity> doctorsFeaturesEntities = new HashSet<>();

    public Set<DoctorsFeaturesEntity> getDoctorsFeaturesEntities() {
        return doctorsFeaturesEntities;
    }

    public void setDoctorsFeaturesEntities(Set<DoctorsFeaturesEntity> doctorsFeaturesEntities) {
        this.doctorsFeaturesEntities = doctorsFeaturesEntities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return  type;
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
