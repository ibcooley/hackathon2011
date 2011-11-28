package com.sparc.weight2GoJPA;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

/**
 * A user
 */
@Entity
@Table(name = "T_USER")
public class User {

    @Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;

	private String firstName;
	
	private String lastName;
	
	private int startWeight;

    private int targetWeight;

    private int height;

    private Date dateOfBirth;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String gender;

    @OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="USER_ID")
	private Collection<UserWeight> userWeights = new LinkedHashSet<UserWeight>();

    @OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="USER_ID")
	private Collection<UserFood> userFoods = new LinkedHashSet<UserFood>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(int startWeight) {
        this.startWeight = startWeight;
    }

    public int getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(int targetWeight) {
        this.targetWeight = targetWeight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
	 * @return the userWeights
	 */
	public Collection<UserWeight> getUserWeights() {
		return userWeights;
	}

	/**
	 * @param userWeights the items to set
	 */
	public void setUserWeights(Collection<UserWeight> userWeights) {
		this.userWeights = userWeights;
	}

    public Collection<UserFood> getUserFoods() {
        return userFoods;
    }

    public void setUserFoods(Collection<UserFood> userFoods) {
        this.userFoods = userFoods;
    }
    
}