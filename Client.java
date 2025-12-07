import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client 
{
	public static void main(String[] args) throws Exception 
	{
		Socket socket = new Socket("localhost", 6000);
		
		new Thread(() -> 
		{
			try 
			{
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String msg;
				while ((msg = in.readLine()) != null) 
					System.out.println(msg);
			} 
			catch (IOException e) {}
		}).start();

		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) 
		{
			out.println(scanner.nextLine());
		}
	}
}