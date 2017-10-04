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
	
	public ChatbotStephanie() {
		String[] temp = {"townhouse", "town", "community", "row house"};
		keywords = temp;
		purchaseKeyword = "yes";
		nopeKeyword = "no";
		response = "";
		townhouse = "2 family townhome with 6 beds and 4 baths. ";
		String[] why = {"size", "bed", "bath","big", "small"};
		reasons = why;
		String[] feel = {"meh", "good", "great", "not bad", "okay", "nah", "no thanks"};
		feelings = feel;
		
	}

	public void debate(String initial ) {
		ChatbotMain.print("Why don't you find this townhouse appealing?");
		response = ChatbotMain.getInput();
		while(ChatbotMain.findKeyword(response, nopeKeyword, 0) == -1) {
			getWord(response);
			if (currReason == "big") // miss or
				ChatbotMain.print("How is it too " + currReason + "?");
			else
				ChatbotMain.print("What is wrong with the " + currReason + "?");
			response = ChatbotMain.getInput();
			int familySize = ChatbotMain.chatbot.getFamilySize();
			if (familySize >= 5)
				ChatbotMain.print("I see that you have a family size of " + familySize + ". That is just the right amount of members to live comfortably. How do you feel now?");
			else
				ChatbotMain.print("I see that you have a family size of " + familySize + ". There is always the option of renting some of the space out to other families. How about that?");
			response = ChatbotMain.getInput();
			if (currFeel == "meh")
				ChatbotMain.print("I can discount the price to $$$$");
			else
				ChatbotMain.print("So would you like to make the purchase");
			response = ChatbotMain.getInput();
			if(ChatbotMain.findKeyword(response, purchaseKeyword, 0) >= 0)
				ChatbotMain.print("Thank you for purchasing this townhouse! It was a pleasure doing business with you " + ChatbotMain.chatbot.getUsername() + "!");
			else
				ChatbotMain.print("I will lower is even more to $$$");
		}
		ChatbotMain.print("Sorry to hear that you're not interested in townhouses, you are really missing out " + ChatbotMain.chatbot.getUsername() + "!");
		ChatbotMain.chatbot.startChatting();
	}
	
	public void talk(String initial) {
		ChatbotMain.print("I know a really nice townhouse, would you like to hear about it?");
		response = ChatbotMain.getInput();
		while(ChatbotMain.findKeyword(response, nopeKeyword, 0) == -1) {
			if(ChatbotMain.findKeyword(response, purchaseKeyword, 0) >= 0)
				ChatbotMain.print("There is one townhouse for sale right now and it is a " + townhouse + "Would you be interested in purchasing this?");
			response = ChatbotMain.getInput();
				if(ChatbotMain.findKeyword(response, purchaseKeyword, 0) >= 0)
				ChatbotMain.print("Thank you for purchasing this townhouse! It was a pleasure doing business with you " + ChatbotMain.chatbot.getUsername() + "!");		
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
	
}
