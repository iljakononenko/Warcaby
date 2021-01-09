package com.iljaknk;

import javafx.scene.Group;

public class Board_Factory
{
    Board sample;

    Board_Factory ()
    {
        sample = new Board();
    }

    public void build_Board (Group nodes, Group pieces)
    {
        sample.setNodes(nodes);
        sample.setPieces(pieces);

        for (int x = -8; x < -4; x ++)
        {
            for(int z = 1; z < 5; z ++)
            {
                for (int y = 1; y < 5; y++)
                {
                    if ((z + y) == x * (-1))
                    {
                        sample.add(Piece_type.RED, x,y);
                    }
                }
            }
        }

        for (int x = 5; x < 9; x ++)
        {
            for(int z = -1; z > -5; z--)
            {
                for (int y = -1; y > -5; y--)
                {
                    if ((z + y) == x * (-1))
                    {
                        sample.add(Piece_type.BLACK, x,y);
                    }
                }
            }
        }

        for (int y = -8; y < -4; y++)
        {
            for(int z = 1; z < 5; z++)
            {
                for (int x = 1; x < 5; x++)
                {
                    if ((z + x) == y * (-1))
                    {
                        sample.add(Piece_type.YELLOW, x,y);
                    }
                }
            }
        }

        for (int y = 5; y < 9; y++)
        {
            for(int z = -1; z > -5; z--)
            {
                for (int x = -1; x > -5; x--)
                {
                    if ((z + x) == y * (-1))
                    {
                        sample.add(Piece_type.BLUE, x,y);
                    }
                }
            }
        }

        for (int z = -8; z < -4; z++)
        {
            for(int y = 1; y < 5; y++)
            {
                for (int x = 1; x < 5; x++)
                {
                    if ((z + x) == y * (-1))
                    {
                        sample.add(Piece_type.WHITE, x,y);
                    }
                }
            }
        }

        for (int z = 5; z < 9; z++)
        {
            for(int y = -1; y > -5; y--)
            {
                for (int x = -1; x > -5; x--)
                {
                    if ((y + x) == z * (-1))
                    {
                        sample.add(Piece_type.GREEN, x,y);
                    }
                }
            }
        }


        for (int x = -4; x < 5; x++)
        {
            for (int z = -4; z < 5; z++)
            {
                for (int y = -4; y < 5; y++)
                {
                    if ((z + y) == x * (-1))
                    {
                        sample.add(Piece_type.NONE, x,y);
                    }
                }
            }
        }
    }


    public Board get_Board ()
    {
        return sample;
    }
}
