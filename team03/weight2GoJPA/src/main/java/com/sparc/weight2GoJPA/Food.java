package com.sparc.weight2GoJPA;

import javax.persistence.*;

@Entity
@Table(name="T_FOOD")
public class Food {

    @Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;

    private String name;

    private int servingSize;

    private int caloriesPerServing;

    private int protein;

    private int carbs;

    private int fat;

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

    public int getServingSize() {
        return servingSize;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public int getCaloriesPerServing() {
        return caloriesPerServing;
    }

    public void setCaloriesPerServing(int caloriesPerServing) {
        this.caloriesPerServing = caloriesPerServing;
    }
}
