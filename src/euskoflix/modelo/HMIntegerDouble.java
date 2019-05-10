package euskoflix.modelo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HMIntegerDouble {

	//ATRIBUTOS
	private HashMap<Integer, HashMap<Integer,Double>> hashmap;
	
	//CONSTRUCTORA
	public HMIntegerDouble() {
		hashmap=new HashMap<Integer, HashMap<Integer,Double>>();
	}
	
	public HashMap<Integer, HashMap<Integer,Double>> getHashMap() {
		return hashmap;
	}
	
	public void print() {
		 for (Map.Entry<?, ?> entry : hashmap.entrySet()) {
		    	System.out.println(entry.getKey()+" "+(HashMap<Integer, Double>) entry.getValue());
		    }
	}
	
	public boolean containsKey(Integer pId) {
		return hashmap.containsKey(pId);
	}
	
	public HashMap<Integer,Double> get(Integer pId) {
		return hashmap.get(pId);
	}
	
	public void put(Integer pId, HashMap<Integer, Double> pHM) {
		hashmap.put(pId, pHM);
	}

	public Set<Entry<Integer, HashMap<Integer, Double>>> entrySet() {
		return hashmap.entrySet();
	}

	public Integer size() {
		return hashmap.size();
	}
	
	public void hashmap2txt(String pDestino) throws IOException {
		BufferedWriter bw=new BufferedWriter(new FileWriter(pDestino));
		for (Map.Entry<?, ?> entry : hashmap.entrySet()) {
			bw.write(entry.getKey() + " "+ (HashMap<Integer, Double>) entry.getValue());
			bw.newLine();	
	    }
		bw.flush();
		bw.close();
	}
}
