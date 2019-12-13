package com.cutools.controller;

import com.cutools.launch.MainLauncher;
import com.jfoenix.controls.JFXTabPane;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Map;

/**
 * author: hubz
 * datetime: 2019/12/10 19:05
 */

public class MainBodyController implements Initializable {
    final ToggleGroup group = new ToggleGroup();
    public RadioButton technology = new RadioButton();
    public RadioButton popsci = new RadioButton();
    public RadioButton life = new RadioButton();

    public Button search = new Button();//搜索按钮
    public TextField searchBody = new TextField();//搜索框

    public MenuBar menuBar = new MenuBar();

    public TabPane tabPane = new JFXTabPane();

    public ChoiceBox<String> choiceBox = new ChoiceBox<>();

    private long oneDay = 86400;
    private long oneMonth = 2592000;
    private long oneYear = 31104000;
    private String TYPE;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        winMoveSet();
        technology.setOnAction(e->{ if(technology.isSelected()) TYPE="technology";});
        popsci.setOnAction(e->{if(popsci.isSelected()) TYPE="popsci";});
        life.setOnAction(e->{if(life.isSelected()) TYPE="life";});

        technology.setToggleGroup(group);
        popsci.setToggleGroup(group);
        life.setToggleGroup(group);
        technology.setSelected(true);

        choiceBox.setItems(FXCollections.observableArrayList("三年内","两年内","一年内","三月内",
                "一月内","一周内","一天内","不限时间"));
        choiceBox.getSelectionModel().select(0);
        search.setOnMouseClicked(new searchHandler());

    }
    private Map<String,Long> getTime(String type){
        Map<String,Long> res = new HashMap<>();
        long curTime = System.currentTimeMillis()/1000;
        switch(type){
            case "三年内":{
                res.put("start",curTime - 3*oneYear);
                res.put("end",curTime);
                break;
            }
            case "两年内":{
                res.put("start",curTime - 2*oneYear);
                res.put("end",curTime);
                break;
            }
            case "一年内":{
                res.put("start",curTime - oneYear);
                res.put("end",curTime);
                break;
            }
            case "三月内":{
                res.put("start",curTime - 3*oneMonth);
                res.put("end",curTime);
                break;
            }
            case "一月内":{
                res.put("start",curTime - oneMonth);
                res.put("end",curTime);
                break;
            }
            case "一周内":{
                res.put("start",curTime - 7*oneDay);
                res.put("end",curTime);
                break;
            }
            case "一天内":{
                res.put("start",curTime - oneDay);
                res.put("end",curTime);
                break;
            }
            case "不限时间":{
                res.put("start",0L);
                res.put("end",curTime);
                break;
            }
        }
        return res;
    }
    private class searchHandler implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            String ques = searchBody.getText();
            if(!ques.equals("")) {
                String chrome = getPropertiesData("chrome");
                String site = getPropertiesData(TYPE);
                String type = choiceBox.getSelectionModel().getSelectedItem();
                Map<String,Long> time = getTime(type);

                if(!chrome.equals("")){
                    try {
                        String site_ques = "site:("+site+") "+URLEncoder.encode(ques);
                        String gpc = "stf%3D"+time.get("start")+"%2C"+time.get("end")+"%7Cstftype%3D2";
                        String url = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=baiduadv&wd="+site_ques+
                                "&oq="+site_ques+"&rqlang=cn&rsv_enter=1&gpc="+gpc+"&tfflag=1&bs="+site_ques;
                        Runtime.getRuntime().exec(new String[]{chrome,"--new-window",url});
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    private void winMoveSet(){
        EventHandler handler = new DragWindowHandler(MainLauncher.getPrimaryStage());//primaryStage为start方法中的局部b
        menuBar.setOnMousePressed(handler); //如果去掉这一行代码将会使鼠标进入面板时面板左上角会定位到鼠标的位置(瞬间移动)
        menuBar.setOnMouseDragged(handler);
    }

    @FXML
    public void minimizeWindow(){
        MainLauncher.getPrimaryStage().setIconified(true);
    }

    @FXML
    public void closeSystem(){
        System.exit(0);
    }

    @FXML
    private String getPropertiesData(String key) {
        try {
            Properties pro = new Properties();
            FileInputStream in = new FileInputStream(System.getProperty("user.dir")+"/src/main/resources/conf/a.properties");
            pro.load(in);
            String value = pro.getProperty(key);
            in.close();
            return value;
        }
        catch(IOException e){
            return null;
        }
    }

}
