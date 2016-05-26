import javax.script.*;
import java.io.*;
import java.net.*;
/**
 * Created by 47419119l on 24/02/16.
 */
public class Servidor extends Thread
{
    /**
     * Metode que s'inicia al executar la classe.
     * @param args
     */
    public static void main(String[] args)
    {
        boolean guay = true;

        System.out.println("Creando servidor");
        try {
            ServerSocket serverSocket = new ServerSocket();

            System.out.println("Realizando el bind");
            InetSocketAddress addr = new InetSocketAddress("0.0.0.0", 5555);


            serverSocket.bind(addr);
            System.out.println("Escuchando");

            /**
             * Aquí és donde el servidor se quedará escoltat
             */

            while(guay){
                Socket socketdeescucha = serverSocket.accept();
                HiloServer th = new HiloServer(socketdeescucha);
                th.start();
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}