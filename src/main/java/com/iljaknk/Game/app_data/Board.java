package com.iljaknk.Game.app_data;

import com.iljaknk.Game.Player_color;

import java.util.ArrayList;

import static com.iljaknk.App.*;

public class Board
{
    ArrayList<node> node_board; // for checking coordinates for moving

    public ArrayList<Piece> pieces;

    Board(int number_of_players)
    {
        node_board = new ArrayList<>();
        pieces = new ArrayList<>();
    }

    public void add (Player_color type, int x, int y)
    {
        x += 4;
        y += 2;
        node new_node = new node(x,y,type);
        node_board.add(new_node);
        node_Group.getChildren().add(new_node);
        if (type != Player_color.NONE)
        {
            Piece new_piece = new Piece(type, x, y);

            new_node.setPiece(new_piece);

            pieces.add(new_piece);

            piece_Group.getChildren().add(new_piece);
        }
    }

    // moves a piece that we need at our local App

    public void move_Piece (int old_x, int old_y, int new_X, int new_Y)
    {
        // finding all necessary objects
        node old_node = find_node(old_x, old_y);
        node new_node = find_node(new_X, new_Y);
        Piece piece_var = old_node.getPiece();

        // rellocate a piece
        piece_var.change_piece_coordinates(new_X, new_Y);
        // change values of nodes' Piece field
        old_node.setPiece(null);
        new_node.setPiece(piece_var);
    }

    // finds node in an Array of nodes

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
        int dir_x = new_x - x_0;

        // we cannot move straight up and down
        // if dir_x hasn't changed, that's what we are trying to do

        if (dir_x == 0)
        {
            System.out.println("(" + x_0 + "; " + y_0 + ") and (" + new_x + "; " + new_y + ")" + " are not neighbours!");
            return false;
        }

        int dir_y = new_y - y_0;

        // y = 0, so x must be 4 or -4 to move straight right or left

        if (dir_y == 0 && Math.abs(dir_x) == 4)
        {
            return true;
        }

        // if we move in any other directions, values cannot be different from -2 or 2
        // top left = (-2; -2)
        // top right = (+2; -2)
        // bottom left = (-2; +2)
        // bottom right = (+2; +2)

        if (Math.abs(dir_x) == 2 && Math.abs(dir_y) == 2)
        {
            return true;
        }

        System.out.println("(" + x_0 + "; " + y_0 + ") and (" + new_x + "; " + new_y + ")" + " are not neighbours!");
        return false;
    }

    public boolean check_if_all_pieces_at_enemy_home (Player_color color_to_check)
    {
        boolean flag = true;
        for (Piece piece_var : pieces)
        {
            if (piece_var.get_piece_color() == color_to_check)
            {
                if (!piece_var.check_if_is_at_enemy_home())
                {
                    flag = false;
                }
            }
        }
        return flag;
    }


}