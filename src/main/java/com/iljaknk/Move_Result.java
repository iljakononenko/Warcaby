package com.iljaknk;

public class Move_Result
{
    private Move_type type;

    public Move_type getType() {
        return type;
    }

    private Piece piece;

    public Piece getPiece()
    {
        return piece;
    }

    public Move_Result(Move_type type)
    {
        this(type, null);
    }

    public Move_Result(Move_type type, Piece piece)
    {
        this.type = type;
        this.piece = piece;
    }
}
