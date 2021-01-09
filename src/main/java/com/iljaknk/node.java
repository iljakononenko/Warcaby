package com.iljaknk;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import static com.iljaknk.App.NODE_SIZE;

public class node extends Ellipse
{
    private Piece piece;
    Piece_type type;
    int x,y,z;
    node (int x, int y)
    {
        this.setFill(Color.valueOf("#7A371C"));
        this.x = x * 2;
        this.y = y * 2;
        setRadiusX(NODE_SIZE);
        setRadiusY(NODE_SIZE);
        relocate(x * NODE_SIZE * 2, y * NODE_SIZE * 2);
    }

    // checks if a coordinates we passed are valid has not piece in it
    // returns true if a coordinate are valid and node is empty (so that we can move there)
    // *useless*
    public boolean check_node (int x, int y)
    {

        if (check_Coordinates(x, y) && check_Node_empty())
        {
            return true;
        }
        return false;
    }

    // checks if a node has a piece of is null (is empty)

    public boolean check_Node_empty ()
    {
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

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    // checks if a coordinates we passed are the coordinates a node has

    public boolean check_Coordinates (int x_to_check, int y_to_check)
    {
        if (this.x == x_to_check && this.y == y_to_check)
        {
            return true;
        }
        return false;
    }

    public void show_node ()
    {
        System.out.println("( "+ this.x + ", " + this.y + ", " + this.z + " )");
    }

}
