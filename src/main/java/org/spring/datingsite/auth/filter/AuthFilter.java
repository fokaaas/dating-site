package org.spring.datingsite.auth.filter;

import org.spring.datingsite.user.entity.UserEntity;
import org.spring.datingsite.user.UserService;
import org.spring.datingsite.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = CookieUtil.getAuthCookie(request);

        if (token == null) {
            response.sendRedirect("/login");
            return;
        }

        UserEntity currentUser = userService.getUserFromToken(token);
        if (currentUser == null) {
            response.sendRedirect("/login");
            return;
        }

        request.setAttribute("currentUser", currentUser);
        filterChain.doFilter(request, response);
    }
}
