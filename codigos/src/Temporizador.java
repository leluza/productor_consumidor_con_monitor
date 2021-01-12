import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Temporizador {
        private Log file;
        private ArrayList<LinkedList<Guardodato>> buffers;
        Consumidor consumidores[];

        public Temporizador(ArrayList<LinkedList<Guardodato>> buf, Consumidor consum[], Log archivo){
            file=archivo;
            buffers = buf;
            consumidores=consum;

            Timer timer = new Timer(true);

            TimerTask tarea = new TimerTask(){
                @Override
                public void run() {

                        String cadena1 = "Plazas en Buffer 15 - Tomadas: " + buffers.get(0).size();
                        String cadena2 = "Plazas en Buffer 10 - Tomadas: " + buffers.get(1).size();
                        try {
                            file.escribirArchivo(cadena1);
                            file.escribirArchivo(cadena2);

                        } catch (IOException e) {
                            System.out.println("Falla al escribir el archivo");
                            e.printStackTrace();
                        }

                    for (int n = 0; n < consumidores.length; n++) {

                        String cadena = "El consumidor " + n + " se encuentra: " + consumidores[n].getState();

                    try {

                        file.escribirArchivo(cadena);   //escribe el estado de los hilos consumidores en el archivo log


                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());

                    }
                }
                }
            };
            timer.schedule(tarea, 0, 30000);    //imprimo cada 30 seg

        }

    }


