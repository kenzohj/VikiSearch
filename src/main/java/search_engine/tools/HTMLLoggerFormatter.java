package search_engine.tools;

import search_engine.utils.DateUtils;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Classe qui extend Formatter, permettant de formater un log sous la forme de fichier HTML.
 */
public class HTMLLoggerFormatter extends Formatter {

    private final long milliseconds; // Variable contenant la date à laquelle le log a été créé.

    /**
     * Constructeur de la classe.
     * @param milliseconds long : date à laquelle le log a été créé.
     */
    public HTMLLoggerFormatter(long milliseconds) {
        this.milliseconds = milliseconds; // Assignation de la date fournie en paramètres à la variable locale.
    }

    /**
     * Méthode appelée lorsque le logger reçoit un log.
     * @param rec : le log à formater.
     * @return String : le log formaté.
     */
    @Override
    public String format(LogRecord rec) {
        return
                "\t\t<div>\n" // Création d'une div.
                        + "\t\t\t<b>" + rec.getLoggerName() + "</b>\n" // Ajout du nom du logger.
                        + "\t\t\t<p>" + rec.getMessage() + "</p>\n" // Ajout du message du log.
                        + "\t\t\t<p class=\"log-date\">" + DateUtils.formatDate(milliseconds) + "</p>\n" // Ajout de la date du log.
                + "\t\t</div>\n\n"; // Fermeture de la div.
    }

    /**
     * Méthode appelée en premier afin d'écrire le début du contenu du fichier.
     * @param h Handler : le handler à traiter.
     * @return String : le début du fichier formaté.
     */
    public String getHead(Handler h) {
        return
                "<!DOCTYPE html>\n" // Balise définissant le fichier comme fichier HTML 5.
                + "<html lang=\"fr\">\n" // Ouverture de la balise html en définissant la langue de la page en français.

                + "<head>\n" // Ouverture de la balise head.
                    + "\t<meta charset=\"UTF-8\">\n"
                    + "\t<title>SE Logs (" + DateUtils.formatDate(milliseconds) + ")</title>\n" // Ajout d'un titre à la page.
                    + "\t<style>\n" // Ouverture d'une basile de stylisation CSS.

                        + "\t\tsection > div {\n" // Définition d'une stylisation pour les balises div qui contiennent les logs.
                            + "\t\t\tmargin: 10px 10px 30px 10px;\n" // Ajout d'une marge.
                            + "\t\t\tpadding: 10px;\n" // Ajout d'une marge intérieure.
                            + "\t\t\tborder: 2px solid #58e2c4;\n" // Ajout d'une bordure.
                            + "\t\t\tborder-radius: 10px;\n" // Arrondissement des angles de la bordure.
                        + "\t\t}\n" // Fin de la stylisation.

                        + "\t\tsection > div b {\n" // Définition d'une stylisation pour les balises b qui contiennent le nom du logger, le mot recherché et le temps de recherche.
                            + "\t\t\tcolor: #ccd6f6;\n" // Ajout d'une couleur.
                        + "\t\t}\n" // Fin de la stylisation.

                        + "\t\tsection > div > p {\n" // Définition d'une stylisation pour les balises p qui contiennent le message du log.
                            + "\t\t\tcolor: #8892b0;\n" // Ajout d'une couleur
                        + "\t\t}\n" // Fin de la stylisation.

                        + "\t\t.log-date {\n"// Définition d'une stylisation pour les textes qui contiennent la date du log.
                            + "\t\t\tcolor: #ccd6f6;\n" // Ajout d'une couleur
                        + "\t\t}\n" // Fin de la stylisation.

                    + "\t</style>\n" // Fermeture de la balise de stylisation.
                + "</head>\n\n" // Fermeture de la balise head.

                + "<body style=\"background-color: #0a192f;\">\n" // Ouverture de la balise body en lui ajoutant une couleur de fond.
                    + "\t<h1 style=\"color: #58e2c4; text-align: center\">" + (DateUtils.formatDate(milliseconds)) + "</h1>\n" // Ajout d'un titre à la page, contenant la date.
                    + "\t<section>\n\n"; // Ouverture d'une balise section.
    }

    /**
     * Méthode appelée en dernier afin d'écrire la fin du contenu du fichier.
     * @param h Handler : le handler à traiter.
     * @return String : la fin du fichier formaté.
     */
    public String getTail(Handler h) {

        return
                "\t</section>\n" // Fermeture de la section.
                + "</body>\n" // Fermeture de la balise body.
                + "</html>"; // Fermeture de la balise html.
    }

}
