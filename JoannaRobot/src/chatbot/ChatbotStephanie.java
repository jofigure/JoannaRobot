package chatbot;

public class ChatbotStephanie implements Topic {
	
	private String[] keywords;
	private String purchaseKeyword;
	private String response;
	private String townhouse;
	private int price;
	private int numNo;
	
	public ChatbotStephanie() {
		String[] temp = {"townhouse", "town", "community", "row house"};
		keywords = temp;
		purchaseKeyword = "yes";
		response = "";
		townhouse = "2 family townhome with 6 beds and 4 baths for $2600 per month. ";
		price = 2600;
		numNo = 0;
	}

	public void debate(String initial ) {
		ChatbotMain.print("Well, why not?");
		response = ChatbotMain.getInput();
		boolean discounted = ChatbotMain.YesNo("I can give you a nice discount, how about that?");
		response = ChatbotMain.getInput();
		while (numNo <= 5) {
			if (discounted) {
				numNo ++;
				discPrice(price);
				ChatbotMain.print("Great, how does $" + price + " sound?");
				//continue discounting
			}
			else {
				ChatbotMain.print("I see that you have a family size of " + ChatbotMain.chatbot.getFamilySize() + "." );
				//continue with user info
			}
		}
		//exit
	}
	
		
	public void talk(String initial) {
		ChatbotMain.print("I know a really nice townhouse, would you like to hear about it?");
		response = ChatbotMain.getInput();
		if (ChatbotMain.findKeyword(response, purchaseKeyword, 0) >= 0 ) {
			boolean renting = ChatbotMain.YesNo("There is one townhouse for rent right now and it is a " + townhouse + "Would you be interested in renting this?");
				if(renting) {
					ChatbotMain.print("Thank you for renting this townhouse! It was a pleasure doing business with you " + ChatbotMain.chatbot.getUsername() + "!");		
				}else {
					debate(initial);
				}
		}else {
			ChatbotMain.print("Sorry to hear that you're not interested in townhouses, you are really missing out " + ChatbotMain.chatbot.getUsername() + "!");
			ChatbotMain.chatbot.startChatting();
		}
	}

	public boolean isTriggered(String response) {
		for(int i = 0; i < keywords.length; i++)
			if(ChatbotMain.findKeyword(response, keywords[i], 0) >= 0)
				return true;
		if(!ChatbotMain.chatbot.getBuying())
			return true;
		return false;
	}
	
	public void discPrice(int initialPrice) {
		initialPrice = price;
		while (numNo <= 5)
			price = price - 100;
		if(numNo > 5)
			ChatbotMain.print("This is definitely your loss. I hope you have a fine day.");
	}
		
	
}
