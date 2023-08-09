import java.util.*;

public class EfficientWordMarkov extends BaseWordMarkov {
    private Map<WordGram,ArrayList<String>> myMap;

    public EfficientWordMarkov() {
        this(2);
    }

    public EfficientWordMarkov(int order){
        myOrder = order;
        myRandom = new Random(RANDOM_SEED);
        myMap = new HashMap<>();
    }

    @Override
    public void setTraining(String text) {
        super.setTraining(text);
        //TODO: Clear and initialize myMap
        myMap.clear();

        for(Integer i=0; i<=myWords.length-myOrder; i++){
            WordGram k = new WordGram(myWords, i, myOrder);
            myMap.putIfAbsent(k, new ArrayList<>());
            if (i+myOrder < myWords.length){
                String nextWord = myWords[i+myOrder];
                myMap.get(k).add(nextWord);
            }else{
                myMap.get(k).add(PSEUDO_EOS);
            }
        }
    }

    //TODO: Complete this method
    @Override
    public ArrayList<String> getFollows(WordGram kGram) {
        if (myMap.get(kGram) == null) {
            throw new NoSuchElementException(kGram + " not in map");
        }
        return myMap.get(kGram);
    }

}
