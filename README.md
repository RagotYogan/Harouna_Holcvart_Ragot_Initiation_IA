# Projet : Analyse des Algorithmes et Intelligence Artificielle

## Description

Ce projet est une exploration des algorithmes et des techniques d'intelligence artificielle (IA), avec un accent particulier sur la recherche dans les arbres et les réseaux de neurones. Il se compose de plusieurs sous-projets et fichiers pour tester et comparer des approches comme le Perceptron multicouche (MLP), KNN, ainsi que les algorithmes de recherche dans les arbres tels que BFS, DFS, GFS, UCS et A*.



### Détails des dossiers et fichiers

#### **DocumentMLP**

Ce dossier contient des documents et des graphiques liés au projet MLP :

- **Initiation IA.pdf** : Document d'introduction aux concepts d'intelligence artificielle.
- **MLP vs KNN.pdf** : Analyse comparative entre les algorithmes MLP et KNN.
- **TA_0.01.png, TA_0.1.png, TA_0.4.png, TA_0.9.png** : Graphiques 

#### **MLPvsKNN**

Sous-projet comparant les performances des algorithmes MLP et KNN. Il contient les éléments suivants :

- **Images/** : Contient les fichiers de données pour tester les algorithmes. Cela inclut les images et les labels.
- **KNN/** : Code source Java pour l'algorithme KNN et le perceptron multicouche (MLP). Les fichiers principaux incluent :
  - `AlgoClassification.java` : Implémentation de l'algorithme KNN.
  - `MLP.java` : Implémentation du perceptron multicouche (MLP).
  - `Sigmoid.java`, `Tangente.java` : Fonctions d'activation utilisées dans MLP.

#### **TreeSearchAndGames**

Sous-projet qui explore les algorithmes de recherche dans les arbres et leur application à des jeux comme Tic-Tac-Toe et Connect Four. Ce sous-projet est organisé de la manière suivante :

- **scripts/** : Scripts pour exécuter des jeux ou des algorithmes.
- **src/** : Code source Java pour les algorithmes de recherche dans les arbres et les jeux.
  - **ia/algo/recherche/** : Implémentations des algorithmes de recherche comme BFS, DFS, A*, UCS, etc.
  - **ia/framework/** : Classes communes pour définir les problèmes, les états de jeu, et les transitions.
  - **ia/problemes/** : Problèmes spécifiques et jeux comme Tic-Tac-Toe et Connect Four.

#### **src**

Le dossier `src` contient les fichiers source pour l'implémentation des réseaux de neurones et des algorithmes de recherche :

- **MLP.java** : Implémentation du perceptron multicouche (MLP).
- **PerceptronTester.java** : Classe de test pour l'entraînement et la validation du modèle MLP.
- **Sigmoid.java**, **Tangente.java** : Fonctions d'activation utilisées dans les réseaux de neurones.




