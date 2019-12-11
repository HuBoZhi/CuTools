package com.cutools.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
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

    public ChoiceBox<String> choiceBox = new ChoiceBox<String>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        technology.setToggleGroup(group);
        popsci.setToggleGroup(group);
        life.setToggleGroup(group);
        technology.setSelected(true);
        choiceBox.setItems(FXCollections.observableArrayList("不限时间","一天内","一周内",
                                    "一月内","一年内","两年内","三年内"));
        choiceBox.getSelectionModel().select(0);
    }

    @FXML
    public void minimizeWindow(){

    }

    @FXML
    public void closeSystem(){

    }

}
