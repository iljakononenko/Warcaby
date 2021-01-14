package com.iljaknk.Client_Server;

import com.iljaknk.App;
import com.iljaknk.Game.Game_engine;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Warcaby_Player extends Thread
{
    private String name;
    private int port;
    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    public void start_Connection () throws Exception
    {
        this.start();
    }

    public void send (String message) throws Exception
    {
        out.println(message);
    }

    public void close_Connection () throws Exception
    {
        this.socket.close();
    }

    private void try_Server_command (String command)
    {
        if (command.startsWith("PLAYER_NUMBER_CHANGED"))
        {
            int num_of_players = Integer.parseInt(command.substring(22));
            Platform.runLater(() -> Game_engine.change_connected_players(num_of_players));
        }
        else if (command.startsWith("GAME_STARTED:"))
        {
            int first_player = Integer.parseInt(command.substring(13));
            System.out.println("first player is: " + first_player);

            Platform.runLater(() -> App.start_game(first_player));
        }
        else if (command.startsWith("YOUR_ID:"))
        {
            int player_id = Integer.parseInt(command.substring(8));
            Platform.runLater(() -> App.initialize_player_id(player_id));
            System.out.println("player_id: " + player_id);
        }
        else if (command.startsWith("TURN:"))
        {
            String str_x_0 = command.substring(5, 7);
            int x_0 = Game_engine.str_two_digit_numbers_to_int(str_x_0);

            String str_y_0 = command.substring(7, 9);
            int y_0 = Game_engine.str_two_digit_numbers_to_int(str_y_0);

            String str_new_X = command.substring(9, 11);
            int new_X = Game_engine.str_two_digit_numbers_to_int(str_new_X);

            String str_new_Y = command.substring(11, 13);
            int new_Y = Game_engine.str_two_digit_numbers_to_int(str_new_Y);

            Platform.runLater(() -> Game_engine.make_move(x_0, y_0, new_X, new_Y));
        }
        else if (command.startsWith("NOT_YOUR_TURN"))
        {
            System.out.println("Not your turn!");
        }
        else if (command.startsWith("SERVER:"))
        {
            App.messages_output.appendText(command.substring(8) + "\n");
        }
        else if (command.startsWith("NEXT_TURN"))
        {
            Platform.runLater(() ->
            {
                try
                {
                    Game_engine.end_turn();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void run()
    {
        try
        {
            socket = new Socket("localhost", 56521);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            while (in.hasNextLine())
            {
                String line = in.nextLine();
                try_Server_command(line);

                // if it's a simple message, server will add "Player:" to the beginning

                if (line.startsWith("Player"))
                {
                    App.messages_output.appendText(line + "\n");
                }

            }
        }
        catch (Exception e)
        {
            System.out.println("Something went wrong! Maybe server is not running...");
        }
    }
}
