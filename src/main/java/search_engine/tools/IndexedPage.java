package search_engine.tools;

import search_engine.utils.ResourcesFolderUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class IndexedPage {

    private final String url; // Url de la page.
    private final HashMap<String, Integer> words; // Dictionnaire contenant la liste des mots associés à leur occurrence.

    private final HashSet<String> blacklistedWords; // Liste contenant les mots blacklistés.
    private final HashMap<String, String> lemmes; // Dictionnaire contenant les mots associés à leurs lemmes.

    /**
     * Constructeur de la classe objet.
     * @param lines String[] : contient l'URL au premier indice et les mots ainsi que leur occurrence aux indices suivants.
     */
    public IndexedPage(String[] lines) {
        this.blacklistedWords = ResourcesFolderUtils.BLACKLISTED_WORDS; // Initialisation de la liste des mots blacklists à partir de la classe BinFolderUtils.
        this.lemmes = ResourcesFolderUtils.LEMMES; // Initialisation du dictionnaire des lemmes à partir de la classe BinFolderUtils.

        words = new HashMap<>(); // Initialisation du dictionnaire qui contiendra la liste des mots.
        if(lines.length == 0)  // Si aucune ligne n'est présente dans le tableau "lines"...
            url = ""; // Initialisation de l'URL à une chaîne de caractères vide.
        else // Sinon...
            url = lines[0]; // Initialisation de l'URL à partir de l'URL fournie au premier élément du tableau "lines".

        String[] data; // Déclaration d'une variable de type tableau de chaînes de caractères nommée data, qui contiendra le mot actuel associé à son occurrence.
        for(int i = 1; i < lines.length; i++) { // Pour chaque indice de 1 à la taille du tableau "lines" ...
            /* On sépare le mot à l'indice i à partir des ":" afin de stocker dans un nouveau tableau : le mot ainsi que son occurrence.
               Le contenu de data est donc : data[0] contient le mot et data[1] contient son occurrence. */
            data = lines[i].split(":");

            String currentWord = data[0].toLowerCase(); // Initialisation d'une variable de type chaîne de caractères contenant le mot courant en lettres minuscules.

            if(blacklistedWords.contains(currentWord)) // Si le mot courant fait partie des mots blacklistés...
                continue; // passage au mot suivant.

            currentWord = lemmes.getOrDefault(currentWord, currentWord); // Assignation du lemme du mot s'il existe à la variable contenant le mot courant.

            /* Sinon, stockage du lemme du mot courant (s'il existe) ainsi que de son occurrence. */
            words.put(currentWord, Integer.parseInt(data[1]));
        }
    }

    /**
     * Second constructeur de la classe objet.
     * @param text String : contient le text à traiter.
     */
    public IndexedPage(String text) {
        this.blacklistedWords = ResourcesFolderUtils.BLACKLISTED_WORDS; // Initialisation de la liste des mots blacklists à partir de la classe BinFolderUtils.
        this.lemmes = ResourcesFolderUtils.LEMMES; // Initialisation du dictionnaire des lemmes à partir de la classe BinFolderUtils.

        url = ""; // Le texte fourni ne contient pas l'URL, on l'initialise donc à un String vide.
        words = new HashMap<>(); // Initialisation du dictionnaire qui contiendra la liste des mots.

        /* L'expression regex [^a-zA-Zà-üÀ-Ü\\s] ne garde que les caractères compris entre a et z (majuscules et minuscules), les lettres accentuées
           (majuscules et minuscules) et conserve les espaces.
           \\s+ remplace tous les espaces et enchaînements d'espaces par un espace simple afin de séparer facilement les mots par la suite. */
        text = text.replaceAll("[^\\p{L}\\s]", "").replaceAll("\\s+", " ");
        String[] w = text.split(" "); // Séparation du texte dans un tableau à partir des espaces.
        for (int i = 0; i < w.length; i++) w[i] = w[i].toLowerCase(); // Conversion de chaque mot du tableau en lettres minuscules.
        Arrays.sort(w); // Tri du tableau.

        for(int i = 0; i < w.length; i++){ // Pour chaque indice de 0 à la taille du tableau "w"...
            if(blacklistedWords.contains(w[i])) // Si le mot courant fait partie des mots blacklistés...
                continue; // passage au mot suivant.

            // Sinon...
            w[i] = lemmes.getOrDefault(w[i], w[i]); // Replacement du mot actuel par son lemme (s'il existe).

            /* Puis, stockage du lemme du mot courant (s'il existe) ainsi que de son occurrence auquel on ajoute 1
               si le mot existe déjà dans le dictionnaire. */
            int occurrence = 1;
            if(words.containsKey(w[i]))
                occurrence += words.get(w[i]);
            words.put(w[i], occurrence);
        }

    }

    /**
     * Dernier constructeur de la classe objet.
     * @param path Path : chemin vers le fichier contenant les lignes à traiter.
     */
    public IndexedPage(Path path) throws IOException {
        this.blacklistedWords = ResourcesFolderUtils.BLACKLISTED_WORDS; // Initialisation de la liste des mots blacklists à partir de la classe BinFolderUtils.
        this.lemmes = ResourcesFolderUtils.LEMMES; // Initialisation du dictionnaire des lemmes à partir de la classe BinFolderUtils.

        List<String> lines = Files.readAllLines(path); // Initialisation d'une liste de lignes à partir des lignes lues dans le fichier traiter.

        words = new HashMap<>(); // Initialisation du dictionnaire qui contiendra la liste des mots.
        url = lines.get(0); // Initialisation de l'URL à partir de l'URL fournie à la première ligne du fichier.

        for(int i = 1; i < lines.size(); i++) { // Pour chaque indice de 1 à la taille de la liste "lines" ...
            /* On sépare le mot à l'indice i à partir des ":" afin de stocker dans un nouveau tableau : le mot ainsi que son occurrence.
               Le contenu de data est donc : data[0] contient le mot et data[1] contient son occurrence. */
            String[] data = lines.get(i).split(":");

            String currentWord = data[0].toLowerCase(); // Initialisation d'une variable de type chaîne de caractères contenant le mot courant en lettres minuscules.

            if(blacklistedWords.contains(currentWord)) // Si le mot courant fait partie des mots blacklistés...
                continue; // passage au mot suivant.

            currentWord = lemmes.getOrDefault(currentWord, currentWord); // Assignation du lemme du mot s'il existe à la variable contenant le mot courant.

            /* Sinon, stockage du lemme du mot courant (s'il existe) ainsi que de son occurrence auquel on ajoute 1
               si le mot existe déjà dans le dictionnaire. */
            words.put(currentWord, Integer.parseInt(data[1]));
        }
    }

    /**
     * Fonction qui renvoie l'URL de la page indexée.
     * @return String : url.
     */
    public String getUrl() {
        return url; // Renvoie de l'URL.
    }

    /**
     * Fonction qui calcule et renvoie la norme du vecteur à partir du nombre d'occurrences de chaque mot.
     * @return double : norme.
     */
    public double getNorm() {
        int norme = 0; // Initialisation d'une variable de type entière nommée norme et initialisée à 0 qui contiendra la somme des normes de l'occurrence de chaque mot de la page indexée.
        for (int count : words.values()) // Pour chaque occurrence dans le dictionnaire "words"...
            norme += count * count; // Ajout du carré de l'entier actuel à la somme des normes.
        return Math.sqrt(norme); // Renvoie de la racine de la somme des normes.
    }

    /**
     * Fonction qui renvoie le nombre d'occurrences du mot "word" dans le document.
     * @param word String : mot à traiter.
     * @return int : occurrence de "word".
     */
    private int getCount(String word) {
        /* Renvoie de l'occurrence du mot dans le dictionnaire et de 0 si le mot est null ou s'il n'y est pas contenu. */
        return words.getOrDefault(word.toLowerCase(), 0);
    }

    /**
     * Fonction qui renvoie la valeur de la coordonnée correspondant au mot word dans le vecteur normalisé correspondant au document indexé.
     * @param word String : mot à traiter.
     * @return double : valeur de la coordonnée correspondant au mot word dans le vecteur normalisé.
     */
    public double getPonderation(String word) {
        /* Si le mot existe, renvoie de l'occurrence du mot divisé par la norme de la page indexée, sinon, renvoie de 0. */
        return words.containsKey(word) ? words.get(word) / getNorm() : 0.0;
    }

    /**
     * Fonction qui renvoie le degré de similitude entre 2 pages.
     * @param page IndexedPage : page à comparer.
     * @return double : degré de similitude.
     */
    public double proximity(IndexedPage page) {
        return words.keySet() // Récupération de chaque mot contenu dans la page sous forme de Set de chaînes de caractères.
                .stream().filter(word -> page.getCount(word) > 0) // Pour chaque mot, si le mot courant est contenu dans la page fournie en paramètres...
                .collect(Collectors.toSet()) // On retient ce mot dans un nouveau Set de chaînes de caractères.
                .stream().mapToDouble(s -> getPonderation(s) * page.getPonderation(s)) // Puis, calcul du produit de la pondération de chaque mot contenu dans les deux pages.
                .sum(); // Et enfin, calcul de la somme des produits.
    }

    /**
     * Redéfinition de la fonction toString().
     * @return String : url de la page.
     */
    @Override
    public String toString() {
        return "IndexedPage [url=" + url + "]";
    }

}