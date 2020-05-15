package com.example.medcenter.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "disease", schema = "public", catalog = "medcenter")
public class DiseaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Basic
    @Column(name = "patient_id" ,insertable = false, updatable = false)
    private Long patientId;

    @Basic
    @Column(name = "date")
    private Date date;

    @Basic
    @Column(name = "diagnosis")
    private String diagnosis;

    @Basic
    @Column(name = "recipe")
    private String recipe;


    @Basic
    @Column(name = "doctor_id")
    private int doctorId;

    @Basic
    @Column(name = "file")
    private String file;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private UsersEntity usersByPatientId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }


    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }


    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
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


    public UsersEntity getUsersByPatientId() {
        return usersByPatientId;
    }

    public void setUsersByPatientId(UsersEntity usersByPatientId) {
        this.usersByPatientId = usersByPatientId;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "DiseaseEntity{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", date=" + date +
                ", diagnosis='" + diagnosis + '\'' +
                ", recipe='" + recipe + '\'' +
                ", doctorId=" + doctorId +
                ", file='" + file + '\'' +
                ", usersByPatientId=" + usersByPatientId +
                '}';
    }
}
