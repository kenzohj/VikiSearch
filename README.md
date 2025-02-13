# 🔍 VikiSearch (2023)

<p align="center">
  <img src="https://github.com/kenzohj/VikiSearch/blob/main/home_page.png" alt="Home page" width="500" />
  <img src="https://github.com/kenzohj/VikiSearch/blob/main/result_page.png" alt="Result page" width="500" />
</p>

**🇫🇷 Français | [🇬🇧 English below](#-english-version-)**

---

## 📌 À propos du projet

**VikiSearch** est un moteur de recherche permettant d'effectuer des recherches vectorielles sur les pages de l'encyclopédie [**Vikidia**](https://fr.vikidia.org/).

Les pages de Vikidia nous ont été fournies sous la forme de **fichiers indexés** :
- **Ligne 1** : URL de la page
- **Lignes suivantes** : Nombre d'occurrences de chaque mot présent sur la page

Grâce à ces données, **VikiSearch** permet d'effectuer des recherches basées sur le contenu des pages.

> **💡 Note :** L'archive des fichiers indexés est disponible [**ici**](#) et est à placer à la racine du projet ou bien dans le même dossier que le fichier jar.

📜 **Fonctionnalités clés** :  
✔️ **Recherche vectorielle** sur toutes les pages Vikidia  
✔️ **Correction automatique** des requêtes (distance de Jaccard & Levenshtein)  
✔️ **Filtrage intelligent** avec :
- Un fichier de **blacklist** pour les mots inutiles (ex. déterminants)
- Un fichier de **lemmes** pour normaliser les mots recherchés

---

## 🚀 Modes d'utilisation

VikiSearch propose **3 façons** d'effectuer une recherche :

### 🔹 Mode 1 : Serveur Web

Démarre un serveur local sur le port `8080` et ouvre une **interface web** permettant d'effectuer des recherches.

```bash
java -jar VikiSearch.jar -server
```

Si configuré, la page web peut ne pas s'ouvrir automatiquement, auquel cas il faudra saisir `http://localhost:8080` manuellement.

```bash
java -jar VikiSearch.jar -server nogui
```

Chaque recherche est sauvegardée dans l'historique affiché à droite de l'interface.

🌐 **Interface web réalisée par** [Ye4hL0w](https://github.com/Ye4hL0w)

📸 **Aperçu** :  
![Mode 1](https://github.com/kenzohj/VikiSearch/blob/main/mode_1.png)

---

### 🔹 Mode 2 : Interface en ligne de commande

Affiche un **menu interactif** demandant à l'utilisateur d'entrer un ou plusieurs mots (séparés par des espaces).

```bash
java -jar VikiSearch.jar
```

L'utilisateur peut taper `exit` pour quitter le programme.

📸 **Aperçu** :  
![Mode 2](https://github.com/kenzohj/VikiSearch/blob/main/mode_2.png)

---

### 🔹 Mode 3 : Recherche rapide en ligne de commande

Effectue une recherche directe avec les mots fournis en arguments.

```bash
java -jar VikiSearch.jar mot1 mot2 mot3
```

Une fois le programme terminé, un fichier **log.html** est généré contenant l'historique des requêtes.

📸 **Aperçu** :  
![Mode 3](https://github.com/kenzohj/VikiSearch/blob/main/mode_3.png)

---

## 📝 Licence

Ce projet a été développé dans un cadre scolaire et n'est pas destiné à un usage commercial.

---

## 🇬🇧 English Version

## 📌 About the project

**VikiSearch** is a search engine that performs **vector searches** on indexed pages from the [**Vikidia**](https://fr.vikidia.org/) encyclopedia.

Vikidia pages were provided as **indexed files**:
- **Line 1**: Page URL
- **Next lines**: Word occurrences on the page

With this data, **VikiSearch** enables content-based searches on all Vikidia pages.

> **💡 Note :** The archive of indexed files is available [**here**](#) and should be placed in the project root or in the same folder as the jar file.

📜 **Key Features**:  
✔️ **Vector-based search** across all Vikidia pages  
✔️ **Automatic query correction** (Jaccard & Levenshtein distances)  
✔️ **Intelligent filtering** using:
- A **blacklist file** for unnecessary words (e.g., determiners)
- A **lemma file** to normalize user-input words

---

## 🚀 Usage Modes

VikiSearch provides **3 ways** to perform a search:

### 🔹 Mode 1: Web Server

Starts a local server on port `8080` and opens a **web interface** to perform searches.

```bash
java -jar VikiSearch.jar -server
```

If configured, the web page might not open automatically, requiring manual navigation to `http://localhost:8080`.

```bash
java -jar VikiSearch.jar -server nogui
```

Each search is saved in the search history on the right panel.

🌐 **Web interface developed by** [Ye4hL0w](https://github.com/Ye4hL0w)

📸 **Preview** :  
![Mode 1](https://github.com/kenzohj/VikiSearch/blob/main/mode_1.png)

---

### 🔹 Mode 2: Interactive Command Line

Displays an **interactive menu** asking the user to enter one or more search terms (separated by spaces).

```bash
java -jar VikiSearch.jar
```

The user can type `exit` to stop the program.

📸 **Preview**:  
![Mode 2](https://github.com/kenzohj/VikiSearch/blob/main/mode_2.png)

---

### 🔹 Mode 3: Quick CLI Search

Performs a **direct search** with the given words.

```bash
java -jar VikiSearch.jar word1 word2 word3
```

Once the program exits, a **log.html** file is generated containing the search history.

📸 **Preview**:  
![Mode 3](https://github.com/kenzohj/VikiSearch/blob/main/mode_3.png)

---

## 📝 License

This project was developed for educational purposes and is not intended for commercial use.  
