package masterproef

import scala.swing.Publisher

// First create a TimerEvent since using an ActionEvent necessitates that ScalaTimer extend
// Component, which isn't ideal as the OP indicated 
case class TimerEvent() extends scala.swing.event.Event

// extending javax.swing.Timer by and large avoids the hassle of writing wrapper methods
class ScalaTimer(val delay0:Int) extends javax.swing.Timer(delay0, null) with Publisher {

  // to mimic the swing Timer interface with an easier-to-user scala closure
  def this(delay0:Int, action:(()=>Unit)) = {
    this(delay0)
    reactions += {
      case TimerEvent() => action()
    }
  }

  addActionListener(new java.awt.event.ActionListener {
    def actionPerformed(e: java.awt.event.ActionEvent) = publish(TimerEvent())
  })
}