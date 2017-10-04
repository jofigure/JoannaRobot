package chatbot;

public class ChatbotAnnie implements Topic {
	
	private String[] keywords;
	private String[] moreKeywords;
	private String goodbyeKeyword;
	private String secretKeyword;
	private String response;

	public ChatbotAnnie() {
		String[] temp = {"apt", "apartment", "city", "flat", "condo", "condominium", "co-op"};
		keywords = temp;
		String[] temp2 = {"listings", "list", "properties"};
		moreKeywords = temp2;
		goodbyeKeyword = "bye";
		secretKeyword = "";
		response = "";
	}

	public void talk(String initial) {
		ChatbotMain.print("It looks like you're in the market for an apartment. Am I correct?");
		response = ChatbotMain.getInput();
		if(ChatbotMain.findKeyword(response, "yes", 0) >= 0) {
			while(ChatbotMain.findKeyword(response, goodbyeKeyword, 0) == -1) {
				for(int i = 0; i < moreKeywords.length; i++)
					if(ChatbotMain.findKeyword(response, secretKeyword, 0) >= 0)
						list();
					else {
						response = ChatbotMain.getInput();
					}
			}
			ChatbotMain.print("Well, it was nice talking to you, " + ChatbotMain.chatbot.getUsername() + "!");
			ChatbotMain.chatbot.startChatting();
		} else 
			ChatbotMain.chatbot.throwBack();
	}

	public boolean isTriggered(String response) {
		if(ChatbotMain.chatbot.getForLiving()) {
			for(int i = 0; i < keywords.length; i++)
				if(ChatbotMain.findKeyword(response, keywords[i], 0) >= 0)
					return true;
			if(ChatbotMain.chatbot.getFamilySize() < 4 && !ChatbotMain.chatbot.getHasPets())
				return true;
		}
		return false;
	}
	
	public void list() {
		
	}
 
}
