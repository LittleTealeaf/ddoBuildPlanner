package classes;

public class DDOUtil {
	
	public static int getMod(int statScore) {
//		int score = statScore;
//		if(score%2 == 1) score--;
//		score /= 2;
//		score-=5;
//		return score;
		return ((int) (statScore / 2) - 5);
	}
}
