import java.util.ArrayList;
import java.util.HashMap;

public class Politicas {
    private HashMap<Integer, Integer> prioridades;

    public Politicas() {
        this.prioridades = new HashMap<>();
    }

    public void addPrioridad(int transicion, int prioridad){
        this.prioridades.put(transicion, prioridad);
    }

    public int decidir(ArrayList<Integer> sensibilizadas) {//devuelve la transicion de mayor prioridad entre las sensibilizadas
        int eleccion = sensibilizadas.get(0);
        for (int transicion : sensibilizadas) {
            if (prioridades.get(transicion) > prioridades.get(eleccion)) {
                eleccion = transicion;
            }
        }
        return eleccion;
    }
}
