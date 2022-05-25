package raison.domain;

import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class UserRepository {

    @PersistenceUnit
    private EntityManagerFactory emf;

    public void save(User user) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(user);
        tx.commit();
        em.close();
    }

    public List<User> findAll() {
        EntityManager em = emf.createEntityManager();
        List<User> users = em.createQuery("select m from User m", User.class).getResultList();
        em.close();
        return users;
    }

    public User findById(Long id) {
        //handling null
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, id);
        return user;
    }

    public void update(Long id, String userId, String password, String name, String email) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        User user = em.find(User.class, id);
        user.update(userId, password, name, email);
        tx.commit();
        em.close();
    }
}
