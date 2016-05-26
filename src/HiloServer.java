import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by 47419119l on 24/02/16.
 */
public class HiloServer extends Thread {

    Socket socketescuchado;

    public HiloServer(Socket s){

        socketescuchado = s;

    }
    @Override
    public void run() {
        try {


            InputStream input = socketescuchado.getInputStream();
            OutputStream output = socketescuchado.getOutputStream();

            byte[] mensaje = new byte[25];
            input.read(mensaje);

            System.out.println(socketescuchado.getInetAddress().toString());
            System.out.println("Operació rebuda :"+new String(mensaje));
            String operacioAcalcular = new String(mensaje);

            output.write(calcular(operacioAcalcular).getBytes());
            String result = calcular(operacioAcalcular);
            System.out.println("Resultat : "+result);
            guardar("\n"+socketescuchado.getInetAddress().toString()+"\n "+"Resultat : "+result);

            /**
             * Tanquem els serveis.
             **/

            input.close();
            output.close();


        }catch (Exception e){

        }
    }
    /**
     * Metode per calcular.
     * @param operacio
     * @return
     */
    public static String calcular(String operacio)
    {
        String [] ok =operacio.split(",");
        double  num1 = Double.parseDouble(ok[0]);
        String operador = String.valueOf(ok[1].charAt(0));
        double num2 = Double.parseDouble(ok[1].replace(operador,""));

        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String result = "No es pot fer aquest càlcul";
        try {
            result = engine.eval(num1+operador+num2).toString();
        }
        catch (ScriptException e) {

        }
        return result;
    }

    /**
     * Metode per guardar en un log
     * @param log
     * @throws IOException
     */
    public static void guardar (String log) throws IOException {
        File archivo = new File("Calculadora.log");
        FileWriter escribir = new FileWriter(archivo,true);
        Calendar calendario = new GregorianCalendar();

        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minutos = calendario.get(Calendar.MINUTE);
        int segundos = calendario.get(Calendar.SECOND);

        escribir.write("\n Hora : "+hora+":"+minutos+":"+segundos+"\n "+log);

        escribir.close();
    }


}
