import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Assignment implements SubmissionHistory {
	//map for unikey to grade
	private HashMap<String, PriorityQueue<MySubmission>> gradeMap;
	//map for unikey to submission time
	private HashMap<String, TreeMap<Date, MySubmission>> timeMap;
	
	
	/**
	* Default constructor
	*/
	public Assignment() {
		gradeMap = new HashMap<String, PriorityQueue<MySubmission>>();
		timeMap = new HashMap<String, TreeMap<Date, MySubmission>>();
	}
	
	@Override
	public Integer getBestGrade(String unikey) {
		if(unikey == null){
			throw new IllegalArgumentException();
		}
		PriorityQueue<MySubmission> priorityQueue = gradeMap.get(unikey);
		if(priorityQueue==null){
			return null;
		}
		MySubmission mySubmission = priorityQueue.peek();
		if(mySubmission==null){
			return null;
		}
		return mySubmission.getGrade();
	}
	
	@Override
	public Submission getSubmissionFinal(String unikey) {
	// TODO Implement this , ideally in better than O(n)
		if(unikey==null){
			throw new IllegalArgumentException();
		}
		TreeMap<Date, MySubmission> treeMap = timeMap.get(unikey);
		if(treeMap==null){
			return null;
		}
		Entry<Date, MySubmission> entry = treeMap.lastEntry();
		return entry.getValue();
	}
	
	@Override
	public Submission getSubmissionBefore(String unikey , Date deadline) {
	// TODO Implement this , ideally in better than O(n)
		if(unikey==null){
			throw new IllegalArgumentException();
		}
		TreeMap<Date, MySubmission> treeMap = timeMap.get(unikey);
		if(treeMap==null){
			return null;
		}
		Entry<Date, MySubmission> entry = treeMap.lowerEntry(deadline);
		if(entry==null){
			return null;
		}
		return entry.getValue();
	}
	
	@Override
	public Submission add(String unikey , Date timestamp , Integer grade) {
		if(unikey==null){
			throw new IllegalArgumentException();
		}
		//add to grademap
		PriorityQueue<MySubmission> priorityQueue = gradeMap.get(unikey);
		MySubmission mySubmission = new MySubmission(unikey, timestamp, grade);
		if(priorityQueue == null){
			Comparator<MySubmission> cmp;
	        cmp = new Comparator<MySubmission>(){
	            public int compare(MySubmission e1,MySubmission e2)
	            {
	                return e2.getGrade() - e1.getGrade();
	            }
	        };
			priorityQueue = new PriorityQueue<MySubmission>(cmp);
			priorityQueue.add(mySubmission);
			gradeMap.put(unikey, priorityQueue);
		}else{
			priorityQueue.add(mySubmission);
		}
		//add to timemap
		TreeMap<Date, MySubmission> treeMap = timeMap.get(unikey);
		if(treeMap==null){		
			treeMap = new TreeMap<Date, MySubmission>();
			treeMap.put(timestamp, mySubmission);
			timeMap.put(unikey, treeMap);
		}else{
			treeMap.put(timestamp, mySubmission);
		}
		
		return mySubmission;
	}
	
	@Override
	public void remove(Submission submission) {
		// TODO Implement this , ideally in better than O(n)
		if(submission==null){
			throw new IllegalArgumentException();
		}
		String unikey = submission.getUnikey();
		if(unikey==null){
			return;
		}
		//remove from grademap 
		PriorityQueue<MySubmission> priorityQueue = gradeMap.get(unikey);
		priorityQueue.remove(submission);
		
		//remove from timemap
		TreeMap<Date, MySubmission> treeMap = timeMap.get(unikey);
		treeMap.remove(submission.getTime(), submission);
	}
	
	@Override
	public List<String> listTopStudents() {
	// TODO Implement this , ideally in better than O(n)
	// (you may ignore the length of the list in the analysis)
		List<String> unikeys = new ArrayList<String>();
		Iterator it = gradeMap.entrySet().iterator();
		Integer maxGrade = Integer.MIN_VALUE;
		PriorityQueue<MySubmission> priorityQueue;
	    while (it.hasNext()) {
	        Entry pair = (Entry)it.next();   
	        priorityQueue = (PriorityQueue<MySubmission>) pair.getValue();
	        if(priorityQueue!=null){
	        	MySubmission mySubmission = priorityQueue.peek();
	        	if(mySubmission.getGrade() > maxGrade){
	        		maxGrade = mySubmission.getGrade();
	        	}
	        }	        
	    }
	    
	    it = gradeMap.entrySet().iterator();
	    while(it.hasNext()){
	    	Entry pair = (Entry)it.next();   
	        priorityQueue = (PriorityQueue<MySubmission>) pair.getValue();
	        if(priorityQueue!=null){
	        	MySubmission mySubmission = priorityQueue.peek();
	        	if(mySubmission.getGrade() == maxGrade){
	        		unikeys.add(mySubmission.getUnikey());
	        	}
	        }
	    }
	    
	    return unikeys;
	}

	@Override
	public List<String > listRegressions() {
	// TODO Implement this , ideally in better than O(n^2)
		List<String> unikeys = new ArrayList<String>();
		Iterator it = gradeMap.entrySet().iterator();
		while(it.hasNext()){
			Entry pair = (Entry)it.next();
			PriorityQueue<MySubmission> priorityQueue = (PriorityQueue<MySubmission>) pair.getValue();
			if(priorityQueue!=null){
				MySubmission mySubmission = priorityQueue.peek();
				String unikey = mySubmission.getUnikey();
				TreeMap<Date, MySubmission> treeMap = timeMap.get(unikey);
				Entry<Date, MySubmission> trEntry = treeMap.lastEntry();
				if(trEntry.getValue().getGrade() < mySubmission.getGrade()){
					unikeys.add(unikey);
				}
			}
		}
		return unikeys;
	}
}