package com.example.medcenter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Time;
import java.util.*;

@Entity
@Table(name = "doctors_features", schema = "public", catalog = "medcenter")
public class DoctorsFeaturesEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic
    @Column(name = "doctor_id",insertable = false , updatable = false)
    private Long doctorId;

    @Basic
    @Column(name = "start_time")
    private Time startTime;

    @Basic
    @Column(name = "end_time")
    private Time endTime;

    @Basic
    @Column(name = "interval_id" , insertable = false , updatable = false)
    private Integer intervalId;

    @Basic
    @Column(name = "info")
    private String info;

    @Basic
    @Column(name = "photo")
    private String photo;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private UsersEntity usersByDoctorId;

    @ManyToOne
    @JoinColumn(name = "interval_id", referencedColumnName = "id")
    private IntervalEntity intervalByIntervalId;

    @JsonIgnore
    @OneToMany(mappedBy = "doctorFeaturesByDoctorId")
    private Collection<QueueEntity> queueEntities;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "doctors_features_types",
            joinColumns = { @JoinColumn(name = "doctor_id") },
            inverseJoinColumns = { @JoinColumn(name = "type_id") })
    @JsonIgnore
    private Set<DoctorsTypeEntity> doctorsTypeEntities = new HashSet<>();

//    private String doctorTypeString;

    public String getDoctorTypeString() {
        String doctorTypeString = "";
        int i = 0;
        for(DoctorsTypeEntity type :this.getDoctorsTypeEntities()){
            if(i==0){
                doctorTypeString += type.getType();
            }else {
                doctorTypeString += ", "+type.getType();
            }
            i++;
        }
        return doctorTypeString;
    }

//    public void setDoctorTypeString(String doctorTypeString) {
//        this.doctorTypeString = doctorTypeString;
//    }
//    private Collection<DoctorsFeaturesTypesEntity> doctorsFeaturesTypesById;


    public Set<DoctorsTypeEntity> getDoctorsTypeEntities() {
        return doctorsTypeEntities;
    }

    public void setDoctorsTypeEntities(Set<DoctorsTypeEntity> doctorsTypeEntities) {
        this.doctorsTypeEntities = doctorsTypeEntities;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }


    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Integer getIntervalId() {
        return intervalId;
    }

    public void setIntervalId(Integer intervalId) {
        this.intervalId = intervalId;
    }

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

    public UsersEntity getUsersByDoctorId() {
        return usersByDoctorId;
    }

    public void setUsersByDoctorId(UsersEntity usersByDoctorId) {
        this.usersByDoctorId = usersByDoctorId;
    }


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


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Collection<QueueEntity> getQueueEntities() {
        return queueEntities;
    }

    public void setQueueEntities(Collection<QueueEntity> queueEntities) {
        this.queueEntities = queueEntities;
    }

    @Override
    public String toString() {
        return "DoctorsFeaturesEntity{" +
                "id=" + id +
                ", doctorId=" + doctorId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", intervalId=" + intervalId +
                ", info='" + info + '\'' +
                '}';
    }
}
