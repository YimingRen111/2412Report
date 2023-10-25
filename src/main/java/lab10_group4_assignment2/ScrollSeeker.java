package lab10_group4_assignment2;

import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 这个类修改前后的区别在于一个使用数组的方式获取，一个使用对象的方式获取，接口我都写好了，你试试看
 */
public class ScrollSeeker {

    private VirtualLibrary library;
    public ArrayList<DigitalScroll> scrolls;
    public ArrayList<DigitalScroll> history;

    private static DatabaseOperator scrolls_DO = new DatabaseOperator("src/main/resources/ScrollDatabase.json");

    private static DatabaseOperator history_DO = new DatabaseOperator("src/main/resources/updateLog.json");



    public ScrollSeeker(DigitalScrollManagement DSM) throws IOException {
        try {
            scrolls_DO.loadJSON();
        } catch (IOException e) {
            System.out.println("database errors");
        }

        this.library = DSM.library;
        this.scrolls  = scrolls_DO.getAllDigitalScrolls();
        this.history  = history_DO.getAllDigitalScrolls();

    }

    public void noFilter() throws IOException {
        this.scrolls  = scrolls_DO.getAllDigitalScrolls();
    }

    public void viewAll(){
        System.out.println("Virtual Library: ");

        scrolls.forEach(scroll -> System.out.println(scroll.getName() + ", ID: " + scroll.getId() + ", uploaded by " + scroll.getUploaderName() + " on " + scroll.getUploadtime() + "\n"));

        System.out.println();
    }

    public void download(String id) throws IOException {
        for (DigitalScroll scroll : scrolls) {
            if (scroll.getId().equals(id)) {
                String fileName = scroll.getName() + "download.txt";
                String filePath = "src/main/Local/" + fileName;
                JSONObject object = new JSONObject();
//                object.put("name", scroll.getName());
//                object.put("id", scroll.getId());
                object.put("content", scroll.getContent());
//                object.put("userID", scroll.getUserID());
//                object.put("uploaderName", scroll.getUploaderName());
                scrolls_DO.addScrolldownloadtime(scroll.getName());
                try {
                    Files.write(Paths.get(filePath), object.toString().getBytes());
                    this.library.downloadAction(scroll);
                    System.out.println("downloaded to " + filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void downloadHistory(String id) throws IOException {
        for (DigitalScroll scroll : history) {
            if (scroll.getId().equals(id)) {
                String fileName = scroll.getName() + "_v" + scroll.getVersion() + ".txt";
                String filePath = "src/main/Local/" + fileName;
                JSONObject object = new JSONObject();
                object.put("content", scroll.getContent());
                history_DO.addScrolldownloadtime(scroll.getName());
                try {
                    Files.write(Paths.get(filePath), object.toString().getBytes());
                    System.out.println("downloaded to " + filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<DigitalScroll> uploaderFilter(String name){
        ArrayList<DigitalScroll> newScroll = new ArrayList<>();

        scrolls.forEach(scroll -> {
            if(scroll.getUploaderName().equals(name)){
                newScroll.add(scroll);
            }
        });

        System.out.println("filter applied\n");
        return newScroll;
    }

    public ArrayList<DigitalScroll> scrollIDFilter(String id){
        ArrayList<DigitalScroll> newScroll = new ArrayList<>();

        for (DigitalScroll scroll : scrolls){
            if (scroll.getId().equals(id)){
                newScroll.add(scroll);
            }
        }

        System.out.println("filter applied\n");
        return newScroll;
    }

    public ArrayList<DigitalScroll> nameFilter(String name){
        ArrayList<DigitalScroll> newScroll = new ArrayList<>();

        for (DigitalScroll scroll : scrolls){
            if (scroll.getName().contains(name)){
                newScroll.add(scroll);
            }
        }

        System.out.println("filter applied\n");
        return newScroll;
    }

    public ArrayList<DigitalScroll> uploadDateFilter(String date){
        ArrayList<DigitalScroll> newScroll = new ArrayList<>();

        for (DigitalScroll scroll : scrolls){
            if (scroll.getUploadtime().equals(date)){
                newScroll.add(scroll);
            }
        }

        System.out.println("filter applied\n");
        return newScroll;
    }

    // All users can preview scrolls on the platform prior to downloading them.
    public String preview(String id){
        String path = "src/main/resources/user_upload/";
        String content = "";

        for (DigitalScroll scroll : scrolls){
            if (scroll.getId().equals(id)){
                content = scroll.getContent();

                return content;
            }
        }

        return "Cannot find the file with id " + id;
    }
}
