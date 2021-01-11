package com.iljaknk.Game.app_data;

import com.iljaknk.Game.Player_color;

public class Board_Factory
{
    public Board sample;

    public Board_Factory(int number_of_players)
    {
        sample = new Board(number_of_players);
    }

    public void build_Board (int number_of_players)
    {

        // creating White's home

        int min_x = 12;
        int max_x = 12;

        for (int y = 0; y < 4; y++)
        {
            for (int x = min_x; x <= max_x; x++)
            {
                if ((x + y) % 2 == 0)
                {
                    if (number_of_players != 4)
                    {
                        sample.add(Player_color.WHITE, x, y);
                    }

                    else
                    {
                        sample.add(Player_color.NONE, x, y);
                    }
                }
            }
            min_x --;
            max_x ++;
        }

        // creating Black's home

        min_x = 18;
        max_x = 24;

        for (int y = 4; y < 8; y++)
        {
            for (int x = min_x; x <= max_x; x++)
            {
                if ((x + y) % 2 == 0)
                {
                    if (number_of_players == 4 || number_of_players == 6)
                    {
                        sample.add(Player_color.BLACK, x, y);
                    }

                    else
                    {
                        sample.add(Player_color.NONE, x, y);
                    }
                }

            }
            max_x --;
            min_x ++;
        }

        // creating Yellow's home

        min_x = 21;
        max_x = 21;

        for (int y = 9; y < 13; y++)
        {
            for (int x = min_x; x <= max_x; x++)
            {
                if ((x + y) % 2 == 0)
                {
                    if (number_of_players != 2)
                    {
                        sample.add(Player_color.YELLOW, x, y);
                    }

                    else
                    {
                        sample.add(Player_color.NONE, x, y);
                    }
                }
            }
            min_x --;
            max_x ++;
        }

        // creating Green's home

        min_x = 9;
        max_x = 15;

        for (int y = 13; y < 17; y++)
        {
            for (int x = min_x; x <= max_x; x++)
            {
                if ((x + y) % 2 == 0)
                {
                    if (number_of_players == 2 || number_of_players == 6)
                    {
                        sample.add(Player_color.GREEN, x, y);
                    }

                    else
                    {
                        sample.add(Player_color.NONE, x, y);
                    }

                }
            }
            max_x --;
            min_x ++;
        }

        // creating Red's home

        min_x = 3;
        max_x = 3;

        for (int y = 9; y < 13; y++)
        {
            for (int x = min_x; x <= max_x; x++)
            {
                if ((x + y) % 2 == 0)
                {
                    if (number_of_players != 2)
                    {
                        sample.add(Player_color.RED, x, y);
                    }

                    else
                    {
                        sample.add(Player_color.NONE, x, y);
                    }

                }
            }
            min_x --;
            max_x ++;
        }

        // creating Blue's home

        min_x = 0;
        max_x = 6;

        for (int y = 4; y < 8; y++)
        {
            for (int x = min_x; x <= max_x; x++)
            {
                if ((x + y) % 2 == 0)
                {
                    if (number_of_players == 4 || number_of_players == 6)
                    {
                        sample.add(Player_color.BLUE, x, y);
                    }

                    else
                    {
                        sample.add(Player_color.NONE, x, y);
                    }

                }
            }
            max_x --;
            min_x ++;
        }

        // creating the center (Part two)

        min_x = 8;
        max_x = 16;

        for (int y = 4; y <= 8; y++)
        {
            for (int x = min_x; x <= max_x; x++)
            {
                if ((x + y) % 2 == 0)
                {
                    sample.add(Player_color.NONE, x, y);
                }
            }
            min_x --;
            max_x ++;
        }

        // creating the center (Part two)

        min_x = 5;
        max_x = 19;

        for (int y = 9; y < 13; y++)
        {
            for (int x = min_x; x <= max_x; x++)
            {
                if ((x + y) % 2 == 0)
                {
                    sample.add(Player_color.NONE, x, y);
                }
            }
            min_x ++;
            max_x --;
        }

    }


    public Board get_Board ()
    {
        return sample;
    }
}
