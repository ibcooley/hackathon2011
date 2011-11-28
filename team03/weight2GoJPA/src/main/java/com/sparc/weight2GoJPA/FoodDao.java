package com.sparc.weight2GoJPA;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FoodDao {
    // Injected database connection:
    @PersistenceContext private EntityManager em;

    // Stores a new food:
    @Transactional
    public void persist(Food food) {
        em.persist(food);
    }

    @Transactional
    public Food update(Food food) {
        return em.merge(food);
    }

    // Retrieves all the guests:
    public List<Food> getAllFoods() {
    	TypedQuery<Food> query = em.createQuery(
            "SELECT f FROM Food u FOOD BY f.id", Food.class);
    	return query.getResultList();
    }

    public Food getFoodById(Long id) {
        return em.find(Food.class, id);
    }

}