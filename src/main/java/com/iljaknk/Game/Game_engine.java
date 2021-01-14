package com.iljaknk.Game;

import com.iljaknk.App;
import com.iljaknk.Game.app_data.Board;
import com.iljaknk.Game.app_data.Board_Factory;

import static com.iljaknk.App.*;


public class Game_engine
{
    public static boolean is_Winner = false;
    public static boolean server_is_notified_about_winner = false;
    public static int players_turn = 0;
    public static Board board;
    public static boolean can_move = false;
    public static boolean can_jump = false;
    public static All_Players list_of_players;

    // Game process functions

    public void start_Game (int first_turn_player)
    {
        // we call board factory to build our board
        Board_Factory board_creator = new Board_Factory(number_of_players);
        board_creator.build_Board(number_of_players);
        board = board_creator.get_Board();

        // assign variables
        list_of_players = new All_Players(number_of_players);
        players_turn = first_turn_player;

        // to check if our player id is the same as player id in list_of_players
        //System.out.println("My color is: " + list_of_players.get_player_color(player_id) + " and my id is: " + list_of_players.get_Player_id(player_id));



        Player_name.setText("You are Player " + (player_id + 1) + "! (" + list_of_players.get_player_color(player_id) + ")");

        // if our App was started by a player that moves first

        if (App.player_id == first_turn_player)
        {
            can_jump = true;
            can_move = true;
            players_turn_label.setText("Your turn!(" + list_of_players.get_player_color(players_turn) + ")");
        }
        else
        {
            change_turn_label();
        }

    }

    public static void end_turn () throws Exception
    {
        board.clear_jump_piece_flags();
        can_move = false;
        can_jump = false;
        players_turn++;
        if (players_turn % number_of_players == 0)
        {
            players_turn = 0;
        }
        check_if_won();

        if (player_id == players_turn)
        {
            can_move = true;
            can_jump = true;
            players_turn_label.setText("Your turn!(" + list_of_players.get_player_color(players_turn) + ")");
        }

        else
        {
            change_turn_label();
        }

    }



    public static void send_turn (int x_0, int y_0, int new_X, int new_Y)
    {
        try
        {
            String str_x_0 = int_to_two_digit_str_number(x_0);

            String str_y_0 = int_to_two_digit_str_number(y_0);

            String str_new_X = int_to_two_digit_str_number(new_X);

            String str_new_Y = int_to_two_digit_str_number(new_Y);

            connection.send("TURN:" + str_x_0 + str_y_0 + str_new_X + str_new_Y);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void make_move (int old_x, int old_y, int new_X, int new_Y)
    {
        board.move_Piece(old_x, old_y, new_X, new_Y);
    }

    public static void check_if_won () throws Exception
    {
        //comment to test server (manually give a player "winner" flag) to field "is_winner"
        is_Winner = board.check_if_players_pieces_at_enemy_home(list_of_players.get_player_color(player_id));
        if (!server_is_notified_about_winner && is_Winner)
        {
            connection.send("WINNER:" + player_id);
            server_is_notified_about_winner = true;
        }
    }

    // Utilities

    public static int str_two_digit_numbers_to_int (String str)
    {
        if (str.startsWith("0"))
        {
            str = str.substring(1);
        }
        int return_value = Integer.parseInt(str);
        return return_value;
    }

    public static String int_to_two_digit_str_number (int number)
    {
        String str = String.valueOf(number);
        if (str.length() == 1)
        {
            str = "0" + str;
        }
        return str;
    }

    public static void change_connected_players (int new_number_of_players)
    {
        number_of_players = new_number_of_players;
        label_Players_connected.setText(new_number_of_players + " / 6 Players are connected");
    }

    public static void change_turn_label ()
    {
        players_turn_label.setText("Player " + (players_turn + 1) + " turn! (" + list_of_players.get_player_color(players_turn) + ")");
    }

    public static Player_color get_Player_color ()
    {
        return list_of_players.get_player_color(players_turn);
    }



}
