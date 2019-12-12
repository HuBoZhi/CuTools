package com.cutools.controller;

import com.cutools.launch.MainLauncher;
import com.jfoenix.controls.JFXTabPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.plaf.nimbus.State;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        winMoveSet();

        technology.setToggleGroup(group);
        popsci.setToggleGroup(group);
        life.setToggleGroup(group);
        technology.setSelected(true);

        choiceBox.setItems(FXCollections.observableArrayList("不限时间","一天内","一周内",
                                    "一月内","一年内","两年内","三年内"));
        choiceBox.getSelectionModel().select(0);

        search.setOnMouseClicked(new searchHandler());

        loadProperties();
    }
    private class searchHandler implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            String ques = searchBody.getText();
            if(!ques.equals("")) {
                Tab tab = new Tab(ques);
                final WebView browser = new WebView();
                final WebEngine webEngine = browser.getEngine();
                tab.setContent(browser);
                String url = "";
                webEngine.load("http://www.baidu.com");
                tabPane.getTabs().add(tab);
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
    public void loadProperties() {
        try {
            Properties pro = new Properties();
            FileInputStream in = new FileInputStream(System.getProperty("user.dir")+"/src/main/resources/conf/a.properties");
            pro.load(in);
            String s = pro.getProperty("technology");
            System.out.println(s);

            in.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

}
