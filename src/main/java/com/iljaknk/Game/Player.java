package com.iljaknk.Game;

public class Player
{
    String name;
    Player_color player_color;
    int player_id;
    static Integer number_of_players = 1;


    Player (Player_color color)
    {
        player_id = number_of_players - 1;
        name = "Player " + number_of_players.toString();
        this.player_color = color;
        number_of_players++;
    }

    public String getName()
    {
        return name;
    }

    public int getPlayer_id()
    {
        return player_id;
    }

    public Player_color getPlayer_color()
    {
        return player_color;
    }



}
