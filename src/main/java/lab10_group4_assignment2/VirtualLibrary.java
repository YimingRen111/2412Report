package lab10_group4_assignment2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VirtualLibrary {
    private ArrayList<DigitalScroll> scrolls = new ArrayList<>();

    public static int IDNUM = 1;

    private static DatabaseOperator databaseOperator = new DatabaseOperator("src/main/resources/ScrollDatabase.json");

    private static DatabaseOperator historyOperator = new DatabaseOperator("src/main/resources/updateLog.json");

    private static DatabaseOperator uploadOperator = new DatabaseOperator("src/main/resources/uploadLog.json");

    public VirtualLibrary(){
        try {
            databaseOperator.loadJSON();
            JSONObject jsonObject = databaseOperator.getJsonObject();
            //将jsonObject中的数据转换为DigitalScroll对象

            JSONArray scroll_info = jsonObject.getJSONArray("ScrollData");
            for (int i = 0; i < scroll_info.length(); i++) {
                JSONObject scrollObject = scroll_info.getJSONObject(i);
                DigitalScroll scroll = new DigitalScroll(scrollObject.getString("name"), scrollObject.getString("scroll_id"), scrollObject.getString("content"), scrollObject.getString("userID"));
                scroll.setUploaderName(scrollObject.getString("uploaderName"));
                scroll.setUploadtime(scrollObject.getString("uploadtime"));
                scroll.setStatus(scrollObject.getString("status"));
                scroll.setModifytime(scrollObject.getString("modifytime"));
                scroll.setVersion(scrollObject.getInt("version"));
                scroll.setDownload(scrollObject.getInt("download"));
                scrolls.add(scroll);
                IDNUM+=1;
            }
            historyOperator.loadJSON();
            uploadOperator.loadJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // adding scroll
    public void addDigitalScrollFromBinary(String binaryData, String uploader_name,String filename) throws IOException {
        String[] parts = binaryData.split(",");
        if (parts.length>= 0) {
//            String name = parts[0];
            String content = binaryData;
//            String userID = parts[2];
            String id = "" + IDNUM++;
            String userID = "userID" + id;
            DigitalScroll scroll = new DigitalScroll(filename, id, content, userID);
            scrolls.add(scroll);

            // add to json to record
            databaseOperator.addScroll(scroll, uploader_name);
            scroll.setUploaderName(uploader_name);
            uploadOperator.saveUploadLog(scroll);

//            System.out.println("New digital scroll added: " + name);
            System.out.println("----------------------------------------");

        } else {
            System.out.println("Invalid binary data format.");
            System.out.println("----------------------------------------");

        }
    }

    public void downloadAction(DigitalScroll digitalScroll) throws IOException {
        digitalScroll.setDownload(digitalScroll.getDownload() + 1);
        databaseOperator.updatescroll(digitalScroll);
    }

    // edit and update
    public void editDigitalScroll(String id, String newContent, String uploader_name) throws IOException {
        String operation = "upload";

        for (DigitalScroll scroll : scrolls) {
            if (scroll.getId().equals(id)) {
                //更新卷轴前，保存旧的卷轴内容
                historyOperator.saveOldScroll(scroll);
                // 更新滚动内容
                scroll.setContent(newContent);
                // 增加版本号
                scroll.setVersion(scroll.getVersion() + 1);
                String current_date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
                scroll.setModifytime(current_date);
                scroll.setUploaderName(uploader_name);
                databaseOperator.updatescroll(scroll);

                //databaseOperator.updatescroll(scroll.getName(), operation, uploader_name);
                break;
            }
        }
    }

    public DigitalScroll getDigitalScroll(String id) {
        for (DigitalScroll scroll : scrolls) {
            if (scroll.getId().equals(id)) {
                return scroll;
            }
        }
        return null; // 或返回一个错误消息，如果未找到相应的滚动
    }


    public String getScrollContent(String id) throws IllegalArgumentException {
        for (DigitalScroll scroll : scrolls) {
            if (scroll.getId().equals(id)) {
                return scroll.getContent();
            }
        }
        throw new IllegalArgumentException("No scroll found with ID: " + id);
    }

    // delete scroll
    public void removeDigitalScroll(String id) throws IOException {
        DigitalScroll digitalScroll = getDigitalScroll(id);
        scrolls.removeIf(scroll -> scroll.getId().equals(id));
        digitalScroll.setStatus("1");
        digitalScroll.setVersion(digitalScroll.getVersion() + 1);
        historyOperator.saveOldScroll(digitalScroll);
        databaseOperator.deleteScroll(digitalScroll);
    }

    // get all scrolls
    public ArrayList<DigitalScroll> getAllDigitalScrolls() {
        return scrolls;
    }

    public boolean isExist(String id) {
        for (DigitalScroll scroll : scrolls) {
            if (scroll.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
