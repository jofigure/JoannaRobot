package chatbot;

public class ChatbotStephanie implements Topic {
	
	private String[] keywords;
	private String purchaseKeyword;
	private String nopeKeyword;
	private String response;
	private String townhouse;
	
	public ChatbotStephanie() {
		String[] temp = {"townhouse", "town", "community", "row house"};
		keywords = temp;
		purchaseKeyword = "yes";
		nopeKeyword = "no";
		response = "";
		townhouse = "2 family townhome, 27,000 sqft, 6 beds and 4 baths.";
	}

	
	
	public void talk(String initial) {
		ChatbotMain.print("I know some really nice townhouses, would you be interested in purchasing one?");
		response = ChatbotMain.getInput();
		while(ChatbotMain.findKeyword(response, nopeKeyword, 0) == -1) {
			if(ChatbotMain.findKeyword(response, purchaseKeyword, 0) >= 0)
				ChatbotMain.print("I am so glad that you're interested, the only townhouse for sale is a " + townhouse + " Does this sound appealing to you?");
				if(ChatbotMain.findKeyword(response, purchaseKeyword, 0) >= 0)
				ChatbotMain.print("Thanks for purchasing this townhouse!");
			else
				ChatbotMain.print("You are not answering my question.");
			response = ChatbotMain.getInput();
		}
		ChatbotMain.print("Well, it was nice talking to you, " + ChatbotMain.chatbot.getUsername() + "!");
		ChatbotMain.chatbot.startChatting();
	}

	public boolean isTriggered(String response) {
		for(int i = 0; i < keywords.length; i++)
			if(ChatbotMain.findKeyword(response, keywords[i], 0) >= 0)
				return true;
		return false;
	}

}
