package Clases;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Enemigo {
    private int x, y, size;
    private Random rand = new Random();
    private Color color;
    private int velocidad = 3;
    private boolean modoPersecucion; 

    public Enemigo(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
        this.modoPersecucion = false; 
    }

    public void setModoPersecucion(boolean estado) {
        this.modoPersecucion = estado;
    }

    public void mover(int TAM, ArrayList<Rectangle> muros, Jugador jugador) {
        if (modoPersecucion && jugador != null) {
            perseguirJugador(jugador);
        } else {
            moverAleatorio(TAM, muros);
        }
    }

    private void moverAleatorio(int TAM, ArrayList<Rectangle> muros) {
        int dx = 0, dy = 0;
        int dir = rand.nextInt(4);

        switch (dir) {
            case 0 -> dy = -TAM; 
            case 1 -> dy = TAM;  
            case 2 -> dx = -TAM; 
            case 3 -> dx = TAM;  
        }

        Rectangle nueva = new Rectangle(x + dx, y + dy, size, size);
        for (Rectangle m : muros)
            if (m.intersects(nueva)) return;

        x += dx;
        y += dy;
    }

    private void perseguirJugador(Jugador jugador) {
        int jx = jugador.getX();
        int jy = jugador.getY();

        if (jx > x) x += velocidad;
        if (jx < x) x -= velocidad;
        if (jy > y) y += velocidad / 2;
        if (jy < y) y -= velocidad / 2;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Rectangle getRect() { return new Rectangle(x, y, size, size); }

    
    public void dibujar(Graphics g) {

    	g.setColor(color);
        g.fillOval(x, y, size, size);

        g.setColor(Color.BLACK);
        g.fillOval(x + size / 3, y + size / 4, size / 6, size / 6);
        g.fillOval(x + size / 2, y + size / 4, size / 6, size / 6);

        g.setColor(new Color(0, 0, 0, 60));
        g.fillOval(x + 4, y + size - 5, size / 2, 5);
    }

	public void moverVertical(int stepY) {
		// TODO Auto-generated method stub
		
	}
}

