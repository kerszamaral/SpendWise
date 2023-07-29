package Tests.Managers;

import org.junit.jupiter.api.*;
import static org.junit.Assert.*;
import SpendWise.Managers.UserManager;

public class UserManagerTest {
    private UserManager userManager;

    @BeforeEach
    public void setUpClass() {
        userManager = new UserManager();
    }

    @Test
    public void testUserManager() {
        assertNotNull(userManager);
    }

    @Test
    public void testcreateUser() {
        assertTrue(userManager.createUser("username", "name", "email", "password", 1000, 500));
    }

    @Test
    public void testcreateUserFalse() {
        userManager.createUser("username", "name", "email", "password", 1000, 500);
        assertFalse(userManager.createUser("username", "name", "email", "password", 1000, 500));
    }

    @Test
    public void testvalidateLogin() {
        userManager.createUser("username", "name", "email", "password", 1000, 500);
        assertTrue(userManager.validateLogin("username", "password"));
    }

    @Test
    public void testvalidateLoginFalse() {
        userManager.createUser("username", "name", "email", "password", 1000, 500);
        assertFalse(userManager.validateLogin("username", "password1"));
    }
}
