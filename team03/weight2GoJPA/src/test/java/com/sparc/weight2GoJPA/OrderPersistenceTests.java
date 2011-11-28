package com.sparc.weight2GoJPA;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderPersistenceTests {

    @Autowired
    private UserDao userDao;

    @Autowired
    private FoodDao foodDao;

    @Test
    @Transactional
    public void testMyTest() throws Exception {
        User user1 = new User();
        user1.setFirstName("testFirst");
        user1.setLastName("testLast");
        user1.setDateOfBirth(new Date());
        user1.setStartWeight(200);
        user1.setTargetWeight(170);

        User user2 = new User();
        user2.setFirstName("testFirst");
        user2.setLastName("testLast");
        user2.setDateOfBirth(new Date());
        user2.setStartWeight(200);
        user2.setTargetWeight(170);

        User user3 = new User();
        user3.setFirstName("testFirst");
        user3.setLastName("testLast");
        user3.setDateOfBirth(new Date());
        user3.setStartWeight(200);
        user3.setTargetWeight(170);

        userDao.persist(user1);
        userDao.persist(user2);
        userDao.persist(user3);

        List<User> users = userDao.getAllUsers();
        assertEquals(3, users.size());
    }

    @Test
    @Transactional
    public void testUserSaveAndGet() throws Exception {
        User user = new User();
        user.setFirstName("testFirst");
        user.setLastName("testLast");
        user.setDateOfBirth(new Date());
        user.setStartWeight(200);
        user.setTargetWeight(170);

        this.userDao.persist(user);
        // Otherwise the query returns the existing order (and we didn't set the
		// parent in the item)...
//		User other = (User) this.userDao.find(User.class, user.getId());
//		assertEquals(true, other.getFirstName().equals("testFirst"));

    }

    @Test
	@Transactional
	public void testUserWeightSaveAndFind() throws Exception {
		User user = new User();
        createUser(user);
		UserWeight userWeight = new UserWeight();
		userWeight.setWeight(180);
		user.getUserWeights().add(userWeight);
		this.userDao.persist(user);
		// Otherwise the query returns the existing order (and we didn't set the
		// parent in the item)...
		User other = (User) this.userDao.getUserById(user.getId());

		assertEquals(1, other.getUserWeights().size());
		assertEquals(other, other.getUserWeights().iterator().next().getUser());
	}

    @Test
    @Transactional
    public void testFullUserSaveAndFind() throws Exception {

        User user = new User();
        createUser(user);

        user.getUserWeights().addAll(createUserWeights(3));
        user.getUserFoods().addAll(createUserFoods(5));

        this.userDao.persist(user);

        User other = (User) this.userDao.getUserById(user.getId());
		assertEquals(5, other.getUserFoods().size());
		assertEquals(other, other.getUserFoods().iterator().next().getUser());

        assertEquals(3, other.getUserWeights().size());
		assertEquals(other, other.getUserWeights().iterator().next().getUser());

        user.getUserWeights().addAll(createUserWeights(2));
        user = this.userDao.update(user);

        other = (User) this.userDao.getUserById(user.getId());
		assertEquals(5, other.getUserFoods().size());

        assertEquals(5, other.getUserWeights().size());


    }

    private List<UserFood> createUserFoods(int numFoods) {
        List<UserFood> userFoods = new ArrayList<UserFood>();
        for (int x = 0; x < numFoods; x++) {
            UserFood userFood = new UserFood();
            userFood.setAmount(1);
            userFood.setFood(createFood());

            userFoods.add(userFood);

        }

        return userFoods;
    }

    private Food createFood() {
        Food food = new Food();
        food.setName("Beer");
        food.setCaloriesPerServing(200);
        food.setServingSize(1);

        foodDao.persist(food);

        return food;
    }

    private List<UserWeight> createUserWeights(int numWeights) {
        List<UserWeight> userWeights = new ArrayList<UserWeight>();
        for (int x = 0; x < numWeights; x++) {
            UserWeight userWeight = new UserWeight();
            userWeight.setWeight(180);

            userWeights.add(userWeight);
        }

        return userWeights;
    }

    private void createUser(User user) {
        user.setFirstName("testFirst");
        user.setLastName("testLast");
        user.setDateOfBirth(new Date());
        user.setStartWeight(200);
        user.setTargetWeight(170);
    }

}
