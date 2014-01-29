import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * Models a mutable system of particles interactions with each-others
 * @param particles
 */
class System(val boundaries: (Int, Int), var particles: ArrayBuffer[Particle] = ArrayBuffer()) {
  val stepInterval = 0.1

  // Add a particle to the system
  def +=(p: Particle): System = {
    // Ensures the particle lies withing the boundaries and isn't colliding with anything
    if (isWithinBoundaries(p) && particles.foldLeft(true) { _ && !_.overlapsWith(p) }) {
      particles += p
    }
    return this
  }

  def reset {
    particles.clear()
  }

  def spawn(n: Integer, radius: Int) {
    val rand = new Random
    boundaries match {
      case (width, height) =>
        for (_ <- 1 to n) {
          this += new Particle(Vector(rand.nextInt(width), rand.nextInt(height)), Vector(10, 10), radius)
        }
    }
  }

  def step {
    particles.map { _.step(stepInterval) }
    // TODO: Use combinations
    for (p <- particles) {
      if (!isWithinBoundaries(p)) collideWithBoundaries(p)
      for (q <- particles if p != q && (p overlapsWith q)) {
        collide(p, q)
      }
    }
  }

  def collide(p: Particle, q: Particle) {
    val l = q.position - p.position

    q.position = p.position + l.normalise * (p.radius + q.radius)

    val projP = p.velocity.project(l)
    val projQ = q.velocity.project(l)

    p.velocity = p.velocity - projP + projQ
    q.velocity = q.velocity - projQ + projP
  }

  def isWithinBoundaries(p: Particle): Boolean = boundaries match {
    case (width, height) =>
      (p.position.x - p.radius) > 0 && (p.position.x + p.radius) < width && (p.position.y - p.radius) > 0 && (p.position.y + p.radius) < height
  }

  def collideWithBoundaries(p: Particle) {
    boundaries match {
      case (width, height) =>
        if (p.position.x - p.radius < 0) {
          p.velocity = Vector(-p.velocity.x, p.velocity.y)
          p.position = Vector(p.radius, p.position.y)
        }
        if (p.position.x + p.radius > width) {
          p.velocity = Vector(-p.velocity.x, p.velocity.y)
          p.position = Vector(width - p.radius, p.position.y)
        }
        if (p.position.y - p.radius < 0) {
          p.velocity = Vector(p.velocity.x, -p.velocity.y)
          p.position = Vector(p.position.x, p.radius)
        }
        if (p.position.y + p.radius > height) {
          p.velocity = Vector(p.velocity.x, -p.velocity.y)
          p.position = Vector(p.position.x, height - p.radius)
        }
    }
  }
}
