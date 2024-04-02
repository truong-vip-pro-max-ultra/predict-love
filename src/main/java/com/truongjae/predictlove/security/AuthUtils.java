package com.truongjae.predictlove.security;

import com.truongjae.predictlove.entity.User;

import java.util.Base64;
import java.util.Date;

public class AuthUtils {
    public static String SECRET = "<3jae<3";
    public static String generateToken(User user){
        String token = user.getUsername()+SECRET+user.getPassword()+SECRET+new Date().toString().replace(" ","");
        for(int i= 0;i<SECRET.length();i++){
            token = Base64.getEncoder().encodeToString(token.getBytes());
        }
        return token;
    }
    public static String decodeToken(String token){
        for(int i= 0;i<SECRET.length();i++){
            token = new String(Base64.getDecoder().decode(token));
        }
        return token;
    }
    public static void main(String[] args) {
        User user = new User();
        user.setUsername("truong");
        user.setPassword("1234");
        String token = generateToken(user);
        System.out.println(token);
        String oldToken = decodeToken(token);
        System.out.println(oldToken);
    }
}
