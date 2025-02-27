package bmstu.isppik.isppik_server.repository.news;

import bmstu.isppik.isppik_server.model.news.Source;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceRepository extends JpaRepository<Source, Long> { }
