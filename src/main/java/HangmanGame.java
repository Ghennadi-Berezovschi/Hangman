
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HangmanGame {

    public static Random random = new Random();
    private static Scanner scanner = new Scanner(System.in);
    private static String WORD_TO_GUESS;
    private static char[] guessedWord;
    private static int attemptsLeft;
    private static int MAX_ATTEMPTS = 6;

    public static void main(String[] args) {
        initializeGame();
        playGame();
    }

    public static ArrayList<String> createWords() {
        ArrayList<String> words = new ArrayList<>();
        words.add("Java");
        words.add("Computer");
        words.add("Developer");
        words.add("Hangman");
        return words;
    }

    public static String chooseRandomWord() {
        ArrayList<String> words = createWords();
        int index = random.nextInt(words.size());
        return words.get(index).toUpperCase();

    }

    public static void initializeGame() {
        WORD_TO_GUESS = chooseRandomWord();
        guessedWord = new char[WORD_TO_GUESS.length()];
        for (int i = 0; i < WORD_TO_GUESS.length(); i++) {
            guessedWord[i] = '*';
        }
        attemptsLeft = MAX_ATTEMPTS;

    }

    public static void displayCurrentState() {
        System.out.println("Current state: " + new String(guessedWord));
        System.out.println(getHangmanStage( MAX_ATTEMPTS - attemptsLeft));
    }

    public static String getUserInput() {
        if (scanner.hasNext()) {
            return scanner.nextLine().toUpperCase();
        }
        return "";
    }

    public static boolean isWordGuessed() {
        for (char c : guessedWord) {
            if (c == '*')
                return false;
        }
        return true;
    }

    public static void playGame() {
        while (attemptsLeft > 0 && !isWordGuessed()) {
            displayCurrentState();
            System.out.println("Enter an english letter: ");
            String userInput = getUserInput();
            if (userInput.isEmpty() || userInput.length() != 1 || !Character.isLetter(userInput.charAt(0))) {
                System.out.println("Your input is wrong. Please enter a proper letter: ");
                continue;
            }
            char guess = userInput.charAt(0);
            processGuess(guess);
        }
        endGame();

    }

    public static void processGuess(char guess) {
        boolean correctGuess = false;
        for (int i = 0; i < WORD_TO_GUESS.length(); i++) {
            if (WORD_TO_GUESS.charAt(i) == guess) {
                guessedWord[i] = guess;
                correctGuess = true;
            }
        }
        if (correctGuess) {
            System.out.println("Good guess!!!");
        } else {
            attemptsLeft--;
            System.out.println("Incorrect guess. Attempts left: " + attemptsLeft);
        }
    }

    public static void endGame() {
        if (isWordGuessed()) {
            System.out.println("Congratulation!!! You won!!!");
        } else {
            System.out.println("Game over. Your guess was incorrect.The word was: " + WORD_TO_GUESS);
            System.out.println(getHangmanStage(MAX_ATTEMPTS - attemptsLeft));
        }
    }

    public static String getHangmanStage(int stage) {

        String[] HANGMAN_STAGES = {
                """
      +---+
          |
          |
          |
         ===
    """,
                """
      +---+
      O   |
          |
          |
         ===
    """,
                """
      +---+
      O   |
      |   |
          |
         ===
    """,
                """
      +---+
      O   |
     /|   |
          |
         ===
    """,
                """
      +---+
      O   |
     /|\\  |
          |
         ===
    """,
                """
      +---+
      O   |
     /|\\  |
     /    |
         ===
    """,
                """
      +---+
      O   |
     /|\\  |
     / \\  |
         ===
    """
        };
        return HANGMAN_STAGES[stage];
    }
}

