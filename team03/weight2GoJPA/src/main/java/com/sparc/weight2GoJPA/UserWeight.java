package com.sparc.weight2GoJPA;

import javax.persistence.*;
import java.util.Date;

/**
 * A user
 */
@Entity
public class UserWeight {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    private User user;

    @Column(name = "WEIGHT")
    private int weight;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @PrePersist
    void updateDates() {
        if (date == null) {
            date = new Date();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
