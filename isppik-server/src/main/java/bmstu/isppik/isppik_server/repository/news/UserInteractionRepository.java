package bmstu.isppik.isppik_server.repository.news;

import bmstu.isppik.isppik_server.model.news.UserInteraction;
import bmstu.isppik.isppik_server.model.news.id.UserInteractionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserInteractionRepository extends JpaRepository<UserInteraction, UserInteractionId> {

}
