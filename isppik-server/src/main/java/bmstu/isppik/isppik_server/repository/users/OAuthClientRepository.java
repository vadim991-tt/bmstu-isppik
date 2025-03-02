package bmstu.isppik.isppik_server.repository.users;

import bmstu.isppik.isppik_server.model.users.OAuthClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthClientRepository extends JpaRepository<OAuthClient, String> { }
