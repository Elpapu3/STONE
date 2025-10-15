package Clases;

import java.awt.*;

public class Bomba {
    private int x, y;
    private int tiempo;
    private boolean explotando;
    private boolean finalizo;
    private int radio = 80;

    public Bomba(int x, int y) {
        this.x = x;
        this.y = y;
        this.tiempo = 20; // 2 segundos (si el timer actualiza cada 100 ms)
        this.explotando = false;
        this.finalizo = false;
    }

    public void actualizar() {
        if (!explotando) {
            tiempo--;
            if (tiempo <= 0) {
                explotando = true;
                tiempo = 10; // Duración de la explosión
            }
        } else {
            tiempo--;
            if (tiempo <= 0) {
                finalizo = true;
            }
        }
    }

    public void dibujar(Graphics g) {
        if (explotando) {
            g.setColor(Color.ORANGE);
            g.fillOval(x - radio / 2, y - radio / 2, radio * 2, radio * 2);
        } else {
            g.setColor(Color.BLACK);
            g.fillOval(x + 10, y + 10, 20, 20);
        }
    }

    public boolean estaExplotando() {
        return explotando;
    }

    public boolean finalizo() {
        return finalizo;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, 40, 40);
    }

    public Rectangle getExplosionArea() {
        return new Rectangle(x - radio, y - radio, radio * 2, radio * 2);
    }
}
