package chatbot;

public class ChatbotKevin implements Topic 
{
	
	private String[] keywords;
	private String noKeyword;
	private String yesKeyword;
	private String sellKeyword;
	private String response;
	private int patience;
	private String[] goodResponses;
	private String[] badResponses;
	private String endResponse;
	private String finalizeResponse;

	public ChatbotKevin() 
	{
		String[] keys = {"land", "lots", "plot"};
		keywords = keys;
		noKeyword = "no";
		yesKeyword = "no";
		sellKeyword = "buy";
		response = "";
		
		patience = 10;
		
		String[] good = {"Please tell me your decision, ", "Please decide on whether you are buying the property or not, ", "Don't worry, you can take a while to decide, ", "I'm sure we can figure this out, "};
		goodResponses = good;
		String[] bad = {"Make your choice already, ", "Hurry up. I have other customers to attend to, ", "I don't have all day, ", "Maybe we should just flip a coin on this, "};
		badResponses = bad;
		endResponse = "Goodbye, ";
		finalizeResponse = "";
		
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
	
	public void finalizePurchase()
	{
		ChatbotMain.print("So, you would like to purchase this land? Are you sure?");
		response = ChatbotMain.getInput();
		if (ChatbotMain.findKeyword(response, yesKeyword, 0) >= 0)
		{
			ChatbotMain.print("Thank you for your patronage. Please come by again for future deals.");
		}
		else
		{
			if (ChatbotMain.findKeyword(response, noKeyword, 0) >= 0)
			{
				ChatbotMain.print("I'm sorry for the inconvenience. Would be interested in looking at our townhouses, apartments, or houses?");
			}
			
			if (patience > 5)
			{
				finalizeResponse = goodResponses[(int) (Math.random()*goodResponses.length)] + ChatbotMain.chatbot.getUsername() + ".";
				patience--;
				response = ChatbotMain.getInput();
			}
			if (patience > 0 && patience <6)
			{
				finalizeResponse = badResponses[(int) (Math.random()*badResponses.length)] + ChatbotMain.chatbot.getUsername() + ".";
				patience--;
				response = ChatbotMain.getInput();
			}
			else
			{
				finalizeResponse = endResponse + ChatbotMain.chatbot.getUsername() + ".";
			}
		}
	}
	
	public void talk(String initial)
	{
		ChatbotMain.print("Are you interested in the various lands available for purchase?");
		response = ChatbotMain.getInput();
		
		while(ChatbotMain.findKeyword(response, noKeyword, 0) == -1) 
		{
			ChatbotMain.print("Well, you need to specify your choice. Would you like a farm or a parking lot?");
			response = ChatbotMain.getInput();
			
			if
			{
				
			}
			if (ChatbotMain.findKeyword(response, sellKeyword, 0) >= 0)
			{
				finalizePurchase();
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
