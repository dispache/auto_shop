package com.company.evgeniy.auto_shop.auth;

import com.company.evgeniy.auto_shop.users.UsersService;
import com.company.evgeniy.auto_shop.users.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class MyOAuth2UserService extends DefaultOAuth2UserService {

    private UsersService usersService;

    @Autowired
    private void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User loadedUser = super.loadUser(userRequest);
        String loadedUserEmail = loadedUser.getAttribute("email");
        UserEntity user = this.usersService.findUserByEmail(loadedUserEmail);
        if ( user == null ) {
            throw new UsernameNotFoundException("User with email: " + loadedUserEmail + " not found");
        }
        return loadedUser;
    }
}
