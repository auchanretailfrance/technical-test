package src.main.java.technical.test.api.model;

/**
 * 
 * @author amfares
 *
 */

@Document 
public class Personne {
	
	@Id
	private String id;
	
	private String name;
	private int yearsOld;
	
	public Personne(String id,String name,int yearsOld) {
		this.id = id;
		this.name = name;
		this.yearsOld = yearsOld;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYearsOld() {
		return yearsOld;
	}

	public void setYearsOld(int yearsOld) {
		this.yearsOld = yearsOld;
	}
	
	
	
	
}
