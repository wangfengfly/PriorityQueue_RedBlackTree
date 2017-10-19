import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/*
 * Test class for Assignment
 */
public class Main {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SubmissionHistory history = new Assignment();
		history.add("aaaa1234", df.parse("2016/09/03 09:00:00"), 66); //submission A
		history.add("aaaa1234", df.parse("2016/09/03 16:00:00"), 86); //submission B
		history.add("cccc1234", df.parse("2016/09/03 16:00:00"), 73); //submission C
		history.add("aaaa1234", df.parse("2016/09/03 18:00:00"), 40); //submission D
		history.add("aaaa1234", df.parse("2016/09/03 18:30:00"), 90); //submission E
		// This will return an Integer corresponding to the number 86
		Integer example1 = history.getBestGrade("aaaa1234");
		System.out.println(example1);
		// This will return null
		Integer example2 = history.getBestGrade("zzzz1234");
		System.out.println(example2);
		
		// This will return a Submission corresponding to submission D
		Submission example4 = history.getSubmissionFinal("aaaa1234");
		System.out.println(example4.getTime());
		// This will return a Submission corresponding to submission C
		Submission example5 = history.getSubmissionFinal("cccc1234");
		System.out.println(example5.getTime());
		// This will return a Submission corresponding to submission A
		Submission example6 = history.getSubmissionBefore("aaaa1234", df.parse("2016/09/03 13:00:00"));
		System.out.println(example6.getTime());
		// This will return null
		Submission example7 = history.getSubmissionBefore("cccc1234", df.parse("2016/09/03 13:00:00"));
		System.out.println(example7);
		// This will return a list containing only {"aaaa1234"}
		// because that student¡¯s final submission had grade 40, but their best was 86
		List<String > example8 = history.listRegressions();
		System.out.println(example8);
		// This will return a list containing only {"aaaa1234"}
		// because that was the only student with the highest grade
		List<String > example9 = history.listTopStudents();
		System.out.println(example9.toString());
		
		Submission submissionB = new MySubmission("aaaa1234", df.parse("2016/09/03 16:00:00"), 86);
		Submission submissionE = new MySubmission("aaaa1234", df.parse("2016/09/03 18:30:00"), 90);
		history.remove(submissionB);
		history.remove(submissionE);
		System.out.println(history.listTopStudents());
		
	}

}
