package search_engine.tools;

public class SearchResult {

    private final String url; // Url de la page.
    private final double score; // Score du résultat.

    /**
     * Constructeur de SearchResult.
     * @param url String : url de la page.
     * @param score double : score du résultat.
     */
    public SearchResult(String url, double score){
        this.url = url; // Initialisation de l'url à partir de l'url fournie.
        this.score = score; // Initialisation du score à partir du score fourni.
    }

    /**
     * Fonction renvoyant l'url de la page.
     * @return String : url de la page.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Fonction renvoyant le score du résultat.
     * @return double : score.
     */
    public double getScore() {
        return score;
    }

    /**
     * Fonction affichant l'url de la page ainsi que le score du résultat.
     * @return String : contenant l'url de la page ainsi que le score.
     */
    @Override
    public String toString() {
        return "SearchResult [url=" + url + ", score=" + score + "]";
    }

}