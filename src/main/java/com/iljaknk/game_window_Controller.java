package com.iljaknk;

import com.iljaknk.Game.Game_engine;

import static com.iljaknk.App.connection;

public class game_window_Controller
{
    public void end_turn () throws Exception
    {
        connection.send("NEXT_TURN");
    }
}
