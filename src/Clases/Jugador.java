package Clases;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Jugador {
    private int x, y;
    private int tamaño;
    private Color color;

    public Jugador(int x, int y, int tamaño, Color color) {
        this.x = x;
        this.y = y;
        this.tamaño = tamaño;
        this.color = color;
    }

    public void dibujar(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, tamaño, tamaño);
    }

    // Movimiento con colisión
    public void mover(int dx, int dy, ArrayList<Rectangle> muros, int anchoVentana, int altoVentana) {
        int nuevoX = x + dx;
        int nuevoY = y + dy;

        Rectangle futuro = new Rectangle(nuevoX, nuevoY, tamaño, tamaño);
        boolean colision = false;

        for (Rectangle muro : muros) {
            if (futuro.intersects(muro)) {
                colision = true;
                break;
            }
        }

        if (!colision) {
            x = Math.max(0, Math.min(anchoVentana - tamaño, nuevoX));
            y = Math.max(0, Math.min(altoVentana - tamaño, nuevoY));
        }
    }

    // 🔹 Nuevo método: devuelve el rectángulo actual del jugador
    public Rectangle getRect() {
        return new Rectangle(x, y, tamaño, tamaño);
    }

    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getTamaño() { return tamaño; }
}
