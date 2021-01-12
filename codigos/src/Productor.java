import java.util.ArrayList;
import java.util.LinkedList;

public class Productor extends Thread {
    private int cantAProducir;
    private Monitor monitor;
    private ArrayList<LinkedList<Guardodato>> listaDeBuffers;
    private static int maxDeBuffer10 = 0;
    private static int maxDeBuffer15 = 0;



    public Productor(Monitor monitor, int cantProducir, ArrayList<LinkedList<Guardodato>> buffers) {
        this.monitor = monitor;
        this.cantAProducir = cantProducir;
        this.listaDeBuffers = buffers;
        System.out.println("Productor entro a Monitor ");
    }

    @Override
    public void run() {                             //inserta un producto en buffer de 15 y en buffer de 10 en forma alternada
        for (int i = 0; i < cantAProducir; i++) {
            if(i%2 == 0){
                this.producirEnBuffer1(new Guardodato(i));
            }
            else {
                this.producirEnBuffer2(new Guardodato(i));
            }
        }
        System.out.println("he puesto todos mis Productos ");
    }

    private void producirEnBuffer1(Guardodato d){
        producirEnBuffer(d, listaDeBuffers.get(0), 0, 1);
    }

    private void producirEnBuffer2(Guardodato d){
        producirEnBuffer(d, listaDeBuffers.get(1), 4, 5);
    }



    private void producirEnBuffer(Guardodato dato, LinkedList<Guardodato> buffer, int tomarHueco, int dejarProducto){
        try {
            monitor.entrar(tomarHueco);
            monitor.salir();
        } catch (Exception e) {
            System.out.println("error al disparar tomarHueco");
        }

        //si la ejecucion llego hasta aca ya tengo el hueco para producir y actualice el estado
        try {
            this.sleep(50);      //tiempo que demora en producir
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            //buffer.add(dato); //agrego un producto al buffer
            //System.out.println("tamaño en buffer" +tomarHueco + "   " +buffer.size());
        } catch (Exception e){
            System.out.println("error al meter");
        }
        try {
            monitor.entrar(dejarProducto);
            buffer.add(dato); //agrego un producto al buffer
      
            monitor.salir();
        } catch (Exception e) {
            System.out.println("error al disparar dejarProducto");
        }
        //si la ejecucion llego hasta aca ya agregue un producto y actualice el estado

    }

    public static int getMaxDeBuffer10() {
        return maxDeBuffer10;
    }

    public static int getMaxDeBuffer15() {
        return maxDeBuffer15;
    }
}