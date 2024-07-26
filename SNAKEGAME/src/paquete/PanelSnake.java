/* SNAKE:

   HOLISSSS - BIENVENIDO AL CÓDIGO DE LA VIVORITA CREADA POR NACHO CHIAPPERO!!   

   (Funca bien, pero se le podria agregar subir velocidad, un contador de puntos y un boton de reinicio)


*/
package paquete;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PanelSnake extends JPanel {

    Color colorSnake = Color.darkGray;
    Color colorComida = Color.RED.darker();
    int tammax, tam, can, res;
    List<int[]> snake = new ArrayList();
    int[] comida = new int[2];
    String direccion = "de";
    String direccionProxima = "de";
    public int puntos = 0;

    Thread hilo;
    Caminante camino;

    public PanelSnake(int tammax, int can) // CONSTRUCTOR
    {

        this.tammax = tammax;
        this.can = can;
        this.tam = tammax / can;
        this.res = tammax % can;

        int[] a = {can / 2 - 1, can / 2 - 1}; // estas son las coordenadas de los cuadrados del cuerpo de la serpiente
        int[] b = {can / 2, can / 2 - 1};
        snake.add(a);
        snake.add(b);

        generarComida();

        camino = new Caminante(this);
        hilo = new Thread(camino);
        hilo.start();

    }

    @Override
    public void paint(Graphics pintor) {

        super.paint(pintor);
        pintor.setColor((colorSnake));

        // Pintando serpiente
        for (int[] par : snake) {
            pintor.fillOval(res / 2 + par[0] * tam, res / 2 + par[1] * tam, tam - 1, tam - 1);
        }

        // Pintando comida
        pintor.setColor(colorComida);
        pintor.fillOval(res / 2 + comida[0] * tam, res / 2 + comida[1] * tam, tam - 1, tam - 1);

    }

    public void avanzar() {

        igualarDireccion();

        int[] ultimo = snake.get(snake.size() - 1);
        int agregarx = 0;
        int agregary = 0;
        switch (direccion) {
            case "de":
                agregarx = 1;
                break;
            case "iz":
                agregarx = -1;
                break;
            case "ar":
                agregary = -1;
                break;
            case "ab":
                agregary = 1;
                break;

        }

        int[] nuevo = {Math.floorMod(ultimo[0] + agregarx, can), Math.floorMod(ultimo[1] + agregary, can)};
        //             Math.floorMod hace que el bicho cuando toque un borde se repinte al otro lado de la pantalla

        boolean existe = false;

        for (int i = 0; i < snake.size(); i++) {
            if (nuevo[0] == snake.get(i)[0] && nuevo[1] == snake.get(i)[1]) {
                existe = true;
                break;
            }
        }

        if (existe) // cuando se pierde
        {

            gameOver();

        } else // cada vez que se suma un punto
        {
            if (nuevo[0] == comida[0] && nuevo[1] == comida[1]) {
                snake.add(nuevo);
                puntos++;
                generarComida();

            } else {
                snake.add(nuevo);
                snake.remove(0);
            }
        }

    }

    public void generarComida() {

        boolean existe = false;

        // COORDENADAS ALEATORIAS PARA LA COMIDA
        int a = (int) (Math.random() * can); // poner el (int) hace que ese nro se transforme a un entero
        int b = (int) (Math.random() * can); // le ponemos el can para que se quede dentro del marco

        for (int[] par : snake) {
            if (par[0] == a && par[1] == b) {
                existe = true;
                generarComida();
                break;
            }
        }

        if (!existe) {
            this.comida[0] = a;
            this.comida[1] = b;

        }

    }

    public void cambiarDireccion(String dir) {

        if ((this.direccion.equals("de") || this.direccion.equals("iz")) && (dir.equals("ar") || dir.equals("ab"))) {
            this.direccionProxima = dir;
        } else if ((this.direccion.equals("ar") || this.direccion.equals("ab")) && (dir.equals("de") || dir.equals("iz"))) {
            this.direccionProxima = dir;
        }

    }

    public void igualarDireccion() {

        this.direccion = this.direccionProxima;

    }

    public void gameOver() {

        JLabel cartel = new JLabel();
        cartel.setLayout(null);

        cartel.setText("GAME OVER");

    
        cartel.setBounds(250, 300, 300, 200);
        cartel.setFont(new Font("Arial", 1, 30));
        this.add(cartel);

    //    cartel.setOpaque(true);
      //  cartel.setBackground(Color.black);
        cartel.setForeground(Color.red.darker());
        cartel.setHorizontalAlignment(SwingConstants.CENTER);

    }

}
