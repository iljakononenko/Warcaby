package com.iljaknk;

import com.iljaknk.Game.Game_engine;
import javafx.scene.control.Button;

import static com.iljaknk.App.connection;

public class menu_window_Controller
{
    public Button start_game_button;

    public void start_game() throws Exception
    {
        connection.send("GAME_STARTED:");
    }
}
