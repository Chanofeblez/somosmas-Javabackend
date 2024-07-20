package com.somosmas.services;

import com.somosmas.dto.AuthLoginRequestDTO;
import com.somosmas.dto.AuthResponseDTO;
import com.somosmas.miembros.IMiembroRepository;
import com.somosmas.miembros.Miembro;
import com.somosmas.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private IMiembroRepository miembroRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //Tenemos nuestro UserSec y necesitamos devolverlo en formato UserDetails
        //Traemos nuestro usuario de la DB
        Miembro miembro = miembroRepository.findUserEntityByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("El usuario "+email+" no fue encontrado"));

        //Creamos una lista para los permisos
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        //Taer roles y convertirlos en SimpleGrantedAuthority
        miembro.getRolesList()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole()))));

        //Traer permisos y convertirlos en SimpleGrantedAuthority
        miembro.getRolesList().stream()
                .flatMap(role -> role.getPermissionsList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getPermissionName())));

        return new User(
                miembro.getEmail(),
                miembro.getPassword(),
                miembro.isEnabled(),
                miembro.isAccountNotExpired(),
                miembro.isCredentialNotExpired(),
                miembro.isAccountNotLocked(),
                authorityList);

    }

    public AuthResponseDTO loginUser(AuthLoginRequestDTO userRequest) {

        Miembro miembro = new Miembro();

        //Recuperamos nombre de usuario y contraseña
        String username = userRequest.email();
        String password = userRequest.password();

        Authentication authentication = this.authenticate(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(username,"LoginOK",accessToken,true);
        return authResponseDTO;

    }

    private Authentication authenticate(String email, String password) {

        UserDetails userDetails = this.loadUserByUsername(email);

        if (userDetails==null){
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(email,userDetails.getPassword(),userDetails.getAuthorities());
    }
}