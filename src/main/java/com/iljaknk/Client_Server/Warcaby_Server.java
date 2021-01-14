package com.iljaknk.Client_Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Warcaby_Server
{
    private static int players_turn;

    private static Integer number_of_players = 0;

    private static List<Integer> winners = new ArrayList<>();

    private static Set<PrintWriter> online_players = new HashSet<>();

    public static void main(String[] args) throws Exception
    {
        try (ServerSocket server = new ServerSocket(56521))
        {
            server.setReuseAddress(true);
            System.out.println("Server is running");
            while (true)
            {
                Socket socket = server.accept();
                new_Player player = new new_Player(socket);
                new Thread(player).start();
            }
        }
    }

    private static class new_Player implements Runnable
    {
        private int id;
        private String player_name;
        private Socket socket;
        private Scanner in;
        private PrintWriter out;

        new_Player (Socket socket)
        {
            this.socket = socket;
        }

        @Override
        public void run()
        {
            try
            {
                send_to_all_Player("hello");

                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);

                id = number_of_players;
                number_of_players++;
                player_name = "Player " + number_of_players;

                out.println("Welcome Player " + number_of_players + "!");
                // we send id immediatly to set it from the beginning
                out.println("YOUR_ID:" + id);

                // we notify other players that new player has connected
                System.out.println("Player " + number_of_players + " has connected!");
                send_to_all_Player("Player " + number_of_players + " has connected!");

                // we add newly created player to our Set of players
                online_players.add(out);

                for (PrintWriter player : online_players)
                {
                    player.println("PLAYER_NUMBER_CHANGED " + number_of_players);
                }

                Random random = new Random();

                while (true)
                {
                    String message = in.nextLine();
                    System.out.println(message);

                    // someone started a game

                    if (message.startsWith("GAME_STARTED:"))
                    {
                        // check if we have an appropriate number of players
                        if (number_of_players == 1 || number_of_players == 5 || number_of_players > 6)
                        {
                            // if number of players isn't equal 2,3,4 or 6 we cannot start a game
                            // we send an error message
                            for (PrintWriter player : online_players)
                            {
                                player.println("SERVER: Wrong players count!");
                                player.println("SERVER: You can start game only with 2,3,4 or 6 players!");
                            }
                        }
                        else
                        {
                            // if everything is ok we start game by sending command to all players
                            players_turn = random.nextInt(number_of_players);

                            System.out.println("First player is: " + players_turn);

                            send_to_all_Player(message + players_turn);
                        }
                    }

                    // Player moved a piece

                    else if (message.startsWith("TURN:"))
                    {
                        // check if it's player's turn

                        if (players_turn == id)
                        {
                            // send a move to all players

                            send_to_all_Player(message);
                        }

                        else
                        {
                            out.println("NOT_YOUR_TURN");
                        }

                    }

                    // if button "end turn" was pressed

                    else if (message.startsWith("NEXT_TURN"))
                    {
                        // check if it's current player's turn
                        if (players_turn == id)
                        {

                            do
                            {
                                // switch to the next turn
                                append_players_turn();

                                // send command to append turn at all Players Apps
                                send_to_all_Player(message);

                            }

                            // if a next player is a winner, do it again

                            while (is_a_winner(players_turn));
                        }

                        else
                        {
                            out.println("NOT_YOUR_TURN");
                        }
                    }

                    else if (message.startsWith("WINNER:"))
                    {
                        int winner = Integer.parseInt(message.substring(7));
                        winners.add(winner);
                        System.out.println(winners.get(0));
                    }

                    else
                    {
                        // simple chat messages
                        send_to_all_Player(player_name + ": " + message);
                    }
                }

            }
            catch (Exception e)
            {
                System.out.println(e);
            }
            finally
            {
                if (out != null)
                {
                    number_of_players--;
                    online_players.remove(out);
                }
                if (player_name != null)
                {
                    System.out.println("Player " + (id + 1) + " is leaving");
                    send_to_all_Player("PLAYER_NUMBER_CHANGED " + number_of_players);
                }
                try
                {
                    socket.close();
                }
                catch (IOException e)
                {

                }
            }
        }

        public void send_to_all_Player (String message)
        {
            for (PrintWriter player : online_players)
            {
                player.println(message);
            }
        }

        public boolean is_a_winner (int id)
        {
            for (Integer i : winners)
            {
                if (i == id)
                {
                    return true;
                }
            }
            return false;
        }

        // append turn and if ended cycle, set to zero

        public void append_players_turn ()
        {
            players_turn++;
            if (players_turn % number_of_players == 0)
            {
                players_turn = 0;
            }
        }
    }
}


