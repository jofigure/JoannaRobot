package chatbot;

public class ChatbotStephanie implements Topic {
	
	private String[] keywords;
	private String[] discSent;
	private String purchaseKeyword;
	private String response;
	private String townhouse;
	private String finalSent;
	private int price;
	private int numNo;
	
	public ChatbotStephanie() {
		String[] temp = {"townhouse", "town", "community", "row house"};
		keywords = temp;
		String[] disc = {"Great, I can give you a starting discount.", "Fine, I'm in a good mood so I can go lower.", "I really shouldn't be going any lower but I can make an exception for you.", "This is probably the best deal that I can get for you.", "I REALLY can not go lower than this."};
		discSent = disc;
		purchaseKeyword = "yes";
		response = "";
		townhouse = "2 family townhome with 6 beds and 4 baths for $2600 per month. ";
		finalSent = "Would you like to make the purchase now?";
		price = 2600;
		numNo = 0;
	}

	public void debate(String initial ) {
		ChatbotMain.print("Well, why not?");
		response = ChatbotMain.getInput();
		boolean discounted = ChatbotAnnie.YesNo("I can give you a nice discount, how about that?");
		response = ChatbotMain.getInput();
		for (int i = 0; i < discSent.length; i++) {
			if (discounted) {
				numNo ++;
				discPrice(price);
				ChatbotMain.print(discSent[i] + "That will be $" + price + ". Would you like to make the purchase now?");
				response = ChatbotMain.getInput();
				//continue discounting
			}
			else {
				ChatbotMain.print("I see that you have a family size of " + ChatbotMain.chatbot.getFamilySize() + "." );
				//continue with user info
			}
		}
		ChatbotMain.print("You really let a nice deal slip by you, what a shame. I hope you have a fine day.");
		ChatbotMain.chatbot.startChatting();
	}
	
		
	public void talk(String initial) {
		ChatbotMain.print("I know a really nice townhouse, would you like to hear about it?");
		response = ChatbotMain.getInput();
		if (ChatbotMain.findKeyword(response, purchaseKeyword, 0) >= 0 ) {
			boolean renting = ChatbotAnnie.YesNo("There is one townhouse for rent right now and it is a " + townhouse + "Would you be interested in renting this?");
				if(renting) {
					ChatbotMain.print("Thank you for renting this townhouse! It was a pleasure doing business with you " + ChatbotMain.chatbot.getUsername() + "!");		
				}else {
					debate(initial);
				}
		}else {
			ChatbotMain.print("Sorry to hear that you're not interested in townhouses, you are really missing out.");
			ChatbotMain.chatbot.throwBack();
		}
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
