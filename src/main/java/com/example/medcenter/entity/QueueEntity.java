package com.example.medcenter.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "queue", schema = "public", catalog = "medcenter")
public class QueueEntity {
    private long id;
    private String talon;
    private Long userId;
    private Long doctorId;
    private Date date;
    private Integer order;
    private String time;
    private Integer intervalId;
    private Integer status;
    private UsersEntity usersByDoctorId;
    private IntervalEntity intervalByIntervalId;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "talon")
    public String getTalon() {
        return talon;
    }

    public void setTalon(String talon) {
        this.talon = talon;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "doctor_id" , insertable = false , updatable = false)
    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
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
    @Column(name = "order")
    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Basic
    @Column(name = "time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Basic
    @Column(name = "interval_id",insertable = false , updatable = false)
    public Integer getIntervalId() {
        return intervalId;
    }

    public void setIntervalId(Integer intervalId) {
        this.intervalId = intervalId;
    }

    @Basic
    @Column(name = "status")
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
                Objects.equals(order, that.order) &&
                Objects.equals(time, that.time) &&
                Objects.equals(intervalId, that.intervalId) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, talon, userId, doctorId, date, order, time, intervalId, status);
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
}
