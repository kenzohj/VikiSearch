package search_engine.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import search_engine.tools.SearchEngine;
import search_engine.tools.SearchResult;
import search_engine.utils.CorrectionUtils;
import search_engine.utils.EngineUtils;
import search_engine.utils.ResourcesFolderUtils;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe permettant de traiter les requêtes du serveur HTTP.
 * Cette classe implémente l'interface HttpHandler pour cela.
 */
public class SubmitHandler implements HttpHandler {

	/**
	 * Implémentation de la méthode de l'interface permettant de traiter les requêtes HTTP.
	 * @param exchange HttpExchange : requête reçue.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        /* Récupération de la valeur de la chaine de caractères entrée par l'utilisateur sur
		   la page web, à partir de l'URL de la page qui est sous la forme de :
           "localhost:8080/submit&searchQuery=CHAINE_ENTREE_PAR_L_UTILISATEUR".
           Cette URL est générée à partir de la balise "form" et d'in "input"
           présent dans le fichier "index.html". */
        String query = exchange.getRequestURI().getQuery(); // Récupération de la requête.
		String inputText = query == null ? null : // Initialisation d'une variable dans laquelle, si la requête n'est pas nulle, ...
                Arrays.stream(query.split("&")) // - séparation de la requête à partir du caractère "&",
						/* - récupération du contenu de la séparation dans "param" afin de ne garder seulement celui commençant par "searchQuery", */
                      .filter(param -> param.startsWith("searchQuery="))
						/* auquel on enlève le préfixe "searchQuery"... */
                      .map(param -> param.substring("searchQuery=".length()))
						/* afin de ne garder que la chaine entrée par l'utilisateur formatée en utf 8. */
                      .map(value -> URLDecoder.decode(value, StandardCharsets.UTF_8))
						/* Assignation du premier résultat trouvé à la suite de ces filtres. */
                      .findFirst()
						/* Si aucun résultat n'est trouvé, assignation d'une chaine nulle. */
                      .orElse(null);

        /* Si aucun résultat n'avait été trouvé, renvoie d'une erreur 400 au serveur HTTP. */
        if (inputText == null) {
            String errorMessage = "Aucune recherche n'a été trouvée dans l'URL de la page !";
            exchange.sendResponseHeaders(400, errorMessage.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(errorMessage.getBytes());
            os.close();
            return;
        }

		/* Récupération de l'instance de SearchEngine. */
        SearchEngine se = EngineUtils.ENGINE;
        /* Traitement du texte reçu afin de fournir un résultat sous forme HTML. */
		byte[] resultBytes = generateResultHtml(inputText, se.getBestResults(inputText));

		/* Envoi de la réponse au serveur HTTP sous la forme de page HTML. */
        exchange.getResponseHeaders().set("Content-Type", "text/html");
        exchange.sendResponseHeaders(200, resultBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(resultBytes);
		os.flush();
        os.close();
    }

	/**
	 * Méthode générant la page HTML à renvoyer au serveur à partir de la chaine entrée par
	 * l'utilisateur ainsi que de la liste des résultats trouvés à partir de cette chaine.
	 * @param inputText String : chaine entrée par l'utilisateur.
	 * @param results ArrayList<SearchResult> : résultats trouvés à partir de la chaine de caractères.
	 * @return byte[] : tableau d'octets contenant le contenu de la page HTML de résultat à renvoyer.
     */
    private byte[] generateResultHtml(String inputText, ArrayList<SearchResult> results) {
		/* Récupération du fichier de résultat HTML présent dans le dossier "resources". */
		InputStream htmlFile = ResourcesFolderUtils.getHTMLFile("result");
		/* Initialisation d'un booléen contenant true si aucun résultat n'est trouvé et false sinon. */
		boolean isEmpty = results.isEmpty();

		/* Si aucun résultat n'est trouvé... */
		if(isEmpty) {
            /* Redéfinition du mot entré par l'utilisateur par le mot le plus proche
			   à partir des distances de jaccard et le levenshtein. */
			inputText = CorrectionUtils.getClosestWord(inputText);
			/* Recherche de nouveaux résultats à partir du nouveau mot. */
			results = EngineUtils.ENGINE.getBestResults(inputText);
			/* Mise à jour de la variable "isEmpty. */
			isEmpty = results.isEmpty();
		}

		/* Initialisation d'un OutputStream d'octets auquel est écrit le contenu du fichier
		   "result.html" présent dans le dossier resources, à partir d'un buffer d'octets. */
		ByteArrayOutputStream outputStream;
		try {
			outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while((length = htmlFile.read(buffer)) != -1)
				outputStream.write(buffer, 0, length);
		} catch (IOException e) { // Si cela provoque une erreur...
			throw new RuntimeException("Impossible de lire le fichier result.html du dossier resources !", e); // Affichage de celle-ci.
		}

		/* Récupération du contenu de l'OutputStream sous la forme de chaine de caractères, auquel les
		   chaines "%input_string%" présentes dans le fichier sont remplacées par le(s) mot(s) recherché(s). */
		String lines = outputStream.toString().replace("%input_string%", inputText);

		/* Si aucun résultat n'est trouvé... */
        if(isEmpty) {
			/* Remplacement des valeurs nécessaires sur la page HTML. */
			lines = lines.replace("%result_count%", "0 résultat")
					     .replace("%results%", "<h2>Aucun résultat trouvé !</h2>");
		}
        else { // Sinon...
			/* Si un seul résultat est trouvé, affichage de "résultat" sans "s". */
			if(results.size() == 1) lines = lines.replace("%result_count%", "1 résultat");
			/* Sinon, affichage de "résultats" avec un "s". */
			else lines = lines.replace("%result_count%", results.size() + " résultats");

			/* Initialisation d'un StringBuilder permettant d'ajouter à la page HTML, les résultats de la recherche en remplaçant la chaine "%results%". */
			StringBuilder resultsList = new StringBuilder();
			for(SearchResult result : results)
				resultsList.append("<p><a href=\"").append(result.getUrl()).append("\" target=\"_blank\">").append(result.getUrl()).append("</a></p>");
			lines = lines.replace("%results%", resultsList.toString());
		}

		/* Récupération de l'historique à partir de la classe utilitaire. */
		ArrayList<String> history = EngineUtils.HISTORY;
		ArrayList<Integer> historyValues = EngineUtils.HISTORY_VALUES;

		/* Si l'historique est vide... */
		if(history.isEmpty())
			/* Remplacement de la chaine "%history%" par un historique vide. */
			lines = lines.replace("%history%", "Aucun historique.");
		else { /* S'il n'est pas vide... */
			/* Initialisation d'un StringBuilder permettant d'ajouter à la page HTML, l'historique de l'utilisateur en remplaçant la chaine "%history%". */
			StringBuilder historyList = new StringBuilder();
			String resultat;
			for(int i = history.size()-1; i >= 0; i--) {
				resultat = historyValues.get(i) > 1 ? "résultats" : "résultat";
				historyList.append("<p>")
						   .append("<a href=\"http://localhost:8080/submit?searchQuery=").append(history.get(i)).append("\">")
						   .append(history.get(i)).append("</a>")
						   .append(" (").append(historyValues.get(i)).append(" ").append(resultat).append(")</p>");
			}
			lines = lines.replace("%history%", historyList.toString());
		}

		/* Ajout de la recherche effectuée à l'historique. */
		EngineUtils.HISTORY.add(inputText);
		EngineUtils.HISTORY_VALUES.add(results.size());

		/* Renvoi du tableau d'octets correspondant à la chaine de caractères traitée. */
        return lines.getBytes();
    }

}
