package br.com.praise.portal.service;

import br.com.praise.portal.model.User;
import br.com.praise.portal.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        System.out.println(email);
        Optional<User> user = repository.login(email);
        if(user.isPresent()) {
            System.out.println("2");
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            user.get().getAuthorities()
                    .forEach(role -> {
                        grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
                    });
            return new org.springframework.security.core.userdetails.User(
                    user.get().getEmail(), user.get().getPassword(),
                    true, true, true ,true,
                    grantedAuthorities
            );
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
    }
}
