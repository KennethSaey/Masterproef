package masterproef.views

import scala.swing.BorderPanel
import scala.swing.SequentialContainer
import scala.swing.Panel

class Gameboard(val parent: Panel) extends BorderPanel with SequentialContainer.Wrapper {

//	add(new DeckView, BorderPanel.Position.West)
//	add(new GraveyardView, BorderPanel.Position.East)
	
	add(new BorderPanel(){
		add(new DeckView, BorderPanel.Position.South)
	}, BorderPanel.Position.West)
	add(new BorderPanel(){
		add(new GraveyardView, BorderPanel.Position.South)
	}, BorderPanel.Position.East)
	add(new BorderPanel(){
//		add(new BorderPanel(){
//			add(new HandView, BorderPanel.Position.Center)
//		}, BorderPanel.Position.South)
		add(new HandView(), BorderPanel.Position.South)
	}, BorderPanel.Position.Center)
//	add(new HandView(this), BorderPanel.Position.Center)

}