
import java.util.ArrayList;
import java.util.Collections;

public class GerantEnchere implements Runnable
{
	private ArrayList<Enchere> lstEnchere;
	private Enchere enchereActuelle;
	private int timer = 0; 
	private boolean objetVendu = false;

	public GerantEnchere (ArrayList<Enchere> encheres)
	{
		this.lstEnchere = encheres;
	}

	@Override
	public void run() 
	{
	
		this.genererEnchere();	
		this.timer = 0;
		this.objetVendu = false;
		
		this.diffuser("NOUVEL OBJET : " + this.enchereActuelle.getNom() + " à " + this.enchereActuelle.getMontant() + "€");

		while (!this.objetVendu) 
		{
			try 
			{
				Thread.sleep(1000); 
				this.timer++;
				
				if (this.timer == 10) 
					diffuser("Une fois... (Prix : " + enchereActuelle.getMontant() + "€)");
				else if (this.timer == 20) 
					diffuser("Deux fois... (Prix : " + enchereActuelle.getMontant() + "€)");
				else if (this.timer == 30) 
				{
					diffuser("ADJUGÉ vendu à " + enchereActuelle.getNomClient() + " pour " + enchereActuelle.getMontant() + "€ !!");
					this.objetVendu = true;
				}

			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	
		diffuser("La vente est terminée.");
	}

	public void genererEnchere()
	{
		Collections.shuffle(this.lstEnchere);
		this.enchereActuelle = this.lstEnchere.getFirst();
		this.lstEnchere.remove(this.enchereActuelle);
	} 


	public void diffuser(String message) 
	{
		System.out.println(message);
		for (int i = 0; i < ServeurSimple.lstGerantCli.size(); i++) 
		{
			GerantDeClient gdc = ServeurSimple.lstGerantCli.get(i);
				gdc.getOut().println(message);
			
		}
	}


	public boolean soumettreEnchere(String nomClient, int montant) 
	{
        if (enchereActuelle == null || objetVendu) 
			return false;

        if (montant > enchereActuelle.getMontant()) 
		{
            enchereActuelle.setMontant(montant);
            enchereActuelle.setNomClient(nomClient);
            
            this.timer = 0;
            
            diffuser("Nouvelle enchère de " + nomClient + " : " + montant + "€");
            return true;
        }
        return false;
    }
	


}



