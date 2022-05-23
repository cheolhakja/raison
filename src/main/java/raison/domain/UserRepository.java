package raison.domain;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(User user) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(user);
        tx.commit();
        em.close();
    }

    public List<User> findAll() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<User> users = em.createQuery("select m from User m", User.class).getResultList();
        //tx.commit();
        em.close();
        return users;
    }

    public void print() {
        System.out.println("em = " + em);
    }
}
