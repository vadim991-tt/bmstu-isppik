package bmstu.isppik.isppik_server.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sources")
@Data
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // Название источника

    private String url;   // URL RSS-ленты или API

    private String type;  // Тип источника (например, RSS, API и т.д.)
}
