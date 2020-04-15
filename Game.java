
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Game {

	public static final char[] codeSymbolen = { 'A', 'B', 'C', 'D', 'E', 'F' };
	public static char[] code = new char[4];
	public static int seplRondes = 12;
	public static final char preciesGoedIndicator = '=';
	public static final char bijnaGoedIndicator = '-';
	public static final char nietGoedIndicator = '.';
	public static int gespeeldeRondes = 0;

	public static void main(String[] args) {

		System.out.println("\n\n======================== MASTERMIND 2020 =========================\n\n" + "Raad binnen "
				+ seplRondes + " rondes de geheime code.\nDe code bestaat uit " + code.length + " symbolen."
				+ "\nKies uit een combinatie van de symbolen: " + String.valueOf(codeSymbolen)
				+ "\nElk symbool mag meerdere keren voorkomen in de code."
				+ "\n\nDe onderstaande tekens worden gebruikt als indicatoren:\n\n " + preciesGoedIndicator
				+ "  Symbool staat op de goede plek.\n " + bijnaGoedIndicator
				+ "  Symbool zit in de code maar staat nog niet op de goede plek\n " + nietGoedIndicator
				+ "  Symbool komt niet in de code voor" + "\n\nHoudt er rekening mee dat de indicatoren niet"
				+ "\nin een andere volgorde staan dan de letters die je hebt ingevoerd."
				+ "\n\n===================================================================\n");

		/*
		 * Onderstaande 5 regels zijn om mee te testen. Comment deze uit, en zet de
		 * methode codeGenerator() eronder aan als je klaar bent met testen.
		 */
		// code[0] = 'A'; // <-- Uitzetten na testen
		// code[1] = 'A'; // <-- Uitzetten na testen
		// code[2] = 'A'; // <-- Uitzetten na testen
		// code[3] = 'F'; // <-- Uitzetten na testen
		// System.out.println(" C: " + String.valueO (code)); // <-- Uitzetten na testen

		codeGenerator(); // <-- Aanzetten na testen

		System.out.println("Raad de code: \n");

		boolean eindeSpel = false;
		while (!eindeSpel) {

			char[] userInput = vraagInput(seplRondes);
			System.out.println("  |\t " + String.valueOf(vergelijk(userInput)));

			/* Game over: Winst */
			if (gameOverDoorWinnen(userInput)) {
				System.out.println("\nGEWONNEN!");
				System.out.println("\tJe hebt de geheime code geraden!");
				System.out.println("\tDe geheime code was: " + String.valueOf(code) + "\n");
				eindeSpel = true;
			}

			/* Game over: Maximum aantal rondes bereikt */
			if (seplRondes <= 1) {
				System.out.println("\nGAME OVER!");
				System.out.println("\tHet maximale aantal rondes is bereikt, je hebt de code niet gekraakt.");
				System.out.println("\tDe geheime code was: " + String.valueOf(code) + "\n");
				eindeSpel = true;
			}

			seplRondes--;
		}

	}

	static boolean gameOverDoorWinnen(char[] userInput) {
		return Arrays.equals(code, userInput);
	}

	static char[] vergelijk(char[] userInput) {

		char[] resultaat = new char[code.length];
		char[] codeClone = code.clone();
		char[] userInputClone = userInput.clone();

		/* Symbolen die op de goede plek staan. */
		for (int i = 0; i < code.length; i++) {
			if (userInputClone[i] == codeClone[i]) {
				userInputClone[i] = 'x';
				codeClone[i] = '*';
				resultaat[i] = preciesGoedIndicator;
			}
		}

		/*
		 * Symbolen die alleen voorkomen in de code, maar nog niet op de goede plek
		 * staan.
		 */
		for (int i = 0; i < code.length; i++) {
			for (int j = 0; j < code.length; j++) {
				if (codeClone[i] == userInputClone[j]) {
					resultaat[i] = bijnaGoedIndicator;
				}
			}
		}

		return sorteerResultaat(resultaat);
	}

	public static char[] sorteerResultaat(char[] inputArray) {
		char[] outputArray = new char[inputArray.length];
		/* outputArray vullen */
		for (int i = 0; i < outputArray.length; i++) {
			outputArray[i] = nietGoedIndicator;
		}
		int count = 0;

		/* preciesGoedSymbol achter elkaar sorteren */
		for (char ch : inputArray) {
			if (ch == preciesGoedIndicator) {
				outputArray[count] = preciesGoedIndicator;
				count++;
			}
		}

		/* bijnaGoedSymbol achter elkaar sorteren */
		for (char ch : inputArray) {
			if (ch == bijnaGoedIndicator) {
				outputArray[count] = bijnaGoedIndicator;
				count++;
			}

		}

		return outputArray;
	}

	static void codeGenerator() {
		Random random = new Random();
		for (int i = 0; i < code.length; i++) {
			int indexVanSymbool = (random.nextInt(1000) % codeSymbolen.length);
			code[i] = codeSymbolen[indexVanSymbool];
		}
	}

	static char[] vraagInput(int ronde) {
		Scanner scanner = new Scanner(System.in);
		boolean valideInput = false;

		while (!valideInput) {
			System.out.print(((ronde < 10) ? ("0" + ronde) : ronde) + "| ");
			String userInput = scanner.nextLine().toUpperCase();
			if (valideer(userInput) == true) {
				return userInput.toCharArray();
			}
		}

		return null;
	}

	static boolean valideer(String userInput) {

		Pattern regEx = Pattern.compile("[" + String.valueOf(codeSymbolen) + "]{" + code.length + "}");
		Matcher matcher = regEx.matcher(userInput.toUpperCase());

		if (matcher.matches()) {
			return true;
		}

		System.out.println("\nOh nee.. verkeerde invoer!\n\tVul een code in die bestaat uit "
				+ "een combinatie van 4 symbolen.\n\tJe kan kiezen uit deze symbolen: " + String.valueOf(codeSymbolen)
				+ "\n\tElk symbool mag meerdere keren voorkomen in de code.\n");

		return false;

	}

}
