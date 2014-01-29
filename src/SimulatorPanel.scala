import java.awt.Graphics2D
import scala.swing.Panel

class SimulatorPanel(sys : System) extends Panel {

  override def paintComponent(g : Graphics2D) {
    for(p : Particle <- sys.particles) {
      g.fillOval(p.position.x.toInt, p.position.y.toInt, p.radius.toInt * 2, p.radius.toInt * 2)
    }
  }

}
