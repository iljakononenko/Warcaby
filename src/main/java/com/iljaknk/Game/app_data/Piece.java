package com.iljaknk.Game.app_data;

import com.iljaknk.Game.Game_engine;
import com.iljaknk.Game.Player_color;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static com.iljaknk.App.NODE_SIZE;
import static com.iljaknk.Game.Game_engine.*;

public class Piece extends StackPane
{
    private Player_color color;
    private Player_color enemy_home_color;
    private double mouseX, mouseY, oldX, oldY;
    private boolean is_at_enemy_home = false;

    // Getters

    public Player_color get_piece_color ()
    {
        return this.color;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    // Constructor

    Piece(Player_color color, int x, int y)
    {
        this.color = color;
        this.enemy_home_color = get_enemy_home_color(color);

        change_piece_coordinates(x * 2, y * 2);

        build_piece();

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });
        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });

        setOnMouseReleased(e -> Move());

    }

    // Game process methods

    public void change_piece_coordinates (int x, int y)
    {
        oldX = x * NODE_SIZE;
        oldY = y * NODE_SIZE;
        relocate(oldX, oldY);
    }

    private void abort_Move()
    {
        relocate(oldX, oldY);
    }

    public boolean check_if_is_at_enemy_home ()
    {
        return is_at_enemy_home;
    }

    // converts pixels into coordinates

    private int to_Board (double pixel)
    {
        return (int)(pixel + NODE_SIZE / 2) / NODE_SIZE;
    }

    private void try_move_to_enemy_home (Player_color color)
    {
        if (color == enemy_home_color)
        {
            // for testing
            //System.out.println("You moved into enemy home!");
            is_at_enemy_home = true;
        }
    }

    private boolean try_move_out_enemy_home (Player_color color)
    {
        if (is_at_enemy_home && color == Player_color.NONE)
        {
            System.out.println("Cannot move out of enemy home!");
            return true;
        }
        else return false;
    }

    private void Move ()
    {
        int x_0 = to_Board(getOldX());
        int y_0 = to_Board(getOldY());

        int new_X = to_Board(getLayoutX());
        int new_Y = to_Board(getLayoutY());

        Move_type move_type = try_Move(x_0, y_0, new_X, new_Y);

        switch (move_type)
        {
            case NONE -> abort_Move();
            case NORMAL -> {
                // uncomment after all tests
                //can_move = false;
                Game_engine.send_turn(x_0, y_0, new_X, new_Y);
            }
            case JUMP -> {
                change_piece_coordinates(new_X, new_Y);
                board.find_node(x_0, y_0).setPiece(null);
                board.find_node(new_X, new_Y).setPiece(this);
            }
        }

    }

    // tries to move a piece to a specific coordinate
    // (checks if a coordinate mouse was released has a node under it and doesn't have a piece on it)

    private Move_type try_Move (int x_0, int y_0, int new_x, int new_y)
    {
        if (is_Winner)
        {
            System.out.println("You won!\nYou don't have to move anymore! ;)");
            return Move_type.NONE;
        }

        node node_to_move = board.find_node(new_x, new_y);

        // node is not found (find_node method return null to node_to_move)

        if (node_to_move == null)
        {
            System.out.println("Node isn't found!");
            return Move_type.NONE;
        }

        // if a color of a piece is different than color of player's turn
        // or player cannot move anymore
        // returns NONE

        if (Game_engine.get_Player_color() != get_piece_color() || (!can_move) )
        {
            if (Game_engine.get_Player_color() != get_piece_color())
            {
                System.out.println("Not your piece!");
            }
            if (!can_move)
            {
                System.out.println("Not your move!");
            }
            return Move_type.NONE;
        }

        // node is found
        // we check if there is a piece
        // can't move if a chosen node has a piece in it
        // returns NORMAL Move_type if there is no piece and NONE if there is a piece

        if (board.check_if_neighbours(x_0, y_0, new_x, new_y) && node_to_move.check_Node_empty())
        {
            try_move_to_enemy_home(node_to_move.getType_of_home());

            if (try_move_out_enemy_home(node_to_move.getType_of_home()))
            {
                System.out.println("You are already at enemy's home!");
                return Move_type.NONE;
            }

            return Move_type.NORMAL;
        }

        else
        {
            return Move_type.NONE;
        }

    }

    // builder methods

    private void color_picker (Player_color type, Ellipse ellipse)
    {
        switch (type)
        {
            case BLACK -> ellipse.setFill(Color.valueOf("#221F27"));
            case RED -> ellipse.setFill(Color.valueOf("#E2252D"));
            case WHITE -> ellipse.setFill(Color.valueOf("#CEC1C1"));
            case GREEN -> ellipse.setFill(Color.valueOf("#347430"));
            case YELLOW -> ellipse.setFill(Color.valueOf("#F9E948"));
            case BLUE -> ellipse.setFill(Color.valueOf("#49599A"));
            case NONE -> ellipse.setFill(Color.valueOf("#7A371C"));
        }

    }

    private void build_piece ()
    {
        Ellipse bg = new Ellipse(NODE_SIZE * 0.3125 * 2, NODE_SIZE * 0.26 * 2);
        bg.setFill(Color.BLACK);
        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(NODE_SIZE * 0.03);

        bg.setTranslateX((NODE_SIZE - NODE_SIZE * 0.3125 * 2) / 2);
        bg.setTranslateY((NODE_SIZE - NODE_SIZE * 0.26 * 2) / 2 + NODE_SIZE * 0.07);

        Ellipse ellipse = new Ellipse(NODE_SIZE * 0.3125 * 2, NODE_SIZE * 0.26 * 2);
        color_picker(color, ellipse);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(NODE_SIZE * 0.03);

        ellipse.setTranslateX((NODE_SIZE - NODE_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((NODE_SIZE - NODE_SIZE * 0.26 * 2) / 2);

        getChildren().addAll(bg, ellipse);
    }

    public Player_color get_enemy_home_color (Player_color player_color)
    {
        switch (player_color)
        {
            case BLACK ->
                    {
                        return Player_color.RED;
                    }
            case YELLOW ->
                    {
                        return Player_color.BLUE;
                    }
            case GREEN ->
                    {
                        return Player_color.WHITE;
                    }
            case RED ->
                    {
                        return Player_color.BLACK;
                    }
            case BLUE ->
                    {
                        return Player_color.YELLOW;
                    }
            case WHITE ->
                    {
                        return Player_color.GREEN;
                    }
        }
        return Player_color.NONE;
    }
}
