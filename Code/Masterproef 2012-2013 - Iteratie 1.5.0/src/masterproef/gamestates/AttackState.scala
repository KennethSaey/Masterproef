package masterproef.gamestates

import masterproef.Masterproef
import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.GameContainer
import org.newdawn.slick.gui.MouseOverArea
import masterproef.cards.Creature
import scala.collection.mutable.ArrayBuffer
import masterproef.cards.Card
import org.newdawn.slick.gui.ComponentListener
import org.newdawn.slick.gui.AbstractComponent
import org.newdawn.slick.Graphics
import org.newdawn.slick.Color
import scala.collection.mutable.HashMap

class AttackState extends BasicPlayState with ComponentListener {

	val attackerAreas: ArrayBuffer[MouseOverArea] = new ArrayBuffer[MouseOverArea]
	val blockerAreas: ArrayBuffer[MouseOverArea] = new ArrayBuffer[MouseOverArea]
	val attackerAreaMap: HashMap[Creature, MouseOverArea] = new HashMap[Creature, MouseOverArea]()
	var continueButton: MouseOverArea = null
	var currentAttackerIndex: Int = -1

	def getID(): Int = Masterproef.ATTACK_ID

	def componentActivated(source: AbstractComponent) {
		if (source == continueButton) {
			if (currentAttackerIndex + 1 < model.attackers.size) {
				currentAttackerIndex += 1
				val attacker = model.attackers(currentAttackerIndex).asInstanceOf[Creature]
				attackerAreaMap.values.foreach(_.setNormalImage(textures("front unselectable")))
				model.message = attacker + " attacks!"
				attackerAreaMap(attacker).setNormalImage(textures("front hover"))
				if (model.blockers.contains(attacker)) {
					for (blocker <- model.blockers(attacker)) {
						attacker.doDamageTo(blocker.asInstanceOf[Creature])
						blocker.asInstanceOf[Creature].doDamageTo(attacker)
						if(attacker.health == 0){
							attacker.kill
							model.player.kill(attacker)
						}
						if(blocker.asInstanceOf[Creature].health == 0){
							blocker.asInstanceOf[Creature].kill
							model.opponent.kill(blocker.asInstanceOf[Creature])
						}
					}
				} else {
					model.player.attack(model.opponent, attacker)
				}
			} else {
				println("CONTINUE TO NEXT PHASE")
				model.attackers.clear
				model.blockers.clear
				if(model.opponent.health <= 0){
					println("GAME SHOULD END")
					model.winner = model.player
					game.enterState(Masterproef.GAME_OVER_ID)
				}else{
					println("GAME MAY CONTINUE")
					model.nextPhase
				}
			}
		}
	}

	override def init(container: GameContainer, game: StateBasedGame) {
		super.init(container, game)
		val xOffset = gameAreas("playerDeck").getX() + (gameAreas("playerDeck").getWidth() - 110) / 2
		val yOffsetBlock = gameAreas("playerDeck").getY() - 60
		continueButton = new MouseOverArea(container, buttons("button"), xOffset, yOffsetBlock, 110, 50, this)
		continueButton.setMouseOverImage(buttons("hover"))
		continueButton.setMouseDownImage(buttons("mousedown"))
	}

	override def render(container: GameContainer, game: StateBasedGame, g: Graphics) {
		super.render(container, game, g)
		renderButton(container, g, continueButton, if(currentAttackerIndex == -1) "Start Attack" else "Continue")
	}

	def renderCreature(creature: Creature, container: GameContainer, g: Graphics, area: MouseOverArea) {
		area.render(container, g)
		g.setFont(BasicPlayState.normalFont)
		g.setColor(Color.black)
		g.drawString(creature.name, area.getX() + 26, area.getY() + 23)
		g.drawImage(images(creature.name), area.getX() + 25, area.getY() + 33, area.getX() + 135, area.getY() + 114, 0, 0, 220, 161)
		g.drawString(creature.abilities.mkString(" | "), area.getX() + 26, area.getY() + 116)
		g.setFont(BasicPlayState.largeFont)
		g.drawString(creature.damage + " / " + creature.health, area.getX() + 35, area.getY() + 150)
	}

	override def renderPlayerBattlefield(container: GameContainer, game: StateBasedGame, g: Graphics) {
		val step = if (model.player.battlefield.size <= 1) 0 else scala.math.min(160, (gameAreas("playerBattlefield").getWidth() - 160) / (model.player.battlefield.size - 1))
		val battlefieldSize = model.player.battlefield.size
		if (attackerAreas.size >= battlefieldSize) {
			for (i <- 0 until model.player.battlefield.size) {
				renderCreature(model.player.battlefield(i).asInstanceOf[Creature], container, g, attackerAreas(i))
			}
		}
	}

	override def enter(container: GameContainer, game: StateBasedGame) {
		super.enter(container, game)
		println("Entering AttackState for " + model.player)
		attackerAreas.clear
		attackerAreaMap.clear
		currentAttackerIndex = -1
		val attackersStep = if (model.player.battlefield.size <= 1) 0 else scala.math.min(160, (gameAreas("playerBattlefield").getWidth() - 160) / (model.player.battlefield.size - 1))
		for (i <- 0 until model.player.battlefield.size) {
			if (model.attackers.contains(model.player.battlefield(i))) {
				attackerAreas += new MouseOverArea(container, textures("front unselectable"), gameAreas("playerBattlefield").getX() + i * attackersStep, gameAreas("playerBattlefield").getY())
				attackerAreaMap(model.player.battlefield(i).asInstanceOf[Creature]) = attackerAreas.last
			} else {
				attackerAreas += new MouseOverArea(container, textures("front"), gameAreas("playerBattlefield").getX() + i * attackersStep, gameAreas("playerBattlefield").getY())
			}
		}

		blockerAreas.clear
		if (model.blockers.size > 0) {
			val blockersStep = if (model.opponent.battlefield.size <= 1) 0 else scala.math.min(160, (gameAreas("opponentBattlefield").getWidth() - 160) / (model.opponent.battlefield.size - 1))
			for (i <- 0 until model.opponent.battlefield.size) {
				val distinctBlockers = model.blockers.flatMap(_._2).asInstanceOf[ArrayBuffer[Card]].distinct
				if (distinctBlockers.contains(model.opponent.battlefield(i))) {
					blockerAreas += new MouseOverArea(container, textures("front selected"), gameAreas("opponentBattlefield").getX() + i * blockersStep, gameAreas("opponentBattlefield").getY())
				}
			}
		}
	}
}