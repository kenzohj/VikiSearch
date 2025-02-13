package search_engine.handlers;

import com.sun.net.httpserver.HttpServer;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Scanner;

/**
 * Classe permettant de démarrer le serveur HTTP.
 */
public class ServerHandler {

    /**
     * Méthode permettant de démarrer le serveur HTTP en créant le contexte racine,
     * ainsi que le contexte submit qui gère les entrées de l'utilisateur sur la page.
     * @param hasGui boolean : true permet d'ouvrir la page web automatiquement.
     */
    public void start(boolean hasGui) {
        final HttpServer server; // Initialisation d'une variable finale de type serveur HTTP.
        try {
            /* Instanciation du serveur HTTP sur le port 8080. */
            server = HttpServer.create(new InetSocketAddress(8080), 0);
        } catch (IOException e) { // Si cela provoque une erreur...
            throw new RuntimeException("Impossible de créer le server HTTP !", e); // Affichage de celle-ci.
        }

        /* Création d'un gestionnaire de requêtes HTTP pour la racine "/". */
        server.createContext("/", new HTMLFileHandler());
        /* Création d'un gestionnaire de requêtes HTTP pour "/submit". */
        server.createContext("/submit", new SubmitHandler());
        /* Lancement du serveur. */
        server.start();

        // Initialisation d'une variable finale contenant l'URL de la page.
        final String url = "http://localhost:8080";
        /* Affichage d'un message prévenant l'utilisateur du lancement du serveur. */
        System.out.println("Serveur HTTP démarré sur le port 8080 (" + url + ") !");

        if(hasGui) { // Si l'ouverture automatique de la page web est activée...
            try {
                /* Ouverture de la page web. */
                System.out.println("Ouverture de la page web automatiquement...");
                Desktop.getDesktop().browse(URI.create(url));
            } catch (IOException e) { // Si cela provoque une erreur...
                /* Affichage d'un message prévenant l'utilisateur ainsi qu'une solution à adopter. */
                throw new RuntimeException(
                        "Impossible d'ouvrir la page automatiquement dans le navigateur !" +
                        "\nPour empêcher cette fonctionnalité, lancez le serveur avec un argument quelconque.",
                        e
                );
            }
        } else { // Sinon...
            /* Affichage prévenant l'utilisateur que cette fonctionnalité est désactivée. */
            System.out.println("Le serveur a été démarré sans ouvrir automatiquement la page web.");
            System.out.println("Pour activer cette fonctionnalité, lancez le serveur sans arguments.");
        }

        /* Affichage d'un message prévenant l'utilisateur sur comment arrêter le serveur. */
        System.out.println("Tapez 'stop' pour arrêter le serveur.");

        /* Initialisation d'un scanner qui lit les entrées de l'utilisateur dans la console. */
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine(); // Lecture de la chaîne de caractères suivante...
        while (!text.equalsIgnoreCase("stop")) // Si la chaîne entrée n'est pas égale à "stop"...
            text = scanner.nextLine(); // Lecture de la ligne suivante.

        /* À ce stade, la chaîne de caractères entrée est "stop". Dans ce cas, arrêt du serveur. */
        System.out.println("Arrêt du serveur...");
        server.stop(0);
        System.out.println("Serveur arrêté avec succès !");

        /* Arrêt du programme. */
        System.exit(0);
    }

}
