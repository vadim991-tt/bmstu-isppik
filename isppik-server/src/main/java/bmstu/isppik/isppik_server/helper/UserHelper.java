package bmstu.isppik.isppik_server.helper;

import java.security.Principal;

/**
 * Класс-помошник для работы с пользователями
 */
public class UserHelper {

    /**
     * Получить ID пользователя из PRINCIPAL
     * @param principal пользователь
     * @return ID пользователя
     */
    public static Long getUserIdFromPrincipal(Principal principal)   {
        return Long.parseLong(principal.getName());
    }

}
