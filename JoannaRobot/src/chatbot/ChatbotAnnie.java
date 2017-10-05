package chatbot;

public class ChatbotAnnie implements Topic {
	
	private String[] keywords;
	private String[] moreKeywords;
	private String goodbyeKeyword;
	private String response;
	private static int annoyedLevel;

	public ChatbotAnnie() {
		String[] temp = {"apt", "apartment", "city", "flat", "condo", "condominium", "co-op"};
		keywords = temp;
		String[] temp2 = {"listings", "list", "properties"};
		moreKeywords = temp2;
		goodbyeKeyword = "bye";
		response = "";
	}

	public void talk(String initial) {
		if(ChatbotMain.chatbot.getForLiving()) {
			ChatbotMain.print("It looks like you may be in the market for an apartment. Am I correct?");
			response = ChatbotMain.getInput();
			if(ChatbotMain.findKeyword(response, "yes", 0) >= 0) {
				while(ChatbotMain.findKeyword(response, goodbyeKeyword, 0) == -1) {
					boolean keywordFound = false;
					for(int i = 0; i < moreKeywords.length; i++)
						if(ChatbotMain.findKeyword(response, moreKeywords[i], 0) >= 0) {
							keywordFound = true;
							list();
						}
					if(!keywordFound) {
						ChatbotMain.print("I don't understand.");
						response = ChatbotMain.getInput();
					}
				}
				ChatbotMain.print("Well, it was nice talking to you, " + ChatbotMain.chatbot.getUsername() + "!");
				ChatbotMain.chatbot.startChatting();
			}
		} else {
			ChatbotMain.print("I thought you said you weren't looking for a residence. Would you like to update your information?");
			response = ChatbotMain.getInput();
			if(ChatbotMain.findKeyword(response, "yes", 0) >= 0)
				if(!ChatbotMain.chatbot.getForLiving())
					ChatbotMain.chatbot.reEvaluate();
		}
		ChatbotMain.chatbot.throwBack(this);
	}

	public boolean isTriggered(String response) {
		for(int i = 0; i < keywords.length; i++)
			if(ChatbotMain.findKeyword(response, keywords[i], 0) >= 0)
				return true;
		if(ChatbotMain.chatbot.getFamilySize() < 4 && !ChatbotMain.chatbot.getHasPets())
			return true;
		return false;
	}
	
	public void list() {
		 
	}
	
	public static int getPositiveNumInput() {
		ChatbotMain.print("Please enter a number.");
		String numString = ChatbotMain.getInput();
		boolean isNum = false;
		int value = 0;
		while(!isNum)
			try {
				value = Integer.parseInt(numString);
				if(value > 0)
					isNum = true;
				else {
					ChatbotMain.print("You must enter a number greater than 0. Please try again.");
					numString = ChatbotMain.getInput();
				}
			} catch(NumberFormatException e) {
				ChatbotMain.print("You must enter a number. Please try again.");
				numString = ChatbotMain.getInput();
			}
		return value;
	}
	
	public static boolean YesNo(String question) {
		annoyedLevel = 0;
		ChatbotMain.print(question);
		String[] responses = {question + " Yes or no?", "Can you please just answer my question?", "It's a simple yes or no question. " + question, "I'm getting annoyed; this isn't even a hard question. " + question.toUpperCase(), "You know what? Go find somewhere else to buy a house. I am tired and you are not answering my question, goodbye!"};
		String response = ChatbotMain.getInput();
		boolean yesOrNo = false;
		while(!yesOrNo)
			if(ChatbotMain.findKeyword(response, "yes", 0) >= 0 || ChatbotMain.findKeyword(response, "no", 0) >= 0) {
				if(annoyedLevel > 0)
					annoyedLevel--;
				if(ChatbotMain.findKeyword(response, "yes", 0) >= 0)
					return true;
				else if(ChatbotMain.findKeyword(response, "no", 0) >= 0)
					return false;
				yesOrNo = true;
			} else {
				ChatbotMain.print(responses[annoyedLevel]);
				if(annoyedLevel < 4) {
					response = ChatbotMain.getInput();
					annoyedLevel++;
				} else
					System.exit(0);
			}
		return false;
	}

 
}
