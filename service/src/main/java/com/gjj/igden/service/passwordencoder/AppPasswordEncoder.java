package com.gjj.igden.service.passwordencoder;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AppPasswordEncoder extends Md5PasswordEncoder {

    private static AppPasswordEncoder encoder;


    @Override
    public String encodePassword(String rawPass, Object salt) {
        return super.encodePassword(rawPass, salt);
    }


    public static AppPasswordEncoder getInstance() {
        if (AppPasswordEncoder.encoder == null) {
            AppPasswordEncoder.encoder = new AppPasswordEncoder();
        }
        return AppPasswordEncoder.encoder;
    }


    public static String generatePassword(String rawPass, String salt) {
        return AppPasswordEncoder.getInstance().encodePassword(rawPass, salt);
    }

    public static void main(String[] args){
        //ea7cb1c6afc7d2d397f3c63c51b21a41
        System.out.println(AppPasswordEncoder.generatePassword("quant", "QuantResearch"));
    }

}

