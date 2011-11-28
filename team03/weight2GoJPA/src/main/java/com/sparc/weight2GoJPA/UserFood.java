package com.sparc.weight2GoJPA;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UserFood {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    private User user;

    @OneToOne
    private Food food;

    @Column
    private int amount;

    @Column
    private String mealType;

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

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

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
