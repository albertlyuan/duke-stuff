import java.io.File;

/**
 * Driver for Markov Model classes
 * @author ola
 * Modified for Fall 2020
 */

public class MarkovDriver {
	
	private static final int TEXT_SIZE = 144;
	
	public static void markovGenerate(MarkovInterface<?> markov, String text) {
		double start = System.nanoTime();
		for(int k=1; k <= 5; k++) {
			markov.setOrder(k);
			markov.resetRandom();
			markov.setTraining(text);
			String random = markov.getRandomText(TEXT_SIZE);
			System.out.printf("%d markov model with %d chars\n", k,random.length());
			printNicely(random,60);
		}
		double end = System.nanoTime();
		System.out.printf("total time = %2.3f\n", (end-start)/1e9);
	}
	
	public static void main(String[] args) {

		//TODO: Change the filename as desired to the different file you want as input.
		//String filename = "data/trump-sou17.txt";
		//String filename = "data/bush-sou07.txt";
		String filename = "data/trumptweets2020.txt";

		if (args.length > 0) {
			filename = args[1];
		}
		
		File f = new File(filename);
		String text = TextSource.textFromFile(f);

		//TODO: Make sure that you uncomment the MarkovInterface you want to use.

		// only one line below should be uncommented
		MarkovInterface<String> standard = new BaseMarkov();
		MarkovInterface<String> efficient = new EfficientMarkov();
		MarkovInterface<WordGram> wmm = new BaseWordMarkov();
		MarkovInterface<WordGram> ewm = new EfficientWordMarkov();

		//TODO: Make sure that you use the uncommented Interface up there.

		// first parameter is one of the MarkovInterface variables
		markovGenerate(wmm,text);
	}

	private static void printNicely(String random, int screenWidth) {
		String[] words = random.split("\\s+");
		int psize = 0;
		System.out.println("----------------------------------");
		for(int k=0; k < words.length; k++){
			System.out.print(words[k]+ " ");
			psize += words[k].length() + 1;
			if (psize > screenWidth) {
				System.out.println();
				psize = 0;
			}
		}
		System.out.println("\n----------------------------------");
	}
}
