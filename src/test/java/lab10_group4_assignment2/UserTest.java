package lab10_group4_assignment2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private UserPage user;

    @BeforeEach
    void setUp(){
        user = new UserPage();
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void userLoginSuccessTest() throws IOException {
        String simulatedInput = "2\ntester\ntester\n5\n3";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        user.setinput(inputStream);
        user.initialUserInterface();
        String expectedOutput = "---------------------------------------\n" +
                "Welcome to our system\n" +
                "1. Register\n" +
                "2. Login\n" +
                "3. Exit\n" +
                "Enter your choice: ----- User Login -----\n" +
                "Enter ID key: Enter Password: ---------------------------------------\n" +
                "Welcome to User Management\n" +
                "1. Profile Options\n" +
                "2. View status of scroll\n" +
                "3. Manage your scrolls\n" +
                "4. Scroll Seeker\n" +
                "5. Logout\n" +
                "Enter your choice: Logging out...\n" +
                "1. Register\n" +
                "2. Login\n" +
                "3. Exit\n" +
                "Enter your choice: Exiting...\n";

        assertEquals(expectedOutput,outContent.toString());
    }


    @Test
    public void userLoginFailTest_nullUser() throws IOException {
        String simulatedInput = "2\n999\n999\n3";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        user.setinput(inputStream);
        user.initialUserInterface();
        String expectedOutput = "---------------------------------------\n" +
                "Welcome to our system\n" +
                "1. Register\n" +
                "2. Login\n" +
                "3. Exit\n" +
                "Enter your choice: ----- User Login -----\n" +
                "Enter ID key: Enter Password: User doest exist!\n" +
                "Login failed! Please try again.\n" +
                "1. Register\n" +
                "2. Login\n" +
                "3. Exit\n" +
                "Enter your choice: Exiting...\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void userLoginFailTest_wrongPassword() throws IOException {
        String simulatedInput = "2\ntester\n999\n3";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        user.setinput(inputStream);
        user.initialUserInterface();
        String expectedOutput = "---------------------------------------\n" +
                "Welcome to our system\n" +
                "1. Register\n" +
                "2. Login\n" +
                "3. Exit\n" +
                "Enter your choice: ----- User Login -----\n" +
                "Enter ID key: Enter Password: Login failed! Please try again.\n" +
                "1. Register\n" +
                "2. Login\n" +
                "3. Exit\n" +
                "Enter your choice: Exiting...\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void userRegisterSuccessTest() throws IOException {
        String simulatedInput = "1\n123\n123\n123\n123\n123\n5\n3";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        user.setinput(inputStream);
        user.initialUserInterface();
        String expectedOutput = "---------------------------------------\n" +
                "Welcome to our system\n" +
                "1. Register\n" +
                "2. Login\n" +
                "3. Exit\n" +
                "Enter your choice: ----- User Registration -----\n" +
                "Enter ID key: Enter Name: Enter Email: Enter Phone Number: Enter Password: Registered user successfully!\n" +
                "Logging you in...\n" +
                "---------------------------------------\n" +
                "Welcome to User Management\n" +
                "1. Profile Options\n" +
                "2. View status of scroll\n" +
                "3. Manage your scrolls\n" +
                "4. Scroll Seeker\n" +
                "5. Logout\n" +
                "Enter your choice: Logging out...\n" +
                "1. Register\n" +
                "2. Login\n" +
                "3. Exit\n" +
                "Enter your choice: Exiting...\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void userRegisterFailTest_alreadyExist() throws IOException {
        String simulatedInput = "1\ntester\n123\n123\n123\n123\n3";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        user.setinput(inputStream);
        user.initialUserInterface();
        String expectedOutput = "---------------------------------------\n" +
                "Welcome to our system\n" +
                "1. Register\n" +
                "2. Login\n" +
                "3. Exit\n" +
                "Enter your choice: ----- User Registration -----\n" +
                "Enter ID key: Enter Name: Enter Email: Enter Phone Number: Enter Password: The user already exists！\n" +
                "Registration failed!\n" +
                "1. Register\n" +
                "2. Login\n" +
                "3. Exit\n" +
                "Enter your choice: Exiting...\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void viewUserProfileTest() throws IOException {
        String simulatedInput = "2\ntester\ntester\n1\n1\n4\n5\n3"; // Login -> Profile Options -> View Profile -> Logout -> Exit
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        user.setinput(inputStream);
        user.initialUserInterface();
        String expectedOutput = "---------------------------------------\n" +
                "Welcome to our system\n" +
                "1. Register\n" +
                "2. Login\n" +
                "3. Exit\n" +
                "Enter your choice: ----- User Login -----\n" +
                "Enter ID key: Enter Password: ---------------------------------------\n" +
                "Welcome to User Management\n" +
                "1. Profile Options\n" +
                "2. View status of scroll\n" +
                "3. Manage your scrolls\n" +
                "4. Scroll Seeker\n" +
                "5. Logout\n" +
                "Enter your choice: --------- Profile Options ---------\n" +
                "1. View Profile\n" +
                "2. Edit Profile\n" +
                "3. Change Password\n" +
                "4. Return to Main Menu\n" +
                "Enter your choice: ----- User Details -----\n" +
                "ID Key: tester\n" +
                "Name: tester\n" +
                "Email: tester@tester\n" +
                "Phone Number: tester\n" +
                "--------- Profile Options ---------\n" +
                "1. View Profile\n" +
                "2. Edit Profile\n" +
                "3. Change Password\n" +
                "4. Return to Main Menu\n" +
                "Enter your choice: 1. Profile Options\n" +
                "2. View status of scroll\n" +
                "3. Manage your scrolls\n" +
                "4. Scroll Seeker\n" +
                "5. Logout\n" +
                "Enter your choice: Logging out...\n" +
                "1. Register\n" +
                "2. Login\n" +
                "3. Exit\n" +
                "Enter your choice: Exiting...\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void editUserProfileTest() throws IOException {
        String simulatedInput = "2\n123\n123\n1\n2\n321\nNewEmail@domain.com\n9876543210\n4\n5\n3"; // Login -> Profile Options -> Edit Profile -> ... -> Logout -> Exit
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        user.setinput(inputStream);
        user.initialUserInterface();
        String expectedOutput = "---------------------------------------\n" +
                "Welcome to our system\n" +
                "1. Register\n" +
                "2. Login\n" +
                "3. Exit\n" +
                "Enter your choice: ----- User Login -----\n" +
                "Enter ID key: Enter Password: ---------------------------------------\n" +
                "Welcome to User Management\n" +
                "1. Profile Options\n" +
                "2. View status of scroll\n" +
                "3. Manage your scrolls\n" +
                "4. Scroll Seeker\n" +
                "5. Logout\n" +
                "Enter your choice: --------- Profile Options ---------\n" +
                "1. View Profile\n" +
                "2. Edit Profile\n" +
                "3. Change Password\n" +
                "4. Return to Main Menu\n" +
                "Enter your choice: ----- Edit User Profile -----\n" +
                "Your ID key: 123\n" +
                "Enter your new name: Enter your new email: Enter your new phone number: User profile updated successfully!\n" +
                "--------- Profile Options ---------\n" +
                "1. View Profile\n" +
                "2. Edit Profile\n" +
                "3. Change Password\n" +
                "4. Return to Main Menu\n" +
                "Enter your choice: 1. Profile Options\n" +
                "2. View status of scroll\n" +
                "3. Manage your scrolls\n" +
                "4. Scroll Seeker\n" +
                "5. Logout\n" +
                "Enter your choice: Logging out...\n" +
                "1. Register\n" +
                "2. Login\n" +
                "3. Exit\n" +
                "Enter your choice: Exiting...\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void changeUserPasswordSuccessfully() throws IOException {
        String simulatedInput = "2\npassword\npassword\n1\n3\npassword\nnewPassword\n4\n5\n3"; // Login -> Profile Options -> Change Password -> ... -> Logout -> Exit
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        user.setinput(inputStream);
        user.initialUserInterface();
        String expectedOutput = "---------------------------------------\n" +
                "Welcome to our system\n" +
                "1. Register\n" +
                "2. Login\n" +
                "3. Exit\n" +
                "Enter your choice: ----- User Login -----\n" +
                "Enter ID key: Enter Password: ---------------------------------------\n" +
                "Welcome to User Management\n" +
                "1. Profile Options\n" +
                "2. View status of scroll\n" +
                "3. Manage your scrolls\n" +
                "4. Scroll Seeker\n" +
                "5. Logout\n" +
                "Enter your choice: --------- Profile Options ---------\n" +
                "1. View Profile\n" +
                "2. Edit Profile\n" +
                "3. Change Password\n" +
                "4. Return to Main Menu\n" +
                "Enter your choice: ----- Change Password -----\n" +
                "Enter Old Password: Enter New Password: Password changed successfully!\n" +
                "--------- Profile Options ---------\n" +
                "1. View Profile\n" +
                "2. Edit Profile\n" +
                "3. Change Password\n" +
                "4. Return to Main Menu\n" +
                "Enter your choice: 1. Profile Options\n" +
                "2. View status of scroll\n" +
                "3. Manage your scrolls\n" +
                "4. Scroll Seeker\n" +
                "5. Logout\n" +
                "Enter your choice: Logging out...\n" +
                "1. Register\n" +
                "2. Login\n" +
                "3. Exit\n" +
                "Enter your choice: Exiting...\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void changeUserPasswordOldPasswardWrong() throws IOException {
        String simulatedInput = "2\n123\n123\n1\n3\ntest\n4\n5\n3"; // Login -> Profile Options -> Change Password -> ... -> Logout -> Exit
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        user.setinput(inputStream);
        user.initialUserInterface();
        String expectedOutput = "---------------------------------------\n" +
                "Welcome to our system\n" +
                "1. Register\n" +
                "2. Login\n" +
                "3. Exit\n" +
                "Enter your choice: ----- User Login -----\n" +
                "Enter ID key: Enter Password: ---------------------------------------\n" +
                "Welcome to User Management\n" +
                "1. Profile Options\n" +
                "2. View status of scroll\n" +
                "3. Manage your scrolls\n" +
                "4. Scroll Seeker\n" +
                "5. Logout\n" +
                "Enter your choice: --------- Profile Options ---------\n" +
                "1. View Profile\n" +
                "2. Edit Profile\n" +
                "3. Change Password\n" +
                "4. Return to Main Menu\n" +
                "Enter your choice: ----- Change Password -----\n" +
                "Enter Old Password: Incorrect password! Try again.\n" +
                "--------- Profile Options ---------\n" +
                "1. View Profile\n" +
                "2. Edit Profile\n" +
                "3. Change Password\n" +
                "4. Return to Main Menu\n" +
                "Enter your choice: 1. Profile Options\n" +
                "2. View status of scroll\n" +
                "3. Manage your scrolls\n" +
                "4. Scroll Seeker\n" +
                "5. Logout\n" +
                "Enter your choice: Logging out...\n" +
                "1. Register\n" +
                "2. Login\n" +
                "3. Exit\n" +
                "Enter your choice: Exiting...\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void viewStatusOfScrollTest() throws InterruptedException, IOException {
        // This test assumes that the scroll with the name "testScroll" exists in the database.
        String simulatedInput = "2\ntester\ntester\n2";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        user.setinput(inputStream);

        // Start the initialUserInterface method in another thread.
        Thread testThread = new Thread(() -> {
            try {
                user.initialUserInterface();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        testThread.start();

        // Give the thread some time to process the input and generate the output.
        Thread.sleep(1000); // Adjust the sleep time if necessary.

        // Since the output is dynamic, we'll only check for certain keywords or patterns.
        assertTrue(outContent.toString().contains("Please input the scroll name."));

        // Optionally, you can stop the testThread here if necessary.
        testThread.interrupt();
    }


    @Test
    public void manageScrollTest() throws InterruptedException, IOException {
        // This test assumes that the scroll with the name "testScroll" exists in the database.
        String simulatedInput = "2\ntester\ntester\n3";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        user.setinput(inputStream);

        // Start the initialUserInterface method in another thread.
        Thread testThread = new Thread(() -> {
            try {
                user.initialUserInterface();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        testThread.start();

        // Give the thread some time to process the input and generate the output.
        Thread.sleep(1000); // Adjust the sleep time if necessary.

        // Since the output is dynamic, we'll only check for certain keywords or patterns.
        assertTrue(outContent.toString().contains("Choose an option:"));

        // Optionally, you can stop the testThread here if necessary.
        testThread.interrupt();
    }

    @Test
    public void scrollSeekerViewAllTest() throws IOException, InterruptedException {
        // This test assumes that the scroll with the name "testScroll" exists in the database.
        String simulatedInput = "2\ntester\ntester\n4\n1";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        user.setinput(inputStream);

        // Start the initialUserInterface method in another thread.
        Thread testThread = new Thread(() -> {
            try {
                user.initialUserInterface();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        testThread.start();

        // Give the thread some time to process the input and generate the output.
        Thread.sleep(1000); // Adjust the sleep time if necessary.

        // Since the output is dynamic, we'll only check for certain keywords or patterns.
        assertTrue(outContent.toString().contains("Virtual Library: "));

        // Optionally, you can stop the testThread here if necessary.
        testThread.interrupt();
    }

    @Test
    public void scrollSeekerDonwloadTest() throws IOException, InterruptedException {
        // This test assumes that the scroll with the name "testScroll" exists in the database.
        String simulatedInput = "2\ntester\ntester\n4\n2";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        user.setinput(inputStream);

        // Start the initialUserInterface method in another thread.
        Thread testThread = new Thread(() -> {
            try {
                user.initialUserInterface();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        testThread.start();

        // Give the thread some time to process the input and generate the output.
        Thread.sleep(1000); // Adjust the sleep time if necessary.

        // Since the output is dynamic, we'll only check for certain keywords or patterns.
        assertTrue(outContent.toString().contains("id:"));

        // Optionally, you can stop the testThread here if necessary.
        testThread.interrupt();
    }

    @Test
    public void scrollSeekerSearchTest() throws IOException, InterruptedException {
        // This test assumes that the scroll with the name "testScroll" exists in the database.
        String simulatedInput = "2\ntester\ntester\n4\n3";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        user.setinput(inputStream);

        // Start the initialUserInterface method in another thread.
        Thread testThread = new Thread(() -> {
            try {
                user.initialUserInterface();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        testThread.start();

        // Give the thread some time to process the input and generate the output.
        Thread.sleep(1000); // Adjust the sleep time if necessary.

        // Since the output is dynamic, we'll only check for certain keywords or patterns.
        assertTrue(outContent.toString().contains("choose the filters:"));

        // Optionally, you can stop the testThread here if necessary.
        testThread.interrupt();
    }

    @Test
    public void scrollSeekerPreviewTest() throws IOException, InterruptedException {
        // This test assumes that the scroll with the name "testScroll" exists in the database.
        String simulatedInput = "2\ntester\ntester\n4\n4";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        user.setinput(inputStream);

        // Start the initialUserInterface method in another thread.
        Thread testThread = new Thread(() -> {
            try {
                user.initialUserInterface();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        testThread.start();

        // Give the thread some time to process the input and generate the output.
        Thread.sleep(1000); // Adjust the sleep time if necessary.

        // Since the output is dynamic, we'll only check for certain keywords or patterns.
        assertTrue(outContent.toString().contains("Please input the scroll id:"));

        // Optionally, you can stop the testThread here if necessary.
        testThread.interrupt();
    }
}
