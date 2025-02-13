package search_engine.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import search_engine.utils.ResourcesFolderUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Classe implémentant l'interface HttpHandler permettant de créer des contextes HTTP
 * à partir d'un fichier "index.html" présent dans le dossier "resources".
 */
public class HTMLFileHandler implements HttpHandler {

    /**
     * Méthode handle(HttpExchange) implémentée de l'interface HttpHandler.
     * @param exchange the exchange containing the request from the client and used to send the response (description provenant de l'interface)
     * @throws IOException : renvoie une erreur Input Output si l'échange avec le serveur est impossible.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {//todo use utils resources files

        /* Récupération du fichier HTML présent dans le dossier "resources". */
        InputStream inputStream = ResourcesFolderUtils.getHTMLFile("index");

        if (inputStream == null) { // Si le fichier est introuvable...
            System.out.println("Impossible de charger le fichier index.html !"); // Affichage de l'erreur.
            exchange.sendResponseHeaders(404, 0); // Envoie d'une erreur 404.
            exchange.close(); // Fermeture de l'échange HTTP.
            return; // Arrêt du code.
        }

        /* Ouverture d'un buffer permettant de lire le contenu du fichier. */
        byte[] fileBytes = inputStream.readAllBytes();
        inputStream.close();

        /* Envoie du contenu du fichier sous la forme de réponse HTTP. */
        exchange.getResponseHeaders().set("Content-Type", "text/html");
        exchange.sendResponseHeaders(200, fileBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(fileBytes);
        os.close();
    }

}
