/**
 * Program Name: CB_Project_Craps.java
 * Purpose: Generate a full game of Alley Craps!
 * Coder: Cody Boyd - 0749033
 * Date: Mar 21, 2023
 * 
 * PSEUDOCODE:
 * Create Scanner
 * Step 1) Create variables that will be needed throughout the program.
 * Step 2) Welcome players to Casino & ask for information about players, store in playerArray.
 * Step 3) Create bankroll for the players and assign value to bankroll stored in bankRollArray.
 * Step 4) Create betAmountArray.
 * Step 5) Welcome the players by name and ask if they would like an explanation of the rules.
 * Step 6) Start the while loop of the game and assign new shooter if need be.
 * Step 7) Ask the players to place their bets.
 * Step 8) Ask opponents to place bets
 * Step 9) roll the dice and check for shooter to natural win or crap out.
 * Step 10) Continue rolling the game if a natural or crap has not been rolled.
 * Step 11) Adjust bank balances and check for winner of the game. Otherwise restart loop for game.

 */


import java.util.*;

public class CB_Project_Craps
{
    //Create Scanner
  public static Scanner input = new Scanner(System.in);


	public static void main(String[] args)
	{
		
		// Step 1) Create variables that will be needed throughout the program.
			int shooterId; //keeps track of shooter
			int opponentId; //keeps track of the opponents
			int shooterBet = 0; //shooters bet 
			int opponentBet = 0; //keeps track of the opponents bet
			int totalOpponentBet = 0; //keeps track of the total bet of all opponents.
			int remainingActionAmount = 0; //keeps track of current action remaining
			int rolls; // keeps track of dice rolls.
			String answer; //will contain the answer Y/N to display the rules
			final int minBet = 10; //min bet possible
			boolean betIsValid = false; // used to verify bets are valid.
			boolean shooterWon = false; // used to check if shooter won the round or player.
			boolean shooterNaturalWin = false; // used to check for natural win.
			boolean shooterCrapped = false; // used to check if shooter crapped out.
			boolean didPass = false; // used to check if the game has passed atleast once.
			boolean winner = false; // used to verify if the game has a winner.  
			
		//Step 2) Welcome players to Casino & ask for information about players
			System.out.println("Welcome to Caesar's Kingdom Casino! The game here is craps, so we need to get some information about your party...");
			String[] playerArray = CB_Project_Methods.createPlayerArray();
			
		//Step 3) create bankroll for the players and assign value to bankroll
			int[] bankRollArray = CB_Project_Methods.createBankRollArray(playerArray); // generates player bankrolls
			int totalMoney = CB_Project_Methods.sumArrayContents(bankRollArray); // add's bankroll contents to get the total amount of money in the game.
			System.out.println("\nWe have " + playerArray.length + " players with $100 each for total game money of $" + totalMoney);
	
		//Step 4) Create betAmountArray.
			int[] betAmountArray = new int[playerArray.length];
			
		//Step 5) welcome the players by name and ask if they would like an explanation of the rules.
			for (int i = 0; i < bankRollArray.length; i++) 
			{
			 System.out.print(playerArray[i] + ", ");   
	    }// end for loop
			
			//ask if they would like to see the rules.
			System.out.print("welcome to the game would you like a brief explanation of the rules of the game? Enter Y for yes, or N for no:");
			//force answer to uppercase letter and store in string.
	    answer = input.next().toUpperCase();
	   
	    //prints the rules if the answer is Y.
	    if (answer.contentEquals("Y"))
	    {
	  	 CB_Project_Methods.showRules();
	    }
	    
	  // Step 6) Start the while loop of the game and assign new shooter if need be.
	    shooterId = 0;
	    while (winner == false) 
	    {
	     totalOpponentBet = 0; // reset total bet count
	      if (didPass == true) // only runs if the game has run through more than once.
	      {  
	       if (bankRollArray[shooterId] == 0) // checks if the Shooter has busted out of the game & switches the shooter if busted.
	       {
	        System.out.println("\n" + playerArray[shooterId] + " you have busted out of the game, passing the dice to the next shooter.");
	        shooterId = CB_Project_Methods.getNextShooter(shooterId, bankRollArray, playerArray);
	        System.out.println(playerArray[shooterId] + " is the new shooter, new shooter!!"); 
	        answer = "Y"; //resets the answer to yes so the shooter does not get passed again.
	       }//end inner if
	       else // asks the current shooter if they want to roll again or pass the dice.
	       {
	        System.out.print(playerArray[shooterId] + ", do you want to roll again or pass the dice? Press Y to roll again or press P to pass the dice: ");
	        answer = input.next().toUpperCase();
	       }// end else
	     }// end if
	               
	     if(answer.contentEquals("P")) // check for reply to passing the dice or not. if user enters P to pass will switch shooter.
	     {
	      System.out.println("OK, " + playerArray[shooterId] + " is passing the dice. New shooter coming out, new shooter!"); // tells players of the game a new shooter is coming out.
	      shooterId = CB_Project_Methods.getNextShooter(shooterId, bankRollArray, playerArray); //switches shooter.
	      System.out.println(playerArray[shooterId] + " you are the shooter!"); // announces the new shooter
	      System.out.println("You have $" + bankRollArray[shooterId] + " in your bankroll"); // lets new shooter know their bankroll amount
	     }
	     
	     // Step 7) Ask the shooter to place their bet.
	     System.out.println("\n" + playerArray[shooterId] + " you are the shooter!");
	     System.out.print("Enter your bet amount (min $10, max $"+ bankRollArray[shooterId] + ", must be an exact multiple of $10): $");
	     shooterBet = input.nextInt();
	     remainingActionAmount = shooterBet; // assign the action amount
	     betIsValid = CB_Project_Methods.validateShooterBet(shooterId, shooterBet, bankRollArray, minBet); //ensure the shooter bet is valid
	     
	     while (betIsValid == false) //while loop for if the bet is invalid.
	     {
	      System.out.print("ERROR: Enter your bet, minimum of $10 up to $" + bankRollArray[shooterId] + ", or your bank balance, whichever is less: $" );
	      shooterBet = input.nextInt();
	      betIsValid = CB_Project_Methods.validateShooterBet(shooterId, shooterBet, bankRollArray, minBet); // validate bet
	     	remainingActionAmount = shooterBet;
	     } //end while -- used to validate the shooter bet is valid.
	     
	     //Step 8) Ask opponents to place bets
       for (int i = 1; i < playerArray.length; i++)  // loop to traverse the array.
       {
       	opponentId = (shooterId + i) % playerArray.length; // Get index of opponent in playerArray
        if (bankRollArray[opponentId] == 0) // Skip this opponent and move on to the next one if bank balance = 0
        {
         continue;
        }
        // take the bets.
        System.out.println("\n" + playerArray[opponentId] + ", " + playerArray[shooterId] + " has bet $" + shooterBet + " how much of the $" + remainingActionAmount +" remaining action do you want?");
        System.out.print("Enter your bet, minimum of $10 up to $" + remainingActionAmount + ", or your bank balance, whichever is less: $" );
        opponentBet = input.nextInt();
        totalOpponentBet = totalOpponentBet + opponentBet;
        betIsValid = CB_Project_Methods.validateOpponentBet(opponentId, opponentBet, bankRollArray, minBet, remainingActionAmount);
        
        while (betIsValid == false) //while loop to verify opponent bet is valid.
        {
         System.out.print("ERROR: Enter your bet, minimum of $10 up to $" + remainingActionAmount + ", or your bank balance, whichever is less: $" );
         opponentBet = input.nextInt();
         totalOpponentBet = 0; // reset if there was an error above.
         totalOpponentBet = totalOpponentBet + opponentBet; // add all of the opponents bets together while traversing the array
         betIsValid = CB_Project_Methods.validateOpponentBet(opponentId, opponentBet, bankRollArray, minBet, remainingActionAmount); // validate bet
        }//end while
           
        betAmountArray[opponentId] = opponentBet;
        remainingActionAmount = remainingActionAmount - opponentBet;
        if (remainingActionAmount == 0) // checks if the shoots bet has been completely covered.
        {
         System.out.println("\nThe shooter's bet has been completely covered. NO MORE BETS!.");
         break;
        }//end if
       }//end for loop containing opponent bets
       
       //Step 9) roll the dice and check for shooter to natural win or crap out.
       rolls = CB_Project_Methods.rollDice();
       System.out.println("\n***** Rolling the dice...and the result is: " + rolls + "! *****");
       if (rolls == 7 || rolls == 11) // Check if shooter had a natural win
       {
       	System.out.println("Congratulations " + playerArray[shooterId] + " You have rolled a natural. You win!");
       	shooterWon = true;
       	shooterNaturalWin = true;
       	
       }//end if
       else if (rolls == 2 || rolls == 3 || rolls == 12 ) // Check if shooter crapped out
       {
       	System.out.println("Shooter has CRAPPED out! " + playerArray[shooterId] + " You lose...");
       	shooterCrapped = true;
       	
       }// end else if
       
       //Step 10) Continue rolling the game if a natural or crap has not been rolled.
       else 
       {
      	 System.out.println("\nOk " + playerArray[shooterId] + " your point is " + rolls + ". To win, you need to roll your point again before you roll a seven.");
         System.out.println(); //for line spacing
         System.out.println("Rolling the dice again to try for your point...");
       }
       while (shooterNaturalWin == false && shooterCrapped == false) //while loop to roll the dice until a round winner is decided
      	 {
      	 int nextRolls = CB_Project_Methods.rollDice();
         
         System.out.println("Rolling...you rolled a " + nextRolls);
         if (nextRolls == rolls) 
         {
             shooterWon = true;
             System.out.println("\nCongratulations " + playerArray[shooterId] + "! You rolled your point and won!");
             break;
         } else if (nextRolls == 7) 
         {
             shooterWon = false;
             System.out.println("\nSorry " + playerArray[shooterId] + " you rolled a seven. You lose...");
             break;
         }//end else if
      	 }//end while
       
       //Step 11) Adjust bank balances and check for winner of the game. Otherwise restart loop for game.
       CB_Project_Methods.adjustBankBalances(betAmountArray, bankRollArray, totalOpponentBet, shooterId, shooterWon);
       winner = CB_Project_Methods.checkForWinner(bankRollArray, totalMoney);
       if (winner == true)
       {
       	String theWinner = CB_Project_Methods.identifyWinner(playerArray, bankRollArray, totalMoney);
       	System.out.println("\n***** AND WE HAVE THE GAME WINNER! Congratulations, " + theWinner + "!*****");
       	System.out.println("You have won the total pot of $" + totalMoney);
       	break;
       }//end if 
       System.out.println("\nAfter this pass, here are the bankroll balances for everyone: ");
       CB_Project_Methods.printPlayerBankBalances(playerArray, bankRollArray);
       didPass = true; //  lets the game know a pass has been completed.
       shooterNaturalWin = false; //reset for next round
       shooterCrapped = false; //reset for next round
       Arrays.fill(betAmountArray, 0); //resets the bet amounts in the array to 0 after the round has completed.

     }// end while loop used to control the game.
	    
	    System.out.println("\n\nEnd of Game");
	  	input.close();
	
	
	
	
	
	
	
	
	
	
	
	} //end main

} //end class