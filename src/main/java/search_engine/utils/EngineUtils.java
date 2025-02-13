package search_engine.utils;

import search_engine.tools.SearchEngine;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe abstraite permettant de récupérer l'instance de SearchEngine définie une seule
 * fois seulement pour chaque exécution du programme afin de ne charger les fichiers
 * indexés qu'une seule fois et de gagner par conséquence un temps précieux.
 * Cette classe contient également deux listes contenant l'historique de l'utilisateur.
 * La première contient les mots recherchés et la seconde le nombre de résultats du mot
 * au même indice. Cet historique est affiché sur la page HTML du serveur HTTP.
 */
public abstract class EngineUtils {

	/* Variable publique et statique contenant l'instance unique de SearchEngine utilisée. */
    public static SearchEngine ENGINE = getEngine();
	/* Variables publiques et statiques qui représentent l'historique
	   des recherches de l'utilisateur depuis le serveur HTTP. */
	public static ArrayList<String> HISTORY = initStringList();
	public static ArrayList<Integer> HISTORY_VALUES = initIntegerList();

	/**
	 * Méthode permettant de récupérer l'instance de SearchEngine à partir des fichiers indexés dans IndexFilesFolderUtils.
	 * @return SearchEngine : instance de SearchEngine.
     */
    private static SearchEngine getEngine() {
        try {
            return new SearchEngine(IndexFilesFolderUtils.getFolder());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	/**
	 * Méthode permettant d'initialiser une ArrayList de String vide.
	 * @return ArrayList : instance d'ArrayList.
	 */
	private static ArrayList<String> initStringList() {
		return new ArrayList<>();
	}

	/**
	 * Méthode permettant d'initialiser une ArrayList d'Integer vide.
	 * @return ArrayList : instance d'ArrayList.
	 */
	private static ArrayList<Integer> initIntegerList() {
		return new ArrayList<>();
	}

}
