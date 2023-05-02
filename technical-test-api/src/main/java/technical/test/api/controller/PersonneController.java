package src.main.java.technical.test.api.controller;

import src.main.java.technical.test.api.service.PersonneServiceImpl;

/**
 * 
 * @author amfares
 *
 */
@RestController
@RequestMapping("/api/personne")
public class PersonneController {
	
	private PersonneServiceImpl personneService;
	
	public PersonneController (PersonneServiceImpl personneService) {
		this.personneService = personneService;
	}
	
@GetMapping()
public responseEntity<List<Personne>> getAllPersonne(){
	return new ResponseEntity<>(personneService.findAll(),HttpStatus.OK);
}

@PostMapping()
public responseEntity<Personne> createPersonne(@RequestBody Personne addpersonne){
	Personne personne = personneService.save(addpersonne);
	return new ResponseEntity<>(personne,Httpstatus.OK);
}
	

}
