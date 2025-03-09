package bmstu.isppik.isppik_server.dto.users;


import lombok.Data;

@Data
public class RegisterRequestDto {

    private String username;

    private String password;

    private String email;

    private String phone;

}