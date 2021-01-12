import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Main {
	private static  int PRODUCTORES = 5; //5
    private static  int CONSUMIDORES = 8;  //8
    private static  int DisparoCONSUMIDOR = 6250; //6250  
    private static  int DisparoPRODUCTOR = 10000; //10000
    
    public static void main(String[] args) throws InterruptedException {
        int cantidad=0;
        long tiempoInicio = System.currentTimeMillis(); 
        Log archivo = new Log();

        int[] marcadoInicial = {0,10,0,5,8,0,0,15,0,0};
        int[][] Incidencia = {                  
                                                        { 1,-1, 0, 0, 0, 0, 0, 0},  
                                                        { 0, 0, 0, 0,-1, 0, 0, 1}, 
                                                        { 0, 0, 0, 0, 0, 1,-1, 0},  
                                                        {-1, 1, 0, 0,-1, 1, 0, 0},  
                                                        { 0, 0,-1, 1, 0, 0,-1, 1},  
                                                        { 0, 0, 1,-1, 0, 0, 0, 0},  
                                                        { 0, 0, 0, 0, 1,-1, 0, 0},  
                                                        {-1, 0, 0, 1, 0, 0, 0, 0},  
                                                        { 0, 1,-1, 0, 0, 0, 0, 0},  
                                                        { 0, 0, 0, 0, 0, 0, 1,-1}   
                                                                                    };

        Politicas politica = new Politicas();
       
        for(int i = 0; i < Incidencia[0].length; i++){
            politica.addPrioridad(i , 0);  //PRIORIDADES A CADA TRANSICION
        }

        Productor productores[]=new Productor[PRODUCTORES];
        Consumidor consumidores[]=new Consumidor[CONSUMIDORES];
        Monitor monitor = new Monitor(new RedDePetri(marcadoInicial, Incidencia), politica);
        ArrayList<LinkedList<Guardodato>> buffers = new ArrayList<>();
        buffers.add(new LinkedList<Guardodato>());
        buffers.add(new LinkedList<Guardodato>()); 
        
        while (cantidad<=8) {    	
        	if (cantidad <PRODUCTORES){
        		productores[cantidad] = new Productor(monitor,DisparoPRODUCTOR, buffers);      // productores
                productores[cantidad].start();
        	    System.out.println("Hilo Productor "+cantidad);
        	}
        	if(cantidad<CONSUMIDORES) {
        		consumidores[cantidad] = new Consumidor(monitor,DisparoCONSUMIDOR, buffers);      //consumidores
                consumidores[cantidad].start();
        		System.out.println("Hilo Consumidor "+cantidad);
        	}
        	cantidad++;
        }

        Temporizador temp = new Temporizador(buffers, consumidores, archivo); //empieza el temporizador(cada 30 seg) a escribir el Archivo
        cantidad=0;
		while (cantidad<=8) {    	
        	if (cantidad <PRODUCTORES){
        		productores[cantidad].join();
        	    System.out.println("Espera el Productor "+cantidad);
        	}
        	if(cantidad<CONSUMIDORES) {
        		consumidores[cantidad].join();	
        		System.out.println("Espera el Consumidor "+cantidad);
        	}
        	cantidad++;
        }
		
		long tiempoFin = System.currentTimeMillis();
		long duracion = (tiempoFin - tiempoInicio);  
		System.out.format("Tiempos = %s, ( Empezo : %s, Termino : %s ) \n", duracion, formatoFecha(tiempoInicio), formatoFecha(tiempoFin) );
		System.out.println("Duracion total del programa: "+formatoFecha( duracion ) );
        try {
            archivo.escribirArchivo("Duracion total del programa: "+ formatoFecha(duracion));
     
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

	private static String formatoFecha(long duracion) {
		 String res = "";    // java.util.concurrent.TimeUnit;
		    long days       = TimeUnit.MILLISECONDS.toDays(duracion);
		    long hours      = TimeUnit.MILLISECONDS.toHours(duracion) -
		                      TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duracion));
		    long minutes    = TimeUnit.MILLISECONDS.toMinutes(duracion) -
		                      TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duracion));
		    long seconds    = TimeUnit.MILLISECONDS.toSeconds(duracion) -
		                      TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duracion));
		    long millis     = TimeUnit.MILLISECONDS.toMillis(duracion) - 
		                      TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(duracion));

		    if (days == 0)      res = String.format("%02d:%02d:%02d.%04d", hours, minutes, seconds, millis);
		    else                res = String.format("%dd %02d:%02d:%02d.%04d", days, hours, minutes, seconds, millis);
		    return res;
	}


}


