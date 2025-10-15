package Clases;

import java.util.ArrayList;

public class Torre {
    int x, y;
    int rango;
    int daño;

    public Torre(int x, int y, int rango, int daño) {
        this.x = x;
        this.y = y;
        this.rango = rango;
        this.daño = daño;
    }

    public void atacar(ArrayList<Enemigo> enemigos) {
        for (Enemigo e : enemigos) {
            double dist = Math.hypot(e.x - x, e.y - y);
            if (dist < rango) {
                e.recibirDaño(daño);
                break;
            }
        }
    }
}
