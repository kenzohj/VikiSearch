package search_engine.utils;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe abstraite permettant d'accéder aux différents fichiers présents dans le dossier Resources du projet.
 */
public abstract class ResourcesFolderUtils {

    /* Variables publiques et statiques permettant d'accéder aux mots blacklists et au dictionnaire des lemmes depuis n'importe quelle classe. */
    public final static HashSet<String> BLACKLISTED_WORDS = getBlackListedWords();
    public final static HashMap<String, String> LEMMES = getLemmes();

    /**
     * Fonction renvoyant la liste des mots blacklists contenus dans le fichier "blacklist.txt".
     * @return HashSet<String> : set contenant les mots blacklists.
     */
    private static HashSet<String> getBlackListedWords() {
        try (InputStream inputStream = ResourcesFolderUtils.class.getClassLoader().getResourceAsStream("blacklist.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return (HashSet<String>) reader.lines().collect(Collectors.toSet());
        } catch (IOException e) { // Si cela provoque une erreur...
            throw new RuntimeException("Le fichier 'blacklisted.txt' est introuvable !", e); // Affichage de l'erreur.
        }
    }

    /**
     * Fonction permettant de lemmatiser les mots contenus dans le tableau "words".
     * @return HashMap<String, String> : dictionnaire contenant les lemmes.
     */
    private static HashMap<String, String> getLemmes() {
        List<String> dictionaryLines;
        try (InputStream inputStream = ResourcesFolderUtils.class.getClassLoader().getResourceAsStream("french_dictionary.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
             dictionaryLines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) { // Si cela provoque une erreur...
            throw new RuntimeException("Le fichier 'blacklisted.txt' est introuvable !", e); // Affichage de l'erreur.
        }

        /* Initialisation d'un dictionnaire avec comme clé une chaîne de caractères (mot lemmatisé) et comme valeur une liste de chaînes de caractères (mots à lemmatiser). */
        HashMap<String, String> lemmes = new HashMap<>();

        for (String currentLine : dictionaryLines) { // Pour chaque ligne contenue dans le fichier (sous la forme de "mot:lemme")...
            String[] data = currentLine.split(":");
            if (data[0].equals(data[1])) // Si le lemme du mot est équivalent au mot...
                continue; // Passage au mot suivant.
            lemmes.put(data[0], data[1]); // Ajout du lemme du mot courant.
        }

        return lemmes; // Renvoie des lemmes.
    }

	/**
	 * Méthode renvoyant l'InputStream d'un fichier HTML présent dans le dossier resources.
	 * @param name String : nom du fichier HTML.
	 * @return InputStream : InputStream du fichier HTML (peut être null !).
     */
	public static InputStream getHTMLFile(String name) {
		return ResourcesFolderUtils.class.getClassLoader().getResourceAsStream(name + ".html");
	}

}
