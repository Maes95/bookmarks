package dev.maes.bookmarks.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.maes.bookmarks.entities.Link;
import dev.maes.bookmarks.entities.User;
import dev.maes.bookmarks.repositories.LinkRepository;
import dev.maes.bookmarks.repositories.UserRepository;
import jakarta.annotation.PostConstruct;

@Service
public class DatabaseInitializer {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LinkRepository linkRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostConstruct
	public void init(){
        User admin = User.builder()
            .username("admin")
            .password(encoder.encode("password"))
            .email("admin@maes.dev")
            .roles(List.of("ROLE_ADMIN", "ROLE_USER"))
            .build();
        userRepository.save(admin);

        Link dummyLink = new Link();
        dummyLink.setUrl("https://maes.dev");
        linkRepository.save(dummyLink);
    }
    
}
