package com.example.medcenter.entity;

import javax.persistence.*;

@Entity
@Table(name = "newComers", schema = "public", catalog = "medcenter")
public class NewComersEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "surname")
    private String surname;


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "queue_id", nullable = false)
    private QueueEntity queue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public QueueEntity getQueue() {
        return queue;
    }

    public void setQueue(QueueEntity queue) {
        this.queue = queue;
    }
}
