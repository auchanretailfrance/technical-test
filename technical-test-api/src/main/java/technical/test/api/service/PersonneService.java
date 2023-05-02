package src.main.java.technical.test.api.service;

/**
 * 
 * @author amfares
 *
 */

public interface PersonneService {
	
	List<Personne> findAll();
	Personne save(Personne personne);

}
