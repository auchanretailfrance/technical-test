# **`Technical test Checklist`**

Cette page contient quelques points de contrôles à vérifier à l'issue du test par le candidat afin de valider ou non les développements ayant été proposé.

## Ce qui est attendu :


### Coté back:
- [ ] Un découpage clair et respectant les couches habituelles
- [ ] Du code **reactif** (reactor Mono/Flux)
- [ ] Des data objects pour la couche de persistence servant la représentation du modèle mongo
- [ ] Des repositories permettant de récupérer les modèles 
- [ ] Une couche de service pour implémenter le business code et utilisant les repositories 
- [ ] Une couche de facade pour transformer storage models en DTO representation 
- [ ] Des conversions propres des storage models via mapstruct 
- [ ] Un endpoint REST exposant les opérations CRUD en faisant appel à la couche Facade
- [ ] Faire que les tests d'intégration déjà proposés passent sans erreur

### Coté front:
