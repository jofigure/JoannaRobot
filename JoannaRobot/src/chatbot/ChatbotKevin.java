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
	private String finalizeResponse;
	
	boolean farmAlreadySold;
	
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
		
		String[] good = {"Please tell me your decision, ", "Please decide on whether you are buying the property or not, ", "Don't worry, you can take a while to decide, ", "I'm sure we can figure this out, "};
		goodResponses = good;
		String[] bad = {"Make your choice already, ", "Hurry up. I have other customers to attend to, ", "I don't have all day, ", "Maybe we should just flip a coin on this, "};
		badResponses = bad;
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
		return false;
	}

	public void buyFarmOrPL()
	{
		if (ChatbotMain.findKeyword(response, farmKeyword, 0) >= 0)
		{
			ChatbotMain.print("So you would like a " + farmKeyword + "? Unfortunately, we only have an alfalfa ranch available for $4,100,00 in Butte Valley. Do you still want it?");
			response = ChatbotMain.getInput();
			
			if (ChatbotMain.findKeyword(response, sellKeyword, 0) >= 0 || ChatbotMain.findKeyword(response, yesKeyword, 0) >= 0)
			{
				finalizePurchase();
				return;
			}
			else
			{
				ChatbotMain.print("Please specify your choice, " + ChatbotMain.chatbot.getUsername() + ".");
				response = ChatbotMain.getInput();
				
				patience--;
				
				if (patience > 0)
				{
					buyFarmOrPL();
				}
				else
				{
					patienceEnd();
				}
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
			
			if (patience > 5)
			{
				finalizeResponse = goodResponses[(int) (Math.random()*goodResponses.length)] + ChatbotMain.chatbot.getUsername() + ".";
				patience--;
				ChatbotMain.print(finalizeResponse);
				response = ChatbotMain.getInput();
			}
			else
			{
				if (patience > 0 && patience <6)
				{
					finalizeResponse = badResponses[(int) (Math.random()*badResponses.length)] + ChatbotMain.chatbot.getUsername() + ".";
					patience--;
					ChatbotMain.print(finalizeResponse);
					response = ChatbotMain.getInput();
				}
				else
				{
					patienceEnd();
				}
			}
		}
	}
	
	public void patienceEnd()
	{
		ChatbotMain.print("ERROR: PATIENCE NOT FOUND... REROUTING... Farewell, " + ChatbotMain.chatbot.getUsername() + ".");
		ChatbotMain.chatbot.startChatting();
	}
	
	public void finalizePurchase()
	{
		ChatbotMain.print("So, you would like to purchase this land? Are you sure?");
		response = ChatbotMain.getInput();
		if (ChatbotMain.findKeyword(response, yesKeyword, 0) >= 0)
		{
			if (this.farmAlreadySold == false)
			{
				ChatbotMain.print("Thank you for your patronage. Please come by again for future deals.");
				this.farmAlreadySold = true;
				ChatbotMain.chatbot.startChatting();
			}
			else
			{
				ChatbotMain.print("Sorry, but you have already bought our farm. Since you're so eager for more land, we'll be sure to stock up on more farms for sale.");
				this.farmAlreadySold = true;
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
				finalizePurchase();
			}
			else
			{
				if (patience > 0 && patience <6)
				{
					finalizeResponse = badResponses[(int) (Math.random()*badResponses.length)] + ChatbotMain.chatbot.getUsername() + ".";
					patience--;
					ChatbotMain.print(finalizeResponse);
					finalizePurchase();
				}
				else
				{
					patienceEnd();
				}
			}
		}
	}
	
	public void talk(String initial)
	{
		if (!ChatbotMain.chatbot.getForLiving())
		{
			ChatbotMain.print("Are you interested in the various lands available for purchase?");
			response = ChatbotMain.getInput();
			
			while(ChatbotMain.findKeyword(response, yesKeyword, 0) >= 0) 
			{
				ChatbotMain.print("Well, you need to specify your choice. Would you like a farm or a parking lot?");
				response = ChatbotMain.getInput();
				
				buyFarmOrPL();
				
				ChatbotMain.print("Please respond to my question, " + ChatbotMain.chatbot.getUsername() + ".");
				response = ChatbotMain.getInput();
			}
				patienceEnd();
		}
		else
		{
			ChatbotAnnie.mistake();
		}
	}
}