package chatbot;

public class ChatbotStephanie implements Topic {
	
	private String[] keywords;
	private String[] discSent;
	private String[] space;
	private String response;
	private String townhouse;
	private String finalSent;
	private String finalSent2;
	private int price;
	private boolean bought;
	
	public ChatbotStephanie() {
		String[] temp = {"townhouse", "town", "towns", "townhouses", "community", "row house"};
		keywords = temp;
		String[] disc = {"Fine, I'm in a good mood so I can go lower.", "I really shouldn't be going any lower but I can make an exception for you.", "This is probably the best deal that I can get for you.", "I REALLY can not go lower than this."};
		discSent = disc;
		String[] famComp = {"The townhouse is approximately 27,000 square feet. That's neither too big nor too small", "I'm sure there's nothing wrong with a house having too much space", "You can always rent out the extra space to other people"};
		space = famComp;
		response = "";
		townhouse = "2 family townhome with 6 beds and 4 baths for $2600 per month. ";
		finalSent = "Would you like to rent the townhouse now?";
		finalSent2 = "Have you reconsidered your decision now?";
		price = 2600;
		bought = false;
	}

	public void debate(String initial ) {
		ChatbotMain.print("Well, why not?");
		response = ChatbotMain.getInput();
		boolean discounted = ChatbotAnnie.YesNo("I can give you a nice discount, how about that?");
		if (discounted) {
			discPrice(price);
			ChatbotMain.print("Great, I can start you off at $" + price + " per month.");
			boolean purchaseTrue = ChatbotAnnie.YesNo("Would you like to rent the townhouse?");
			if (!purchaseTrue) {
			discPrice(price);
				for (int i = 0; i <discSent.length; i++) {
					ChatbotMain.print(discSent[i] + " That will be $" + price + " per month.");
					boolean discTrue = ChatbotAnnie.YesNo(finalSent);
					if (!discTrue) {
						discPrice(price);
					}
					else {
						ChatbotMain.print("I'm so glad you decided to make the purchase! Please enjoy your newly rented townhouse!");
						bought = true;
						ChatbotMain.chatbot.startChatting();
					}
				}
				ChatbotMain.print("You really let a nice deal slip by you, what a shame. I hope you have a fine day.");
				ChatbotMain.chatbot.startChatting();
			}
			else {
				ChatbotMain.print("I'm so glad you decided to make the purchase! Please enjoy your newly rented townhouse!");
				bought = true;
				ChatbotMain.chatbot.startChatting();
			}
		}
		else {
			ChatbotMain.print("You're a really strange person to be refusing a discount. If price is not the problem then it must be the size of the house. I see that you have a family size of " + ChatbotMain.chatbot.getFamilySize() + ". That's just the right amount of people to live comfortably");
			boolean sizeProblem = ChatbotAnnie.YesNo("Have you reconsidered your decision?");
			if(sizeProblem) {
				ChatbotMain.print("I'm so glad that you reconsidered, I hope you live comfortably in the new townhouse!");
				bought = true;
				ChatbotMain.chatbot.startChatting();
			}
			else {
				for (int i = 0; i < space.length; i++) {
					ChatbotMain.print(space[i]);
					boolean sizeTrue = ChatbotAnnie.YesNo(finalSent2);
					if(sizeTrue) {
						ChatbotMain.print("I knew you would understand, thank you for renting this townhouse. Please enjoy your new life here!");
						bought = true;
						ChatbotMain.chatbot.startChatting();
					}
				}
				ChatbotMain.print("I'm sorry to hear that you don't find this comfortable. Perhaps a townhouse isn't fit for you afterall.");
				ChatbotMain.chatbot.startChatting();
			}
		}
	}
		
	
		
	public void talk(String initial) {
		if(ChatbotMain.chatbot.getForLiving()) {
			ChatbotMain.print("I know a really nice townhouse!");
			boolean interested = ChatbotAnnie.YesNo("Would you like to hear about it?");
			if(bought == true) {
				ChatbotMain.print("I'm Sorry but this townhouse has already been bought, please come back when there are more available.");
				ChatbotMain.chatbot.startChatting();
			}
			if (interested) {
				ChatbotMain.print("There is one townhouse for rent right now and it is a " + townhouse);
				boolean renting = ChatbotAnnie.YesNo("Would you be interested in renting this?");
					if(renting) {
						ChatbotMain.print("Thank you for renting this townhouse! It was a pleasure doing business with you " + ChatbotMain.chatbot.getUsername() + "!");	
						bought = true;
						ChatbotMain.chatbot.startChatting();
					}
					else 
						debate(initial);
			}		
			else {
				ChatbotMain.print("Sorry to hear that you're not interested in townhouses, you are really missing out.");
				ChatbotMain.chatbot.throwBack();
			}
		} else
			ChatbotAnnie.mistake();
	}

	public boolean isTriggered(String response) {
		for(int i = 0; i < keywords.length; i++)
			if(ChatbotMain.findKeyword(response, keywords[i], 0) >= 0)
				return true;
		if(ChatbotMain.findKeyword(response, "no", 0) >= 0 && !ChatbotMain.chatbot.getBuying())
			return true;
		return false;
	}
	
	public void discPrice(int initialPrice) {
		initialPrice = price;
		price = price - 100;
	}

	
}
