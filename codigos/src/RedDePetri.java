public class RedDePetri {
    private int[] marcaActual;
    private int[][] matrizDeIncidencia;
    private boolean[] transiciones;

    public RedDePetri(int[] marcaInicial, int[][] matrizDeIncidencia) {

        this.marcaActual = marcaInicial;
        this.matrizDeIncidencia = matrizDeIncidencia;
        this.transiciones = new boolean[matrizDeIncidencia[0].length]; //TRUE = SENSIBILIZADA, FALSE = NO SENSIBILIZDA
        actualizarSensibilizadas();
    }

    public boolean[] getTransiciones() {
        return transiciones;
    }

    private void actualizarSensibilizadas() {       //actualiza el vector de las transiciones que estan sensibilizadas
        for (int i = 0; i < transiciones.length ; i++) {
            this.transiciones[i] = esSensibilizadoInterno(i) ? true : false;
        }
    }

    private boolean esSensibilizadoInterno(int transicion){ //devuelve true si la transicion esta sensibilizada
        for(int i = 0; i < marcaActual.length; i++){
            if((marcaActual[i] + matrizDeIncidencia[i][transicion])  < 0){
                return false;
            }
        }
        return true;
    }

    public boolean esSensibilizado(int transicion) {//devuelve true si la transicion esta sensibilizada
        return (this.transiciones[transicion]);

    }



    public void disparar(int transicion) {  //dispara una transicion modificando la marcaActual
        //System.out.println(this.esSensibilizado(transicion));
        if (this.esSensibilizado(transicion)) {   //la dispara solo si esta sensibilizada
            for (int i = 0; i < marcaActual.length; i++) {
                marcaActual[i] = marcaActual[i] + matrizDeIncidencia[i][transicion];
            }
            actualizarSensibilizadas();
        }
    }
}