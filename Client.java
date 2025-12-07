import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


/**
 * Interface utilisateur console gérant l'envoi et la réception de messages.
 * Utilise deux threads pour la lecture (Socket) et l'écriture (Clavier) simultanées.
 */
public class Client 
{
	public static void main(String[] args) throws Exception 
	{
		Socket socket = new Socket("localhost", 6000);
		
		new Thread(() -> 
		{
			try 
			{
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8));
				String msg;
				while ((msg = in.readLine()) != null) 
					System.out.println(msg);
			} 
			catch (IOException e) {}
		}).start();

		PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),true);
		Scanner scanner = new Scanner(System.in,StandardCharsets.UTF_8);

		
		while (scanner.hasNextLine()) 
		{
			out.println(scanner.nextLine());
		}
	}
}