package euskoflix.modelo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class HMStringDouble {

	//ATRIBUTOS
	private HashMap<Integer, HashMap<String,Double>> hashmap;
	
	//CONSTRUCTORA
	public HMStringDouble() {
		hashmap=new HashMap<Integer, HashMap<String,Double>>();
	}
	
	public HashMap<Integer, HashMap<String,Double>> getHashMap() {
		return hashmap;
	}
	
	public void print() {
		 for (Map.Entry<?, ?> entry : hashmap.entrySet()) {
		    	System.out.println(entry.getKey()+" "+(HashMap<String, Double>) entry.getValue());
		    }
	}
	
	public boolean containsKey(Integer pId) {
		return hashmap.containsKey(pId);
	}
	
	public HashMap<String,Double> get(Integer pId) {
		return hashmap.get(pId);
	}
	
	public void put(Integer pId, HashMap<String, Double> pHM) {
		hashmap.put(pId, pHM);
	}

	public Set<Entry<Integer, HashMap<String, Double>>> entrySet() {
		return hashmap.entrySet();
	}
}
