package pkgPokerBLL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import pkgPokerEnum.eRank;
import pkgPokerEnum.eSuit;

public class Deck {

	private UUID DeckID;
	private ArrayList<Card> DeckCards = new ArrayList<Card>();

	public Deck()
	{
		for (eSuit Suit : eSuit.values())
		{
			for (eRank Rank : eRank.values()) {
				Card car = new Card(Rank,Suit);
				DeckCards.add(car);
			}
		}
		Collections.shuffle(DeckCards);
	}

	public Card DrawCard()
	{
		if(CardsToDraw() > 0){ //if there are cards left, draw one.
			return DeckCards.remove(0);
		}  
		return null; //TODO: Add an exception to handle this. ie: no cards left exception.
	}

	public int CardsToDraw(){
		int numCards =  DeckCards.size()-1;
		System.out.println("There are " + numCards + " Cards Left in the Deck.");
		return numCards;
	}
}

