package com.example.medcenter.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.*;

@Entity
@Table(name = "doctors_features", schema = "public", catalog = "medcenter")
public class DoctorsFeaturesEntity {
    private int id;
    private Long doctorId;
    private Time startTime;
    private Time endTime;
    private Integer intervalId;
    private String info;
    private UsersEntity usersByDoctorId;
    private IntervalEntity intervalByIntervalId;

    @ManyToMany(mappedBy = "doctorsFeaturesEntities",fetch = FetchType.EAGER)
    private List<DoctorsTypeEntity> doctorsTypeEntities ;
//    private Collection<DoctorsFeaturesTypesEntity> doctorsFeaturesTypesById;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "doctor_id",insertable = false , updatable = false)
    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    @Basic
    @Column(name = "start_time")
    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time")
    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "interval_id" , insertable = false , updatable = false)
    public Integer getIntervalId() {
        return intervalId;
    }

    public void setIntervalId(Integer intervalId) {
        this.intervalId = intervalId;
    }

    @Basic
    @Column(name = "info")
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorsFeaturesEntity that = (DoctorsFeaturesEntity) o;
        return id == that.id &&
                Objects.equals(doctorId, that.doctorId) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(intervalId, that.intervalId) &&
                Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, doctorId, startTime, endTime, intervalId, info);
    }

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    public UsersEntity getUsersByDoctorId() {
        return usersByDoctorId;
    }

    public void setUsersByDoctorId(UsersEntity usersByDoctorId) {
        this.usersByDoctorId = usersByDoctorId;
    }

    @ManyToOne
    @JoinColumn(name = "interval_id", referencedColumnName = "id")
    public IntervalEntity getIntervalByIntervalId() {
        return intervalByIntervalId;
    }

    public void setIntervalByIntervalId(IntervalEntity intervalByIntervalId) {
        this.intervalByIntervalId = intervalByIntervalId;
    }

//    @OneToMany(mappedBy = "doctorsFeaturesByDoctorId")
//    public Collection<DoctorsFeaturesTypesEntity> getDoctorsFeaturesTypesById() {
//        return doctorsFeaturesTypesById;
//    }
//
//    public void setDoctorsFeaturesTypesById(Collection<DoctorsFeaturesTypesEntity> doctorsFeaturesTypesById) {
//        this.doctorsFeaturesTypesById = doctorsFeaturesTypesById;
//    }
}
