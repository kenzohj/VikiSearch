package search_engine;

import search_engine.handlers.ServerHandler;
import search_engine.tools.SearchEngine;
import search_engine.utils.EngineUtils;
import search_engine.utils.SELoggerUtils;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Initialisation du moteur de recherche...");
        long time = System.currentTimeMillis(); // Variable permettant de calculer le temps d'exécution.
        SearchEngine se = EngineUtils.ENGINE; // Récupération de l'instance de SearchEngine.
        System.out.println("Moteur initialisé en " + (System.currentTimeMillis() - time) + " ms !\n"); // Affichage du temps d'initialisation du moteur.

        if(args.length > 0 && args[0].equals("-server")) { // Si l'exécutable jar est appelé avec l'argument "--server"...
            /* Lancement du serveur en passant en paramètre un booléen vérifiant la présence d'arguments supplémentaires. */
            new ServerHandler().start(args.length == 1);
            return;
        }

        if(args.length > 0) { // Si des arguments sont fournis lors de l'appel de la fonction...
            System.out.println("Chargement en cours...");
            /* Affichage des résultats pour chaque argument (= mot) fourni, auquel sont enlevés les crochets et les virgules ajoutés par la fonction Arrays.toString(). */
            se.printResults(Arrays.toString(args).replace("[", "").replace(",", "").replace("]", "").trim());
            System.exit(0); // Sortie du code sans erreurs.
        }

        /* À ce stade, le nombre d'arguments est de 0. Cela signifie que la fonction main a été appelée depuis un terminal de commandes. */

        /* Affichage d'un menu dans le terminal contenant les consignes d'utilisation pour effectuer une recherche. */
        System.out.println("\n");
        System.out.println("========== MOTEUR DE RECHERCHE ==========");
        System.out.println("  VERSION 2.0");
        System.out.println("  Développé par Kenzo Hambli");
        System.out.println("=========================================");
        System.out.println("Entrez des mots séparés par des espaces: ");
        System.out.println("(Tapez 'exit' pour quitter)\n");

        Scanner scanner = new Scanner(System.in); // Initialisation d'une instance de Scanner à partir du System Input permettant de récupérer les inputs de l'utilisateur.

        String inputString = scanner.nextLine(); // Lecture de la chaîne de caractères entrée par l'utilisateur.
        while(!inputString.equalsIgnoreCase("exit")) { // Tant que la chaîne entrée est différente de "exit"...
            System.out.println("Chargement en cours...");

            /* Affichage du résultat de recherche pour le mot fourni par l'utilisateur. */
            se.printResults(inputString);

            System.out.println("Entrez des mots séparés par des espaces: ");
            System.out.println("(Tapez 'exit' pour quitter)");
            inputString = scanner.nextLine(); // Lecture de la chaîne de caractères (= mot) suivante.
        }

        // À ce stade, "exit" a été entré par l'utilisateur, la boucle while est donc terminée.

        SELoggerUtils.LOGGER.getHandlers()[0].close(); // Fermeture du logger.

        scanner.close(); // Fermeture du scanner.

        System.exit(0); // Sortie du code sans erreurs.
    }

}
