package com.iljaknk;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static com.iljaknk.App.NODE_SIZE;

public class Piece extends StackPane
{
    private Piece_type type;
    private double mouseX, mouseY, oldX, oldY;

    public Piece_type getType() {
        return type;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Piece(Piece_type type, int x, int y)
    {
        this.type = type;
        Move(x * 2, y * 2);

        Ellipse bg = new Ellipse(NODE_SIZE * 0.3125 * 2, NODE_SIZE * 0.26 * 2);
        bg.setFill(Color.BLACK);
        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(NODE_SIZE * 0.03);

        bg.setTranslateX((NODE_SIZE - NODE_SIZE * 0.3125 * 2) / 2);
        bg.setTranslateY((NODE_SIZE - NODE_SIZE * 0.26 * 2) / 2 + NODE_SIZE * 0.07);

        Ellipse ellipse = new Ellipse(NODE_SIZE * 0.3125 * 2, NODE_SIZE * 0.26 * 2);
        color_picker(type, ellipse);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(NODE_SIZE * 0.03);

        ellipse.setTranslateX((NODE_SIZE - NODE_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((NODE_SIZE - NODE_SIZE * 0.26 * 2) / 2);

        getChildren().addAll(bg, ellipse);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });
        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });


    }

    public void Move (int x, int y)
    {
        oldX = x * NODE_SIZE;
        oldY = y * NODE_SIZE;
        relocate(oldX, oldY);
    }

    public void abort_Move()
    {
        relocate(oldX, oldY);
    }



    public void color_picker (Piece_type type, Ellipse ellipse)
    {
        if (type == Piece_type.BLACK)
        {
            ellipse.setFill(Color.valueOf("#221F27"));
        }
        if (type == Piece_type.RED)
        {
            ellipse.setFill(Color.valueOf("#E2252D"));
        }
        if (type == Piece_type.WHITE)
        {
            ellipse.setFill(Color.valueOf("#CEC1C1"));
        }
        if (type == Piece_type.GREEN)
        {
            ellipse.setFill(Color.valueOf("#347430"));
        }
        if (type == Piece_type.YELLOW)
        {
            ellipse.setFill(Color.valueOf("#F9E948"));
        }
        if (type == Piece_type.BLUE)
        {
            ellipse.setFill(Color.valueOf("#49599A"));
        }
        if (type == Piece_type.NONE)
        {
            ellipse.setFill(Color.valueOf("#7A371C"));
        }

    }
}
