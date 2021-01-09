package com.iljaknk;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    Stage window;

    public static final int NODE_SIZE = 15;
    public static final int COORDINATE_X = 17;
    public static final int COORDINATE_Y = 17;
    public static final int COORDINATE_Z = 17;
    private node[][][] board = new node[COORDINATE_X][COORDINATE_Y][COORDINATE_Z];
    private Group node_Group = new Group();
    private Group piece_Group = new Group();

    @Override
    public void start(Stage stage) throws IOException
    {
        window = stage;

        Label label = new Label("Hello, there!");
        Button button = new Button("Show board");

        Board_Factory board_creator = new Board_Factory();
        board_creator.build_Board(node_Group, piece_Group);
        Board board = board_creator.get_Board();

        VBox layout = new VBox(20);
        Pane game_pane = new Pane();
        game_pane.getChildren().addAll(node_Group, piece_Group);

        BorderPane parent_game_pane = FXMLLoader.load(App.class.getResource("/game_window.fxml"));

        parent_game_pane.setCenter(game_pane);

        Parent menu = FXMLLoader.load(App.class.getResource("/menu_window.fxml"));

        Scene scene = new Scene(layout, 640, 480);
        Scene game_scene = new Scene(parent_game_pane, 1024, 648);

        button.setOnAction(e ->
        {
            board.show_board();
            window.setScene(game_scene);
        });

        layout.getChildren().addAll(label, button);


        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }




}