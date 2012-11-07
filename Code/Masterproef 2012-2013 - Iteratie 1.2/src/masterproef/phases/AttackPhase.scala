package masterproef.phases

import masterproef.actions.AttackAction
import masterproef.actions.DeclareAttackAction
import masterproef.actions.DeclareDefenceAction

class AttackPhase extends GamePhase("Attack Phase") {
	this +=  new DeclareAttackAction
	this +=  new DeclareDefenceAction
	this +=  new AttackAction
}