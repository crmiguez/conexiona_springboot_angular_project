package com.practicas.conexiona.rest;

import com.practicas.conexiona.model.JwtRequest;
import com.practicas.conexiona.model.JwtResponse;
import com.practicas.conexiona.security.JwtTokenUtil;
import com.practicas.conexiona.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final String token;

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

         String tokenInitial = jwtTokenUtil.generateToken(userDetails);

        if (jwtTokenUtil.canTokenBeRefreshed(tokenInitial))
            token = jwtTokenUtil.generateToken(userDetails);
        else
            token = tokenInitial;

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody com.practicas.conexiona.model.User user) throws Exception {
        return ResponseEntity.ok(userDetailsService.addUser(user));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
