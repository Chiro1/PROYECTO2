import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.event.*;
import java.util.*;

public class jugamela {

	private JFrame frame;
	private JPanel Une;
	private JLabel lblDRL1, lblDRL2, lblDRL3;
	private Timer display;
	private JLabel lblTime,lblImg1,lblImg2,lblImg3,lblImg4,lblImg5,lblImg7,lblImg8,lblImg6;
	private int tiempo, contador, aleatorio;
	private int seleccion;
	private String[] parejas;
	private JLabel[] matrizLabel;
	private Object[] imagenes;
	private String[] nombres;
	private int aciertos;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					jugamela window = new jugamela();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public jugamela() {
		seleccion=0;
		aciertos=0;
		parejas=new String[8];
		parejas[0]="vacio";
		contador= 1;
		tiempo=15;
		initialize();
		display = new Timer();
		display.schedule(new TimerTask() { public void run() { quitarMensaje(); lblTime.setVisible(true); ;for (int i=0; i<8; i++){matrizLabel[i].setVisible(true);}}}, 2000);
		display.scheduleAtFixedRate(new TimerTask() {public void run() {if (actualizarTimer()==0){for (int i=0; i<8; i++){ matrizLabel[i].setVisible(false);lblTime.setVisible(false); lblDRL1.setVisible(true);lblDRL1.setText("¡PERDISTE!");}}}}, 2000, 1000);
//		display.scheduleAtFixedRate(new TimerTask() {public void run() {if (aciertos == 4){for (int i=0; i<8; i++){ matrizLabel[i].setVisible(false);lblTime.setVisible(false); lblDRL1.setVisible(true);lblDRL1.setText("¡GANASTE!");display.purge(); }}}}, 100, 100);
		display.scheduleAtFixedRate(new TimerTask() {public void run() {if (aciertos == 4){for (int i=0; i<8; i++){ matrizLabel[i].setVisible(false);lblTime.setVisible(false); lblDRL1.setVisible(true); }}}}, 100, 100);
	}
	
	private void comprobacion(int numero){
		if (matrizLabel[numero].getName().equals(matrizLabel[seleccion].getName())){
			contador=1;
			matrizLabel[numero].setVisible(false);
			matrizLabel[seleccion].setVisible(false);
			aciertos++;
		}
		else{
			contador=1;
		}
	}
	private int actualizarTimer(){
		lblTime.setText("Time: "+tiempo+" s");
		tiempo=tiempo-1;
		return tiempo;
	}
	private void quitarMensaje(){
		lblDRL1.setVisible(false);
		lblDRL2.setVisible(false);
		lblDRL3.setVisible(false);
	}
	private void genGame(){
		int has=3;
		int desde=0;
		
		//objetoRandom.nextInt(HASTA-DESDE+1)+DESDE
		Random rand = new Random();
		//Se generan los numeros aleatoriamente
		aleatorio = rand.nextInt(4);
		int[] posiciones= new int[4];
		
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.getContentPane().setLayout(null);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		Une = new JPanel();
		frame.getContentPane().add(Une);
		Une.setLayout(null);
		
	//LABEL DE INSTRUCCIONES	
		lblDRL1 = new JLabel("DRAW LINE");
		lblDRL1.setFont(new Font("Tekton Pro", Font.PLAIN, 58));
		lblDRL1.setBounds(32, 48, 345, 43);
		Une.add(lblDRL1);
		
		lblDRL2 = new JLabel("Este juego consiste en emparejar las palabras con");
		lblDRL2.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblDRL2.setBounds(31, 102, 393, 43);
		Une.add(lblDRL2);
		
		lblDRL3 = new JLabel(" su respectiva imagen");
		lblDRL3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDRL3.setBounds(117, 144, 434, 43);
		Une.add(lblDRL3);
		
		lblTime = new JLabel("Time:");
		lblTime.setBounds(344, 11, 80, 43);
		lblTime.setVisible(false);
		Une.add(lblTime);
	//MATRIZ DE IMAGENES	
		imagenes= new Object[5];
		nombres= new String[8];
		nombres[4]="bebé";
		nombres[5]="lentes";
		nombres[6]="pájaro";
		nombres[7]="celular";
		String imagen="src\\img";
		for (int i=0; i<4; i++){
			imagenes[i]= new ImageIcon(imagen+i+".jpg",imagen+i+".jpg" );
		}
		
	//LABELS DE IMAGENES	
		matrizLabel= new JLabel[8];
		int v1=29;
		int v2=35;
		String name="IMG";
		for (int i=0; i<8; i++){
			if (i < 4 ){
			matrizLabel[i]= new JLabel();
			matrizLabel[i].setName("n"+ (i));
			matrizLabel[i].setBounds(v1, v2, 61,50);
			matrizLabel[i].setVisible(false);
			matrizLabel[i].addMouseListener(new LineListener());
			matrizLabel[i].setIcon((Icon) imagenes[i]);
			
			Une.add(matrizLabel[i]);
			v2= v2+56;
			}
			else{
				v1=301;
				matrizLabel[i]= new JLabel();
				matrizLabel[i].setName("n"+ (i-4));
				matrizLabel[i].setBounds(v1, v2, 61,14);
				matrizLabel[i].setVisible(false);
				matrizLabel[i].addMouseListener(new LineListener());
				matrizLabel[i].setText(nombres[i]);
				Une.add(matrizLabel[i]);
				v2= v2+56;
			}
			
			if (i== 3){
				v2=52;
			}	
		}
	}

	//Clase que implementa el listener
private class LineListener implements MouseListener, MouseMotionListener{

public void mousePressed (MouseEvent e)
{
}

public void mouseDragged (MouseEvent e)
{
}
@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	}
@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	}
@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub	
}
@Override
public void mouseMoved(MouseEvent e) {
	// TODO Auto-generated method stub
}
@Override
public void mouseClicked(MouseEvent e) {

	if (contador==1){
		if (parejas[0] == "vacio" ){
		if (e.getSource()== matrizLabel[0]){
			seleccion=0;
			contador++;
		}
		if (e.getSource()== matrizLabel[1]){
			seleccion=1;
			contador++;
		}
		if (e.getSource()== matrizLabel[2]){
			seleccion=2;
			contador++;
		}
		if (e.getSource()== matrizLabel[3]){
			seleccion=3;
			contador++;
		}
		}

		}
		else if (contador==2){
		if (e.getSource()== matrizLabel[4]){
			
			comprobacion(4);
		}
		if (e.getSource()== matrizLabel[5]){
			
			comprobacion(5);
		}
		if (e.getSource()== matrizLabel[6]){
			comprobacion(6);
		}
		if (e.getSource()== matrizLabel[7]){
			comprobacion(7);
		}
		}	
	
	
}
}
	
	
	

}
