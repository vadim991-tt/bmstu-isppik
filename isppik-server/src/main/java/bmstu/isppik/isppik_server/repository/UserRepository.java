package bmstu.isppik.isppik_server.repository;

import bmstu.isppik.isppik_server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
