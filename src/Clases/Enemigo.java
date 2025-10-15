package Clases;

public class Enemigo {
    int x, y;
    int vida;
    int velocidad;

    public Enemigo(int x, int y, int vida, int velocidad) {
        this.x = x;
        this.y = y;
        this.vida = vida;
        this.velocidad = velocidad;
    }

    public void mover() {
        x += velocidad;
    }

    public void recibirDaño(int d) {
        vida -= d;
    }

    public boolean estaVivo() {
        return vida > 0;
    }
}
