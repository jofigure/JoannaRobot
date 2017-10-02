package chatbot;

public class ChatbotKevin implements Topic 
{
	
	private String[] keywords;
	private String noKeyword;
	private String yesKeyword;
	private String sellKeyword;
	private String response;
	private String[] sellResponse;

	public ChatbotKevin() 
	{
		String[] keys = {"land", "lots", "plot"};
		keywords = keys;
		noKeyword = "no";
		yesKeyword = "no";
		sellKeyword = "buy";
		response = "";
		String[] sellKeys = {"buy"};
		sellResponse = sellKeys;
	}
	public boolean isTriggered(String response) 
	{
		for(int i = 0; i < keywords.length; i++)
		{
			if(ChatbotMain.findKeyword(response, keywords[i], 0) >= 0)
			{
				return true;
			}
		}
		return false;
	}
	
	
	public void talk(String initial) 
	{
		ChatbotMain.print("Hello, would you like to look at different lands available for purchase?");
		response = ChatbotMain.getInput();
		
		while(ChatbotMain.findKeyword(response, noKeyword, 0) == -1) 
		{
			if (ChatbotMain.findKeyword(response, sellKeyword, 0) >= 0)
			{
				ChatbotMain.print("So, you would like to purchase this land? Are you sure?");
				response = ChatbotMain.getInput();
				if (ChatbotMain.findKeyword(response, yesKeyword, 0) >= 0)
				{
					ChatbotMain.print("Thank you for your patronage. Please come by again for future deals.");
				}
			}
			else
			{
				ChatbotMain.print("Are there any specific lands you are interested in?");
			}
			response = ChatbotMain.getInput();
		}
		ChatbotMain.print("I'm sorry for not having property which appeals to you. Please come by again when new purchases are available, " + ChatbotMain.chatbot.getUsername() + ".");
		ChatbotMain.chatbot.startChatting();
	}
}
