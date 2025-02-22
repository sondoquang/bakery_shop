package com.stlang.bakery_shop.configs;

import com.stlang.bakery_shop.constants.ConstantRole;
import com.stlang.bakery_shop.domains.User;
import com.stlang.bakery_shop.services.iservices.IUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {


    private final IUserService userService;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public CustomSuccessHandler(IUserService userService) {
        this.userService = userService;
    }

    protected String determineTargetUrl(final Authentication authentication) {


        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put(ConstantRole.ROLE_USER, "/");
        roleTargetUrlMap.put(ConstantRole.ROLE_ADMIN, "/admin/product/index");

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if(roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }

        throw new IllegalStateException();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, Authentication authentication) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        // Get email //
        String email = authentication.getName();
        session.setAttribute("email",email);
        User user = userService.findByEmail(email);
        session.setAttribute("fullName",user.getFullname());
        session.setAttribute("phoneNumber",user.getPhoneNumber());
        session.setAttribute("address",user.getAddress());
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
        clearAuthenticationAttributes(request, authentication);
    }
}
