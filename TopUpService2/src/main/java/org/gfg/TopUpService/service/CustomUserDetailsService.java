package org.gfg.TopUpService.service;


import org.example.CommonConstants;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       String resp = restTemplate.getForObject("http://localhost:8081/user-service/validate/user?mobileNo="+username,String.class);

        JSONObject response = new JSONObject(resp);

        System.out.println(response);

        String password = response.getString(CommonConstants.USER_PASSWORD);

        UserDetails user = User.builder().username(username).password(password).build();
        return user;
    }
}
