package hangman;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import util.DisplayWord;
import util.HangmanDictionary;

public class Guesser {

    private String guesserType;
    // for simple
    private int myIndex=0;
    private static final String LETTERS_ORDERED_BY_FREQUENCY = "etaoinshrldcumfpgwybvkxjqz";
    //for clever
    private HashMap<String,ArrayList<String>> letterDict = new HashMap<>();
    private List<String> possibleWords;
    private ArrayList<String> guessedLetters = new ArrayList<>();


    public Guesser(HangmanDictionary dictionary, int wordLength,String type){
        guesserType = type;
        if (guesserType == "Clever") {
            possibleWords = dictionary.getWords(wordLength);
            initLetterDict();
        }
    }

    private void initLetterDict() {
        /**
         * creates a HashMap with letters as keys and an arraylist of words with the letters
         * based on the words remaining in possibleWords
         */
        letterDict.clear();
        for (String word: possibleWords.toArray(new String[0])){
            for(String letter : word.split("")){
                if (!letterDict.containsKey(letter)){
                    letterDict.put(letter, new ArrayList<>());
                }
                letterDict.get(letter).add(word);
            }
        }

    }

    /**
     * called everytime to guess a letter
     *
     */
    public String getGuess(){
        /**
         * returns the guessed letter based on the type of guesser
         */
        switch (guesserType) {
            case "Interactive":
                return "";
            case "Simple":
                return "" + LETTERS_ORDERED_BY_FREQUENCY.charAt(myIndex++);
            case "Clever":
                return findBestGuess();
        }
        return "";
    }

    private String findBestGuess() {
        /**
         * for CLEVER guesser
         * finds the letter that has the highest probability of being a correct guess
         * based on how many words have that letter
         */
        String mostCommon = "";
        int maxFreq = 0;
        for (String letter : letterDict.keySet()) {
            if (guessedLetters.contains(letter)){
                continue;
            }
            if (letterDict.get(letter).size() > maxFreq) {
                mostCommon = letter;
                maxFreq = letterDict.get(letter).size();
            }
        }
        //System.out.println(mostCommon);
        guessedLetters.add(mostCommon);
        return mostCommon;
    }

    public void updateLetterDict(StringBuilder lettersLeft, DisplayWord myDisplayWord){
        /**
         * takes the current displayword and removes words from possibleWords based on if the guess was correct
         * IF correct: words that don't match the new displayword pattern are removed
         * IF incorrect: words with the guessed letter are removed
         * calls initLetterDict to create a new hashmap based on the updated possibleWords
         */
        String currLetters = myDisplayWord.toString().replaceAll(" ","");
        String lastGuess = guessedLetters.get(guessedLetters.size()-1);
        boolean hit = currLetters.contains(lastGuess);
        //System.out.println(currLetters);
        //System.out.println("possibleword count------------");
        //System.out.println(possibleWords.size());
        if (hit){
            possibleWords.removeIf(word -> !compareToDisplay(word.split(""), currLetters.split("")));
        }else{
            possibleWords.removeIf(word -> word.contains(lastGuess));
        }
        System.out.println(possibleWords.size());
        initLetterDict();
    }
    private boolean compareToDisplay(String[] word, String[] template){
        /**
         * return false if the word doesn't match the template
         */

        for (int i=0; i<word.length; i++){
            if (template[i].equals("_")){
                if (guessedLetters.contains(word[i])) {
                    return false;
                }
            }else{
                if (!template[i].equals(word[i])) {
                    return false;
                }
            }
        }
        return true;
    }
}
