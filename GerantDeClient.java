
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class GerantDeClient implements Runnable 
{
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private String nomClient;

	public GerantDeClient(Socket socket) 
	{
		this.socket = socket;
		try 
		{
			this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream(),StandardCharsets.UTF_8));
			this.out = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream(),StandardCharsets.UTF_8) ,true);
		} catch (Exception e) 
		{
			System.out.println("Problème lors de l'initialisation du client");
		}
	}
	
	
	public void diffuser(String message) 
	{
		for (int i = 0; i < ServeurSimple.lstGerantCli.size(); i++) 
		{
			GerantDeClient gdc = ServeurSimple.lstGerantCli.get(i);

			if (gdc != this) 
				gdc.getOut().println(message);
			
		}
	}

	public String demanderNom() 
	{
		String nom = "";
		this.out.println("Entrez votre nom :"); 

		try 
		{
			do 
			{
				nom = this.in.readLine();
			} while (nom != null && nom.trim().equals(""));
		} catch (Exception e) 
		{
			System.out.println(e);
		}
		return nom;
	}

	@Override
	public void run() 
	{
		try 
		{
			this.nomClient = this.demanderNom();
			
			if(this.nomClient == null)
			{ 
				this.socket.close();
				return;
			}
			ServeurSimple.lstGerantCli.add(this);

			System.out.println("Un nouveau client est connecté : " + this.nomClient);
			this.out.println("Bienvenue " + this.nomClient);
			
			this.diffuser("SERVEUR: " + this.nomClient + " a rejoint le chat.\n" +
						  "SERVEUR:  Il y a maintenant " + ServeurSimple.lstGerantCli.size() + " clients sur le serveur.");

			String messageRecu;

			while ((messageRecu = this.in.readLine()) != null) 
			{
				
				System.out.println("[" + this.nomClient + "]: " + messageRecu);	
				try 
				{
					int prix = Integer.parseInt(messageRecu);
					boolean resultat = ServeurSimple.gerantEnchere.soumettreEnchere(this.nomClient, prix);
					
					if (!resultat) 
						this.out.println("Votre enchère est trop basse ou la vente est close !");
					
				
				} catch (NumberFormatException e) {
					this.out.println("Veuillez entrer un nombre entier pour enchérir.");
				}
				
			
			}

		} catch (IOException e) {
			System.out.println("Erreur de connexion avec " + this.nomClient);
		} finally 
		{
			fermerConnexion();
		}
	}

	private void fermerConnexion() 
	{
		try 
		{
			if (this.nomClient != null) 
			{
				ServeurSimple.lstGerantCli.remove(this);
				String messageDepart = "SERVEUR: " + this.nomClient + " a quitté le chat. Clients restants: " + ServeurSimple.lstGerantCli.size();
				System.out.println(messageDepart);
				this.diffuser(messageDepart);
			}
			if (this.socket != null && !this.socket.isClosed()) 
			{
				this.socket.close();
			}
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public PrintWriter getOut() {return this.out;}
}