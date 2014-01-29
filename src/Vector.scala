import scala.math._

/**
 * Vector in a 2 dimensional universe
 * @param x
 * @param y
 */
case class Vector(x: Double, y: Double) {
  val norm = sqrt(dot(this))

  def +(that: Vector) = Vector(this.x + that.x, this.y + that.y)
  def -(that: Vector) = Vector(this.x - that.x, this.y - that.y)
  def *(c: Double) = Vector(x * c, y * c)
  def /(c: Double) = Vector(x / c, y / c)
  def unary_- = Vector(-x, -y)

  def dot(that: Vector) : Double = this.x * that.x + this.y * that.y
  def project(that: Vector) = that * this.dot(that) / that.dot(that)

  def normalise = this / this.norm
}

object Vector {
  val Zero = Vector(0, 0)
  def fromPolar(r: Double, θ: Double) = Vector(r * cos(θ), r * sin(θ))
}