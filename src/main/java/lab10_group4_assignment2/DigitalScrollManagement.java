package lab10_group4_assignment2;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class DigitalScrollManagement {

    VirtualLibrary library = new VirtualLibrary();


    Scanner scanner = new Scanner(System.in);
    boolean fileExists = false;
    String filePath = "";
    int count = 1;

    private static int maxTries = 3;


    static boolean is_Binary(byte[] buffer) {
        String content = new String(buffer, StandardCharsets.UTF_8);
        for (char ch : content.toCharArray()) {
            if (ch != '0' && ch != '1' && ch != ' ' && ch != '\n' && ch != '\r' && ch != '\t') {
                return false;
            }
        }
        return true;
    }

    public static String binaryToString(String binary) {
        StringBuilder output = new StringBuilder();
        String[] binaries = binary.split(" ");

        for (String s : binaries) {
            output.append((char) Integer.parseInt(s, 2));
        }

        return output.toString();
    }


    private static void editScroll(VirtualLibrary library, Scanner scanner, int maxTries, String uploader_name) throws IOException {
        int tries = 0;
        while (tries < maxTries) {
            System.out.println("Which scroll do you want to edit (ID): ");
            String editID = scanner.nextLine();
            if (library.isExist(editID)) {
                // 提示用户输入新的内容
                System.out.println("Enter the updated content for the scroll:");
                String newContent = scanner.nextLine();
                library.editDigitalScroll(editID, newContent, uploader_name);

                System.out.println("Scroll updated successfully.");
                System.out.println("----------------------------------------");

                return;
            } else {
                System.out.println("ERROR! Scroll does not exist!");
                System.out.println("----------------------------------------");

                tries++;
            }
        }
        System.out.println("Max retries reached. Exiting the function.");
        System.out.println("----------------------------------------");
    }


    private static void deleteScroll(VirtualLibrary library, Scanner scanner, int maxTries) throws IOException {
        int tries = 0;
        while (tries < maxTries) {
            System.out.println("Which scroll do you want to delete (ID): ");
            String deleteID = scanner.nextLine();
            if (library.isExist(deleteID)) {
                library.removeDigitalScroll(deleteID);
                System.out.println("Scroll deleted successfully.");
                System.out.println("----------------------------------------");

                return; // Exit the function if successful
            } else {
                System.out.println("ERROR! Scroll does not exist!");
                System.out.println("----------------------------------------");
                tries++;
            }
        }
        System.out.println("Max retries reached. Exiting the function.");
        System.out.println("----------------------------------------");

    }

    public int choiceFromUser(){
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        return choice;
    }

    public static void listFiles(File folder) {
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(file.getName());
                    listFiles(file); //if directory met, iterate in directory and keep searching
                }
            }
        }
    }

    public static String findFilePath(File folder, String fileName) {
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equals(fileName)) {
                    return "src/main/Local/" + fileName;
                } else if (file.isDirectory()) {
                    String filePath = findFilePath(file, fileName);
                    if (filePath != null) {
                        return filePath;
                    }
                }
            }
        }

        return null; // file not found
    }

    public void choice_on_3_2_option(int choice, String uploader_name) throws IOException {
        switch (choice) {
            case 1: // add scroll
                System.out.println("This is your Local directory:");
                String folderPath = "src/main/Local";
                File folder = new File(folderPath);
                String fileName = null;
                if (folder.exists() && folder.isDirectory()) {
                    listFiles(folder);
                } else {
                    System.out.println("Directory does not exist");
                    return;
                }

                do {
                    fileExists = false; // 重置fileExists为false
                    filePath = null;    // 重置filePath为null

                    System.out.println("Choose your uploading file (binary file only):");
                    fileName = scanner.nextLine().trim();
                    filePath = findFilePath(folder, fileName);

                    if (count == maxTries) {
                        System.out.println("Max retries reached. Exiting the program.");
                        System.out.println("----------------------------------------");

                        count = 0;
                        return;
                    }

                    if (filePath != null) {
                        System.out.println(filePath);
                        System.out.println("Uploading file...");
                        fileExists = true; // 如果文件存在，则设置fileExists为true
                    } else {
                        System.out.println("File not found in the directory. Please try again.");
                        System.out.println("----------------------------------------");

                        count++;
                    }
                } while (!fileExists);


                //copy_user_file_into_resource(filePath);


                // for reading binary file
                if (filePath != null){
                    try {
                        // 读取txt文件内容
                        byte[] fileData = Files.readAllBytes(Paths.get(filePath));

                        // 将二进制数据转换为字符串
                        String fileContent = new String(fileData, "UTF-8");

                        // 检查是否只有1, 0 和空格
                        if (is_Binary(fileData)) {
                            System.out.println("Binary file received!");
                            System.out.println("----------------------------------------");

                            String convertedString = binaryToString(fileContent);

                            String filename = fileName.split("\\.")[0];
                            // 假设有一个添加到Scrolls的方法
                            library.addDigitalScrollFromBinary(convertedString, uploader_name,filename);
                            Files.delete(Paths.get(filePath));
                        } else {
                            System.out.println("ERROR! Binary file only");
                            System.out.println("----------------------------------------");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("NO SUCH DIRECTORY!");
                    System.out.println("----------------------------------------");

                }

                break;

            case 2: // edit and update
                editScroll(library, scanner, maxTries, uploader_name);
                break;

            case 3: // delete
                deleteScroll(library, scanner, maxTries);
                break;
            default:
                System.out.println("Invalid choice. Please select a valid option.");
        }

        // print
//        List<DigitalScroll> scrolls = library.getAllDigitalScrolls();
//        for (DigitalScroll scroll : scrolls) {
//            System.out.println("Name: " + scroll.getName());
//            System.out.println("ID: " + scroll.getId());
//            System.out.println("Content: " + scroll.getContent());
//            System.out.println("------------------------");
//        }
    }


}
