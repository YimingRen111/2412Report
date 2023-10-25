package lab10_group4_assignment2;

import java.io.IOException;
import java.util.Scanner;

public class AdminPage {
    private static final String FILE_PATH = "src/main/resources/AdminDatabase.json";
    private static DatabaseOperator dataset = new DatabaseOperator(FILE_PATH);
    private static DatabaseOperator userinfo = new DatabaseOperator("src/main/resources/UserDatabase.json");
    private static DatabaseOperator scrollinfo = new DatabaseOperator("src/main/resources/ScrollDatabase.json");
    private static Scanner s = new Scanner(System.in);

    private boolean isMainadmin = false;
    private String adminname;
    private static DigitalScrollManagement DSM = new DigitalScrollManagement();

    private String option;
    public AdminPage(){
        try {
            dataset.loadJSON();
            userinfo.loadJSON();
            scrollinfo.loadJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adminLoginPage() throws IOException {
//        s = new Scanner(System.in);

        if(adminLogin() == true){
            isMainadmin = dataset.isMain(adminname);
            System.out.println("What can I help you Admin");
            adminPage();
        }
        else{
            System.out.println("Your username or password is wrong!");
            handleInvalidInput();
        }
    }

    public void setinput(){
        s = new Scanner(System.in);
    }
    public boolean adminLogin() throws IOException {

//        s = new Scanner(System.in);
        System.out.println("Please input your Username:");
        adminname = s.nextLine();
        System.out.println("Please input your password:");
        String password = s.nextLine();

        return dataset.adminlogin(adminname,password);
    }
    public void handleInvalidInput() throws IOException {
        System.out.println("Do you want to stay at admin login page?");
        System.out.println("1.Yes");
        System.out.println("2.Return Main Page");
        String option = s.nextLine();

        if (option.equals("1")) {
            adminLoginPage();
        } else if (option.equals("2")) {
            return;
        } else {
            System.out.println("invalid input");
            handleInvalidInput();
        }
    }
    public void adminPage() throws IOException {
        while(true){
            System.out.println("1.View a list of all users and their profiles");
            System.out.println("2.Add User");
            System.out.println("3.Delete User");
            System.out.println("4.View status of scroll");
            System.out.println("5.Manage your scrolls");
            System.out.println("6.Scroll Seeker");
            System.out.println("7.Return home page");
            System.out.println("8.Add Admin(Main admin only)");
            System.out.println("9.Delete Admin(Main admin only)");
            System.out.println(" ");
            option = s.nextLine();
            if(option.equals("1")){
                userinfo.viewUserinfo();
            }
            else if(option.equals("2")){
                System.out.println("Please input your name:");
                String name = s.nextLine();
                System.out.println("Please input your password:");
                String password = s.nextLine();
                System.out.println("Please input your email:");
                String email = s.nextLine();
                System.out.println("Please input your phone:");
                String phone = s.nextLine();
                System.out.println("Please input your idkey:");
                String idkey = s.nextLine();
                userinfo.addUser(name,password,email,phone,idkey);
            }
            else if(option.equals("3")){
                userinfo.printUserIDkey();
                System.out.println("Please input the user IDkey to delete:(input # to cancel)");
                String idkey = s.nextLine();
                if(idkey.equals("#")){
                    System.out.println("Cancel delete user.");
                }
                else{
                    userinfo.deleteUser(idkey);
                }
                //guest user
            }
            else if(option.equals("4")){
                if(scrollinfo.printscroll()){
                    System.out.println("Please input the scroll name.");
                    String scrollname = s.nextLine();
                    scrollinfo.scrollstatus(scrollname);
                }


            }
            else if(option.equals("5")){
                // manege scrolls
                System.out.println("Choose an option:");
                System.out.println("1. Add Scroll");
                System.out.println("2. Edit Scroll");
                System.out.println("3. Delete Scroll");

                DSM.choice_on_3_2_option(DSM.choiceFromUser(), adminname);
            }
            else if(option.equals("6")){
                System.out.println("Choose an option:");
                System.out.println("1. view all");
                System.out.println("2. download");
                System.out.println("3. search");
                System.out.println("4. preview");
                System.out.println("5. back");

                ScrollSeeker seeker = new ScrollSeeker(DSM);
                String option = s.nextLine();

                while (!option.equals("5")){
                    if (option.equals("1")){
                        seeker.noFilter();
                        seeker.viewAll();
                    }
                    else if (option.equals("2")){
                        System.out.println("Download Options:");
                        System.out.println("1. download newest");
                        System.out.println("2. download history");
                        String downloadOption = s.nextLine();

                        if (downloadOption.equals("1")){
                            System.out.println("please input the scroll id: ");
                            String id = s.nextLine();
                            seeker.download(id);
                        }
                        else if (downloadOption.equals("2")){
                            System.out.println("please input the scroll id: ");
                            String id = s.nextLine();
                            seeker.downloadHistory(id);
                        }
                        else{
                            System.out.println("invalid option");
                        }

                    }
                    else if (option.equals("3")){
                        System.out.println("choose the filters: ");
                        System.out.println("1. uploader");
                        System.out.println("2. scroll ID");
                        System.out.println("3. name");
                        System.out.println("4. upload date");
                        String filter = s.nextLine();

                        if (filter.equals("1")){
                            System.out.println("Please input the username (case-sensitive): ");
                            String uploaderName = s.nextLine();
                            seeker.scrolls = seeker.uploaderFilter(uploaderName);
                        }

                        else if (filter.equals("2")) {
                            System.out.println("Please input the scroll id: ");
                            String scrollID = s.nextLine();
                            seeker.scrolls = seeker.scrollIDFilter(scrollID);
                        }
                        else if (filter.equals("3")) {
                            System.out.println("Please input the name (case-sensitive): ");
                            String name = s.nextLine();
                            seeker.scrolls = seeker.nameFilter(name);
                        }
                        else if (filter.equals("4")) {
                            System.out.println("Please input the upload date (yyyy/mm/dd):");
                            String date = s.nextLine();
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
                        String id = s.nextLine();
                        System.out.println(seeker.preview(id));
                    }
                    else{
                        System.out.println("invalid option");
                    }

                    System.out.println("Choose an option:");
                    System.out.println("1. view all");
                    System.out.println("2. download");
                    System.out.println("3. search");
                    System.out.println("4. preview");
                    System.out.println("5. back");
                    option = s.nextLine();
                }

            }
            else if(option.equals("7")){
                System.out.println("Goodbye Admin");
                break;
            }
            else if(option.equals("8")){
                if (isMainadmin){
                    System.out.println("Please input your name:");
                    String name = s.nextLine();
                    System.out.println("Please input your password:");
                    String password = s.nextLine();
                    dataset.addAdmin(name,password);
                }
                else{
                    System.out.println("You don't have the access.");
                }
            }
            else if(option.equals("9")){
                if (isMainadmin){
                    dataset.printAdmin();
                    System.out.println("Please input the Admin username to delete:(input # to cancel)");
                    String idkey = s.nextLine();
                    if(idkey.equals("#")){
                        System.out.println("Cancel delete Admin.");
                    }
                    else{
                        dataset.deleteAdmin(idkey);
                    }
                }
                else{
                    System.out.println("You don't have the access.");
                }
            }
            else{
                System.out.println("Error input, please input 1 ~ 9.");
            }

        }

    }
}
