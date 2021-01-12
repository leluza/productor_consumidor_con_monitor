import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Monitor {
    private Semaphore mutex=new Semaphore(1);  //semaforo binario de exclusion mutua
    private Semaforo[] VariablesDeCondicion;  //condiciones de sincronizacion de cada transicion
    private RedDePetri RdP;     //red que controla la logica del sistema
    private Politicas politica; //politicas que resuelven los conflictos de la red


    public Monitor(RedDePetri red, Politicas politicas){
        this.RdP = red;
        this.VariablesDeCondicion = new Semaforo[RdP.getTransiciones().length];
        this.politica = politicas;
        GenerarVarCond();

    }


    public void entrar(int transicion){ //dispara una transicion sin devolver el mutex
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(!(RdP.esSensibilizado(transicion))){
            VariablesDeCondicion[transicion].Delay();       //bloqueo
        }
        this.RdP.disparar(transicion); //dispara la transicion actualizando asi el estado

    }

    public void salir(){
        desbloquearUno();          //desbloqueo otro hilo
        mutex.release();        //devuelve mutex

    }


    private void desbloquearUno(){      //desbloquea un hilo de las colas de condicion cuya transicion esté sensibilizada
        ArrayList<Integer> desbloqueables = ColasAndSensibilizadas();
        if(!desbloqueables.isEmpty()){
            VariablesDeCondicion[politica.decidir(desbloqueables)].Resume();
        }
        return;
    }

    private ArrayList<Integer> ColasAndSensibilizadas() { //devuelve una lista de las transiciones sensibilizadas
                                                        // que tienen hilos queriendo dispararlas
        ArrayList<Integer> desbloqueables = new ArrayList<>();
        for(int i = 0; i < this.VariablesDeCondicion.length; i++){
            if( !(VariablesDeCondicion[i].Empty()) && RdP.esSensibilizado(i)){
                desbloqueables.add(i);
            }
        }
        return desbloqueables;
    }

    private void GenerarVarCond(){ //crea tantas variables de condicion como cantidad de transiciones tiene la red de petri
        for(int i = 0; i < this.VariablesDeCondicion.length; i++){
            this.VariablesDeCondicion[i] = new Semaforo(this.mutex);
        }
    }

}
