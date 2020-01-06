//package com.example.medcenter.entity;
//
//import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "doctors_features_types", schema = "public", catalog = "medcenter")
//public class DoctorsFeaturesTypesEntity {
//    private DoctorsFeaturesEntity doctorsFeaturesByDoctorId;
//    private DoctorsTypeEntity doctorsTypeByTypeId;
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
