
import java.net.*;
import java.util.ArrayList;

public class ServeurSimple 
{
	public static ArrayList<GerantDeClient> lstGerantCli = new ArrayList<>();
	public static GerantEnchere gerantEnchere;

	public ServeurSimple(int port) 
	{
		ArrayList<Enchere> lstEnchere = ServeurSimple.genererEnchere();

		ServeurSimple.gerantEnchere = new GerantEnchere(lstEnchere);
		new Thread(this.gerantEnchere).start();

		try 
		{
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Serveur démarré sur le port " + port + ", en attente de clients...");

			while (true) 
			{
				Socket s = serverSocket.accept();

				GerantDeClient gdc = new GerantDeClient(s);

				Thread tgdc = new Thread(gdc);
				tgdc.start();

			}

		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public static ArrayList<Enchere> genererEnchere()
	{
        ArrayList<Enchere> liste = new ArrayList<>();

		liste.add(new Enchere("Tableau 'Nuit Étoilée'", 500));
        liste.add(new Enchere("Vase Ming ancien", 1200));
        liste.add(new Enchere("Statue en bronze du XIXe", 300));
        liste.add(new Enchere("Console PS5 Édition Collector", 450));
        liste.add(new Enchere("MacBook Pro M3", 900));
        liste.add(new Enchere("Borne d'Arcade Pac-Man", 250));
        liste.add(new Enchere("Vélo électrique", 600));
        liste.add(new Enchere("Scooter Vintage Vespa", 1500));
        liste.add(new Enchere("Dîner avec une célébrité", 100));
        liste.add(new Enchere("Voyage surprise de 3 jours", 200));

		return liste;
	}

	public static void main(String[] args) 
	{
		new ServeurSimple(6000); 
	}
}