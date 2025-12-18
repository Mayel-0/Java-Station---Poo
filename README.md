# 🎮 Java Station

Java Station est une application développée en Java avec une interface graphique en JavaFX.
Le projet simule une boutique de jeux vidéo, permettant à des clients d’acheter différents types de jeux, tandis que des employés (vendeurs et managers) gèrent le magasin.

Ce projet met en pratique la programmation orientée objet (POO), l’héritage, les interfaces, ainsi que la gestion d’un stock et d’un panier.

## voici le diagramme d'uml utiliser pour la logique de notre code. 

<img src="/UML.jpg" height="100%" width="100%">

## 📌 Objectifs du projet

- Appliquer les concepts fondamentaux de la POO en Java

- Utiliser un diagramme UML pour structurer l’application

- Créer une logique métier claire (client, magasin, panier, jeux, employés)

- Implémenter une interface graphique en JavaFX

- Gérer des interactions réalistes (achat, promotion, stock)


# 🏗️ Architecture générale

L’application repose sur plusieurs entités principales :

- Client : gère un panier et un solde

- Panier : contient les jeux sélectionnés

- Magasin : stocke les jeux et les employés

- Jeu : classe mère représentant un jeu vidéo

- Employé : classe mère pour les employés

- Vendeur et Manager : rôles spécifiques

- Interface Decrire : permet d’afficher les détails d’un jeu


# 🎮 Gestion des jeux

La classe Jeu représente un jeu vidéo générique avec :

- un titre

- un prix

- un type

- un état de promotion

**Types de jeux disponibles**

- JeuRetro

- Année de sortie

- JeuConsole

- Console associée

- JeuPC

*Configuration minimale requise*

Chaque type de jeu redéfinit la méthode getDetails() grâce à l’interface Decrire.

# 🛒 Panier et Client

## Panier

Le Panier permet de :

- ajouter un jeu

- retirer un jeu

- vider le panier

- calculer le total

- afficher le contenu

## Client

Le Client peut :

- consulter son panier

- ajouter ou retirer des jeux

- payer un montant

- consulter son solde


# 🏬 Magasin

Le Magasin gère :

- le stock de jeux

- la liste des employés

- l’ajout et la suppression de jeux

- l’embauche et le licenciement d’employés

- l’affichage du stock et du personnel

## 👨‍💼 Gestion des employés
### Employé (classe mère)

Chaque employé possède :

- un nom

- un identifiant

- Méthode commune :

- sePresenter()

**Vendeur**

Le Vendeur peut :

- encaisser un client

- appliquer une promotion sur un jeu

- se présenter

**Manager**

Le Manager peut :

- ajouter un nouveau jeu au magasin

- retirer un jeu du rayon

- se présenter

## 🖥️ Interface graphique (JavaFX)

L’application utilise JavaFX pour :

- afficher le stock de jeux

- gérer le panier du client

- effectuer les paiements

- interagir avec les employés

L’interface est pensée pour être :

*simple*

*intuitive*

*claire pour l’utilisateur*

## 🧪 Concepts Java utilisés

Programmation Orientée Objet

- Héritage

- Interfaces

- Encapsulation

- Polymorphisme

- UML

- JavaFX

***pour le lancer il suffit d'aller dans le App et de run le code / projet pour pourvoire y jouer.***

