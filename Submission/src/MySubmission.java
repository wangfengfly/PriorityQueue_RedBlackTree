import java.util.Date;

public class MySubmission implements Submission{
	
	private String uniKey;
	private Date time;
	private Integer grade;
	
	public MySubmission(String unikey, Date time, Integer grade){
		this.uniKey = unikey;
		this.time = time;
		this.grade = grade;
	}
	
	/**
	* @return the unikey (a String of the form "abcd1234")
	*/
	public String getUnikey(){
		return uniKey;
	}
	
	/**
	* @return a Date object representing the time the submission was made
	*/
	public Date getTime(){
		return time;
	}
	
	/**
	* @return an integer grade
	*/
	public Integer getGrade(){
		return grade;
	}
	
	/*
	 * customed equals method
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o){
		MySubmission o2 = (MySubmission)o;
		if(o2.getUnikey().equals(uniKey) && o2.getTime().equals(time)){
			return true;
		}
		
		return false;
	}

}
