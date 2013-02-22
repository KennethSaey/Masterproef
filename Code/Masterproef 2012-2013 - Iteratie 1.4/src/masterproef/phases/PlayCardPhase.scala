package masterproef.phases

import masterproef.actions.PlayCardAction

class PlayCardPhase extends GamePhase("Play Card Phase") {

	this += new PlayCardAction
}