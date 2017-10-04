package chatbot;

public class JoannaChatbot implements Topic {
	
	private String[] keywords1;
	private String[] keywords2;
	private String[] keywords3;
	private String goodbyeKeyword;
	private String niceKeyword;
	private String response;
	private String[] houseListings;
	private boolean pool;
	public JoannaChatbot() {
		
		String[] temp = {"total control","remodel" , "privacy","own", "single family" ,"house"};
		keywords1 = temp;
		goodbyeKeyword = "bye";
		 niceKeyword = "thanks";
		response = "";
		String[] home =  { "Blue 3 floor house" ," Red 1 floor house" , "Wooden house", "Brick no basement house"};
		houseListings = home;
		pool = false;
		String[] temp2 = { "yes","see", "like to","okay"};
		keywords2 = temp2;

	}

	@Override
	public void talk(String initialInput) {
		ChatbotMain.print("We have great house listings with afforable pricing. Would you like to look for a house?");
				response = ChatbotMain.getInput();
		
	
	
	
		if(ChatbotMain.findKeyword(response,"no" , 0) >=0)
		{
			
		}
		else
		{	
			ChatbotMain.print( "Sorry please input an apporiprate response.");

			response = ChatbotMain.getInput();
		}
		
	
		
		while(ChatbotMain.findKeyword(response, goodbyeKeyword, 0) == -1)
		{
				if(ChatbotMain.findKeyword(response, niceKeyword, 0) >= 0)
				{
					
					ChatbotMain.print( "No problem! Glad to help! Anything else?");

					
				}
				ChatbotMain.print(" Does any of these seem of interest to you:" );
				
				for(int i =0; i< houseListings.length; i++)
				{
					int Num = i+1;
					ChatbotMain.print( Num + ". "  + houseListings[i]);
					String[] temp = { "Blue","Red", "Wooden", "Brick", "no basement", "1" , "2", "3"  ,"4"};
					keywords3 = temp;
					
				}
				response = ChatbotMain.getInput();
			
		}
		
		
		
		
		//access variables from other classes
		ChatbotMain.print("Well, it was nice talking to you, " + ChatbotMain.chatbot.getUsername()+ "!");
		ChatbotMain.chatbot.startChatting();
	}

	@Override
	public boolean isTriggered(String response) {
		for(int i = 0; i< keywords1.length; i++)
		{
			if(ChatbotMain.findKeyword(response, keywords1[i], 0) >= 0)
			{	
				return true;
			}
			if(ChatbotMain.chatbot.getFamilySize()>= 2 && ChatbotMain.chatbot.getBuying() == true) 
			{
				return true;
				
			}
			
			
				
		}
		return false;
	}

}





































































