package Controller;//package Controller;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//class ControllerTest {
//
//    @Test
//    void checkPasswordFormat() {
//        assertFalse(Controller.checkPasswordFormat("Paswrd8"));
//        assertFalse(Controller.checkPasswordFormat("Password"));
//        assertFalse(Controller.checkPasswordFormat("password8"));
//        assertFalse(Controller.checkPasswordFormat("PASSWORD8"));
//        assertFalse(Controller.checkPasswordFormat("Pass"));
//        assertFalse(Controller.checkPasswordFormat("pas8"));
//        assertFalse(Controller.checkPasswordFormat("PAS8"));
//        assertFalse(Controller.checkPasswordFormat("password"));
//        assertFalse(Controller.checkPasswordFormat("PASSWORD"));
//        assertFalse(Controller.checkPasswordFormat("12345678"));
//        assertFalse(Controller.checkPasswordFormat("pass"));
//        assertFalse(Controller.checkPasswordFormat("PASS"));
//        assertFalse(Controller.checkPasswordFormat("1234"));
//        assertFalse(Controller.checkPasswordFormat("*/-+@#"));
//        assertTrue(Controller.checkPasswordFormat("Password8"));
//
//    }
//}