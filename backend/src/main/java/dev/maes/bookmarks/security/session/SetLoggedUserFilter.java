package dev.maes.bookmarks.security.session;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.maes.bookmarks.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SetLoggedUserFilter extends OncePerRequestFilter{

    @Autowired
    private UserSession userSession;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null && auth.isAuthenticated()){
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if(username != null){
                userSession.setLoggedUser(userRepository.findByUsername(username).get());
            }
        }

        filterChain.doFilter(request, response);
    }
    
}
