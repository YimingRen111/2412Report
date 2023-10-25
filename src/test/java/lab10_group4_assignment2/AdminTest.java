package lab10_group4_assignment2;

import org.junit.jupiter.api.BeforeEach;

import java.util.NoSuchElementException;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private AdminPage admin;
    @BeforeEach
    void setUp(){
        admin = new AdminPage();
        System.setOut(originalOut);
        System.setIn(originalIn);
    }
    @Test
    public void adminloginPage() throws IOException {
        String simulatedInput = "1\n1\n7\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        admin.setinput();
        admin.adminLoginPage();
        String expectedOutput ="Please input your Username:\n" +
                "Please input your password:\n" +
                "Login Success!\n" +
                "What can I help you Admin\n" +
                "1.View a list of all users and their profiles\n" +
                "2.Add User\n" +
                "3.Delete User\n" +
                "4.View status of scroll\n" +
                "5.Manage your scrolls\n" +
                "6.Scroll Seeker\n" +
                "7.Return home page\n" +
                "8.Add Admin(Main admin only)\n" +
                "9.Delete Admin(Main admin only)\n"+
                " \n" +
                "Goodbye Admin\n";
        assertEquals(expectedOutput,outContent.toString());
    }
    @Test
    public void adminloginFailPage() throws IOException {
        String simulatedInput = "1\n123\n2";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        admin.setinput();
        admin.adminLoginPage();

        assertTrue(outContent.toString().contains("Your username or password is wrong!"));
        assertTrue(outContent.toString().contains("Do you want to stay at admin login page?"));
    }
    @Test
    public void adminloginFailTwicePage() throws IOException {
        String simulatedInput = "1\n123\n1\n1\n123\n2";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        admin.setinput();
        admin.adminLoginPage();
        assertTrue(outContent.toString().contains("Your username or password is wrong!"));
        assertTrue(outContent.toString().contains("Do you want to stay at admin login page?"));
    }
    @Test
    public void adminloginFailErrorInputPage() throws IOException {
        String simulatedInput = "1\n123\n10\n\n2";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        admin.setinput();
        admin.adminLoginPage();
        assertTrue(outContent.toString().contains("Your username or password is wrong!"));
        assertTrue(outContent.toString().contains("Do you want to stay at admin login page?"));
        assertTrue(outContent.toString().contains("invalid input"));
//        assertEquals("",outContent.toString());
    }
    @Test
    public void adminOption1test() throws IOException {
        String simulatedInput = "1\n1\n1\n7\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        admin.setinput();
        admin.adminLoginPage();
        assertTrue(outContent.toString().contains("Login Success!"));
        assertTrue(outContent.toString().contains("What can I help you Admin"));
        assertTrue(outContent.toString().contains("0. User:"));
        assertTrue(outContent.toString().contains("Goodbye Admin"));
//        assertEquals("",outContent.toString());
    }
    @Test
    public void adminOption2test() throws IOException {
        String simulatedInput = "1\n1\n2\nusername\npasswoord1\n123@gmail.com\n123456789\nsoft2412\n7\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        admin.setinput();
        admin.adminLoginPage();
        assertTrue(outContent.toString().contains("Login Success!"));
        assertTrue(outContent.toString().contains("What can I help you Admin"));
        assertTrue(outContent.toString().contains("Please input your name:"));
        assertTrue(outContent.toString().contains("Please input your password:"));
        assertTrue(outContent.toString().contains("Please input your idkey:"));
//        assertTrue(outContent.toString().contains("Add user account successfully(IDkey:soft2412"));
        assertTrue(outContent.toString().contains("Goodbye Admin"));
//        assertEquals("",outContent.toString());
    }
    @Test
    public void adminOption3test() throws IOException {
        String simulatedInput = "1\n1\n3\nsoft2412\n7\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        admin.setinput();
        admin.adminLoginPage();
        assertTrue(outContent.toString().contains("Login Success!"));
        assertTrue(outContent.toString().contains("What can I help you Admin"));
        assertTrue(outContent.toString().contains("Please input the user IDkey to delete:(input # to cancel)"));
        assertTrue(outContent.toString().contains("Goodbye Admin"));
        assertTrue(outContent.toString().contains("0. User:"));
        assertTrue(outContent.toString().contains("Success remove soft2412."));
//        assertEquals("",outContent.toString());
    }
    @Test
    public void adminOption3andCanceltest() throws IOException {
        String simulatedInput = "1\n1\n3\n#\n7\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        admin.setinput();
        admin.adminLoginPage();
        assertTrue(outContent.toString().contains("Login Success!"));
        assertTrue(outContent.toString().contains("What can I help you Admin"));
        assertTrue(outContent.toString().contains("Please input the user IDkey to delete:(input # to cancel)"));
        assertTrue(outContent.toString().contains("Goodbye Admin"));
        assertTrue(outContent.toString().contains("0. User:"));
        assertTrue(outContent.toString().contains("Cancel delete user."));
//        assertEquals("",outContent.toString());
    }
    @Test
    public void adminOption8Failtest() throws IOException {
        String simulatedInput = "1\n1\n8\n7\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        admin.setinput();
        admin.adminLoginPage();
        assertTrue(outContent.toString().contains("Login Success!"));
        assertTrue(outContent.toString().contains("What can I help you Admin"));

        assertTrue(outContent.toString().contains("Goodbye Admin"));
        assertTrue(outContent.toString().contains("You don't have the access."));

//        assertEquals("",outContent.toString());
    }
    @Test
    public void adminOption8test() throws IOException {
        String simulatedInput = "Taozhao\npassword\n8\n1\n1\n7\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        admin.setinput();
        admin.adminLoginPage();
        assertTrue(outContent.toString().contains("Login Success!"));
        assertTrue(outContent.toString().contains("What can I help you Admin"));

        assertTrue(outContent.toString().contains("Goodbye Admin"));
        assertTrue(outContent.toString().contains("Please input your name:"));
        assertTrue(outContent.toString().contains("The admin user already exists！"));
        assertTrue(outContent.toString().contains("Please input your password:"));

//        assertEquals("",outContent.toString());
    }
    @Test
    public void adminOption8Successtest() throws IOException {
        String simulatedInput = "Taozhao\npassword\n8\n12\n12\n7\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        admin.setinput();
        admin.adminLoginPage();
        assertTrue(outContent.toString().contains("Login Success!"));
        assertTrue(outContent.toString().contains("What can I help you Admin"));

        assertTrue(outContent.toString().contains("Goodbye Admin"));
        assertTrue(outContent.toString().contains("Please input your name:"));
        assertTrue(outContent.toString().contains("Add admin account successfully Username:12"));
        assertTrue(outContent.toString().contains("Please input your password:"));

//        assertEquals("",outContent.toString());
    }
    @Test
    public void adminOption9Successtest() throws IOException {
        String simulatedInput = "Taozhao\npassword\n9\n12\n7\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        admin.setinput();
        admin.adminLoginPage();
        assertTrue(outContent.toString().contains("Login Success!"));
        assertTrue(outContent.toString().contains("What can I help you Admin"));

        assertTrue(outContent.toString().contains("Goodbye Admin"));
        assertTrue(outContent.toString().contains("Admin: Taozhao"));
        assertTrue(outContent.toString().contains("Please input the Admin username to delete:(input # to cancel)"));
        assertTrue(outContent.toString().contains("Success remove 12."));

//        assertEquals("",outContent.toString());
    }
    @Test
    public void adminOption9Failtest() throws IOException {
        String simulatedInput = "1\n1\n9\n7\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        admin.setinput();
        admin.adminLoginPage();
        assertTrue(outContent.toString().contains("Login Success!"));
        assertTrue(outContent.toString().contains("What can I help you Admin"));

        assertTrue(outContent.toString().contains("Goodbye Admin"));
        assertTrue(outContent.toString().contains("You don't have the access."));

//        assertEquals("",outContent.toString());
    }
    @Test
    public void adminOptionFailtest() throws IOException {
        String simulatedInput = "1\n1\n100\n7\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        admin.setinput();
        admin.adminLoginPage();
        assertTrue(outContent.toString().contains("Login Success!"));
        assertTrue(outContent.toString().contains("What can I help you Admin"));

        assertTrue(outContent.toString().contains("Goodbye Admin"));
        assertTrue(outContent.toString().contains("Error input, please input 1 ~ 9."));

//        assertEquals("",outContent.toString());
    }
    @Test
    public void adminOption9andCanceltest() throws IOException {
        String simulatedInput = "Taozhao\npassword\n9\n#\n7\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        admin.setinput();
        admin.adminLoginPage();
        assertTrue(outContent.toString().contains("Login Success!"));
        assertTrue(outContent.toString().contains("What can I help you Admin"));
        assertTrue(outContent.toString().contains("Please input the Admin username to delete:(input # to cancel)"));
        assertTrue(outContent.toString().contains("Goodbye Admin"));

        assertTrue(outContent.toString().contains("Cancel delete Admin."));
//        assertEquals("",outContent.toString());
    }
}
