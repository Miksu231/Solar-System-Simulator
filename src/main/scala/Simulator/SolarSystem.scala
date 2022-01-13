package Simulator

import java.awt.Graphics2D
import scala.swing.Panel
import javax.swing.Timer
import java.awt.event._
import java.awt.Font

class SolarSystem(length: Double, interval: Double) {
 //This class represents the whole of the solar system and has references to all objects in the solar system as well as functions to add celestial objects through the GUI.

var stars = Array[Star]()

var planets = Array[Planet]()

var asteroids = Array[Asteroid]()

var moons = Array[Moon]()

var simulationLength: Double = length   //length in days

var simulationInterval: Double = interval // interval in days

def addPlanet(planet: Planet) = {
  planet.system = this
  this.planets = this.planets :+ planet
}
def addStar(star: Star) = {
  star.system = this
  this.stars = this.stars :+ star
}

  def addMoon(moon: Moon) = {
    moon.system = this
    this.moons = this.moons :+ moon
  }

  def addAsteroid(asteroid: Asteroid) = {
    asteroid.system = this
    this.asteroids = this.asteroids :+ asteroid
  }

var simulationRunning: Boolean = false
var time = 0.0
var isCollided: Boolean = false



def clock: Unit = {
  if(time>=simulationLength) timer.setRepeats(false)
  for(x <- this.asteroids) {
   x.calculateOrbit
   }
  for(y <- this.stars) {
   y.calculateOrbit
   }
  for(z <- this.planets) {
   z.calculateOrbit
   }
  for(t <- this.moons) {
   t.calculateOrbit
   }

  for(x <- this.asteroids) {
    x.move
   }
  for(y <- this.stars) {
   y.move
   }
  for(z <- this.planets) {
   z.move
   }
   for(t <- this.moons) {
   t.move
   }
  if(this.asteroids.nonEmpty) if(this.asteroids.exists(_.isCollided))this.isCollided = true
  if(this.stars.nonEmpty) if(this.stars.exists(_.isCollided)) this.isCollided=true
  if(this.planets.nonEmpty)if(this.planets.exists(_.isCollided)) this.isCollided=true
  if(this.moons.nonEmpty)if(this.moons.exists(_.isCollided)) this.isCollided=true

  if(isCollided) {
    timer.setRepeats(false)

  }
   time = time + this.simulationInterval

 }
      val timeOut = new javax.swing.AbstractAction() {
      def actionPerformed(e : java.awt.event.ActionEvent): Unit = clock
    }
    val timer = new Timer(33, timeOut)

def pauseSimulation: Unit = timer.stop()

def resumeSimulation: Unit = {
 if(timer.isRepeats)timer.start()
}

def endSimulation: Unit = sys.exit(0)

}
class Canvas(sys: SolarSystem) extends Panel {

  override def paintComponent(g: Graphics2D) {
    g.setFont(new Font("Times New Roman", Font.PLAIN, 13))

    // Start by erasing this Canvas
    g.clearRect(0, 0, size.width, size.height)

    // Draw background here
    g.setColor(java.awt.Color.black)
    g.fillRect(0, 0, 1000, 1000)

    for(star <- sys.stars) {
      g.setColor(java.awt.Color.yellow)
      g.drawString(star.name, (star.location._1 - 20).round, (star.location._2-50).round)
      g.fillOval((star.location._1 - star.radius/20000).round.toInt, (star.location._2- star.radius/20000).round.toInt, (star.radius/10000).round.toInt, (star.radius/10000).round.toInt)
      g.drawLine((star.location._1 - star.radius/20000).round.toInt, (star.location._2- star.radius/20000).round.toInt, (star.location._1+star.velocity.+(star.calculateOrbit).x).round.toInt, (star.location._2+star.velocity.+(star.calculateOrbit).y).round.toInt)
      repaint()
    }
      for(planet <- sys.planets) {
      g.setColor(java.awt.Color.green)
      g.drawString(planet.name, (planet.location._1 - 15).round, (planet.location._2-30).round)
      g.fillOval((planet.location._1-planet.radius/1000).round.toInt,(planet.location._2-planet.radius/1000).round.toInt, (planet.radius/500).round.toInt, (planet.radius/500).round.toInt)
      g.drawLine((planet.location._1-planet.radius/1000).round.toInt,(planet.location._2-planet.radius/1000).round.toInt, (planet.location._1+planet.velocity.+(planet.calculateOrbit).x).round.toInt,(planet.location._2+planet.velocity.+(planet.calculateOrbit).y).round.toInt)
      repaint()
      }
      for(moon <- sys.moons) {
      g.setColor(java.awt.Color.lightGray)
      g.drawString(moon.name, (moon.location._1 - 10).round, (moon.location._2-20).round)
      g.fillOval((moon.location._1-moon.radius/400).round.toInt, (moon.location._2-moon.radius/400).round.toInt, (moon.radius/200).round.toInt, (moon.radius/200).round.toInt)
      g.drawLine((moon.location._1-moon.radius/400).round.toInt, (moon.location._2-moon.radius/400).round.toInt,(moon.location._1+moon.velocity.+(moon.calculateOrbit).x).round.toInt, (moon.location._2+moon.velocity.+(moon.calculateOrbit).y).round.toInt)
      repaint()
        }
      for(asteroid <- sys.asteroids) {
      g.setColor(java.awt.Color.red)
      g.drawString(asteroid.name, (asteroid.location._1).round, (asteroid.location._2-10).round)
      g.fillOval((asteroid.location._1-asteroid.radius/2).round.toInt, (asteroid.location._2-asteroid.radius/2).round.toInt, (asteroid.radius).round.toInt, (asteroid.radius).round.toInt)
      g.drawLine((asteroid.location._1-asteroid.radius/2).round.toInt, (asteroid.location._2-asteroid.radius/2).round.toInt, (asteroid.location._1+asteroid.velocity.+(asteroid.calculateOrbit).x).round.toInt, (asteroid.location._1+asteroid.velocity.+(asteroid.calculateOrbit).y).round.toInt)
      repaint()
    }
  }
}


