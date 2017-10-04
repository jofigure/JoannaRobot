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
				String word = s.substring(psn - negations[i].length() - 1, psn);
				if(word.compareTo(negations[i] + " ") == 0)
					return false;
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
		int cutoff = 55;
		//this while loop last as long as there are words left in the original String
		while(s.length() > 0){

			String currentCut = "";
			String nextWord = "";

			//while the current cut is still less than the line length 
			//AND there are still words left to add
			while(currentCut.length()+nextWord.length() < cutoff && s.length() > 0){

				//add the next word
				currentCut += nextWord;

				//remove the word that was added from the original String
				s = s.substring(nextWord.length());

				//identify the following word, exclude the space
				int endOfWord = s.indexOf(" ");

				//if there are no more spaces, this is the last word, so add the whole thing
				if(endOfWord == -1) {
					endOfWord = s.length()-1;//subtract 1 because index of last letter is one les than length
				}

				//the next word should include the space
				nextWord = s.substring(0,endOfWord+1);
			}

			printString +=currentCut+"\n";

		}
		System.out.print(printString);
	}

	public static int getPositiveNumInput() {
		print("Please enter a number.");
		String numString = getInput();
		boolean isNum = false;
		int value = 0;
		while(!isNum)
			try {
				value = Integer.parseInt(numString);
				if(value > 0)
					isNum = true;
				else {
					print("You must enter a number greater than 0. Please try again.");
					numString = getInput();
				}
			} catch(NumberFormatException e) {
				print("You must enter a number. Please try again.");
				numString = getInput();
			}
		return value;
	}
	
	public static boolean YesNo(String question) {
		print(question);
		String[] responses = {question + " Yes or no?", "Can you please just answer my question?", "It's a simple yes or no question. " + question,"I'm getting annoyed; this isn't even a hard question. " + question.toUpperCase(), "You know what? Go find somewhere else to buy a house. I am tired and you are not answering my question, goodbye!"};
		int notAnswering = 0;
		String response = getInput();
		boolean YesOrNo = false;
		while(!YesOrNo)
			if(findKeyword(response, "yes", 0) >= 0) {
				return true;
			} else if(findKeyword(response, "no", 0) >= 0) {
				return false;
			} else {
				print(responses[notAnswering]);
				if(notAnswering < 4) {
					response = getInput();
					notAnswering++;
				} else {
					System.exit(0);			
				}
			}
		return false;
	}
	

}
