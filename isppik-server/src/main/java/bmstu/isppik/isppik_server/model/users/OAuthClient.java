package bmstu.isppik.isppik_server.model.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "oauth_clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthClient {

    @Id
    private String clientId;

    private String clientSecret;

    private String scopes;

    private String grantTypes;
}
