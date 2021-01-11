package com.iljaknk.Game.app_data;

import com.iljaknk.Game.Player_color;

import java.util.ArrayList;

import static com.iljaknk.App.*;

public class Board
{
    ArrayList<node> node_board; // for checking coordinates for moving

    Board(int number_of_players)
    {
        node_board = new ArrayList<>();
    }

    public void add (Player_color type, int x, int y)
    {
        node new_node = new node(x,y);
        node_board.add(new_node);
        node_Group.getChildren().add(new_node);
        if (type != Player_color.NONE)
        {
            Piece new_piece = new Piece(type, x, y);

            new_node.setPiece(new_piece);

            piece_Group.getChildren().add(new_piece);
        }
    }

    // finds a node with a coordinates we need

    public node find_node (int x_node, int y_node)
    {
        node found_node = null;
        for (node node_var : node_board)
        {
            if (node_var.check_Coordinates(x_node, y_node))
            {
                found_node = node_var;
            }
        }
        return found_node;
    }

    // checks if a node to move is neighbour to previous piece node

    public boolean check_if_neighbours (int x_0, int y_0, int new_x, int new_y)
    {
        // debug and fix
        int dir_x = new_x - x_0;
        int dir_y = new_y - y_0;

        if (Math.abs(dir_x + dir_y) <= 4)
        {
            return true;
        }
        System.out.println("(" + x_0 + "; " + y_0 + ") and (" + new_x + "; " + new_y + ")" + " are not neighbours!");
        return false;
    }

}