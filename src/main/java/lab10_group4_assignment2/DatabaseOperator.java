package lab10_group4_assignment2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class DatabaseOperator {

    private JSONObject jsonObject;

    private final String testFilePath;

    public DatabaseOperator(String testFilePath) {
        this.testFilePath = testFilePath;
    }

    public void loadJSON() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(testFilePath)));
        jsonObject = new JSONObject(content);
    }

    private String generateRandomSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16]; // 16字节的随机盐值
        random.nextBytes(saltBytes);
        return Hex.encodeHexString(saltBytes); // 将随机盐值转换为十六进制字符串
    }

    public static String hashPassword(String password, String salt) {
        String saltedPassword = password + salt;
        return DigestUtils.sha256Hex(saltedPassword);
    }

    //
    public static boolean validatePassword(String inputPassword,String salt,String storedPasswordHash ) {
        String inputPasswordHash = hashPassword(inputPassword, salt);
        return storedPasswordHash.equals(inputPasswordHash);
    }

    //add admin
    public boolean addAdmin(String username,String password) throws IOException {
        loadJSON();
        if (jsonObject != null) {
            JSONArray admin_account = jsonObject.getJSONArray("admin_account");
            if (admin_account != null) {
                for (int i = 0; i < admin_account.length(); i++) {
                    if (admin_account.getJSONObject(i).getString("username").equals(username)) {
                        System.out.println("The admin user already exists！");
                        return false;
                    }
                }
                JSONObject admin = new JSONObject();

                String salt = generateRandomSalt();
                String hashedPassword = hashPassword(password, salt);
                admin.put("username", username);
                admin.put("password",hashedPassword);
                admin.put("salt",salt);
                admin.put("isMain",false);
                admin_account.put(admin);

                saveJSON();
                System.out.println("Add admin account successfully Username:" + username);
                return true;
            } else {

                System.out.println("Can't connect the admin database");
            }
        } else {

            System.out.println("Can't connect the admin database");
        }

        return false;
    }
    //admin login
    public boolean adminlogin(String username,String password) throws IOException {
        loadJSON();
        if (jsonObject != null) {
            JSONArray admin_account = jsonObject.getJSONArray("admin_account");
            if (admin_account != null) {
                for (int i = 0; i < admin_account.length(); i++) {
                    if(username.equals(admin_account.getJSONObject(i).getString("username"))){

                        String salt = admin_account.getJSONObject(i).getString("salt");
                        String storepassword = admin_account.getJSONObject(i).getString("password");
                        if (validatePassword(password,salt,storepassword)) {
                            System.out.println("Login Success!");
                            return true;
                        }
                    }
                }

            } else {

                System.out.println("Can't connect the admin database");
            }

        } else {

            System.out.println("Can't connect the admin database");
        }

        return false;
    }
    public boolean printUserIDkey() throws IOException {
        loadJSON();
        if(jsonObject != null){
            JSONArray user_info = jsonObject.getJSONArray("User");
            if(user_info != null){
                for(int i = 0;i < user_info.length();i++){
                    String idkey = user_info.getJSONObject(i).getString("IDkey");
                    System.out.println(i+". User: "+idkey);
                }
            }
            else{
                System.out.println("Can't connect the user database");
                return false;
            }
        }
        else{
            System.out.println("Can't connect the user database");
            return false;
        }
        return true;
    }
    public boolean deleteUser(String idkey) throws IOException {
        loadJSON();
        if(jsonObject != null){
            JSONArray user_info = jsonObject.getJSONArray("User");
            if(user_info != null){
                for(int i = 0;i < user_info.length();i++){
                    if (idkey.equals(user_info.getJSONObject(i).getString("IDkey"))){
                        user_info.remove(i);
                        saveJSON();
                        System.out.println("Success remove "+ idkey+".");
                        return true;
                    }
                }
            }
            System.out.println("Can't find the "+idkey+" user.");
            return false;
        }
        else{
            System.out.println("Can't connect the user database");
            return false;
        }
    }
    public boolean viewUserinfo() throws IOException {
        loadJSON();
        if(jsonObject != null){
            JSONArray user_info = jsonObject.getJSONArray("User");
            if(user_info != null){
                for(int i = 0;i < user_info.length();i++){
                    String name = user_info.getJSONObject(i).getString("name");
                    String phone = user_info.getJSONObject(i).getString("phone");
                    String email = user_info.getJSONObject(i).getString("email");
                    String idkey = user_info.getJSONObject(i).getString("IDkey");
                    System.out.println(i+". User: "+idkey+" full name is "+ name+" phone number is "+ phone+" Email address is "+ email);
                }
            }
            else{
                System.out.println("Can't connect the user database");
                return false;
            }
        }
        else{
            System.out.println("Can't connect the user database");
            return false;
        }
        return true;
    }
    public boolean addUser(String fullname,String password,String email,String phone,String idkey) throws IOException {
        loadJSON();
        if (jsonObject != null) {
            JSONArray user_info = jsonObject.getJSONArray("User");

            if (user_info != null) {
                for (int i = 0; i < user_info.length(); i++) {
                    if (user_info.getJSONObject(i).getString("IDkey").equals(idkey)) {
                        System.out.println("The user already exists！");
                        return false;
                    }
                }
                JSONObject user = new JSONObject();

                user.put("name", fullname);
                user.put("password",password);
                user.put("IDkey",idkey);
                user.put("email",email);
                user.put("phone",phone);
                user_info.put(user);
                saveJSON();
                System.out.println("Add user account successfully(IDkey:" + idkey);
                return true;
            } else {
                System.out.println("Can't connect the user database error01");
            }
        } else {
            System.out.println("Can't connect the user database error02");
        }

        return false;
    }

    // Register user
    public boolean registerUser(String customID, String password, String fullName, String email, String phoneNumber) throws IOException {
        loadJSON();
        if (jsonObject != null) {
            JSONArray user_info = jsonObject.getJSONArray("User");

            if (user_info != null) {
                for (int i = 0; i < user_info.length(); i++) {
                    if (user_info.getJSONObject(i).getString("IDkey").equals(customID)) {
                        System.out.println("The user already exists！");
                        return false;
                    }
                }
                JSONObject user = new JSONObject();

                String salt = generateRandomSalt();
                String hashedPassword = hashPassword(password, salt);

                user.put("name", fullName);
                user.put("password", hashedPassword);
                user.put("salt", salt);
                user.put("IDkey", customID);
                user.put("email", email);
                user.put("phone", phoneNumber);
                user_info.put(user);
                saveJSON();
                System.out.println("Registered user successfully!");
                return true;
            } else {
                System.out.println("Can't connect to the user database, error 01");
            }
        } else {
            System.out.println("Can't connect to the user database, error 02");
        }
        return false;
    }

    // User login method
    public boolean userLogin(String customID, String password) throws IOException {
        loadJSON();
        if (jsonObject != null) {
            JSONArray user_info = jsonObject.getJSONArray("User");
            for (int i = 0; i < user_info.length(); i++) {
                JSONObject user = user_info.getJSONObject(i);
                if (customID.equals(user.getString("IDkey"))) {
                    String salt = user.getString("salt");
                    String storedPasswordHash = user.getString("password");
                    if (validatePassword(password, salt, storedPasswordHash)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Update user profile method

    // Update user profile based on their custom ID
    public boolean updateUserProfile(String customID, String newName, String newEmail, String newPhone) throws IOException {
        loadJSON();
        if (jsonObject != null) {
            JSONArray users = jsonObject.getJSONArray("User");
            if (users != null) {
                for (int i = 0; i < users.length(); i++) {
                    if (customID.equals(users.getJSONObject(i).getString("IDkey"))) {
                        users.getJSONObject(i).put("name", newName);
                        users.getJSONObject(i).put("email", newEmail);
                        users.getJSONObject(i).put("phone", newPhone);
                        saveJSON();
                        return true;
                    }
                }
            } else {
                System.out.println("Can't connect to the user database");
            }
        } else {
            System.out.println("Can't connect to the user database");
            return false;
        }
        System.out.println("User profile update failed!");
        return false;
    }

    // Change user password method
    public boolean changeUserPassword(String customID, String oldPassword, String newPassword) throws IOException {
        loadJSON();
        if (jsonObject != null) {
            JSONArray user_info = jsonObject.getJSONArray("User");
            for (int i = 0; i < user_info.length(); i++) {
                JSONObject user = user_info.getJSONObject(i);
                if (customID.equals(user.getString("IDkey"))) {
                    String salt = user.getString("salt");
                    String storedPasswordHash = user.getString("password");
                    if (validatePassword(oldPassword, salt, storedPasswordHash)) {
                        String newHashedPassword = hashPassword(newPassword, salt);
                        user.put("password", newHashedPassword);
                        saveJSON();
                        System.out.println("Password changed successfully!");
                        return true;
                    }
                }
            }
        }
        System.out.println("Password change failed!");
        return false;
    }

    // Get user details using IDkey
    public JSONObject getUserDetails(String customID) throws IOException {
        loadJSON();
        if (jsonObject != null) {
            JSONArray userArray = jsonObject.getJSONArray("User");
            for (int i = 0; i < userArray.length(); i++) {
                JSONObject user = userArray.getJSONObject(i);
                if (customID.equals(user.getString("IDkey"))) {
                    return user;
                }
            }
        }
        return null;
    }

    private void saveJSON() {
        try (FileWriter file = new FileWriter(testFilePath)) {
            file.write(jsonObject.toString(4));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean scrollstatus(String scrollname) throws IOException {
        loadJSON();
        if(jsonObject != null){
            JSONArray scroll_info = jsonObject.getJSONArray("ScrollData");
            if(scroll_info != null){
                for(int i = 0;i < scroll_info.length();i++){
                    if(scroll_info.getJSONObject(i).getString("name").equals(scrollname)){
                        int updatetime = scroll_info.getJSONObject(i).getInt("version") + 1;
                        System.out.println("The number of scroll <"+scrollname+"> update time is: "+updatetime);
                        System.out.println("The number of scroll <"+scrollname+"> download time is: "+scroll_info.getJSONObject(i).getInt("download"));
                        return true;
                    }

                }
                System.out.println("Can't find the scroll <"+scrollname+"> in database.");
                return false;
            }
            else{
                System.out.println("Can't connect the Scroll database");
                return false;
            }
        }
        else{
            System.out.println("Can't connect the Scroll database");
            return false;
        }
    }
    public boolean printscroll() throws IOException {
        loadJSON();
        if(jsonObject != null){
            JSONArray scroll_info = jsonObject.getJSONArray("ScrollData");
            if(scroll_info != null){
                if(scroll_info.length() == 0){
                    System.out.println("There are no scroll in database");
                    return false;
                }
                for(int i = 0;i < scroll_info.length();i++){
                    System.out.println(i+".<"+scroll_info.getJSONObject(i).getString("name")+">");
                }

            }
            else{
                System.out.println("Can't connect the Scroll database");
                return false;
            }
        }
        else{
            System.out.println("Can't connect the Scroll database");
            return false;
        }
        return true;
    }

    public ArrayList<String[]> getAllScrolls() throws IOException {
        loadJSON();
        ArrayList<String[]> scrolls = new ArrayList<String[]>();

        if(jsonObject != null){
            JSONArray scroll_info = jsonObject.getJSONArray("ScrollData");

            if(scroll_info != null){
                for(int i = 0;i < scroll_info.length();i++){
                    String[] scroll = new String[4];
                    scroll[0] = scroll_info.getJSONObject(i).getString("name");
                    scroll[1] = scroll_info.getJSONObject(i).getString("scroll_id");
                    scroll[2] = scroll_info.getJSONObject(i).getString("uploaderName");
                    scroll[3] = scroll_info.getJSONObject(i).getString("upload_time");

                    scrolls.add(scroll);
                }
            }
            else{
                System.out.println("Can't connect the Scroll database");
            }
        }
        else{
            System.out.println("Can't connect the Scroll database");
        }
        return scrolls;
    }

    public ArrayList<DigitalScroll> getAllDigitalScrolls() throws IOException {
        loadJSON();
        ArrayList<DigitalScroll> scrolls = new ArrayList<DigitalScroll>();
        if(jsonObject != null){
            JSONArray scroll_info = jsonObject.getJSONArray("ScrollData");

            if(scroll_info != null && scroll_info.length() > 0){
                for(int i = 0;i < scroll_info.length();i++){
                    DigitalScroll scroll = new DigitalScroll();
                    scroll.setName(scroll_info.getJSONObject(i).getString("name"));
                    scroll.setId(scroll_info.getJSONObject(i).getString("scroll_id"));
                    scroll.setContent(scroll_info.getJSONObject(i).getString("content"));
                    scroll.setUserID(scroll_info.getJSONObject(i).getString("userID"));
                    scroll.setUploaderName(scroll_info.getJSONObject(i).getString("uploaderName"));
                    scroll.setUploadtime(scroll_info.getJSONObject(i).getString("uploadtime"));
                    scroll.setStatus(scroll_info.getJSONObject(i).getString("status"));
                    scroll.setModifytime(scroll_info.getJSONObject(i).getString("modifytime"));
                    scroll.setVersion(scroll_info.getJSONObject(i).getInt("version"));
                    scroll.setDownload(scroll_info.getJSONObject(i).getInt("download"));
                    scrolls.add(scroll);
                }
            }
            else{
                System.out.println("Can't connect the Scroll database");
            }
        }
        else{
            System.out.println("Can't connect the Scroll database");
        }
        return scrolls;
    }



    public boolean updatescroll(DigitalScroll scroll) throws IOException {
        loadJSON();
        if(jsonObject != null){
            JSONArray scroll_info = jsonObject.getJSONArray("ScrollData");
            if(scroll_info != null){
                for(int i = 0;i < scroll_info.length();i++){
                    if(scroll.getName().equals(scroll_info.getJSONObject(i).getString("name"))){
                        scroll_info.remove(i);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name",scroll.getName());
                        jsonObject.put("scroll_id",scroll.getId());
                        jsonObject.put("content",scroll.getContent());
                        jsonObject.put("userID",scroll.getUserID());
                        jsonObject.put("uploaderName",scroll.getUploaderName());
                        jsonObject.put("uploadtime", scroll.getUploadtime());
                        jsonObject.put("status",scroll.getStatus());
                        jsonObject.put("modifytime",scroll.getModifytime());
                        jsonObject.put("version",scroll.getVersion());
                        jsonObject.put("download",scroll.getDownload());
                        scroll_info.put(jsonObject);
                        saveJSON();
                        System.out.println("Scroll <"+scroll.getName()+"> update successfully!");
                        return true;
                    }
                }
                return false;
            }
            else{
                System.out.println("Can't connect the Scroll database");
                return false;
            }
        }
        else{
            System.out.println("Can't connect the Scroll database");
            return false;
        }
    }

    public void saveUploadLog(DigitalScroll scroll) throws IOException {
        loadJSON();
        if(jsonObject != null){
            JSONArray scroll_info = jsonObject.getJSONArray("ScrollUpload");
            if(scroll_info != null){
                JSONObject object = new JSONObject();
                object.put("name",scroll.getName());
                object.put("scroll_id",scroll.getId());
                object.put("content",scroll.getContent());
                object.put("userID",scroll.getUserID());
                object.put("uploaderName",scroll.getUploaderName());
                object.put("uploadtime",scroll.getUploadtime());
                object.put("status", scroll.getStatus());
                object.put("modifytime",scroll.getModifytime());
                object.put("version",scroll.getVersion());
                object.put("download",scroll.getDownload());
                jsonObject.put("Count",jsonObject.getInt("Count") + 1);
                scroll_info.put(object);

                saveJSON();
                System.out.println("Upload Successfully!");
                System.out.println("UploadLog:Scroll < "+scroll.getName()+" > is saved.");
            } else{
                System.out.println("Can't connect the Scroll upload database");
            }
        }
        else{
            System.out.println("Can't connect the Scroll upload database");
            return;
        }
    }

    public void saveOldScroll(DigitalScroll scroll) throws IOException {
        loadJSON();
        if(jsonObject != null){
            JSONArray scroll_info = jsonObject.getJSONArray("ScrollData");
            if(scroll_info != null){
                JSONObject object = new JSONObject();
                object.put("name",scroll.getName());
                object.put("scroll_id",scroll.getId());
                object.put("content",scroll.getContent());
                object.put("userID",scroll.getUserID());
                object.put("uploaderName",scroll.getUploaderName());
                object.put("uploadtime",scroll.getUploadtime());
                object.put("status",scroll.getStatus());
                object.put("modifytime",scroll.getModifytime());
                object.put("version",scroll.getVersion());
                object.put("download",scroll.getDownload());
                scroll_info.put(object);
                jsonObject.put("Count",jsonObject.getInt("Count") + 1);
                saveJSON();
                System.out.println("UpdateLog:Scroll < "+scroll.getName() +" > is saved.");
            }
            else{
                System.out.println("Can't connect the Scroll update database");
            }
        }
        else{
            System.out.println("Can't connect the Scroll update database");
        }
    }

    public boolean addScroll(DigitalScroll scroll, String uploader_name) throws IOException {
        loadJSON();
        if(jsonObject != null){
            JSONArray scroll_info = jsonObject.getJSONArray("ScrollData");
            if(scroll_info != null){
                for(int i = 0;i < scroll_info.length();i++){
                    if(scroll.equals(scroll_info.getJSONObject(i).getString("name"))){
                        System.out.println("Scroll <"+scroll+"> is exist.");
                        return false;
                    }
                }
                scroll.setUploaderName(uploader_name);
                String current_date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
                scroll.setUploadtime(current_date);
                JSONObject object = new JSONObject();
                object.put("name",scroll.getName());
                object.put("scroll_id",scroll.getId());
                object.put("content",scroll.getContent());
                object.put("userID",scroll.getUserID());
                object.put("uploaderName",scroll.getUploaderName());
                object.put("uploadtime",scroll.getUploadtime());
                object.put("status",scroll.getStatus());
                object.put("modifytime","2023/10/23");
                object.put("version",scroll.getVersion());
                object.put("download",scroll.getDownload());
                scroll_info.put(object);
                jsonObject.put("Count",jsonObject.getInt("Count") + 1);
                saveJSON();
                System.out.println("Add scroll < "+ object +" > successfully.");
                return true;
            }
            else{
                System.out.println("Can't connect the Scroll database");
                return false;
            }
        }
        else{

            System.out.println("2");

            System.out.println("Can't connect the Scroll database");
            return false;
        }
    }
    public boolean deleteAdmin(String idkey) throws IOException {
        loadJSON();
        if(jsonObject != null){
            JSONArray admin_info = jsonObject.getJSONArray("admin_account");
            if(admin_info != null){
                for(int i = 0;i < admin_info.length();i++){
                    if (idkey.equals(admin_info.getJSONObject(i).getString("username"))){
                        admin_info.remove(i);
                        saveJSON();
                        System.out.println("Success remove "+ idkey+".");
                        return true;
                    }
                }
            }
            System.out.println("Can't find the "+idkey+" user.");
            return false;
        }
        else{
            System.out.println("Can't connect the user database");
            return false;
        }
    }
    public void deleteScroll(DigitalScroll scroll) throws IOException {
        loadJSON();
        if(jsonObject != null){
            JSONArray scroll_info = jsonObject.getJSONArray("ScrollData");
            if(scroll_info != null){
                for(int i = 0;i < scroll_info.length();i++){
                    if(scroll.getId().equals(scroll_info.getJSONObject(i).getString("scroll_id"))){
                        scroll_info.remove(i);
                        jsonObject.put("ScrollData",scroll_info);
                        jsonObject.put("Count",jsonObject.getInt("Count") - 1);
                        saveJSON();
                        return;
                    }
                }
                return;
            }
            else{
                System.out.println("Can't connect the Scroll database");
                return;
            }
        }
        else{

            System.out.println("2");

            System.out.println("Can't connect the Scroll database");
            return;
        }
    }

    public boolean isMain(String adminname) throws IOException {
        loadJSON();
        if (jsonObject != null) {
            JSONArray admin_account = jsonObject.getJSONArray("admin_account");
            if (admin_account != null) {
                for (int i = 0; i < admin_account.length(); i++) {
                    if(adminname.equals(admin_account.getJSONObject(i).getString("username"))){
                        if(admin_account.getJSONObject(i).getBoolean("isMain") == true){
                            return true;
                        }
                    }
                }

            } else {

                System.out.println("Can't connect the admin database");
            }

        } else {

            System.out.println("Can't connect the admin database");
        }

        return false;
    }

    public void addScrolldownloadtime(String name) throws IOException {
        loadJSON();
        if(jsonObject != null){
            JSONArray scroll_info = jsonObject.getJSONArray("ScrollData");
            if(scroll_info != null){
                for(int i = 0;i < scroll_info.length();i++){
                    if(scroll_info.getJSONObject(i).getString("name").equals(name)){
                        int downloadValue = scroll_info.getJSONObject(i).getInt("download");
                        downloadValue += 1;
                        scroll_info.getJSONObject(i).put("download", String.valueOf(downloadValue));
                        return;
                    }
                }
                return;
            }
            else{
                System.out.println("Can't connect the Scroll database");
                return;
            }
        }
        else{

            System.out.println("2");

            System.out.println("Can't connect the Scroll database");
            return;
        }
    }
    public boolean printAdmin() throws IOException {
        loadJSON();
        if(jsonObject != null){
            JSONArray admin_info = jsonObject.getJSONArray("admin_account");
            if(admin_info != null){
                for(int i = 0;i < admin_info.length();i++){
                    String idkey = admin_info.getJSONObject(i).getString("username");
                    System.out.println(i+". Admin: "+idkey);
                }
            }
            else{
                System.out.println("Can't connect the user database");
                return false;
            }
        }
        else{
            System.out.println("Can't connect the user database");
            return false;
        }
        return true;
    }
    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}