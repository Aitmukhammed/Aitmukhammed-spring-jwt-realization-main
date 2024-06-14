package com.example.demoauth.pojo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}


// Этот класс, называемый AuthenticationFacade, используется для предоставления удобного способа
// доступа к объекту аутентификации (Authentication) в Spring Security.
//
// Обычно объект Authentication содержит информацию о текущем пользователе, включая его имя
// пользователя, роли и другие атрибуты аутентификации. Этот объект может быть полезен в различных
// частях вашего приложения, где требуется доступ к информации о текущем пользователе, например,
// при создании или обновлении данных, требующих идентификации пользователя.
//
// AuthenticationFacade обеспечивает централизованный доступ к объекту Authentication из любой
// части вашего приложения, без необходимости повторного получения этого объекта из контекста
// безопасности (SecurityContextHolder). Это делает ваш код более модульным и упрощает его тестирование,
// так как вы можете легко мокировать или заменять AuthenticationFacade в юнит-тестах.