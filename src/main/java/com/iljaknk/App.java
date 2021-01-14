package com.iljaknk;

import com.iljaknk.Client_Server.Warcaby_Player;
import com.iljaknk.Game.Game_engine;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
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

    public static int player_id = 0;
    public static TextArea messages_output = new TextArea();
    public static TextField messages_input = new TextField();

    public static Warcaby_Player connection = create_Warcaby_Player();

    public static Label label_Players_connected = new Label("0 / 6 Players are connected");
    public static Label players_turn_label = new Label();
    public static Label Player_name = new Label();
    public static Stage window;
    public static Scene menu_scene, game_scene;
    public static int number_of_players;

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
        window = stage;

        messages_input.setOnAction(event ->
        {
            String message;
            message = messages_input.getText();
            messages_input.clear();

            try
            {
                connection.send(message);
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

        label_Players_connected.setFont(Font.font(16.0));
        label_Players_connected.setAlignment(Pos.CENTER);

        Player_name.setAlignment(Pos.CENTER);
        Player_name.setFont(Font.font(24));

        players_turn_label.setAlignment(Pos.CENTER);
        players_turn_label.setFont(Font.font(24));

        Pane game_pane = new Pane();
        game_pane.getChildren().addAll(node_Group, piece_Group);

        BorderPane parent_game_pane = FXMLLoader.load(App.class.getResource("/game_window.fxml"));

        parent_game_pane.setTop(players_turn_label);
        parent_game_pane.setRight(Player_name);
        BorderPane.setAlignment(players_turn_label, Pos.TOP_CENTER);
        parent_game_pane.setCenter(game_pane);

        BorderPane menu = FXMLLoader.load(App.class.getResource("/menu_window.fxml"));

        menu.setTop(label_Players_connected);
        BorderPane.setMargin(label_Players_connected, new Insets(10, 20, 30, 40));
        BorderPane.setAlignment(label_Players_connected, Pos.TOP_CENTER);

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

    public static void start_game (int first_turn_player)
    {
        Game_engine game_engine = new Game_engine();
        game_engine.start_Game(first_turn_player);
        window.setScene(game_scene);
    }

    public static void initialize_player_id (int player_id)
    {
        App.player_id = player_id;
    }

    private static Warcaby_Player create_Warcaby_Player()
    {
        return new Warcaby_Player();
    }

    public static void main(String[] args) {
        launch();
    }

}