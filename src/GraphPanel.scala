import java.awt.Graphics2D
import scala.swing.Panel

class GraphPanel(sys : System) extends Panel {

  override def paintComponent(g : Graphics2D) { sys.boundaries match {
    case (width, height) =>
      val velocities = sys.particles.map(_.velocity.norm)
      for (i <- 0 to 50) {
        val n = velocities.count { x => x >= i && x < i + 1 }
        g.fillRect(i * 6, height - n * 3, 6, height)
      }
  }
  }

}
