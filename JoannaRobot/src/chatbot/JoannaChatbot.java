package chatbot;

public class JoannaChatbot implements Topic {
	
	private String[] keywords1;
	private String[] pleading; 
	private String[] keywords3;
	private String goodbyeKeyword;
	private String niceKeyword;
	private String response;
	private String[] houseListings;
	private boolean pool;
	private int keepPlead;
	
	
	public JoannaChatbot() {
		
		String[] temp = {"total control","remodel" , "privacy","own", "single family" ,"house"};
		keywords1 = temp;
		goodbyeKeyword = "bye";
		 niceKeyword = "thanks";
		response = "";
		String[] home =  { "Blue 3 floor house" ," Red 1 floor house" , "Wooden house", "Brick no basement house"};
		houseListings = home;
		pool = false;
		String[] adj = { "Blue","Red", "Wooden", "Brick", "no basement", "1" , "2", "3"  ,"4"};
		keywords3 = adj;
		keepPlead= -1;
	}

	@Override
	public void talk(String initialInput) {
		ChatbotMain.print("We have great house listings with afforable pricing. Would you like to look for a house?");
				response = ChatbotMain.getInput();
		
	
	
	
		while(ChatbotMain.findKeyword(response,"no" , 0) >=0)
		{
			
			ChatbotMain.print( getPleads());
				response = ChatbotMain.getInput();
				
				if(ChatbotMain.findKeyword(response,"yes" , 0) >=0)
						{
							dontUnderstand("yes", "Cool!Let's buy houses!");
							listOptions();
						}
				dontUnderstand("no", "I'm sorry. I don't understand.");
				if(pleading[keepPlead].equals(pleading[2]))
				{
					
					ChatbotMain.print( "So if buying a house isn't for you.");
				ChatbotMain.chatbot.throwBack(this);
					
				}
				
		}
		
	
		
		while(ChatbotMain.findKeyword(response, goodbyeKeyword, 0) == -1)
		{
				if(ChatbotMain.findKeyword(response, niceKeyword, 0) >= 0)
				{
					
					ChatbotMain.print( "No problem! Glad to help! Anything else?");

					break;
				}
				listOptions();
		}
		
		
		
		
		//access variables from other classes
		ChatbotMain.print("Well, it was nice talking to you, " + ChatbotMain.chatbot.getUsername()+ "!");
		ChatbotMain.chatbot.startChatting();
	}

	private void listOptions() {
		
		ChatbotMain.print(" Does any of these seem of interest to you:" );
		
		for(int i =0; i< houseListings.length; i++)
		{
			int Num = i+1;
			ChatbotMain.print( Num + ". "  + houseListings[i]);
			
			
		}
		response = ChatbotMain.getInput();
		
	}

	@Override
	public boolean isTriggered(String response) {
		String[] pleads = {"Do you like houses?", "Houses are great for " + convertFam() + " people, right?" , "If you have a house,you can build your own personal pool in the backyard. Would you like one?"};
		pleading = pleads;
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

	
	public String getPleads()
	{
		keepPlead++;
		if(keepPlead==0)
		{	
			return pleading[keepPlead];
			
			
		}
		
		else if(keepPlead==1)
		{	
			return pleading[keepPlead];
			
			
		}
		
		else if(keepPlead==2)
		{	
			
			return pleading[keepPlead];
			
			
		}
		else {
			return "?";
		}
	}
 

public String convertFam()
{

	
 String str =String.valueOf(ChatbotMain.chatbot.getFamilySize());  
	return str;
}


public String dontUnderstand(String word, String react) {
	
	while(ChatbotMain.findKeyword(response, word, 0) == -1) {
		ChatbotMain.print(react);
		break;
	}
	return word;
	
}


}























































