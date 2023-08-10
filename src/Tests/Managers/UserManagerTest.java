package Tests.Managers;

import org.junit.jupiter.api.*;
import static org.junit.Assert.*;

import SpendWise.Logic.User;
import SpendWise.Logic.Managers.UserManager;

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
        User user = new User("username", "name", "email", "password", 1000, 500);
        assertTrue(userManager.createUser(user));
    }

    @Test
    public void testcreateUserFalse() {
        User user = new User("username", "name", "email", "password", 1000, 500);
        assertFalse(userManager.createUser(user));
    }

    @Test
    public void testvalidateLogin() {
        User user = new User("username", "name", "email", "password", 1000, 500);
        userManager.createUser(user);
        assertTrue(userManager.validateLogin("username", "password"));
    }

    @Test
    public void testvalidateLoginFalse() {
        User user = new User("username", "name", "email", "password", 1000, 500);
        userManager.createUser(user);
        assertFalse(userManager.validateLogin("username", "password1"));
    }
}
