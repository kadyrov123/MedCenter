package com.example.medcenter.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "interval", schema = "public", catalog = "medcenter")
public class IntervalEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic
    @Column(name = "interval")
    private Integer interval;
//    private Collection<DoctorsFeaturesEntity> doctorsFeaturesById;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntervalEntity that = (IntervalEntity) o;
        return id == that.id &&
                Objects.equals(interval, that.interval);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, interval);
    }

//    @OneToMany(mappedBy = "intervalByIntervalId")
//    public Collection<DoctorsFeaturesEntity> getDoctorsFeaturesById() {
//        return doctorsFeaturesById;
//    }
//
//    public void setDoctorsFeaturesById(Collection<DoctorsFeaturesEntity> doctorsFeaturesById) {
//        this.doctorsFeaturesById = doctorsFeaturesById;
//    }
}
