package chatbot;

public class ChatbotKevin implements Topic 
{
	
	private String[] keywords;
	private String noKeyword;
	private String yesKeyword;
	private String sellKeyword;
	private String response;
	private int patience;
	private String[] patienceMeter;
	private String[] goodResponses;
	private String[] badResponses;
	private String endResponse;
	private String finalizeResponse;
	
	private boolean farmAlreadySold;
	
	private String farmKeyword;
	private String parkingKeyword;

	public ChatbotKevin() 
	{
		String[] keys = {"land", "lots", "plot", "parking lot", "farm"};
		keywords = keys;
		noKeyword = "no";
		yesKeyword = "yes";
		sellKeyword = "buy";
		response = "";
		
		patience = 10;
		String [] patienceM = {"5", "4", "3", "2", "1"};
		patienceMeter = patienceM;
		
		String[] good = {"Please tell me your decision, ", "Please decide on whether you are buying the property or not, ", "Don't worry, you can take a while to decide, ", "I'm sure we can figure this out, "};
		goodResponses = good;
		String[] bad = {"Make your choice already, ", "Hurry up. I have other customers to attend to, ", "I don't have all day, ", "Maybe we should just flip a coin on this, "};
		badResponses = bad;
		endResponse = "Goodbye, ";
		finalizeResponse = "";
		
		farmAlreadySold = false;
		
		farmKeyword = "farm";
		parkingKeyword = "parking lot";
		
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
		if (!ChatbotMain.chatbot.getForLiving())
		{
			return true;
		}
		return false;
	}
	
	
	
	public void finalizePurchase()
	{
		ChatbotMain.print("So, you would like to purchase this land? Are you sure?");
		response = ChatbotMain.getInput();
		if (ChatbotMain.findKeyword(response, yesKeyword, 0) >= 0)
		{
			if (farmAlreadySold = false)
			{
				ChatbotMain.print("Thank you for your patronage. Please come by again for future deals.");
				farmAlreadySold = true;
				ChatbotMain.chatbot.startChatting();
			}
			else
			{
				ChatbotMain.print("Sorry, but you have already bought our farm. Since you're so eager for more land, we'll notify you when farms are available.");
				farmAlreadySold = true;
				ChatbotMain.chatbot.startChatting();
			}
		}
		else
		{
			if (ChatbotMain.findKeyword(response, noKeyword, 0) >= 0)
			{
				ChatbotMain.print("I'm sorry for the inconvenience. Perhaps you would be interested in our townhouses, apartments, or houses.");
				ChatbotMain.chatbot.startChatting();
				return;
			}
			
			if (patience > 5)
			{
				finalizeResponse = goodResponses[(int) (Math.random()*goodResponses.length)] + ChatbotMain.chatbot.getUsername() + ".";
				patience--;
				ChatbotMain.print(finalizeResponse);
				response = ChatbotMain.getInput();
			}
			if (patience > 0 && patience <6)
			{
				finalizeResponse = badResponses[(int) (Math.random()*badResponses.length)] + ChatbotMain.chatbot.getUsername() + ".";
				patience--;
				ChatbotMain.print(finalizeResponse);
				response = ChatbotMain.getInput();
			}
			else
			{
				finalizeResponse = endResponse + ChatbotMain.chatbot.getUsername() + ".";
				ChatbotMain.print(finalizeResponse);
				return;
			}
		}
	}
	
	public void talk(String initial)
	{
		ChatbotMain.print("Are you interested in the various lands available for purchase?");
		response = ChatbotMain.getInput();
		
		while(ChatbotMain.findKeyword(response, yesKeyword, 0) >= 0) 
		{
			ChatbotMain.print("Well, you need to specify your choice. Would you like a farm or a parking lot?");
			response = ChatbotMain.getInput();
			
			if (ChatbotMain.findKeyword(response, farmKeyword, 0) >= 0)
			{
				ChatbotMain.print("So you would like a " + farmKeyword + "? Unfortunately, we only have an alfalfa ranch available for $4,100,00 in Butte Valley. Do you still want it?");
				response = ChatbotMain.getInput();
				
				if (ChatbotMain.findKeyword(response, sellKeyword, 0) >= 0 || ChatbotMain.findKeyword(response, yesKeyword, 0) >= 0)
				{
					finalizePurchase();
					return;
				}
			}
			else
			{
				if (ChatbotMain.findKeyword(response, parkingKeyword, 0) >= 0)
				{
					ChatbotMain.print("So you would like a " + parkingKeyword + "? Sorry " + ChatbotMain.chatbot.getUsername() + ", but all parking lots have already been sold. Please come back another time.");
					ChatbotMain.chatbot.startChatting();
					return;
				}
				
				ChatbotMain.print("Please specify your choice, " + ChatbotMain.chatbot.getUsername() + ".");
				response = ChatbotMain.getInput();
			}
		}
		for (int i = 0; i < patienceMeter.length; i++)
		{
			ChatbotMain.print("Please respond to my question, " + ChatbotMain.chatbot.getUsername() + ".");
			response = ChatbotMain.getInput();
		}
	}
}
