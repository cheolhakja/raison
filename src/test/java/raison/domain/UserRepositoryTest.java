package raison.domain;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;
import java.util.List;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void save_and_find() {
        User user = new User("id", "pw", "name", "mail@gmail.com");

        userRepository.save(user);
        List<User> userList = userRepository.findAll();
        User userFindById = userRepository.findById(userList.get(0).getId());

        assertThat(userList.size()).isEqualTo(1);
        assertThat(userFindById.getName()).isEqualTo("name");
    }
}
