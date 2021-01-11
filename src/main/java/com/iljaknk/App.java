package com.iljaknk;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static int players_turn = 0;
    public static Label players_turn_label = new Label();
    public static Stage window;
    public static Scene menu_scene, game_scene;
    public static int number_of_players = 6;

    public static final int NODE_SIZE = 15;
    public static Group node_Group = new Group();
    public static Group piece_Group = new Group();

    @Override
    public void start(Stage stage) throws IOException
    {
        players_turn_label.setAlignment(Pos.CENTER);

        players_turn_label.setFont(Font.font(24));


        window = stage;

        Pane game_pane = new Pane();
        game_pane.getChildren().addAll(node_Group, piece_Group);

        BorderPane parent_game_pane = FXMLLoader.load(App.class.getResource("/game_window.fxml"));

        parent_game_pane.setTop(players_turn_label);
        BorderPane.setAlignment(players_turn_label, Pos.TOP_CENTER);
        parent_game_pane.setCenter(game_pane);

        Parent menu = FXMLLoader.load(App.class.getResource("/menu_window.fxml"));

        menu_scene = new Scene(menu);
        game_scene = new Scene(parent_game_pane);

        window.setScene(menu_scene);
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }




}