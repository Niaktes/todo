package ru.job4j.todo.filter;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AuthorizationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {
        String uri = request.getRequestURI();
        if (isAlwaysPermitted(uri)) {
            chain.doFilter(request, response);
            return;
        }
        boolean userLoggedIn = request.getSession().getAttribute("user") != null;
        if (!userLoggedIn) {
            String loginPageUrl = request.getContextPath() + "/users/login";
            response.sendRedirect(loginPageUrl);
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isAlwaysPermitted(String uri) {
        return uri.startsWith("/users/register") || uri.startsWith("/users/login");
    }

}