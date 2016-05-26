import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
/**
 * Created by 47419119l on 24/02/16.
 */
public class ClientCalculadora
{


    /**
     * Metode que s'inicia al executar la classe.
     * @param args
     */

    public static void main(String[] args)
    {
        Scanner teclat = new Scanner(System.in);

        System.out.println("Creant el socket client");
        Socket client = new Socket();
        System.out.println("Establint connexió");

        InetSocketAddress address = new InetSocketAddress("localhost", 5555);

        System.out.println("Introdueix la operació ");
        System.out.println("   (Format 2,+2 | 2,*2 | 2,-2 | 2,/2 )");
        String operacio = teclat.nextLine();
        try
        {
            client.connect(address);

            InputStream inSt = client.getInputStream();
            OutputStream outSt = client.getOutputStream();
            /**
             * Afeguim la operació al canal té que estar en bits.
             */
            outSt.write(operacio.getBytes());


            byte[] mensaje = new byte[25];
            inSt.read(mensaje);

            String resultat = new String(mensaje);
            System.out.println("Resposta : "+resultat);

            client.close();
            inSt.close();
            outSt.close();
        }
        catch (IOException e) {}
    }
}


