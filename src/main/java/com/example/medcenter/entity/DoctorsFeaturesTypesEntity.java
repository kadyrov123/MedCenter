//package com.example.medcenter.entity;
//
//import javax.persistence.*;
//import java.util.Objects;
//
//@Entity
//@Table(name = "doctors_features_types", schema = "public", catalog = "medcenter")
//public class DoctorsFeaturesTypesEntity {
//    private Long doctorId;
//    private Integer typeId;
//    private DoctorsFeaturesEntity doctorsFeaturesByDoctorId;
//    private DoctorsTypeEntity doctorsTypeByTypeId;
//
//    @Basic
//    @Column(name = "doctor_id")
//    public Long getDoctorId() {
//        return doctorId;
//    }
//
//    public void setDoctorId(Long doctorId) {
//        this.doctorId = doctorId;
//    }
//
//    @Basic
//    @Column(name = "type_id")
//    public Integer getTypeId() {
//        return typeId;
//    }
//
//    public void setTypeId(Integer typeId) {
//        this.typeId = typeId;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        DoctorsFeaturesTypesEntity that = (DoctorsFeaturesTypesEntity) o;
//        return Objects.equals(doctorId, that.doctorId) &&
//                Objects.equals(typeId, that.typeId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(doctorId, typeId);
//    }
//
//    @ManyToOne
//    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
//    public DoctorsFeaturesEntity getDoctorsFeaturesByDoctorId() {
//        return doctorsFeaturesByDoctorId;
//    }
//
//    public void setDoctorsFeaturesByDoctorId(DoctorsFeaturesEntity doctorsFeaturesByDoctorId) {
//        this.doctorsFeaturesByDoctorId = doctorsFeaturesByDoctorId;
//    }
//
//    @ManyToOne
//    @JoinColumn(name = "type_id", referencedColumnName = "id")
//    public DoctorsTypeEntity getDoctorsTypeByTypeId() {
//        return doctorsTypeByTypeId;
//    }
//
//    public void setDoctorsTypeByTypeId(DoctorsTypeEntity doctorsTypeByTypeId) {
//        this.doctorsTypeByTypeId = doctorsTypeByTypeId;
//    }
//}
