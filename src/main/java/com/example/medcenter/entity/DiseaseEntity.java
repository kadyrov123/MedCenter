package com.example.medcenter.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "disease", schema = "public", catalog = "medcenter")
public class DiseaseEntity {
    private long id;
    private Long patientId;
    private Date date;
    private String diagnosis;
    private String recipe;
    private Long doctorId;
    private UsersEntity usersByPatientId;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "patient_id" ,insertable = false, updatable = false)
    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "diagnosis")
    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Basic
    @Column(name = "recipe")
    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    @Basic
    @Column(name = "doctor_id")
    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiseaseEntity that = (DiseaseEntity) o;
        return id == that.id &&
                Objects.equals(patientId, that.patientId) &&
                Objects.equals(date, that.date) &&
                Objects.equals(diagnosis, that.diagnosis) &&
                Objects.equals(recipe, that.recipe) &&
                Objects.equals(doctorId, that.doctorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patientId, date, diagnosis, recipe, doctorId);
    }

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    public UsersEntity getUsersByPatientId() {
        return usersByPatientId;
    }

    public void setUsersByPatientId(UsersEntity usersByPatientId) {
        this.usersByPatientId = usersByPatientId;
    }
}
