import java.util.ArrayList;
import java.util.LinkedList;

public class Consumidor extends Thread {
    private int cant_a_consumir;
    private Monitor monitor;
    private ArrayList<LinkedList<Guardodato>> listaDeBuffers;


    public Consumidor(Monitor monitor, int cantConsumir, ArrayList<LinkedList<Guardodato>> buffers) {
        this.monitor = monitor;
        this.cant_a_consumir = cantConsumir;
        this.listaDeBuffers = buffers;
        System.out.println("entra el consumidor ");
    }


    @Override
    public void run() {
        for (int i = 0; i < cant_a_consumir; i++) {
            if (i % 2 == 0) {
                this.consumirEnBuffer1();
            } else {
                this.consumirEnBuffer2();
            }
        }

          System.out.println("he sacado todos mis Productos ");
    }

    private void consumirEnBuffer1() {
        consumirEnBuffer( listaDeBuffers.get(0), 2, 3);
    }

    private void consumirEnBuffer2() {
        consumirEnBuffer( listaDeBuffers.get(1), 6, 7);
    }


    private void consumirEnBuffer( LinkedList<Guardodato> buffer, int tomarProducto, int dejarHueco) {
        try {
            monitor.entrar(tomarProducto);
            monitor.salir();
        } catch (Exception e) {
            System.out.println("error al disparar tomarProducto");
        }
        //si la ejecucion llego hasta aca ya tengo el producto para consumir y actualice el estado
        try {
            this.sleep(50);      //tiempo que demora en consumir
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            monitor.entrar(dejarHueco);     //dispara una transicion y me quedo con el mutex
            buffer.poll(); //agrego un hueco al buffer
            monitor.salir();        //devuelvo el mutex

        } catch (Exception e) {
            System.out.println("error al disparar dejarHueco");
        }
        //si la ejecucion llego hasta aca ya agregue un hueco y actualice el estado
        // System.out.println("he sacado el elemento ");

    }
}



