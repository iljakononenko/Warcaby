package com.iljaknk.Game.app_data;

import com.iljaknk.Game.Player_color;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import static com.iljaknk.App.NODE_SIZE;

public class node extends Ellipse
{
    private Piece piece;
    private Player_color type_of_home;
    int x,y,z;
    node (int x, int y, Player_color type_of_home)
    {
        this.type_of_home = type_of_home;
        this.setFill(Color.valueOf("#7A371C"));
        this.x = x * 2;
        this.y = y * 2;
        setRadiusX(NODE_SIZE);
        setRadiusY(NODE_SIZE);
        relocate(x * NODE_SIZE * 2, y * NODE_SIZE * 2);
    }

    // checks if a node has a piece or is null (is empty)

    public boolean check_Node_empty ()
    {
        // fix for pieces of the same color
        if (piece == null)
        {
            return true;
        }
        else
        {
            System.out.println("Node is not empty!");
            return false;
        }
    }

    public Piece getPiece()
    {
        return piece;
    }

    public Player_color getType_of_home()
    {
        return type_of_home;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    // checks if a coordinates we passed are the coordinates a node has

    public boolean check_Coordinates (int x_to_check, int y_to_check)
    {
        return this.x == x_to_check && this.y == y_to_check;
    }

}
