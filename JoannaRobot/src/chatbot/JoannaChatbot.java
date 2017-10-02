package chatbot;

public class JoannaChatbot implements Topic {
	
	private String[] keywords;
	private String goodbyeKeyword;
	private String niceKeyword;
	private String response;
	private String[] houseListings;
	public JoannaChatbot() {
		
		String[] temp = {"total control","remodel" , "privacy","own", "single family"};
		keywords = temp;
		goodbyeKeyword = "bye";
		 niceKeyword = "thanks";
		response = "";
		String[] home =  { "Blue 3 floor house" ," Red 1 floor house" , "Wooden house", "Brick no basement house"};
		houseListings = home;
	}

	@Override
	public void talk(String initialInput) {
		ChatbotMain.print("Hi, Welcome to JASK real estate! You've been directed to the House Department. ");
		response = ChatbotMain.getInput();
		while(ChatbotMain.findKeyword(response, goodbyeKeyword, 0) == -1)
		{
				if(ChatbotMain.findKeyword(response, niceKeyword, 0) >= 0)
				{
					ChatbotMain.print("No problem! Glad to help! Anything else?");
	
					
				}
				ChatbotMain.print("I see you're looking for a house. Does any of these seem of interest to you:");
			
		}
		//access variables from other classes
		ChatbotMain.print("Well, it was nice talking to you, " + ChatbotMain.chatbot.getUsername()+ "!");
		ChatbotMain.chatbot.startChatting();
	}

	@Override
	public boolean isTriggered(String response) {
		for(int i = 0; i< keywords.length; i++)
		{
			if(ChatbotMain.findKeyword(response, keywords[i], 0) >= 0)
			{
				return true;
			}
				
		}
		return false;
	}

}
