package com.iljaknk;

import com.iljaknk.Game.Game_engine;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static com.iljaknk.Game.Game_engine.list_of_players;

public class App extends Application {

    public static int players_turn = 0;
    public static Label players_turn_label = new Label();
    public static Label label_Players_connected = new Label();
    public static Stage window;
    public static Scene menu_scene, game_scene;
    public static int number_of_players = 6;

    public static final int NODE_SIZE = 15;
    public static Group node_Group = new Group();
    public static Group piece_Group = new Group();

    static Socket socket;
    static Scanner in;
    static PrintWriter out;
    public Button end_turn_button;
    public Button start_game_button;

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
        //run();
    }

    // menu window controller

    public void start_game()
    {
        Game_engine game_engine = new Game_engine();
        game_engine.start_Game();
        window.setScene(game_scene);
        out.println("START_GAME");
    }

    // game_window controller part

    public void end_turn ()
    {
        Game_engine.end_turn();
        change_turn_label();
        out.println("NEXT_TURN");
    }

    public void change_turn_label ()
    {
        players_turn_label.setText("Player " + (players_turn + 1) + " turn! (" + list_of_players.get_player_color(players_turn) + ")");
    }

    // run method for server

    public static void run()
    {
        try
        {
            socket = new Socket("localhost", 56521);

            in = new Scanner(socket.getInputStream());

            out = new PrintWriter(socket.getOutputStream(), true);

            while (in.hasNextLine())
            {
                var line = in.nextLine();
                System.out.println(line);
                if (line.startsWith("ONLINE_UPDATE"))
                {
                    label_Players_connected.setText(line.substring(14) + " / 6 Players are connected");
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            window.close();
        }
    }

    public static void main(String[] args)
    {
        launch();

    }
}