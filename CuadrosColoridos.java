import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridLayout;

import javax.swing.JPanel;

import java.awt.Color;
import java.util.Random;


public class CuadrosColoridos extends JFrame implements ActionListener, MouseListener{

	private JPanel Principal;
	private JPanel[][] cuadros;
	private int n1, n2, tiempo, contador;
	
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run() {
				try{
					CuadrosColoridos window = new CuadrosColoridos();
					window.setVisible(true);
				}
				catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}
	
	public CuadrosColoridos() {
		setResizable(false);
		setTitle("posicion 1: "+n1+" posicion 2: "+n2);
		cuadros = new JPanel[6][6];
		n1=0; n2=0; contador=0;
		initialize();
	}

	private void initialize() {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		Principal = new JPanel();
		getContentPane().add(Principal);
		Principal.setLayout(new GridLayout(6, 6, 0, 0));
		
		agregarCuadros();
		cambioColor();
	}
	
	private void agregarCuadros(){
		for(int f=0; f<6; f++){
			for(int c=0; c<6; c++){
				cuadros[f][c] = new JPanel();
				cuadros[f][c].setBackground(Color.black);
				Principal.add(cuadros[f][c]);
			}
		}
	}
	
	private void cambioColor(){
		cuadros[n1][n2].setBackground(Color.BLACK);
		n1 = aleatorio();
		n2 = aleatorio();
		cuadros[n1][n2].setBackground(Color.white);
		cuadros[n1][n2].addMouseListener(this);
		contador++;
		if (contador==5){
			VentanaPrincipal principal = new VentanaPrincipal();
			setVisible(false);
		}
	}
	
	public int aleatorio(){
		int num=0;
		Random ran = new Random();
		num = ran.nextInt(5)+1;
		return num;	
	}

	@Override
	public void actionPerformed(ActionEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	public void mousePressed(MouseEvent e) {
		if(e.getSource()==cuadros[n1][n2]){
			cambioColor();
		}
	}
	
	public void mouseReleased(MouseEvent e) {}
}
