
/**
 * A WordGram represents a sequence of strings
 * just as a String represents a sequence of characters
 * 
 * @author YOUR NAME HERE
 *
 */
public class WordGram {
	
	private String[] myWords;   
	private String myToString;  // cached string
	private int myHash;         // cached hash value

	/**
	 * Create WordGram by creating instance variable myWords and copying
	 * size strings from source starting at index start
	 * @param source is array of strings from which copying occurs
	 * @param start starting index in source for strings to be copied
	 * @param size the number of strings copied
	 */
	public WordGram(String[] source, int start, int size) {
		myWords = new String[size];
		myToString = ""; // Leave this alone, you'll change myToString in toString
		myHash = 0; //Leave this alone, you'll change myHash in hashCode

		// TODO: Initialize myWords
		for (Integer i = 0; i < size; i++){
			myWords[i] = source[i+start];
		}
	}

	/**
	 * Return string at specific index in this WordGram
	 * @param index in range [0..length() ) for string 
	 * @return string at index
	 */
	public String wordAt(int index) {
		if (index < 0 || index >= myWords.length) {
			throw new IndexOutOfBoundsException("bad index in wordAt "+index);
		}
		return myWords[index];
	}

	@Override
	public String toString(){
		// TODO: Complete this method, assign to myToString as needed
		if (myToString.equals("")){
			myToString = String.join(" ", myWords);
		}
		return myToString;
	}

	@Override
	public int hashCode(){
		// TODO: complete this method: assign to myHash as needed
		if(myHash == 0){
			myHash = this.toString().hashCode();
		}
		return myHash;
	}

	@Override
	public boolean equals(Object o) {
		if (! (o instanceof WordGram) || o == null){
			return false;
		}
		// TODO: Complete this method
		WordGram other = (WordGram) o;
		if (other.myWords.length != this.myWords.length){
			return false;
		}
		for (Integer i = 0; i < this.myWords.length; i++){
			if (! other.myWords[i].equals(this.myWords[i])){
				return false;
			}
		}
		return true;
	}

	public int length(){
		// TODO: change this
		return myWords.length;
	}

	public WordGram shiftAdd(String last) {
		// TODO: Complete this method
		String [] temp = new String[myWords.length];
		for (Integer i = 1; i < myWords.length; i++ ){
			temp[i-1] = myWords[i];
		}
		temp[myWords.length-1] = last;
		WordGram wg = new WordGram(temp,0,myWords.length);
		return wg;
	}
}
