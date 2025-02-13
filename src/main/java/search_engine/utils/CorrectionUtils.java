package search_engine.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe abstraite contenant un dictionnaire généré à partir du dictionnaire
 * de lemmes. Cette classe contient également une méthode permettant de
 * récupérer le mot le plus proche d'un mot donné à partir des fonctions
 * calculant les distances de Jaccard et de Levenshtein.
 */
public abstract class CorrectionUtils {
	
	/* Variable privée contenant le dictionnaire. */
	private static final Set<String> DICTIONARY = initDictionary();

	/**
	 * Méthode générant le dictionnaire à partir du dictionnaire des lemmes.
	 * @return Set<String> : dictionnaire.
     */
	private static Set<String> initDictionary() {
		/* Renvoie de la liste des clés du dictionnaire des lemmes. */
		return ResourcesFolderUtils.LEMMES.keySet();
	}

	/**
	 * Méthode permettant de récupérer le mot le plus proche d'un mot donné.
	 * @param word String : mot de départ.
	 * @return String : mot le plus proche trouvé.
     */
	public static String getClosestWord(String word) {
		/* Initialisation des variables. */
		String closestWord = ""; // Chaine de caractères qui contiendra le mot le plus proche.
		double minJaccardDistance = Double.MAX_VALUE; // Double qui contiendra la distance de jaccard la plus faible.
		int minLevenshteinDistance = Integer.MAX_VALUE; // Entier qui contiendra la distance de levenshtein la plus faible.
		
		for (String dictionaryWord : DICTIONARY) { // Pour chaque mot présent dans le dictionnaire...
			/* Récupération de la distance de jaccard entre le mot fourni et le mot courant. */
			double jaccardDistance = jaccardDistance(word, dictionaryWord);
			/* Récupération de la distance de levenshtein entre le mot fourni et le mot courant. */
			int levenshteinDistance = levenshteinDistance(word, dictionaryWord);
			/* Remplacement des distances minimales si les distances courantes sont plus petites. */
			if (jaccardDistance < minJaccardDistance || (jaccardDistance == minJaccardDistance && levenshteinDistance < minLevenshteinDistance)) {
				minJaccardDistance = jaccardDistance;
				minLevenshteinDistance = levenshteinDistance;
				closestWord = dictionaryWord;
			}
		}
		
		/* Renvoie du mot le plus proche trouvé. */
		return closestWord;
	}
  
	/**
	 * Méthode calculant la distance de jaccard entre deux mots.
	 * @param s1 String : premier mot.
	 * @param s2 String : second mot.
	 * @return double : distance entre les deux mots.
     */
    private static double jaccardDistance(String s1, String s2) {
		/* Initialisation de deux Sets de caractères, qui
           contiendront les caractères des deux chaines fournies. */
		Set<Character> set1 = new HashSet<>();
		Set<Character> set2 = new HashSet<>();

		/* Ajout de tous les caractères de la première chaîne dans l'ensemble set1. */
		for (int i = 0; i < s1.length(); i++)
			set1.add(s1.charAt(i));

		/* Ajout de tous les caractères de la seconde chaîne dans l'ensemble set2. */
		for (int i = 0; i < s2.length(); i++)
			set2.add(s2.charAt(i));

		/* Calcul de la taille de l'intersection et de l'union des deux ensembles */
		Set<Character> intersection = new HashSet<>(set1);
		intersection.retainAll(set2);
		Set<Character> union = new HashSet<>(set1);
		union.addAll(set2);

		/* Renvoie de la distance de jaccard. */
		return 1 - (double) intersection.size() / (double) union.size();
	}
	
	/**
	 * Méthode calculant la distance de levenshtein entre deux mots.
	 * @param s1 String : premier mot.
	 * @param s2 String : second mot.
	 * @return int : distance entre les deux mots.
     */
    private static int levenshteinDistance(String s1, String s2) {
		/* Initialisation de deux entiers à partir de la taille des deux chaines de caractères. */
		int m = s1.length();
		int n = s2.length();

		/* Initialisation d'un tableau en deux dimensions à partir des tailles des chaines de caractères. */
		int[][] d = new int[m+1][n+1];

		/* Initialisation de la première colonne du tableau "d" à partir de
		   la distance entre chaque caractère de "s1" et une chaîne vide. */
		for (int i = 0; i <= m; i++)
			d[i][0] = i;

		/* Initialisation de la première ligne du tableau "d" à partir de
		   la distance entre chaque caractère de "s2" et une chaîne vide. */
		for (int j = 0; j <= n; j++)
			d[0][j] = j;

		/* Remplissage des cases restantes du tableau "d" en utilisant
		   les formules de calcul de la distance de Levenshtein. */
		for (int j = 1; j <= n; j++) {
			for (int i = 1; i <= m; i++) {

				/* Si les caractères comparés sont identiques, alors la case
				   courante prend la même valeur que la case diagonale précédente. */
				if (s1.charAt(i-1) == s2.charAt(j-1))
					d[i][j] = d[i-1][j-1];

				else {
					/* Sinon, la case courante prend la valeur du coût minimum entre le coût
					   de substitution, le coût d'insertion et le coût de suppression. */
					int substitutionCost = d[i-1][j-1] + 1;
					int insertionCost = d[i][j-1] + 1;
					int deletionCost = d[i-1][j] + 1;
					int minCost = Math.min(substitutionCost, Math.min(insertionCost, deletionCost));
					d[i][j] = minCost;
				}
			}
		}

		/* Renvoie de la valeur de la dernière case du tableau "d", qui représente
		   la distance de Levenshtein entre les deux chaînes de caractères "s1" et "s2". */
		return d[m][n];
	}
	
}