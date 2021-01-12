import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Log {

    private FileWriter archivo = null;
    private PrintWriter escritor = null;

    public Log() {

        try
        {
            archivo=new FileWriter("C:\\Users\\feex3\\Desktop\\log2.txt");

            escritor=new PrintWriter(archivo);

        } catch(Exception e) {     //Si existe un problema cae aqui

            System.out.println("Error al escribir: "+ e.getMessage());
        }
    }

    public void escribirArchivo(String datos) throws IOException {
        try {
            escritor.println(datos);
            escritor.flush();



        }catch(Exception e){
            throw new IOException("Error al escribir: "+ e.getMessage());
        }
    }

}