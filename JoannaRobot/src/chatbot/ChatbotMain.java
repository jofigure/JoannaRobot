package chatbot;

import java.util.Scanner;

public class ChatbotMain {

	public static final Chatbot chatbot = new Chatbot();
	private static Scanner inputSource = new Scanner(System.in);
	
	public static void main(String[] args) {
		chatbot.startChatting();
	}
		
	public static int findKeyword(String searchString, String keyword, int startPsn) {
		searchString = searchString.toLowerCase();
		keyword = keyword.toLowerCase();
		int psn = searchString.indexOf(keyword, startPsn);
		while(psn >= 0)
			if(keywordIsIsolated(psn, keyword, searchString) && noNegations(searchString, psn))
				return psn;
			else
				psn = searchString.indexOf(keyword, psn + 1);
		return -1;
	}

	public static boolean keywordIsIsolated(int psn, String keyword, String s){
		if(keyword.length() == s.length())
			return true;
		else if(psn == 0) {
			String charAfter = s.substring(psn + keyword.length(), psn + keyword.length() + 1);
			return charAfter.compareTo("a") < 0;
		} else if(psn + keyword.length() == s.length()) {
			String charBefore = s.substring(psn - 1, psn);
			return charBefore.compareTo("a") < 0;
		} else {
			String charBefore = s.substring(psn - 1, psn);
			String charAfter = s.substring(psn + keyword.length(), psn + keyword.length() + 1);
			return charAfter.compareTo("a") < 0 && charBefore.compareTo("a") < 0;
		}
	}

	public static boolean noNegations(String s, int psn){
		String[] negations = {"no", "not"};
		if(psn == 0)
			return true;
		else {
			for(int i = 0; i < negations.length; i++) {
				if(psn - negations[i].length() - 1 >= 0) {
					String word = s.substring(psn - negations[i].length() - 1, psn);
					if(word.compareTo(negations[i] + " ") == 0)
						return false;
				}
			}
			return true;
		}
	}

	public static String getInput(){
		return inputSource.nextLine();
	}

	public static void print(String s){
		multiLinePrint(s);
	}

	public static void multiLinePrint(String s){
		String printString = "";
		int cutoff = 60;
		while(s.length() > 0){
			String currentCut = "";
			String nextWord = "";
			while(currentCut.length()+nextWord.length() < cutoff && s.length() > 0){
				currentCut += nextWord;
				s = s.substring(nextWord.length());
				int endOfWord = s.indexOf(" ");
				if(endOfWord == -1) {
					endOfWord = s.length()-1;
				}
				nextWord = s.substring(0,endOfWord+1);
			}
			printString +=currentCut+"\n";
		}
		System.out.print(printString);
	}

	public static int getPositiveNumInput() {
		return ChatbotAnnie.getPositiveNumInput();
	}

	public static boolean YesNo(String question) {
		return ChatbotAnnie.YesNo(question);
	}

}
