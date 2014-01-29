/**
 * Models a mutable particle
 * @param position
 * @param velocity
 * @param radius
 */
class Particle(var position : Vector, var velocity : Vector = Vector.Zero, val radius : Double = 1) {

  def overlapsWith(that : Particle) = (this.position - that.position).norm < this.radius + that.radius

  def step(t : Double) {
    position = position + velocity * t
  }
}
