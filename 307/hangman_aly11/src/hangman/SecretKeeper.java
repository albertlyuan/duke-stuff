package hangman;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.TextInputDialog;
import util.DisplayWord;
import util.HangmanDictionary;

public class SecretKeeper {
    private String mySecretWord;
    private String SecretKeeperType;
    private List<String> possibleWords;
    private DisplayWord myDisplayWord;

    public SecretKeeper(HangmanDictionary dictionary, int wordLength, String type){
        SecretKeeperType = type;
        possibleWords = dictionary.getWords(wordLength);
        switch (SecretKeeperType){
            case "Interactive":
                mySecretWord = SimpleSecretKeeper(String.format("Please enter a secret word %d letters long", wordLength), wordLength);
                break;
            case "Simple":
            case "Clever":
                mySecretWord = dictionary.getRandomWord(wordLength).toLowerCase();
                break;
        }
        System.out.println("*** " + mySecretWord);
    }

    private void updateSecretWord(String guess){
        /**
         * When secretkeeper = CLEVER, changes the secret word so that the guessed letter
         * is used to maximize the possible words that could be the answer
         */
        HashMap<String, ArrayList<String>> templates = generateAllTemplates(guess);

        String mostCommonTemplate = GetMostCommonTemplate(templates);

        possibleWords = templates.get(mostCommonTemplate);
        //picks random word out of possibleWords
        String newSecretWord = possibleWords.get((int)Math.random() * possibleWords.size());
        System.out.println("*** " + mySecretWord);
        mySecretWord = newSecretWord;
    }

    private String GetMostCommonTemplate(HashMap<String, ArrayList<String>> templates) {
        /**
         * Finds the template (ie _e__e_) that has the most possible words
         * returns the template
         */
        String displayTemplate="";
        int numPossibleWords = 0;
        for (String template : templates.keySet()){
            if (numPossibleWords < templates.get(template).size()){
                displayTemplate = template;
                numPossibleWords = templates.get(template).size();
            }
        }
        //System.out.println(displayTemplate);
        //System.out.println(numPossibleWords);
        return displayTemplate;
    }

    private HashMap<String, ArrayList<String>> generateAllTemplates(String guess) {
        /**
         * takes the guessed letter and current display word and generates all possible templates (ie _ee__)
         * given the possible words left
         * returns a hashmap with templates(str) as keys and an arraylist of the words it can represent
         */
        HashMap<String,ArrayList<String>> templates = new HashMap<>();
        String currLetters = myDisplayWord.toString().replaceAll(" ","");
        for (String word : possibleWords){
            String newtemplate = generateTemplate(currLetters, word, guess);
            if (!templates.containsKey(newtemplate)){
                templates.put(newtemplate,new ArrayList());
            }
            templates.get(newtemplate).add(word);
        }
        return templates;
    }

    private String generateTemplate(String currLetters, String word, String guess) {
        /**
         * given the current template, possible word, and guessed letter, generates a template
         * returns the template as a string
         */
        String[] word_as_list = word.split("");
        String[] currLetters_as_list = currLetters.split("");
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<word_as_list.length;i++){
            if (word_as_list[i].equals(guess)){
                sb.append(guess);
            } else{
                sb.append(currLetters_as_list[i]);
            }
        }
        String returnTemplate = sb.toString();
        return returnTemplate;

    }

    public boolean CheckHit(String guess) {
        /**
         * returns true or false based on if the guessed letter is in the secret word
         * for CLEVER secretkeeper, the secretword is first changed before the guessed letter is evaluated
         */
        if (SecretKeeperType == "Clever"){
            updateSecretWord(guess);
        }
        if (! mySecretWord.contains(guess)) {
            return false;
        }
        return true;
    }
    public DisplayWord updateDisplay(char guess){
        /**
         * returns an updated displayword after a guess has been made
         */
        myDisplayWord.update(guess,mySecretWord);
        return myDisplayWord;
    }
    public boolean checkDisplay(){
        /**
         * checks displayword to see if the game is over
         */
        return myDisplayWord.equals(mySecretWord);
    }
    public DisplayWord createDisplay(){
        /**
         * creates the displayword when the game is initiated
         */
        myDisplayWord = new DisplayWord(mySecretWord);
        return myDisplayWord;
    }
    private String SimpleSecretKeeper (String prompt, int numCharacters) {
        /**
         * prompts the user to input a word to be guessed for INTERACTIVE secretkeeper
         * returns the word as a string
         */
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText(prompt);
        // DO NOT USE IN GENERAL - this is a TERRIBLE solution from UX design, and we will better ways later
        Optional<String> answer = dialog.showAndWait();
        while (answer.isEmpty() || answer.get().length() != numCharacters) {
            answer = dialog.showAndWait();
        }
        return answer.get();
    }
}
