package com.iljaknk.Game;

import java.util.ArrayList;

public class All_Players
{
    ArrayList<Player> list_of_players;

    All_Players (int number_of_players)
    {
        list_of_players = new ArrayList<>();
        fill_list_of_players(number_of_players);
    }

    public boolean check_if_won (int id)
    {
        return true;
    }

    public Player get_player (int id)
    {
        return list_of_players.get(id);
    }

    public int get_Player_id (int id) {return list_of_players.get(id).getPlayer_id();}

    public String get_Players_name (int id)
    {
        return list_of_players.get(id).getName();
    }

    public Player_color get_player_color (int id)
    {
        return list_of_players.get(id).getPlayer_color();
    }

    private void fill_list_of_players (int number_of_players)
    {
        switch (number_of_players)
        {
            case 2:
                fill_list_of_2_players();
                break;
            case 3:
                fill_list_of_3_players();
                break;
            case 4:
                fill_list_of_4_players();
                break;
            case 6:
                fill_list_of_6_players();
                break;
        }
    }

    private void fill_list_of_2_players()
    {
        list_of_players.add(new Player(Player_color.WHITE));
        list_of_players.add(new Player(Player_color.GREEN));
    }

    private void fill_list_of_3_players()
    {
        list_of_players.add(new Player(Player_color.WHITE));
        list_of_players.add(new Player(Player_color.YELLOW));
        list_of_players.add(new Player(Player_color.RED));
    }

    private void fill_list_of_4_players()
    {
        list_of_players.add(new Player(Player_color.BLUE));
        list_of_players.add(new Player(Player_color.BLACK));
        list_of_players.add(new Player(Player_color.YELLOW));
        list_of_players.add(new Player(Player_color.RED));
    }

    private void fill_list_of_6_players()
    {
        list_of_players.add(new Player(Player_color.WHITE));
        list_of_players.add(new Player(Player_color.BLACK));
        list_of_players.add(new Player(Player_color.YELLOW));
        list_of_players.add(new Player(Player_color.GREEN));
        list_of_players.add(new Player(Player_color.RED));
        list_of_players.add(new Player(Player_color.BLUE));
    }

}
