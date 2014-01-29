import java.awt.{Color, Dimension}
import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.Timer
import scala.swing.{BoxPanel, Button, MainFrame, Frame}
import scala.util.Random

object Main extends App {
  var sys = new System((300, 300))

  sys.spawn(1000, 2)

  val window = new MainFrame {
    contents = new BoxPanel(scala.swing.Orientation.Horizontal) {
      contents += new SimulatorPanel(sys) {
        preferredSize = new Dimension(300, 300)
      }
      contents += new GraphPanel(sys) {
        preferredSize = new Dimension(300, 300)
      }
      contents += Button("Add particle") {
        sys.spawn(1, 2)
      }
    }
  }

  override def main(args: Array[String]) {
    super.main(args)
    window.visible = true

    val taskPerformer = new ActionListener {
      def actionPerformed(e: ActionEvent) {
        sys.step
        window.repaint()
      }
    }
    new Timer(10, taskPerformer).start()
  }
}
