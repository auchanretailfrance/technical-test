# **`Documentation`**

## **`API`**
### Swagger
The swagger is available at this address after launching the application : http://localhost:18080/swagger

## **`Front`**
### Install
```npm install --legacy-peer-deps```
### Launch
```npm run serve```

Available at : http://localhost:8080/

# **`Technical test`**

Salut et bienvenue ici. Auchan te propose de consacrer un peu de temps pour nous exposer tes talents.

Tu trouveras ici le squelette d'un projet utilisant:
- **Sprint Boot 3.1 (Java 17)**
- **MongoDB** 
- **Vue.js 3 (TypeScript)**.

Passe ici 2-3 heures avec pour objectif de **créer un endpoint permettant de créer un objet qui va s'inscrire dans une collection MongoDB** de ton choix. 

Un autre endpoint permettra d'**enrichir cette collection avec de nouveaux objets** (maximum 2-3 attributs, on ne demande pas la lune !) et enfin un front avec une **single page qui expose la liste des objets** contenus dans ta collection.

Pas de fioritures, pas de piège, pas de bonne ou mauvaise façon de faire, ce sera juste l'occasion pour toi de créer un support sur lequel baser notre prochaine rencontre.
Have fun !

## **`Prérequis`**
### Java 17
Installation via sdkman qui va gérer tout seul la bonne version depuis le fichier .sdkmanrc
  - https://sdkman.io/

## **`User Stories`**
### _En tant que collectionneur, je souhaite référencer un livre dans ma collection_
J'ai besoin de référencer un livre par son **isbn**, **le titre**, l'**année de parution** et l'**auteur**
### _En tant que collectionneur, je souhaite référencer un auteur_
J'ai besoin de référencer un auteur par son **nom**
### _En tant que collectionneur, je souhaite consulter la liste de tous les livres._
Je souhaite visualiser le titre, l'année de parution et le nom de l'auteur
### _En tant que collectionneur, je souhaite consulter la liste des livres correspondant à plusieurs critères (auteur, année)._
Je souhaite visualiser le titre, l'année de parution et le nom de l'auteur et pouvoir filtrer par auteur et année

##  **`Help`**
**Si tu rencontres un problème n'hésite pas à créer une issue sur le repo que tu as fork et on répondra aussi vite que possible !**
