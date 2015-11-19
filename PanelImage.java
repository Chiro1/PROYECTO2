
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.*;

public class PanelImage extends JPanel{
    private ImageIcon imagen;
    private String url;
    public PanelImage(){

    }
    public PanelImage(String url){
    	this.url = url;
    }
    public PanelImage(String url,LayoutManager layoutManager){
        super(layoutManager);
        this.url = url;
    }
    public PanelImage(String url,GridBagLayout layoutManager){
        super(layoutManager);
        this.url = url;
    }
    public void paint(Graphics g){
        if(url != null){
            Dimension tamanio = getSize();
            imagen = new ImageIcon("src/fondoPrincipal.jpg");
            g.drawImage(imagen.getImage(),0,0,tamanio.width,tamanio.height,null);
            setOpaque(false);
            super.paint(g);
        }else{
            Dimension tamanio = getParent().getSize();
            imagen = new ImageIcon(getClass().getResource(url));
            g.drawImage(imagen.getImage(),0,0,tamanio.width,tamanio.height,null);
            setOpaque(false);
            super.paint(g);
        }

    }
}

