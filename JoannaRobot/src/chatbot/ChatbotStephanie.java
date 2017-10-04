package chatbot;

public class ChatbotStephanie implements Topic {
	
	private String[] keywords;
	private String purchaseKeyword;
	private String nopeKeyword;
	private String response;
	private String townhouse;
	private String[] reasons;
	private String currReason;
	private String[] feelings;
	private String currFeel;
	private int price;
	private int numNo;
	
	public ChatbotStephanie() {
		String[] temp = {"townhouse", "town", "community", "row house"};
		keywords = temp;
		purchaseKeyword = "yes";
		nopeKeyword = "no";
		response = "";
		townhouse = "2 family townhome with 6 beds and 4 baths for $2600 per month. ";
		String[] why = {"size","big", "small"};
		reasons = why;
		String[] feel = {"meh", "good", "great", "not bad", "okay", "nah", "no thanks"};
		feelings = feel;
		price = 2600;
		numNo = 0;
	}

	public void debate(String initial ) {
		ChatbotMain.print("Why don't you find this townhouse appealing?");
		response = ChatbotMain.getInput();
		while(ChatbotMain.findKeyword(response, nopeKeyword, 0) == -1) {
			getWord(response);
			if (currReason == "size") // miss or
				ChatbotMain.print("What is wrong with the " + currReason + "?"); 
			else
				ChatbotMain.print("How is it too " + currReason + "?");
			response = ChatbotMain.getInput();
			int familySize = ChatbotMain.chatbot.getFamilySize();
			if (familySize >= 5)
				ChatbotMain.print("I see that you have a family size of " + familySize + ". That is just the right amount of members to live comfortably. How do you feel now?");
			else
				ChatbotMain.print("I see that you have a family size of " + familySize + ". There is always the option of renting some of the space out to other families. How about that?");
			response = ChatbotMain.getInput();
			if (currFeel == "meh")
				numNo ++;
				discPrice(price);
				ChatbotMain.print("I can discount the price to " + price + ".");
			response = ChatbotMain.getInput();
			if(ChatbotMain.findKeyword(response, purchaseKeyword, 0) >= 0)
				ChatbotMain.print("Thank you for renting this townhouse! It was a pleasure doing business with you " + ChatbotMain.chatbot.getUsername() + "!");
			else
				numNo ++;
				ChatbotMain.print("I will lower it even more to " + price + ".");
			response = ChatbotMain.getInput();
		}
		ChatbotMain.print("Sorry to hear that you're not interested in townhouses, you are really missing out " + ChatbotMain.chatbot.getUsername() + "!");
		ChatbotMain.chatbot.startChatting();
	}
	
	public void talk(String initial) {
		ChatbotMain.print("I know a really nice townhouse, would you like to hear about it?");
		response = ChatbotMain.getInput();
		while(ChatbotMain.findKeyword(response, nopeKeyword, 0) == -1) {
			if(ChatbotMain.findKeyword(response, purchaseKeyword, 0) >= 0)
				ChatbotMain.print("There is one townhouse for rent right now and it is a " + townhouse + "Would you be interested in renting this?");
			response = ChatbotMain.getInput();
				if(ChatbotMain.findKeyword(response, purchaseKeyword, 0) >= 0)
				ChatbotMain.print("Thank you for renting this townhouse! It was a pleasure doing business with you " + ChatbotMain.chatbot.getUsername() + "!");		
			else
				ChatbotMain.print("You are not answering my question.");
			response = ChatbotMain.getInput();
		}
		debate(initial);
	}

	public boolean isTriggered(String response) {
		for(int i = 0; i < keywords.length; i++)
			if(ChatbotMain.findKeyword(response, keywords[i], 0) >= 0)
				return true;
		if(!ChatbotMain.chatbot.getBuying())
			return true;
		return false;
	}
	
	public String getWord(String response) {
		for (int i = 0; i < reasons.length; i++)
			if(ChatbotMain.findKeyword(response, reasons[i], 0) >= 0)
				currReason = reasons[i];
				return currReason;
	}
	
	public String getFeel(String response) {
		for (int i = 0; i < feelings.length; i++)
			if(ChatbotMain.findKeyword(response, feelings[i], 0) >= 0)
				currFeel = feelings[i];
				return currFeel;
	}
	
	public void discPrice(int initialPrice) {
		initialPrice = price;
		while (numNo <= 4)
			price = price - 100;
		if(numNo > 4)
			ChatbotMain.print("This is definitely your loss. I hope you have a fine day.");
	}
		
	
}
