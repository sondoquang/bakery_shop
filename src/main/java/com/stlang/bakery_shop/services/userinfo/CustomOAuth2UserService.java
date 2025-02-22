package com.stlang.bakery_shop.services.userinfo;

import java.util.Collections;
import java.util.Map;

import com.stlang.bakery_shop.domains.User;
import com.stlang.bakery_shop.domains.enums.Role;
import com.stlang.bakery_shop.services.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    public CustomOAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // call api
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        // get provider
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // Process oAuth2User or map it to your local user database
        String email = (String) attributes.get("email");
        String fullName = (String) attributes.get("name");
//        Long googleId = Long.parseLong((String) attributes.get("sub"));

        if(email != null){
            User user = userService.findByEmail(email);
            if(user == null){
                // Create new user //
                User newUser = User.builder()
                        .fullname(fullName)
                        .email(email)
                        .phoneNumber("0999999999")
                        .address("address")
                        .password("password")
                        .isActive(true)
                        .role(Role.USER)
                        .provider("google")
                        .build();
                userService.addUser(newUser);
            }
        }

        System.out.println("User email: " + email);

        return new DefaultOAuth2User(
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+Role.USER)),
                oAuth2User.getAttributes(),
                "email");
    }
}