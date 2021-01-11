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
    private Player_color type;
    private double mouseX, mouseY, oldX, oldY;

    public Player_color getType() {
        return type;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    Piece(Player_color type, int x, int y)
    {
        this.type = type;
        Move_piece(x * 2, y * 2);

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

    private void Move_piece (int x, int y)
    {
        oldX = x * NODE_SIZE;
        oldY = y * NODE_SIZE;
        relocate(oldX, oldY);
    }

    private void abort_Move()
    {
        relocate(oldX, oldY);
    }

    // converts pixels into coordinates

    private int to_Board (double pixel)
    {
        return (int)(pixel + NODE_SIZE / 2) / NODE_SIZE;
    }

    private Player_color get_piece_color ()
    {
        return this.type;
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
                can_move = false;
                Move_piece(new_X, new_Y);
                board.find_node(x_0, y_0).setPiece(null);
                board.find_node(new_X, new_Y).setPiece(this);
            }
            case JUMP -> {
                Move_piece(new_X, new_Y);
                board.find_node(x_0, y_0).setPiece(null);
                board.find_node(new_X, new_Y).setPiece(this);
            }
        }

    }

    // tries to move a piece to a specific coordinate
    // (checks if a coordinate mouse was released has a node under it and doesn't have a piece on it)

    private Move_type try_Move (int x_0, int y_0, int new_x, int new_y)
    {
        node node_var = board.find_node(new_x, new_y);

        // node is not found (find_node method return null to node_var)

        if (node_var == null)
        {
            System.out.println("Node isn't found!");
            return Move_type.NONE;
        }

        // if a color of a piece is different than color of player's turn
        // or player cannot move anymore
        // returns NONE

        if (Game_engine.get_Player_color() != get_piece_color() || (!can_move) )
        {
            System.out.println("Not your turn!");
            return Move_type.NONE;
        }

        // node is found
        // we check if there is a piece
        // can't move if a chosen node has a piece in it
        // returns NORMAL Move_type if there is no piece and NONE if there is a piece

        if (board.check_if_neighbours(x_0, y_0, new_x, new_y) && node_var.check_Node_empty())
        {
            return Move_type.NORMAL;
        }

        else
        {
            return Move_type.NONE;
        }

    }

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
        color_picker(type, ellipse);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(NODE_SIZE * 0.03);

        ellipse.setTranslateX((NODE_SIZE - NODE_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((NODE_SIZE - NODE_SIZE * 0.26 * 2) / 2);

        getChildren().addAll(bg, ellipse);
    }
}
