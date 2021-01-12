package com.iljaknk;

import com.iljaknk.Client_Server.Network_Connection;
import com.iljaknk.Client_Server.Online_Player;
import com.iljaknk.Client_Server.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static boolean is_Server = false;

    public static TextArea messages_output = new TextArea();
    public static TextField messages_input = new TextField();

    public static Network_Connection connection = is_Server ? create_Server() : create_Player();

    public static int players_turn = 0;
    public static Label players_turn_label = new Label();
    public static Stage window;
    public static Scene menu_scene, game_scene;
    public static int number_of_players = 6;

    public static final int NODE_SIZE = 15;
    public static Group node_Group = new Group();
    public static Group piece_Group = new Group();

    @Override
    public void init() throws Exception
    {
        connection.start_Connection();
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        messages_input.setOnAction(event ->
        {
            String sms = is_Server ? "Server: " : "Player: ";
            sms += messages_input.getText();
            messages_input.clear();

            messages_output.appendText(sms + "\n");

            try
            {
                connection.send(sms);
            }
            catch (Exception e)
            {
                messages_output.appendText("Failed to send");
            }
        });
        messages_output.setPrefHeight(150);
        messages_output.setPrefWidth(200);
        VBox chat_panel = new VBox(20, messages_output, messages_input);
        chat_panel.setPrefSize(200, 200);


        players_turn_label.setAlignment(Pos.CENTER);

        players_turn_label.setFont(Font.font(24));

        window = stage;

        Pane game_pane = new Pane();
        game_pane.getChildren().addAll(node_Group, piece_Group);

        BorderPane parent_game_pane = FXMLLoader.load(App.class.getResource("/game_window.fxml"));

        parent_game_pane.setTop(players_turn_label);
        BorderPane.setAlignment(players_turn_label, Pos.TOP_CENTER);
        parent_game_pane.setCenter(game_pane);

        BorderPane menu = FXMLLoader.load(App.class.getResource("/menu_window.fxml"));

        menu.setLeft(chat_panel);

        menu_scene = new Scene(menu);
        game_scene = new Scene(parent_game_pane);

        window.setScene(menu_scene);
        window.show();
    }

    @Override
    public void stop () throws Exception
    {
        connection.close_Connection();
    }

    private static Server create_Server()
    {
        return new Server(55555, data ->
        {
            Platform.runLater(() ->
            {
                messages_output.appendText(data.toString() + "\n");
            });
        });
    }

    private static Online_Player create_Player()
    {
        return new Online_Player("localhost", 55555, data ->
        {
            Platform.runLater(() ->
            {
                messages_output.appendText(data.toString() + "\n");
            });
        });
    }

    public static void main(String[] args) {
        launch();
    }

}