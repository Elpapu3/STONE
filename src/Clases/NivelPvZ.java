package Clases;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class NivelPvZ extends JPanel implements Runnable{
	private ArrayList<Enemigo> enemigos;
	private ArrayList<Torre> torres;
	private boolean corriendo = true;
	
	public NivelPvZ() {
		
			enemigos = new ArrayList<>();
			torres = new ArrayList<>();
			
			enemigos.add(new Enemigo(0,200,100,2));
			
			torres.add(new Torre(300,100,100,10));
			
			setBackground(new Color(100,200,100));
			new Thread(this).start();
	}
	@Override
	public void run() {
		while (corriendo) {
            actualizar();
			repaint();
			
			try {
				Thread.sleep(30);
			}catch (InterruptedException e) {
				
			}
		}
	}
	
	private void actualizar() {
		for(Enemigo e : enemigos) e.mover();
		for(Torre t : torres) t.atacar(enemigos);
        enemigos.removeIf(e -> !e.estaVivo());
	}
	
	@Override
	public void paintComponent(Graphics g) {
		 super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D) g;

	        // Dibujar el "camino"
	        g2.setColor(new Color(180, 140, 80));
	        g2.fillRect(0, 180, getWidth(), 60);

	        // Dibujar enemigos (rojos)
	        g2.setColor(Color.RED);
	        for (Enemigo e : enemigos) {
	            g2.fillRect(e.x, e.y, 30, 30);
	        }

	        // Dibujar torres (verdes)
	        g2.setColor(Color.BLUE);
	        for (Torre t : torres) {
	            g2.fillRect(t.x, t.y, 40, 40);
	        }
	}
}
