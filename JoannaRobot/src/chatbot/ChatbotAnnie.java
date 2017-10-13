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
		String[] temp = {"apt", "apartment", "city", "flat", "condo", "condominium", "co-op", "loft", "duplex", "urban"};
		keywords = temp;
		String[] temp2 = {"studio co-op", "studio apartment", "one-bedroom condominium", "three-bedroom duplex", "two-bedroom loft", "two-bedroom co-op"};
		listings = temp2;
		String[] temp3 = {"Located in Hell's Kitchen, the apartment features 10' ceilings, 2 closets, a built-in murphy bed, and hardwood floors. This 450 sqft studio co-op is on sale for $419,000.", 
				"Located in Williamsburg, this spacious alcove studio is conducive for a separate sleeping area from your living/dining space. Featuring white oak hardwood floors, the apartment is up for rent for $2,900 per month.", 
				"Located in Woodside, the apartment offers two balconies, stainless steel appliances, white marble countertops, a walk-in closet, and hardwood floors. This luxury one-bedroom condominium is on sale for $619,000.", 
				"Located in the Upper West Side, this massive Brownstone duplex boasts 3 large bedrooms, 2 beautiful bathrooms, a spacious kitchen, a laundry room, as well as a lovely patio. This property is up for rent for $5,900 per month.",
				"Located in the Flatiron District, this charming loft has 2 bedrooms, 2.5 bathrooms, a washer dryer unit, and oversized windows that offer plenty of light. This 1,281 sqft loft is up for rent for $8,000 per month.",
				"Located in Forest Hills, the apartment covers 1,050 sqft and features hardwood floors, stainless steel appliances, and plenty of storage space. This listing is on sale for $448,888."};
		listingsInfo = temp3;
		boolean[] temp4 = {true, false, true, false, false, true};
		onSale = temp4;
		boolean[] temp5 = {false, false, false, false, false, false};
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
		if(ChatbotMain.findKeyword(response, "no", 0) >= 0 && ChatbotMain.chatbot.getFamilySize() <= 6 && !ChatbotMain.chatbot.getHasPets()) {
			isSuggestion = true;
			return true;
		}
		return false;
	}
		
	public void talk(String initial) {
		setup();
		if(ChatbotMain.chatbot.getForLiving()) {
			if(isSuggestion)
				ChatbotMain.print("It looks like you may be in the market for an apartment.");
			boolean lookingApt = YesNo("Would you like to take a look at some apartments?");
			if(lookingApt) {
				boolean aptsExist = aptsAvailable();
				if(aptsExist) {
					listAsk();
					while(!answerAccepted && notAnsweringCount <= 2) {
						response = ChatbotMain.getInput();
						provideInfo(response);
					}
					if(answerAccepted)
						sellRent();
					else {
						ChatbotMain.print("I guess you don't want to look at properties.");
						notAnsweringCount++;
					}
				} else
					ChatbotMain.print("Sorry, there are no more apartments that you can " + buyOrRent + ".");
				if(notAnsweringCount <= 2) {
					continueLooking = YesNo("Would you like to look at other properties?");
					if(continueLooking)
						ChatbotMain.chatbot.evaluate();
				}
				if(!continueLooking) {
					ChatbotMain.print("Well, it was nice doing business with you, " + ChatbotMain.chatbot.getUsername() + "!");
					ChatbotMain.chatbot.startChatting();
				}
			} else
				ChatbotMain.chatbot.throwBack();	
		} else
			mistake();
	}
	
	public void setup() {
		answerAccepted = continueLooking = false;
		notAnsweringCount = 0;
		if(ChatbotMain.chatbot.getForLiving()) {
			buying = ChatbotMain.chatbot.getBuying();
			if(buying)
				buyOrRent = "buy";
			else
				buyOrRent = "rent";
		}
	}

	public boolean aptsAvailable() {
		for(int i = 0; i < listings.length; i++)
			if(buying == onSale[i] && !taken[i])
				return true;
		return false;
	}

	public void listAsk() {
		ChatbotMain.print("I have some listings that might be of interest to you.");
		ChatbotMain.print("Properties that you can " + buyOrRent + " include: ");
		for(int i = 0; i < listings.length; i++)
			if(buying == onSale[i] && !taken[i])
				ChatbotMain.print("   - " + listings[i]);
		ChatbotMain.print("Which one would you like to know more about?");
	}
	
	public void provideInfo(String response) {
		for(int i = 0; i < listings.length; i++)
			if(ChatbotMain.findKeyword(response, listings[i], 0) >= 0 && buying == onSale[i] && !taken[i]) {
				answerAccepted = true;
				choice = i;
			}
		if(answerAccepted)
			ChatbotMain.print(listingsInfo[choice]);
		else if(notAnsweringCount < 2)
			ChatbotMain.print("Please pick one of the listings that I mentioned.");
		if(!answerAccepted && notAnsweringCount <= 2)
			notAnsweringCount++;
	}

	public void sellRent() {
		ChatbotMain.print("The price is non-negotiable.");
		boolean buying = YesNo("Would you like to " + buyOrRent + " this unit?");
		if(buying) {
			taken[choice] = true;
			ChatbotMain.print("Great! Thank you for " + buyOrRent + "ing the " + listings[choice] + ".");
		} else
			ChatbotMain.print("I'm sorry to hear that.");
	}

	public static int getPositiveNumInput() {
		ChatbotMain.print("Please enter a number.");
		String numStr = ChatbotMain.getInput();
		boolean isNum = false;
		int value = 0;
		while(!isNum)
			try {
				value = Integer.parseInt(numStr);
				if(value > 0)
					isNum = true;
				else {
					ChatbotMain.print("You must enter a number greater than 0. Please try again.");
					numStr = ChatbotMain.getInput();
				}
			} catch(NumberFormatException e) {
				ChatbotMain.print("You must enter a number. Please try again.");
				numStr = ChatbotMain.getInput();
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
