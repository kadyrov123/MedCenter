package com.example.medcenter.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "queue", schema = "public", catalog = "medcenter")
public class QueueEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Basic
    @Column(name = "talon")
    private String talon;

    @Basic
    @Column(name = "user_id")
    private Long userId;

    @Basic
    @Column(name = "doctor_id" , insertable = false , updatable = false)
    private Integer doctorId;

    @Basic
    @Column(name = "date")
    private Date date;

    @Basic
    @Column(name = "queue_order")
    private Integer queueOrder;

    @Basic
    @Column(name = "time")
    private String time;

    @Basic
    @Column(name = "interval_id",insertable = false , updatable = false)
    private Integer intervalId;

    @Basic
    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private DoctorsFeaturesEntity doctorFeaturesByDoctorId;

    @ManyToOne
    @JoinColumn(name = "interval_id", referencedColumnName = "id")
    private IntervalEntity intervalByIntervalId;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "queue")
    private NewComersEntity newComer;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTalon() {
        return talon;
    }

    public void setTalon(String talon) {
        this.talon = talon;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getOrder() {
        return queueOrder;
    }

    public void setOrder(Integer order) {
        this.queueOrder = order;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getIntervalId() {
        return intervalId;
    }

    public void setIntervalId(Integer intervalId) {
        this.intervalId = intervalId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueueEntity that = (QueueEntity) o;
        return id == that.id &&
                Objects.equals(talon, that.talon) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(doctorId, that.doctorId) &&
                Objects.equals(date, that.date) &&
                Objects.equals(queueOrder, that.queueOrder) &&
                Objects.equals(time, that.time) &&
                Objects.equals(intervalId, that.intervalId) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, talon, userId, doctorId, date, queueOrder, time, intervalId, status);
    }

    public DoctorsFeaturesEntity getDoctorFeaturesByDoctorId() {
        return doctorFeaturesByDoctorId;
    }

    public void setDoctorFeaturesByDoctorId(DoctorsFeaturesEntity doctorFeaturesByDoctorId) {
        this.doctorFeaturesByDoctorId = doctorFeaturesByDoctorId;
    }

    public IntervalEntity getIntervalByIntervalId() {
        return intervalByIntervalId;
    }

    public void setIntervalByIntervalId(IntervalEntity intervalByIntervalId) {
        this.intervalByIntervalId = intervalByIntervalId;
    }
}
