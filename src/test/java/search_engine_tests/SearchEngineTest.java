package search_engine_tests;

import search_engine.tools.IndexedPage;
import search_engine.tools.SearchEngine;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SearchEngineTest {

    public static void main(String[] args) throws IOException {
        long globalTime = System.currentTimeMillis(); // Variable permettant de calculer le temps d'exécution global.

        System.out.println("==================== TESTS VERSION 0.1 ====================\n");
        long time = System.currentTimeMillis(); // Variable permettant de calculer le temps d'exécution.

        IndexedPage page1 = new IndexedPage(new String[] {"http://fr.example.org", "hello:10", "world:5"});
        System.out.println(page1);
        System.out.println(page1.getPonderation("hello"));
        System.out.println(page1.getPonderation("other"));
        IndexedPage page2 = new IndexedPage(new String[] {"http://fr.example2.org", "hello:5", "france:2"});
        System.out.println("Degre de similarite des deux pages: " + page1.proximity(page2));

        System.out.println("\nTEMPS ÉCOULÉ: " + (System.currentTimeMillis() - time) + " ms"); // Affichage du temps d'exécution.
        System.out.println("\n===========================================================");


        System.out.println("\n==================== TESTS VERSION 0.2 ====================\n");
        time = System.currentTimeMillis();

        /* Il faut d'abord récupérer le chemin du dossier INDEX, pour initialiser le moteur de recherche.
        On pourrait procéder simplement comme ceci :
           Path index = Paths.get("INDEX");

        Mais il semble difficile d'arriver à le faire fonctionner à la fois dans Eclipse
        et dans le terminal (trop sensible au classpath)...

        Une solution plus fiable (mais nettement plus complexe !) est de procéder ainsi : */

        URL location = SearchEngine.class.getProtectionDomain().getCodeSource().getLocation();
        Path binFolder;
        try {
            binFolder = Paths.get(location.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Path indexFolder = binFolder.resolve("INDEX");

        // On crée maintenant une instance du moteur de recherche.
        SearchEngine se = new SearchEngine(indexFolder);

        // On lance une recherche et on affiche les résultats.
        String requestString = "cerise flan";
        IndexedPage request = new IndexedPage(requestString);
        se.printResults(requestString);

        System.out.println("TEMPS ÉCOULÉ: " + (System.currentTimeMillis() - time) + " ms"); // Affichage du temps d'exécution.
        System.out.println("\n===========================================================");

        System.out.println("\n==================== TESTS VERSION 0.3 ====================\n");
        time = System.currentTimeMillis();

        IndexedPage indexedPageTest = new IndexedPage("Hier, j'ai mangé manger mange mangea de la salade avec des tomates et je me suis abaissé abaissais abaisser pour prendre ma fourchette.");
        System.out.println("Pondération de 'hier': " + indexedPageTest.getPonderation("hier"));
        System.out.println("Pondération de 'de': " + indexedPageTest.getPonderation("de"));
        System.out.println("Pondération de 'la': " + indexedPageTest.getPonderation("la"));
        System.out.println("Pondération de 'abaisser': " + indexedPageTest.getPonderation("abaisser"));
        System.out.println("Pondération de 'manger': " + indexedPageTest.getPonderation("manger"));

        System.out.println("\nTEMPS ÉCOULÉ: " + (System.currentTimeMillis() - time) + " ms"); // Affichage du temps d'exécution.
        System.out.println("\n===========================================================");

        System.out.println("\n==================== TESTS VERSION 1.0 ====================\n");
        time = System.currentTimeMillis();

        //indexFolder = BinFolderUtils.BIN_FOLDER.resolve("INDEX_FILES");
        se = new SearchEngine(indexFolder);
        se.printResults("developpeur qui code");

        System.out.println("\nTEMPS ÉCOULÉ: " + (System.currentTimeMillis() - time) + " ms"); // Affichage du temps d'exécution.
        System.out.println("\n===========================================================");

        System.out.println("\nTEMPS GLOBAL ÉCOULÉ: " + (System.currentTimeMillis() - globalTime) + " ms"); // Affichage du temps d'exécution global.
    }

}