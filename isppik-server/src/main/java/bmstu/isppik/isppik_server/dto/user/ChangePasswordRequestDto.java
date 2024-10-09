package bmstu.isppik.isppik_server.dto.user;

@Data
public class ChangePasswordRequestDto {
    
    private String oldPassword;

    private String newPassword;
    
}
