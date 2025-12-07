public class Enchere 
{

	private String nom;
	private int montant;
	private String nomClient;
	private Boolean vendue;
	
	public Enchere (String nom, int montant)
	{
		this.nom = nom;
		this.montant = montant;
	}

// --- Getters ---
	public String getNom() {return nom;}
	public int getMontant() {return montant;}
	public String getNomClient() {return nomClient;}
	public boolean getVendue(){return this.vendue;}

// --- Setters ---
	public void setNomClient(String nom)
	{
		this.nomClient = nom;
	}

	public void setMontant (int montant)
	{
		this.montant =montant;
	}

	public void setVendue(boolean vendue)
	{
		this.vendue = vendue;
	}
	
}
