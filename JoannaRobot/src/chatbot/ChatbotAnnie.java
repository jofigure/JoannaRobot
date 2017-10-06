package chatbot;

public class ChatbotAnnie implements Topic {
	
	private String[] keywords;
	private String[] listings;
	private String[] listingsInfo;
	private boolean[] onSale;
	private int choice;
	private static int annoyedLevel;
	private String goodbyeKeyword;
	private String response;

	public ChatbotAnnie() {
		String[] temp = {"apt", "apartment", "city", "flat", "condo", "condominium", "co-op"};
		keywords = temp;
		String[] temp2 = {"studio apartment", "one-bedroom condominium", "three-bedroom duplex", "two-bedroom loft"};
		listings = temp2;
		String[] temp3 = {"stuff"};
		listingsInfo = temp3;
		boolean[] temp4 = {true, true, false, false};
		onSale = temp4;
		choice = -1;
		annoyedLevel = 0;
		goodbyeKeyword = "bye";
		response = "";
	}

	public void talk(String initial) {
		if(ChatbotMain.chatbot.getForLiving()) {
			ChatbotMain.print("It looks like you may be in the market for an apartment. Am I correct?");
			boolean lookingApt = YesNo("Are you looking for apartments?");
			if(lookingApt) {
				while(ChatbotMain.findKeyword(response, goodbyeKeyword, 0) == -1) {
					ChatbotMain.print("Here are some listings that might be of interest to you: ");
					list();
					ChatbotMain.print("Which one would you like to know more about?");
					response = ChatbotMain.getInput();
					provideInfo(response);
				}
				ChatbotMain.print("Well, it was nice talking to you, " + ChatbotMain.chatbot.getUsername() + "!");
				ChatbotMain.chatbot.startChatting();
			}
		} else {
			mistake();
		}
		ChatbotMain.chatbot.throwBack(this);
	}

	public boolean isTriggered(String response) {
		for(int i = 0; i < keywords.length; i++)
			if(ChatbotMain.findKeyword(response, keywords[i], 0) >= 0)
				return true;
		if(ChatbotMain.findKeyword(response, "no", 0) >= 0 && ChatbotMain.chatbot.getFamilySize() <= 4 && !ChatbotMain.chatbot.getHasPets())
			return true;
		return false;
	}
		
	public void list() {
		 ChatbotMain.print("Hi!");
	}
	
	private void provideInfo(String response) {
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
	
	public static void mistake() {
		String wereWereNot = "";
		if(ChatbotMain.chatbot.getForLiving())
			wereWereNot = "were";
		else
			wereWereNot = "weren't";
		ChatbotMain.print("I thought you said you " + wereWereNot + " looking for a residence.");
		if(YesNo("Would you like to update your information?"))
			ChatbotMain.chatbot.evaluate();
		else
			ChatbotMain.chatbot.throwBack(ChatbotMain.chatbot);
	}
	
}
