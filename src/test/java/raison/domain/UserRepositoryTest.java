package raison.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

//@DataJpaTest no such bean exception
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    @PersistenceUnit
    private EntityManagerFactory emf;

    private void save(User user) {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(user);
        tx.commit();
        em.close();
    }

    @Test
    public void save_and_find() {
        System.out.println("userRepository = " + userRepository);

        User user = new User("id", "pw", "name", "mail@gmail.com");

        System.out.println("em = " + em);

        userRepository.print();

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(user);
        tx.commit();

        //tx.begin();
        List<User> users = em.createQuery("select m from User m", User.class).getResultList();

        //tx.commit();
        em.close();

        Assertions.assertThat(users.size()).isEqualTo(1);
    }
}
