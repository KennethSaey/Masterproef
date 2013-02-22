package masterproef.views

import scala.swing.BorderPanel
import scala.swing.SequentialContainer
import scala.swing.Panel
import java.awt.Graphics2D

class Gameboard(val parent: Panel) extends BorderPanel with SequentialContainer.Wrapper {
	
	val deck: DeckView = new DeckView(this, 20)
	val graveyard: GraveyardView = new GraveyardView(this, 0)
	val hand: HandView = new HandView(this, 0)
	val battlefield: BattlefieldView = new BattlefieldView(this, 0)
	
	add(new BorderPanel(){
		add(deck, BorderPanel.Position.South)
	}, BorderPanel.Position.West)
	add(new BorderPanel(){
		add(graveyard, BorderPanel.Position.South)
	}, BorderPanel.Position.East)
	add(new BorderPanel(){
		add(hand, BorderPanel.Position.South)
	}, BorderPanel.Position.Center)
	add(new BorderPanel(){
		add(battlefield, BorderPanel.Position.South)
	}, BorderPanel.Position.North)
}