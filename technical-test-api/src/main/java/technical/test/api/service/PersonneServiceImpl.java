package src.main.java.technical.test.api.service;

import src.main.java.technical.test.api.repository.PersonneRepository;

/**
 * 
 * @author amfares
 *
 */

@Service
public class PersonneServiceImpl implements PersonneService {
	
	private PersonneRepository personneRepository;
	
	public PersonneServiceImpl(PersonneRepository personneRepository) {
		this.personneRepository = personneRepository;
	}
	
	@Override
	public List<Personne> findAll(){
		return personneRepository.findAll();
	}
	
	@Override
	public Personne save(Personne personne) {
		return PersonneService.save(personne);
	}
	

	

}
