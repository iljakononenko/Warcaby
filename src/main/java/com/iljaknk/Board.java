package com.iljaknk;

import javafx.scene.Group;

import java.util.ArrayList;

import static com.iljaknk.App.NODE_SIZE;

public class Board
{
    ArrayList<node> board; // for checking coordinates for moving
    private Group nodes, pieces; // for creating an App
    Board ()
    {
        board = new ArrayList<>();
    }

    public void add (Piece_type type, int x, int y)
    {
        x += 13;
        y += 8;
        node new_node = new node(x,y);
        board.add(new_node);
        nodes.getChildren().add(new_node);
        if (type != Piece_type.NONE)
        {
            Piece new_piece = new Piece(type, x, y);

            new_node.setPiece(new_piece);

            new_piece.setOnMouseReleased(e ->
            {
                int x_0 = to_Board(new_piece.getOldX());
                int y_0 = to_Board(new_piece.getOldY());

                int new_X = to_Board(new_piece.getLayoutX());
                int new_Y = to_Board(new_piece.getLayoutY());

                //System.out.println("X = " + new_X + " Y = " + new_Y);

                Move_type move_type = try_Move(x_0, y_0, new_X, new_Y);



                switch (move_type)
                {
                    case NONE:
                        new_piece.abort_Move();
                        break;
                    case NORMAL:
                        new_piece.Move(new_X, new_Y);
                        find_node(x_0, y_0).setPiece(null);
                        find_node(new_X, new_Y).setPiece(new_piece);
                        break;
                }



            });

            pieces.getChildren().add(new_piece);
        }
    }

    // finds a node with a coordinates we need

    public node find_node (int x_node, int y_node)
    {
        node found_node = null;
        for (node node_var : board)
        {
            if (node_var.check_Coordinates(x_node, y_node))
            {
                found_node = node_var;
            }
        }
        return found_node;
    }

    // converts pixels into coordinates

    public int to_Board (double pixel)
    {
        return (int)(pixel + NODE_SIZE / 2) / NODE_SIZE;
    }

    // function to print out all nodes' coordinates to console (for test)

    public void show_board ()
    {
        for (node node_var : board)
        {
            node_var.show_node();
        }
    }

    // tries to move a piece to a specific coordinate
    // (checks if a coordinate mouse was released has a node under it and doesn't have a piece on it)

    private Move_type try_Move (int x_0, int y_0, int new_x, int new_y)
    {
        node node_var = find_node(new_x, new_y);

        // node is not found (find_node method return null to node_var)

        if (node_var == null)
        {
            System.out.println("Node isn't found!");
            return Move_type.NONE;
        }

        // node is found
        // we check if there is a piece
        // can't move if a chosen node has a piece in it
        // returns NORMAL Move_type if there is no piece and NONE if there is a piece

        if (check_if_neighbours(x_0, y_0, new_x, new_y) && node_var.check_Node_empty())
        {
            return Move_type.NORMAL;
        }

        else
        {
            return Move_type.NONE;
        }

    }

    // checks if a node to move is neighbour to previous piece node

    private boolean check_if_neighbours (int x_0, int y_0, int new_x, int new_y)
    {
        int dir_x = new_x - x_0;
        int dir_y = new_y - y_0;

        if (Math.abs(dir_x + dir_y) <= 2)
        {
            return true;
        }
        System.out.println("(" + x_0 + "; " + y_0 + ") and (" + new_x + "; " + new_y + ")" + " are not neighbours!");
        return false;
    }

    // functions to set Board's fields so we can use them in future

    public void setNodes(Group nodes)
    {
        this.nodes = nodes;
    }

    public void setPieces(Group pieces)
    {
        this.pieces = pieces;
    }
}