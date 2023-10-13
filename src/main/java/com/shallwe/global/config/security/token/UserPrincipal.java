package com.shallwe.global.config.security.token;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.shallwe.domain.shopowner.domain.ShopOwner;
import com.shallwe.domain.user.domain.User;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
public class UserPrincipal implements OAuth2User, UserDetails{

    private final User user;
    private final ShopOwner shopOwner;
    
    private final Long id;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(User user, Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.shopOwner = null;
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public UserPrincipal(ShopOwner shopOwner, Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.user = null;
        this.shopOwner = shopOwner;
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal createUser(final User user) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getValue()));
        return new UserPrincipal(
                user,
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public static UserPrincipal createShopOwner(final ShopOwner shopOwner) {
        return new UserPrincipal(
                shopOwner,
                shopOwner.getId(),
                shopOwner.getPhoneNumber(),
                shopOwner.getPassword(),
                null
        );
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.createUser(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
