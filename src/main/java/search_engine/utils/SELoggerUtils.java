package search_engine.utils;

import search_engine.tools.HTMLLoggerFormatter;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Cette classe abstraite est une classe utilitaire qui permet d'initialiser un Logger nommée "SE.Logger",
 * qui a pour but de log chaque résultat de recherche effectuée dans la classe SearchEngine.
 * La classe log les informations dans un fichier au format HTML afin de pouvoir visualiser
 * les logs de manière simple.
 * Chaque fichier HTML est nommé par la date à laquelle la première recherche est effectuée et contient
 * toutes les recherches suivantes, tant que le programme n'a pas été arrêté (avec le mot clé "exit").
 */
public abstract class SELoggerUtils {

    /* Initialisation du Logger accessible statiquement à partir de la fonction initLogger(). */
    public final static Logger LOGGER = initLogger();

    /**
     * Fonction permettant d'initialiser le logger.
     * @return Logger : logger initialisé.
     */
    private static Logger initLogger() {
        /* Instance de la classe Logger nommée "SE.Logger" pour "SearchEngine.Logger" qui
        aura pour objectif de log chaque recherche de résultats de la classe SearchEngine. */
        Logger logger = Logger.getLogger("SE.Logger");
        logger.setUseParentHandlers(false); // Désactivation de tous les handlers du logger afin de rediriger les logs dans un fichier spécifique.

        try {
            /* Initialisation d'un Handler de type fichier nommé "logs-'date_actuelle'.html" dans le répertoire "logs".
            Le temps actuel est formaté à partir de la classe abstraite "DateUtils" auquel on remplace les espaces par des "_".
            Cette action peut provoquer une erreur Input Output, c'est pour cela que la définition de l'instance se
            trouve dans un try {} catch(IOException) {}. */
            long milliseconds = System.currentTimeMillis(); // Récupération du temps actuel.

            File folder = new File("logs"); // Récupération du dossier logs.
            if(!folder.exists()) // S'il n'existe pas...
                if(!folder.mkdir()) // Essaie de création du dossier...
                    throw new RuntimeException("Impossible de créer le dossier logs !"); // Sinon, envoie d'une erreur.

            /* Création d'un fichier logs.html qui contient la date à laquelle le logger est appelé. */
            FileHandler fileHandler = new FileHandler("logs/logs-" + DateUtils.formatFileNameDate(milliseconds) + ".html");
            logger.addHandler(fileHandler); // Ajout du Handler de type fichier au logger.
            fileHandler.setFormatter(new HTMLLoggerFormatter(milliseconds)); // Définition du formatage du logger en HTML à partir de la classe HTMLLoggerFormatter.

        } catch (IOException e) { // Si une erreur Input Output est trouvée...
            throw new RuntimeException("Impossible de créer le fichier logs au format HTML."); // Affichage de l'erreur.
        }

        return logger; // Sinon, renvoie du logger.
    }

}
