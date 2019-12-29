package ru.anton.gorbachev.ag_spring_boot.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        boolean user = false;
        boolean admin = false;
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals("ADMIN")) {
                admin = true;
            } else if (authority.getAuthority().equals("USER")) {
                user = true;
            }
        }

        if (user) {
            httpServletResponse.sendRedirect("/user");
        } else if (admin) {
            httpServletResponse.sendRedirect("/admin/allUsers");
        }
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    }
}
