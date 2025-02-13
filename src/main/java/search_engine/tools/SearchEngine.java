package search_engine.tools;

import search_engine.utils.CorrectionUtils;
import search_engine.utils.SELoggerUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;

public class SearchEngine {

    private final ArrayList<IndexedPage> pages; // ArrayList d'IndexedPage contenant les pages indexées présentes dans le dossier.

    /**
     * Constructeur de SearchEngine.
     * @param indexationDirectory Path : chemin vers le dossier contenant les fichiers textes à traiter.
     * @throws IOException renvoie une erreur InputOutput si le dossier n'est pas accessible.
     */
    public SearchEngine(Path indexationDirectory) throws IOException {
        List<Path> paths = Files.list(indexationDirectory).toList(); // Récupération sous la forme de liste de chaque page contenue dans le dossier.
        pages = new ArrayList<>(); // Initialisation de la liste contenant les pages indexées présentes dans le dossier.

        for (Path path : paths) // Pour chaque page de la liste "paths"...
            pages.add(new IndexedPage(path)); // Ajout de la page courante dans la liste "pages".
    }

    /**
     * Fonction renvoyant la page à l'indice "i".
     * @param i int : indice de la page dans la liste "pages".
     * @return IndexedPage : la page à l'indice "i".
     */
    public IndexedPage getPage(int i) {
        return pages.get(i);
    }

    /**
     * Fonction renvoyant le nombre de pages présentes dans le dossier.
     * @return int : nombre de pages.
     */
    public int getPagesNumber() {
        return pages.size();
    }

    /**
     * Fonction lançant une recherche.
     * @param requestString String : chaîne de caractères contenant les mots à rechercher.
     * @return ArrayList<SearchResult> : liste de résultats contenant le résultat de la recherche de chaque page.
     */
    private ArrayList<SearchResult> launchRequest(String requestString) {
        ArrayList<SearchResult> results = new ArrayList<>(); // Initialisation d'une ArrayList de SearchResult qui contiendra les résultats de la recherche pour chaque page.

        for (int i = 0; i < getPagesNumber(); i++) {  // Pour chaque indice des pages (pour chaque page)...
            IndexedPage request = new IndexedPage(requestString); // Initialisation d'une requête de recherche sur la page actuelle.
            results.add(new SearchResult(getPage(i).getUrl(), getPage(i).proximity(request) * 100)); // Ajout du résultat de la requête dans la liste "results".
        }

        return results; // Renvoie des résultats de la requête.
    }

	/**
	 * Fonction récupérant les 15 (au maximum) meilleurs résultats de la recherche.
	 * @param requestString String : chaîne de caractères contenant les mots à rechercher.
	 * @return ArrayList<SearchResult> : liste de résultats contenant les 15 (au maximum) meilleurs résultats de la recherche.
     */
    public ArrayList<SearchResult> getBestResults(String requestString) {
        ArrayList<SearchResult> searchResults = launchRequest(requestString); // Initialisation d'un tableau de SearchResult contenant les résultats de la requête à partir du "requestString".

        /* Utilisation de la méthode sort de Collections afin de comparer le score entre les deux pages courantes.
           Cette méthode utilise une version améliorée du tri rapide. */
        searchResults.sort((o1, o2) -> Double.compare(o2.getScore(), o1.getScore()));

        /* Initialisation d'une nouvelle liste qui contiendra les résultats pertinents. */
        ArrayList<SearchResult> sortedSearchResults = new ArrayList<>();

        for (SearchResult result : searchResults) // Pour chaque résultat de la recherche...
            if (result.getScore() > 0.0 && sortedSearchResults.size() < 15) // Si son résultat n'est pas nul et que ce n'est pas le 15ᵉ...
                sortedSearchResults.add(result); // Ajout du résultat.

        return sortedSearchResults; // Renvoie des résultats pertinents.
    }

    /**
     * Fonction affichant les résultats d'une recherche.
     * @param requestString String : chaîne de caractères contenant les mots à rechercher.
     */
    public void printResults(String requestString) {
        long time = System.currentTimeMillis(); // Variable permettant de calculer le temps d'exécution.

        ArrayList<SearchResult> results = getBestResults(requestString);

        StringBuilder loggerInfo = new StringBuilder();
        loggerInfo.append("<b>Mot(s) recherché(s): ").append(requestString).append("</b>\n");

        /* Mise en forme de l'affichage du résultat de la recherche. */
        System.out.println("========== RÉSULTAT DE LA RECHERCHE ==========");
        System.out.println("Mot(s) recherché(s): " + requestString + "\n");

        if(results.isEmpty()) { // Si aucun résultat n'est trouvé...
            System.out.println("Aucun résultat n'a été trouvé."); // Affichage d'un message avertissant l'utilisateur.
            loggerInfo.append("\t\t\t<br>Aucun résultat n'a été trouvé.\n");
        }

        else // Sinon...
            /* Affichage des 15 résultats (au plus) les plus pertinents. */
            for (SearchResult result : results) {
                System.out.println(result.toString());
                loggerInfo.append("\t\t\t<br>").append(result).append("\n");
            }

        time = System.currentTimeMillis() - time;

        SELoggerUtils.LOGGER.log(Level.INFO, loggerInfo + "\t\t\t<br><b>Recherche effectuée en " + time + " ms.</b>");

        System.out.println("\nRecherche effectuée en " + time + " ms !"); // Affichage du temps d'exécution de la recherche.
        System.out.println("============================================\n");
    }

}