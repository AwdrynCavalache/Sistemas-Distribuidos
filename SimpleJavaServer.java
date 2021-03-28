import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleJavaServer {
    public static void main(String[] args) 	{
        try {
            ServerSocket s = new ServerSocket(9999);
            int numero1, numero2, resultado;
            String operador, str;

            while (true) {
                Socket c = s.accept();
                InputStream i = c.getInputStream();
                OutputStream o = c.getOutputStream();

                do {
                    byte[] line = new byte[100];
                    i.read(line);
                    str = new String(line).trim();
                    try {
                        numero1 = Integer.parseInt(str);
                    } catch (NumberFormatException e) {
                        o.write("O numero eh invalido!".getBytes());
                    }

                    i.read(line);
                    if (!str.equals("+") && !str.equals("-") && !str.equals("/") && !str.equals("*")) {
                        o.write("O operador eh invalido!".getBytes());
                    } else {
                        o.write(str.getBytes());
                        operador = str;
                    }

                    i.read(line);
                    try {
                        numero2 = Integer.parseInt(str);
                    } catch (NumberFormatException e) {
                        o.write("O numero eh invalido!".getBytes());
                    }

                    switch (operador) {
                        case "+":
                            resultado = numero1+numero2;
                            break;
                        case "-":
                            resultado = numero1-numero2;
                            break;
                        case "/":
                            resultado = numero1/numero2;
                            break;
                        case "*":
                            resultado = numero1*numero2;
                            break;
                        default:
                            o.write("A operacao eh invalida!");
                    }

                    if (resultado != null) {
                        o.write("Resultado:"+String.valueOf(resultado).getBytes());
                    }
                } while (!str.trim().equals("bye"));

                c.close();
            }
        }
        catch (Exception err){
            System.err.println(err);
        }
    }
}