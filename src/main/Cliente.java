package main;

import java.io.*;
import java.net.Socket;
import java.sql.Time;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Cliente {
    public static void main(String[] args) {
        try {

            while(true) {
                System.out.println("Escriba el comando para el servidor");
                Scanner scanner = new Scanner(System.in);
                String comando = scanner.nextLine();
                System.out.println(comando);

                //System.out.println("Enviando solicitud...");
                //Socket socket = new Socket("192.168.1.69", 5001);
                //System.out.println("Conectados");

                System.out.println("Enviando solicitud...");
                Socket socket = new Socket("4.tcp.ngrok.io", 11872);
                System.out.println("Conectados");

                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bwriter = new BufferedWriter(osw);

                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader breader = new BufferedReader(isr);

                bwriter.write(comando + "\n");
                bwriter.flush();

                if (comando.equalsIgnoreCase("RTT") || comando.equalsIgnoreCase("speed")) {
                    //Realizamos la conexión por ngrok

                    long tiempoAntes = 0;

                    if (comando.equalsIgnoreCase(("RTT"))) {
                        String[] abecedario = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

                        int cantidadBits = 0;
                        String mensaje = "";
                        while (cantidadBits < 1024) {
                            int numRandon = (int) Math.round(Math.random() * 25);
                            mensaje += abecedario[numRandon];
                            cantidadBits++;
                        }
                        tiempoAntes = System.currentTimeMillis();
                        if (mensaje.getBytes().length == 1024) {
                            bwriter.write(mensaje + "\n");
                            bwriter.flush();
                        } else {
                            System.out.println("Not working");
                        }
                    } else if (comando.equalsIgnoreCase(("speed"))) {

                        String[] abecedario = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

                        int cantidadBits = 0;
                        String mensaje = "";
                        while (cantidadBits < 8192) {

                            int numRandon = (int) Math.round(Math.random() * 25);
                            mensaje += abecedario[numRandon];
                            cantidadBits++;
                        }
                        tiempoAntes = System.currentTimeMillis();
                        if (mensaje.getBytes().length == 8192) {
                            bwriter.write(mensaje + "\n");
                            bwriter.flush();
                        } else {
                            System.out.println("Not working");
                        }


                    }
                    String textoRecibido = breader.readLine();
                        if(textoRecibido.getBytes().length == 1024){
                            long tiempoDespues = System.currentTimeMillis();
                            long tiempo = tiempoDespues - tiempoAntes;
                            long tiempoSegundos = TimeUnit.NANOSECONDS.toSeconds(tiempo);
                            System.out.println("Tiempo de ida y venida: " + tiempo + " milisegundos");
                        }else if(textoRecibido.getBytes().length == 8192){
                            long tiempoDespues = System.currentTimeMillis();
                            long tiempo = tiempoDespues - tiempoAntes;
                            long tiempoSegundos = TimeUnit.NANOSECONDS.toSeconds(tiempo);
                            double kb = (textoRecibido.getBytes().length)/1000.0;
                            double velocidadTransmision = (kb*2)/(tiempo/1000);

                            System.out.println("Tiempo de ida y venida: " + tiempo + " milisegundos");
                            System.out.println("Velocidad de transmisión: " + velocidadTransmision + "KB/s");
                        }else{
                            System.out.println("Mensaje recibido: " + textoRecibido);
                        }




                } else {
                    //Realizamos la conexión por IP

                    String recibido = breader.readLine();
                    System.out.println("Mensaje recibido: " + recibido);
                }

                int contador = 0;
                while (contador < 2000) {
                    if (contador == 1999) {
                        System.out.println("Desconectandose del servidor");
                    }
                    contador++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
