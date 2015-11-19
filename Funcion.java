
import java.util.Random;

public class Funcion {
	public int primerNumero(){
		Random random = new Random();
		int aleatorio = (int)(random.nextDouble()*20+1);;
		return aleatorio;
	}
	public int segundoNumero(){
		Random random = new Random();
		int aleatorio = (int)(random.nextDouble()*20+1);
		return aleatorio;
	}
	public int obtenerSigno(){
		Random random = new Random();
		int aleatorio = (int)(random.nextDouble()*4+1);
		return aleatorio;
	}
}
