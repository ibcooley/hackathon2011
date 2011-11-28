package com.sparc.weight2GoJPA;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class UserDao {
    // Injected database connection:
    @PersistenceContext private EntityManager em;

    // Stores a new guest:
    @Transactional
    public void persist(User user) {
        em.persist(user);
        em.flush();
        em.clear();
    }

    @Transactional
    public User update(User user){
        return em.merge(user);
    }

    // Retrieves all the guests:
    public List<User> getAllUsers() {
    	TypedQuery<User> query = em.createQuery(
            "SELECT u FROM User u ORDER BY u.id", User.class);
    	return query.getResultList();
    }

    public User getUserById(Long id) {
        return em.find(User.class, id);
    }
}