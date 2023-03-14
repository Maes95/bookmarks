package dev.maes.bookmarks.security.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import dev.maes.bookmarks.entities.User;
import lombok.Data;

@Component
@SessionScope
@Data
public class UserSession {

    private User loggedUser;
    
}
