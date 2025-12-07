
import java.util.ArrayList;
import java.util.Collections;

public class GerantEnchere implements Runnable
{
	private ArrayList<Enchere> lstEnchere;
	private Enchere enchereActuelle;
	private int timer ; 
	private boolean objetVendu = false;

	public GerantEnchere (ArrayList<Enchere> encheres)
	{
		this.lstEnchere = encheres;
	}

	@Override
	public void run() 
	{
		System.out.println("Les enchères sont prêtes a commencer , en attente de clients.");

		while (ServeurSimple.lstGerantCli.isEmpty()) 
		{
            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) 
			{ e.printStackTrace(); }
        }

		System.out.println("Un client est arrivé, les enchères vont commencer dans 4 secondes.");
		try {
			Thread.sleep(4000);
		} catch (Exception e) {
			e.printStackTrace();
		}

			
	

		while (!this.lstEnchere.isEmpty())
		{
			this.genererEnchere();
			this.diffuser("Nouvel enchère : " + this.enchereActuelle.getNom() + " à " + this.enchereActuelle.getMontant() + " euros");

			while (!this.objetVendu) 
			{
				try 
				{
					Thread.sleep(1000); 
					this.timer++;
					
					if (this.timer == 10) 
						diffuser("Une fois... (Prix : " + enchereActuelle.getMontant() + " euros pour " + this.enchereActuelle.getNom() +")");
					else if (this.timer == 20) 
						diffuser("Deux fois... (Prix : " + enchereActuelle.getMontant() +  " euros pour " + this.enchereActuelle.getNom() +")");
					else if (this.timer == 30) 
					{
						diffuser("ADJUGÉ vendu à " + enchereActuelle.getNomClient() + " pour " + enchereActuelle.getMontant() + " euros !!");
						this.objetVendu = true;
					}

				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		
			diffuser("La vente est terminée.");
			this.timer = 0;
			this.objetVendu = false;
		}
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
            
            diffuser("Nouvelle enchère de " + nomClient + " : " + montant + "euros");
            return true;
        }
        return false;
    }
	


}



