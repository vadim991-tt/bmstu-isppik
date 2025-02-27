package bmstu.isppik.isppik_server.repository.users;

import bmstu.isppik.isppik_server.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
