
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
public class VentanaPrincipal extends JFrame implements ActionListener, MouseListener {

	private PanelImage jpPrincipal;
	private JPanel panel;
	private JPanel panelPantalla;
	private JLabel jlPantalla;
	private JLabel jlEstatico;
	private JLabel jlOperacion;
	private JButton opcionUNO;
	private JButton opcionDOS;
	private JButton opcionTRES;
	private JButton opcionReintento;
	private int valUno;
	private int valDos;
	private int valTres;
	private JButton opcionTimer;
	private int numeroUno;
	private int numeroDos;
	private int signo;
	private int resultado;
	
	private int intentos;
	
	public VentanaPrincipal(){
		/*obitiene los números aleatorios */
		setValor();
		jpPrincipal = new PanelImage("src/fondoPrincipal.jpg",new FlexGridLayout(4, 2));
		setBounds(100, 100, 450, 300);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - getHeight()) / 2);
	    /*generacion de elementos visuales */
	    panel = new JPanel();
	    panel.setPreferredSize(new Dimension(50, 50));
	    GridBagConstraints coordenada = new GridBagConstraints();
        coordenada.gridx =0;
        coordenada.gridy=0;
        jlPantalla = new JLabel();
        jlPantalla.setFont(new Font("Arial",Font.BOLD,12));
        jlPantalla.setForeground(Color.blue);
        jlPantalla.setOpaque(true);
        jlPantalla.setText("Bienvenido");
        panel.add(jlPantalla);
        jpPrincipal.add(panel,coordenada);
        coordenada.gridy=1;
        panelPantalla = new JPanel();
        jlOperacion = new JLabel();
        setJLOperacion();
        panelPantalla.add(jlOperacion);
        panelPantalla.setSize(new Dimension(100,250));
        jpPrincipal.add(panelPantalla,coordenada);
        
        JPanel panelDos = new JPanel();
        valorBotones();
        panelDos.add(opcionUNO);
        panelDos.add(opcionDOS);
        panelDos.add(opcionTRES);
        coordenada.gridx =1;
        coordenada.gridy=2;
        opcionUNO.addMouseListener(this);
        opcionDOS.addMouseListener(this);
        opcionTRES.addMouseListener(this);
        opcionUNO.addActionListener(this);
        opcionDOS.addActionListener(this);
        opcionTRES.addActionListener(this);
        jpPrincipal.add(panelDos,coordenada);
       
        JPanel panelTres = new JPanel();
        opcionReintento = new JButton("Intente nuevamente");
        opcionReintento.addMouseListener(this);
        opcionReintento.addActionListener(this);
        opcionTimer = new JButton("Opcion Tiempo");
        opcionTimer.addMouseListener(this);
        panelTres.add(opcionReintento);
        panelTres.add(opcionTimer);
        
        
        coordenada.gridx =0;
        coordenada.gridy=3;
        jpPrincipal.add(panelTres,coordenada);
	    /*Fin elementos Visuales */
	    setLocation(x, y);
		setTitle("Juego UVG-CIFUENTES");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(jpPrincipal);
        setVisible(true);
        intentos = 1;
	}
	
	public void setJLOperacion(){
		jlOperacion.setFont(new Font("Arial",Font.BOLD,11));
        jlOperacion.setForeground(Color.DARK_GRAY);
        jlOperacion.setOpaque(true);
        String simbolo ="";
        switch(signo){
        	case 1:
        		simbolo ="+";
        		break;
        	case 2:
        		simbolo ="-";
        		break;
        	case 3:
        		simbolo ="*";
        		break;
        	case 4:
        		simbolo ="/";
        		break;
        	default:
        		simbolo ="&";
        
        }
        String texto = "<html><body>¿Cúal es el resultado: <br>    &nbsp  &nbsp "+numeroUno+""+simbolo +numeroDos+" ?"+"</body></html>";
        jlOperacion.setText(texto);
	}
	public void valorBotones(){		
		Random random = new Random();
		int sumar = (int)(random.nextDouble()*10+1);
		 resultado = 0;
		switch(signo){
    	case 1:
    		resultado  = numeroUno+numeroDos;
    		break;
    	case 2:
    		resultado  = numeroUno-numeroDos;
    		break;
    	case 3:
    		resultado  = numeroUno*numeroDos;
    		break;
    	case 4:
    		resultado  = numeroUno/numeroDos;
    		break;
    	default:
    		resultado  = 0;
    
		}
//		System.out.println("Resultado = "+resultado);
		
		int numberBoton = (int)(random.nextDouble()*3+1);
		if(opcionUNO==null)	opcionUNO = new JButton();
		opcionUNO.setText("Resultado: "+(resultado+sumar));
		valUno = resultado+sumar;
		if(opcionDOS==null)opcionDOS = new JButton();
		opcionDOS.setText("Resultado: "+(resultado-sumar));
		valDos = resultado-sumar;
		if(opcionTRES==null)opcionTRES = new JButton();
		opcionTRES.setText("Resultado: "+(resultado));
		valTres= resultado;
		switch(numberBoton){
    	case 1:
    		opcionUNO.setText("Resultado: "+(resultado+sumar));
    		opcionDOS.setText("Resultado: "+(resultado-sumar)); 
    		opcionTRES.setText("Resultado: "+(resultado));
    		valUno = resultado+sumar;
    		valDos = resultado-sumar;
    		valTres= resultado;
    		break;
    	case 2:
    		opcionUNO.setText("Resultado: "+(resultado));
    		opcionDOS.setText("Resultado: "+(resultado+sumar)); 
    		opcionTRES.setText("Resultado: "+(resultado-sumar));
    		valUno = resultado;
    		valDos = resultado+sumar;
    		valTres= resultado-sumar;
    		break;
    	case 3:
    		opcionUNO.setText("Resultado: "+(resultado+sumar));
    		opcionDOS.setText("Resultado: "+(resultado)); 
    		opcionTRES.setText("Resultado: "+(resultado-sumar));
    		valUno = resultado+sumar;
    		valDos = resultado;
    		valTres= resultado-sumar;
    		break;
    	
    	default:
    		opcionUNO.setText("Resultado: "+(resultado+sumar));
    		opcionDOS.setText("Resultado: "+(resultado-sumar)); 
    		opcionTRES.setText("Resultado: "+(resultado));
    		valUno = resultado+sumar;
    		valDos = resultado-sumar;
    		valTres= resultado;
    
		}
		
		opcionUNO.addMouseListener(this);
        opcionDOS.addMouseListener(this);
        opcionTRES.addMouseListener(this);
        opcionUNO.addActionListener(this);
        opcionDOS.addActionListener(this);
        opcionTRES.addActionListener(this);
	}
	public void setValor(){
		Funcion funcion = new Funcion();
		numeroUno = funcion.primerNumero();
		numeroDos = funcion.segundoNumero();
		signo =funcion.obtenerSigno();
	}
	public void bloquearBotones(){
		opcionUNO.setEnabled(false);
		opcionDOS.setEnabled(false);
		opcionTRES.setEnabled(false);
	}
	public void desbloquear(){
		opcionUNO.setEnabled(true);
		opcionDOS.setEnabled(true);
		opcionTRES.setEnabled(true);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
	public void actionPerformed(ActionEvent e) {
		if(opcionUNO.equals(e.getSource())){
			if(valUno==resultado){
				JOptionPane.showMessageDialog(this, "Opcion Correcta");
//				System.out.println("correcta");
				setVisible(false);
				jugamela j = new jugamela();
			}else{
				JOptionPane.showMessageDialog(this, "Opcion Incorrecta, presione el boton Intentar de Nuevo");
				bloquearBotones();
			}
		}else if(opcionDOS.equals(e.getSource())){
			if(valDos==resultado){
				JOptionPane.showMessageDialog(this, "Opcion Correcta");
				setVisible(false);
				jugamela j = new jugamela();
				
			}else{
				JOptionPane.showMessageDialog(this, "Opcion Incorrecta, presione el boton Intentar de Nuevo");
				bloquearBotones();
				
			}
		}else if(opcionTRES.equals(e.getSource())){
			if(valTres==resultado){
				JOptionPane.showMessageDialog(this, "Opcion Correcta");
				setVisible(false);
				jugamela j = new jugamela();
				
			}else{
				JOptionPane.showMessageDialog(this, "Opcion Incorrecta, presione el boton Intentar de Nuevo");
				bloquearBotones();
			}
		}else if(opcionReintento.equals(e.getSource())){
			if(intentos<=5){
				desbloquear();
				setValor();
				setJLOperacion();
				valorBotones();
				intentos +=1;
			}else{
				JOptionPane.showMessageDialog(this, "NO PUEDE UTILIZAR MÁS EL PROGRAMA\n número máximo de intentos");
				bloquearBotones();
			}
			
		}else if(opcionTimer.equals(e.getSource())){
			
		}
		
		
	}
	
}
