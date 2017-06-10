package config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;

/**
 * Created by AndersWOlsen on 10-06-2017.
 */
public class Config {
    // TODO: Something funky is going on when logging in using login.html (tomcat)
    public static final Key AUTH_KEY = MacProvider.generateKey(SignatureAlgorithm.HS512);
}
