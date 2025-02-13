package search_engine.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe permettant de formater un temps système en millisecondes, vers une date lisible.
 */
public abstract class DateUtils {

    /**
     * Fonction permettant de formater une date à partir du temps compté en millisecondes par le système.
     * @param milliseconds long : temps actuel.
     * @return String : date formatée.
     */
    public static String formatDate(long milliseconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM yyyy"); // Création du format de la date.
        Date date = new Date(milliseconds); // Récupération de la date actuelle à partir du temps.
        return dateFormat.format(date); // Renvoie du temps formaté.
    }

    /**
     * Fonction permettant de formater une date à partir du temps compté en millisecondes par le système.
     * Cette version contient uniquement des caractères supportés par les noms de fichiers.
     * @param milliseconds long : temps actuel.
     * @return String : date formatée.
     */
    public static String formatFileNameDate(long milliseconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH-mm_dd-MM_yyyy"); // Création du format de la date.
        Date date = new Date(milliseconds); // Récupération de la date actuelle à partir du temps.
        return dateFormat.format(date); // Renvoie du temps formaté.
    }

}
