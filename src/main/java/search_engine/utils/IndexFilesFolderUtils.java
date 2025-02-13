package search_engine.utils;

import java.io.File;
import java.nio.file.Path;

/**
 * Classe abstraite permettant d'accéder au dossier INDEX_FILES présent dans le dossier du projet.
 */
public abstract class IndexFilesFolderUtils {

    /**
     * Fonction renvoyant le chemin vers le répertoire INDEX_FILES.
     * @return Path : répertoire INDEX_FILES.
     */
    public static Path getFolder() {
        /* Récupération du dossier "INDEX_FILES" présent dans le même répertoire que le projet. */
        return new File("./INDEX_FILES").toPath();
    }

}
