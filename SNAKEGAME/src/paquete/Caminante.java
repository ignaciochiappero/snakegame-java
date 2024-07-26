/*   HOLISSSS - BIENVENIDO AL CÓDIGO DE LA VIVORITA CREADA POR NACHO CHIAPPERO!!   */
package paquete;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Caminante implements Runnable {

    PanelSnake panel;
    boolean estado=true;
    
    public Caminante(PanelSnake panel){
        
        this.panel=panel;
        
    }
    
    
    
    
    @Override
    public void run() {
        
        while (estado)
        {
            panel.avanzar();
            panel.repaint();
        
            try {
            Thread.sleep(50);
            } catch (InterruptedException ex) {
            Logger.getLogger(Caminante.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        
       
    }//FIN METODO RUN
    
    
    public void parar(){
        this.estado=false;
    }
    
}// FINAL
