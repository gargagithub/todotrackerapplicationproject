package com.example.userauthentication.service;


import com.example.userauthentication.model.User;
import com.example.userauthentication.service.SecurityTokenGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityTokenGeneratorImpl implements SecurityTokenGenerator {

    @Override
    public Map<String, String> generateToken(User user) {

// compact- actually builds the JWT and serialize it to a compact , URL-safe string according to the JWT compact serialization rules
        String jwttoken= Jwts.builder().setSubject(user.getUserEmail())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"secretkey").compact();
        Map<String,String> map=new HashMap<>();
        map.put("token",jwttoken);
        map.put("message","User successfully logged in");
        return map;

    }

    // Jwts.builder() -it returns a new JwtBuilder instance that can be configured and then used to create JWT compact serialized strings.

// setSubject- sets the JWT claims sub(subject value )

//  setIssuedAt- sets the JWT claims iat( issued at) value

//signWith - sign the constructed JWT using some algorithm and with the specified key

}
