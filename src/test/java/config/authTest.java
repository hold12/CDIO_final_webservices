package config;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by AndersWOlsen on 12-06-2017.
 */
public class authTest {
    @Test
    public void enumROOT_returnsAuth() throws Exception {
        assertEquals("auth", Routes.AUTH_ROOT);
    }

    @Test
    public void enumAUTHENTICATION_returnsAuthAuthentication() throws Exception {
        assertEquals("authentication", Routes.AUTH_AUTHENTICATION);
    }
}