package bmstu.isppik.isppik_server.repository.users;


import bmstu.isppik.isppik_server.model.users.Role;
import bmstu.isppik.isppik_server.model.users.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleType name);
}