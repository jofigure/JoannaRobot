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
	private boolean wantHouse;
	private boolean wantPool;
	
	
	public JoannaChatbot() {
		
		String[] temp = {"total control","remodel" , "privacy","own", "single family" ,"house"};
		keywords1 = temp;
		goodbyeKeyword = "bye";
		 niceKeyword = "thanks";
		response = "";
		String[] home =  { " A blue 3 floor house" ," A red 1 floor house" , "A wooden house", "A brick no basement house"};
		houseListings = home;
		pool = false;
		String[] adj = { "Blue","Red", "Wooden", "Brick", "no basement", "1" , "2", "3"  ,"4"};
		keywords3 = adj;
		keepPlead= -1;
	}


	public void talk(String initialInput) {
		
		if(ChatbotMain.chatbot.getForLiving()) {
		  
			
			

			ChatbotMain.print("We have great house listings with affordable pricing.");
					
					
					
					 wantHouse=ChatbotAnnie.YesNo("Would you like to look at the houses?");
			while(!wantHouse)
			{
						getPleads();
							if(pleading[keepPlead].equals(pleading[2]))
							{	if(wantPool)
								{
								pool= true;
								
								listOptions();
								break;
								}
							else {
								if(ChatbotAnnie.YesNo("Last chance... Want a pool?"))
									{
									
								pool= true;
									
										listOptions();
										break;
									}
							}
								ChatbotMain.print( "Judging from your responses,buying a house isn't for you.");
							ChatbotMain.chatbot.throwBack();
								
							}
							
							
						}
				
				listOptions();
			
			
			
			
			
			
		} else
			ChatbotAnnie.mistake();
		} 

	

	private void listOptions() {
		
		ChatbotMain.print(" Do any of these seem of interest to you:" );
		
		for(int i =0; i< houseListings.length; i++)
		{
			int Num = i+1;
			ChatbotMain.print( Num + ". "  + houseListings[i]);
			
			
		}
		response = ChatbotMain.getInput();
		buyhouse();
		while(ChatbotMain.findKeyword(response, goodbyeKeyword, 0) == -1)
		{
				if(ChatbotMain.findKeyword(response, niceKeyword, 0) >= 0)
				{
					
					ChatbotMain.print( "No problem! Glad to help! Bye!");

					
				}
				
				response = ChatbotMain.getInput();
		}
	
		ChatbotMain.print("Well, it was nice talking to you, " + ChatbotMain.chatbot.getUsername()+ "!");
		ChatbotMain.chatbot.startChatting();
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
			if(ChatbotMain.chatbot.getFamilySize()>= 2 && ChatbotMain.chatbot.getBuying() == true && ChatbotMain.findKeyword(response, "no", 0)>= 0) 
			{
				return true;
				
			}
			
			
				
		}
		return false;
	}

	
	public void getPleads()
	{
		keepPlead++;
		if(keepPlead<2)
		{	
			wantHouse=ChatbotAnnie.YesNo(pleading[keepPlead]);
		}
		else {
		if(keepPlead==2)
			{
				wantPool=ChatbotAnnie.YesNo(pleading[keepPlead]);
			}
	else {
			ChatbotMain.print("Now you're just playing around. We are a very busy real estate company. Please come again when you have decided what you want to buy or rent. See you! "); 
			keepPlead=-1;
			ChatbotMain.chatbot.startChatting();
		}
		}
	}
 

public String convertFam()
{

	
 String str =String.valueOf(ChatbotMain.chatbot.getFamilySize());  
	return str;
}




public boolean buyhouse()
{
	for(int i = 0; i< keywords3.length; i++)
	{
		if(ChatbotMain.findKeyword(response, keywords3[i], 0) >= 0)
		{	
			ChatbotMain.print("You've successfully bought the " + keywords3[i]+ " house! Congrats.");
			if(pool==true)
			{
			ChatbotMain.print("And with the pool expansion too!Hope you enjoy your new place.");
			}
			
			response = ChatbotMain.getInput();
			
		} 
	
		
	}
	return false;
}


public boolean getYesNo(String reponse)
{
	
	this.response= reponse;  
	if( response.equals("yes")==true || response.equals("no")==true)
	{
		ChatbotMain.print("Would you like to look for a house?Please answer with yes or no");
		return true;
		
		
	}
	else {
		
		return false;
		
	}
}


 




}






















































