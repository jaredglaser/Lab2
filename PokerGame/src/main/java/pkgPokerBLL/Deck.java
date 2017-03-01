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
		//TODO: Implement This Constructor (no-arg Deck should build up a deck with 52 cards)
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
		return DeckCards.remove(0);
	}
}
