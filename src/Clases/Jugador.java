package Clases;

import java.awt.*;
import java.util.ArrayList;

public class Jugador {
    private int x, y, size;
    private Color color;
    private boolean vivo = true;

    public Jugador(int x, int y, int size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public void mover(int dx, int dy, ArrayList<Rectangle> muros) {
        Rectangle nueva = new Rectangle(x + dx, y + dy, size, size);
        for (Rectangle m : muros) if (nueva.intersects(m)) return;
        x += dx;
        y += dy;
    }

    public void morir() { vivo = false; }
    public boolean estaVivo() { return vivo; }

    public int getX() { return x; }
    public int getY() { return y; }
    public Rectangle getRect() { return new Rectangle(x, y, size, size); }

    public void dibujar(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, size, size);
    }
    public void moverVertical(int dy) {
        y += dy;
    }

}
