package bmstu.isppik.isppik_server.model.news.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInteractionId implements Serializable {

    private Long userId;

    private Long newsId;

}
