//package modules;
//
//import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.test.JerseyTest;
//import org.junit.Before;
//import org.junit.Test;
//
//import javax.ws.rs.core.Application;
//
//import static org.junit.Assert.*;
//
///**
// * Created by AndersWOlsen on 11-06-2017.
// */
//public class UserModuleTest extends JerseyTest {
//    @Override
//    protected Application configure() {
//        return new ResourceConfig(UserModule.class);
//    }
//
//    @Test
//    public void getUser() throws Exception {
////        final String userJson = "{ }";
////        final String response = target("module/user/get/1").request().get(String.class);
////        System.out.println(response);
//    }
//
//    @Test
//    public void getAllUsers() throws Exception {
//    }
//
//}