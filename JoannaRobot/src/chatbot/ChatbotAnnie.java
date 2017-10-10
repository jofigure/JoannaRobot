package chatbot;

public class ChatbotAnnie implements Topic {
	
	private static int annoyedLevel;
	private String response;
	private String buyOrRent;
	private String[] keywords;
	private String[] listings;
	private String[] listingsInfo;
	private boolean[] onSale;
	private boolean[] taken;
	private boolean isSuggestion;
	private boolean buying;
	private boolean answerAccepted;
	private boolean continueLooking;
	private int notAnsweringCount;
	private int choice;

	public ChatbotAnnie() {
		annoyedLevel = 0;
		response = buyOrRent = "";
		String[] temp = {"apt", "apartment", "city", "flat", "condo", "condominium", "co-op", "loft", "duplex"};
		keywords = temp;
		String[] temp2 = {"studio apartment", "one bedroom condominium", "three bedroom duplex", "two bedroom loft"};
		listings = temp2;
		String[] temp3 = {"stuff", "stuff2", "stuff3", "stuff4"};
		listingsInfo = temp3;
		boolean[] temp4 = {true, true, false, false};
		onSale = temp4;
		boolean[] temp5 = {false, false, false, false};
		taken = temp5;
		isSuggestion = buying = answerAccepted = continueLooking = false;
		notAnsweringCount = 0;
		choice = -1;
	}

	public boolean isTriggered(String response) {
		for(int i = 0; i < keywords.length; i++)
			if(ChatbotMain.findKeyword(response, keywords[i], 0) >= 0) {
				isSuggestion = false;
				return true;
			}
		if(ChatbotMain.findKeyword(response, "no", 0) >= 0 && ChatbotMain.chatbot.getForLiving() && ChatbotMain.chatbot.getFamilySize() <= 4 && !ChatbotMain.chatbot.getHasPets()) {
			isSuggestion = true;
			return true;
		}
		return false;
	}
		
	public void talk(String initial) {
		answerAccepted = continueLooking = false;
		notAnsweringCount = 0;
		if(ChatbotMain.chatbot.getForLiving()) {
			if(isSuggestion)
				ChatbotMain.print("It looks like you may be in the market for an apartment. Am I correct?");
			boolean lookingApt = YesNo("Would you like to take a look at some apartments?");
			if(lookingApt) {
				buying = ChatbotMain.chatbot.getBuying();
				if(buying)
					buyOrRent = "buy";
				else
					buyOrRent = "rent";
				boolean aptsExist = aptsAvailable();
				if(aptsExist) {
					list();
					ChatbotMain.print("Which one would you like to know more about?");
					while(!answerAccepted && notAnsweringCount <= 2) {
						response = ChatbotMain.getInput();
						provideInfo(response);
					}
					if(answerAccepted)
						sell();
				} else {
					ChatbotMain.print("Sorry, there are no apartments that you can " + buyOrRent + ".");
				}
				if(notAnsweringCount <= 2) {
					continueLooking = YesNo("Would you like to look at other properties?");
					if(continueLooking)
						ChatbotMain.chatbot.evaluate();
				}
				if(!continueLooking) {
					ChatbotMain.print("Well, it was nice talking to you, " + ChatbotMain.chatbot.getUsername() + "!");
					ChatbotMain.chatbot.startChatting();
				}
			}
		} else
			mistake();
		if(notAnsweringCount <= 2)
			ChatbotMain.chatbot.throwBack();
	}

	private boolean aptsAvailable() {
		for(int i = 0; i < listings.length; i++)
			if(buying == onSale[i] && !taken[i])
				return true;
		return false;
	}

	public void list() {
		ChatbotMain.print("I have some listings that might be of interest to you.");
		ChatbotMain.print("Properties that you can " + buyOrRent + " include: ");
		for(int i = 0; i < listings.length; i++)
			if(buying == onSale[i] && !taken[i])
				ChatbotMain.print("   - " + listings[i]);
	}
	
	public void provideInfo(String response) {
		if(notAnsweringCount < 2) {
			for(int i = 0; i < listings.length; i++)
				if(ChatbotMain.findKeyword(response, listings[i], 0) >= 0) {
					answerAccepted = true;
					choice = i;
				}
			if(answerAccepted)
				ChatbotMain.print(listingsInfo[choice]);
			else {
				ChatbotMain.print("Please pick one of the listings that I mentioned.");
				notAnsweringCount++;
			}
		} else {
			ChatbotMain.print("I guess you don't want to look at properties.");
			notAnsweringCount++;
		}
	}

	public void sell() {
		boolean buying = YesNo("Would you like to " + buyOrRent + " this unit?");
		if(buying) {
			taken[choice] = true;
			ChatbotMain.print("Great! Thank you for " + buyOrRent + "ing the " + listings[choice] + ".");
		} else
			ChatbotMain.print("I'm sorry to hear that.");
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
		String[] responses = {question + " Yes or no?", "Please just answer my question.", "It's a simple yes or no question. " + question, "I'm getting annoyed; this isn't even a hard question.", question.toUpperCase(), "You know what? Go find somewhere else to buy a house. I am tired, and you are not answering my question. Goodbye!"};
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
				if(annoyedLevel < responses.length - 1) {
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
			ChatbotMain.chatbot.throwBack();
	}
	
}
