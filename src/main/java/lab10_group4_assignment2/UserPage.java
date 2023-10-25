package lab10_group4_assignment2;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class UserPage {
    private static DatabaseOperator databaseOperator = new DatabaseOperator("src/main/resources/UserDatabase.json");
    private Scanner scanner = new Scanner(System.in);
    private String ID;
    private String userName;
    private static DatabaseOperator scrollinfo = new DatabaseOperator("src/main/resources/ScrollDatabase.json");
    private static DigitalScrollManagement DSM = new DigitalScrollManagement();
    private static Scanner s = new Scanner(System.in);
    public void initialUserInterface() throws IOException {
        System.out.println("---------------------------------------");
        System.out.println("Welcome to our system");
        try {
            databaseOperator.loadJSON();
            scrollinfo.loadJSON();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int initialChoice = Integer.parseInt(scanner.nextLine());

            switch (initialChoice) {
                case 1:
                    if(displayRegistrationUI()){
                        displayUserUI(); // After registration, user is taken to the main UI
                        break;
                    }
                    break;
                case 2:
                    if (displayLoginUI()) {
                        displayUserUI(); // After successful login, user is taken to the main UI
                    } else {
                        System.out.println("Login failed! Please try again.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public boolean displayRegistrationUI() throws IOException {
        System.out.println("----- User Registration -----");
        System.out.print("Enter ID key: ");
        String customID = scanner.nextLine();
        System.out.print("Enter Name: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        if(databaseOperator.registerUser(customID, password, fullName, email, phoneNumber)){
            this.ID = customID;
            this.userName = fullName;
            System.out.println("Logging you in...");
            return true;
        }else{
            System.out.println("Registration failed!");
            return false;
        }
    }

    public boolean displayLoginUI() throws IOException {
        System.out.println("----- User Login -----");
        System.out.print("Enter ID key: ");
        String customID = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        this.ID = customID;
        JSONObject userDetails = databaseOperator.getUserDetails(customID);
        try{
            this.userName = userDetails.getString("name");
            return (databaseOperator.userLogin(customID, password));
        }catch(NullPointerException e){
            System.out.println("User doest exist!");
            return false;
        }

    }

    public void displayUserUI() throws IOException {
        System.out.println("---------------------------------------");
        System.out.println("Welcome to User Management");
        while (true) {
            System.out.println("1. Profile Options");
            System.out.println("2. View status of scroll");
            System.out.println("3. Manage your scrolls");
            System.out.println("4. Scroll Seeker");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    displayProfileOptions();
                    break;
                case 2:
                    viewStatusOfScroll();
                    break;
                case 3:
                    manageYourScrolls();
                    break;
                case 4:
                    scrollSeeker();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void displayProfileOptions() throws IOException {
        while (true) {
            System.out.println("--------- Profile Options ---------");
            System.out.println("1. View Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. Change Password");
            System.out.println("4. Return to Main Menu");
            System.out.print("Enter your choice: ");

            int profileChoice = Integer.parseInt(scanner.nextLine());
            switch (profileChoice) {
                case 1:
                    displayUserDetails(this.ID);
                    break;
                case 2:
                    displayEditProfileUI();
                    break;
                case 3:
                    displayChangePasswordUI();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void displayEditProfileUI() throws IOException {
        System.out.println("----- Edit User Profile -----");
        System.out.print("Your ID key: " + this.ID + "\n");
        System.out.print("Enter your new name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter your new email: ");
        String newEmail = scanner.nextLine();
        String newPhone;
        while (true) {
            System.out.print("Enter your new phone number: ");
            newPhone = scanner.nextLine();
            if (newPhone.matches("^\\d+$")) {
                break;
            } else {
                System.out.println("Phone number should only contain digits.");
                System.out.print("Enter new Phone Number again: ");
            }
        }
        if (databaseOperator.updateUserProfile(this.ID, newName, newEmail, newPhone)) {
            System.out.println("User profile updated successfully!");
        } else {
            System.out.println("Failed to update user profile. Please try again.");
        }
    }

    public void displayChangePasswordUI() throws IOException {
        System.out.println("----- Change Password -----");
        String customID = this.ID;
        boolean passwordCorrect = false;
        while (!passwordCorrect) {
            System.out.print("Enter Old Password: ");
            String oldPassword = scanner.nextLine();
            if (databaseOperator.userLogin(customID, oldPassword)) {
                passwordCorrect = true;
                System.out.print("Enter New Password: ");
                String newPassword = scanner.nextLine();
                databaseOperator.changeUserPassword(customID, oldPassword, newPassword);
            } else {
                System.out.println("Incorrect password! Try again.");
                return;
            }
        }
    }

    public void displayUserDetails(String customID) throws IOException {
        JSONObject userDetails = databaseOperator.getUserDetails(customID);
        if (userDetails != null) {
            System.out.println("----- User Details -----");
            System.out.println("ID Key: " + userDetails.getString("IDkey"));
            System.out.println("Name: " + userDetails.getString("name"));
            System.out.println("Email: " + userDetails.getString("email"));
            System.out.println("Phone Number: " + userDetails.getString("phone"));
        } else {
            System.out.println("User details not found!");
        }
    }

    public void viewStatusOfScroll() throws IOException {
        scrollinfo.printscroll();
        System.out.println("Please input the scroll name.");
        String scrollname = scanner.nextLine();
        scrollinfo.scrollstatus(scrollname);
    }

    public void manageYourScrolls() throws IOException {
        System.out.println("Choose an option:");
        System.out.println("1. Add Scroll");
        System.out.println("2. Edit Scroll");
        System.out.println("3. Delete Scroll");
        DSM.choice_on_3_2_option(DSM.choiceFromUser(), this.userName);
    }

    public void scrollSeeker() throws IOException {
        System.out.println("Choose an option:");
        System.out.println("1. view all");
        System.out.println("2. download");
        System.out.println("3. search");
        System.out.println("4. preview");

        ScrollSeeker seeker = new ScrollSeeker(DSM);
        String option = scanner.nextLine();
        if (option.equals("1")){
            seeker.noFilter();
            seeker.viewAll();
        }
        else if (option.equals("2")){
            System.out.println("id: ");
            String id = scanner.nextLine();
            seeker.download(id);
        }
        else if (option.equals("3")){
            System.out.println("choose the filters: ");
            System.out.println("1. uploader ID");
            System.out.println("2. scroll ID");
            System.out.println("3. name");
            System.out.println("4. upload date");
            String filter = scanner.nextLine();

            if (filter.equals("1")){
                System.out.println("Please input the username (case-sensitive): ");
                String uploaderName = scanner.nextLine();
                seeker.scrolls = seeker.uploaderFilter(uploaderName);
            }

            else if (filter.equals("2")) {
                System.out.println("ID: ");
                String scrollID = scanner.nextLine();
                seeker.scrolls = seeker.scrollIDFilter(scrollID);
            }
            else if (filter.equals("3")) {
                System.out.println("Name: ");
                String name = scanner.nextLine();
                seeker.scrolls = seeker.nameFilter(name);
            }
            else if (filter.equals("4")) {
                System.out.println("Please input the upload date (yyyy/mm/dd):");
                String date = scanner.nextLine();
                seeker.scrolls = seeker.uploadDateFilter(date);
            }
            else{
                System.out.println("invalid filter option");
            }

            seeker.viewAll();
            System.out.println();

            // reset at the end
            seeker.noFilter();
        }
        else if (option.equals("4")){
            System.out.println("Please input the scroll id: ");
            String id = scanner.nextLine();
            System.out.println(seeker.preview(id));
        }
        else{
            System.out.println("invalid option");
        }
    }

    public void GuestUserPage() throws IOException {
        ScrollSeeker seeker = new ScrollSeeker(DSM);
        seeker.viewAll();
    }

    public void setinput(InputStream inputStream){
        scanner = new Scanner(inputStream);
    }


}
