package Clases;

import java.awt.*;

public class Bomba {
    private int x, y, size = 40;
    private int tiempo = 30; 
    private boolean explotando = false;

    public Bomba(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void actualizar() {
        tiempo--;
        if (tiempo <= 0 && !explotando) {
            explotando = true;
            tiempo = 10; 
        } else if (explotando) {
            tiempo--;
        }
    }

    public boolean estaExplotando() {
        return explotando;
    }

    public boolean finalizo() {
        return explotando && tiempo <= 0;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, size, size);
    }

    public Rectangle getExplosionArea() {
        int ext = size * 2;
        return new Rectangle(x - size, y - size, ext + size, ext + size);
    }

    public void dibujar(Graphics g) {
        if (!explotando) {
            g.setColor(Color.BLACK);
            g.fillOval(x, y, size, size);
        } else {
            g.setColor(Color.ORANGE);
            g.fillRect(x - size, y - size, size * 3, size * 3);
        }
    }
}
