import java.util.*;

public class EfficientMarkov extends BaseMarkov {
	private Map<String,ArrayList<String>> myMap;
	
	public EfficientMarkov(){
		this(3);
	}

	public EfficientMarkov(int order) {
		super(order);
		myMap = new HashMap<>();
	}

	@Override
	public void setTraining(String text) {
		super.setTraining(text);
		//TODO: Clear and initialize myMap
		myMap.clear();
		for(Integer i=0; i<=text.length()-myOrder; i++){
			String k = myText.substring(i, i+myOrder);
			myMap.putIfAbsent(k, new ArrayList<>());
			if (i+myOrder < text.length()){
				String nextLetter = myText.substring(i+myOrder, i+myOrder+1);
				myMap.get(k).add(nextLetter);
			}else{
				myMap.get(k).add(PSEUDO_EOS);
			}
		}
	}

	//TODO: Complete this method
	@Override
	public ArrayList<String> getFollows(String key) {
		if (myMap.get(key) == null) {
			throw new NoSuchElementException(key + " not in map");
		}
		return myMap.get(key);
	}

}	
