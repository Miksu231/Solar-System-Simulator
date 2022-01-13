package Simulator

import scala.math._

abstract class CelestialObject(objectMass: Double, objectRadius: Double, objectVelocity: Double, objectName: String, var objectDirection: Double, var objectLocation: (Double, Double), objectSystem: SolarSystem) {

 //CelestialObject is an abstract class which acts to represent all kinds of objects in the solar system we are representing, they have common properties like mass, direction, radius, etc and all obey the same laws of physics.

private val mass = this.objectMass //mass in kg
val name = this.objectName //name
var system = this.objectSystem
val radius = this.objectRadius //radius in km
private val direction = this.objectDirection //direction in compass direction
var location = objectLocation
private val startvelocity= {
    val rad = (direction-90.0)*Pi/180.0
    val vectordire = new Vector2D(this.location._1, this.location._2,cos(rad), sin(rad))
    val vector2 = vectordire.*(objectVelocity)
  vector2
}
var velocity = startvelocity
var collisionRange: Double
var heading: Vector2D = startvelocity


 private def gravForce(m1: Double, m2: Double, dist: Double): Double = { // this calculates the gravitational force, using Newton's law of universal gravitation
   // masses in kg and distance in km
  val G = 6.6743e-11 //gravitational constant
  G*(m1*m2)/(pow(dist*1000, 2))
}

 private def makeVector(s2: CelestialObject): Vector2D = { // creates a new 2D Vector from one object towards another.
   val a = (gravForce(this.mass, s2.mass, new Vector2D(this.location._1, this.location._2, (s2.location._1-this.location._1), (s2.location._2-this.location._2)).length)/this.mass) // Newton's second law, a = F/m
   val directionVect = new Vector2D(this.location._1, this.location._2, (s2.location._1-this.location._1), (s2.location._2-this.location._2))
   directionVect./(directionVect.length).*((a*(this.system.simulationInterval*86400)))./(7e12)
  }

 def isCollided: Boolean = { // checks if the distance to any object is smaller or equal to the sum of the radiuses of the two objects. If they are, this object has collided with it.
  var initialAst = false
  var initialStar = false
  var initialPlanet = false
  var initialMoon = false
   //each of these calculates the distance from the center of one to the other, and if the distance is greater than their summed radiuses
 if(this.system.asteroids.nonEmpty) initialAst = !this.system.asteroids.filter(x=> x!=this).forall(x=> new Vector2D(this.location._1, this.location._2, x.location._1-this.location._1, x.location._2-this.location._2).length>sqrt(2*pow((this.collisionRange+x.collisionRange), 2)))
 if(this.system.stars.nonEmpty) initialStar = !this.system.stars.filter(x=> x!=this).forall(x=> new Vector2D(this.location._1, this.location._2, x.location._1-this.location._1, x.location._2-this.location._2).length>sqrt(2*pow((this.collisionRange+x.collisionRange), 2)))
 if(this.system.planets.nonEmpty) initialPlanet = !this.system.planets.filter(x=> x!=this).forall(x=> new Vector2D(this.location._1, this.location._2, x.location._1-this.location._1, x.location._2-this.location._2).length>sqrt(2*pow((this.collisionRange+x.collisionRange), 2)))
 if(this.system.moons.nonEmpty) initialMoon = !this.system.moons.filter(x=> x!=this).forall(x=> new Vector2D(this.location._1, this.location._2, x.location._1-this.location._1, x.location._2-this.location._2).length>sqrt(2*pow((this.collisionRange+x.collisionRange), 2)))
  (initialAst || initialStar || initialMoon || initialPlanet)
 }

def move: Unit = {
 val nextmove = velocity.+(heading)
 location = (location._1 + nextmove.x, location._2 + nextmove.y)
  velocity = nextmove
}


  def calculateOrbit: Vector2D = { // calculate the sum of all gravitational forces affecting the object
      var output: Vector2D = new Vector2D(this.location._1, this.location._2, 0, 0)
      var gravPlanet = Array[Vector2D]()
      var gravStar = Array[Vector2D]()
      var gravAst = Array[Vector2D]()
      var gravMoon = Array[Vector2D]()
          for(planet <- this.system.planets.filter(x=> x!= this)) gravPlanet = gravPlanet :+ makeVector(planet)
          for(star <- this.system.stars.filter(x=> x!= this)) gravStar = gravStar :+ makeVector(star)
          for(asteroid <- this.system.asteroids.filter(x=> x!= this)) gravAst = gravAst :+ makeVector(asteroid)
          for(moon <- this.system.moons.filter(x=> x!= this)) gravMoon = gravMoon :+ makeVector(moon)
     var arrayHolder = Array[Vector2D]()
    if(gravPlanet.nonEmpty) arrayHolder = arrayHolder :+ gravPlanet.reduceLeft(_.+(_))
    if(gravStar.nonEmpty) arrayHolder = arrayHolder :+ gravStar.reduceLeft(_.+(_))
    if(gravAst.nonEmpty) arrayHolder = arrayHolder :+ gravAst.reduceLeft(_.+(_))
    if(gravMoon.nonEmpty) arrayHolder = arrayHolder :+ gravMoon.reduceLeft(_.+(_))
    if(arrayHolder.nonEmpty)  output = output.+(arrayHolder.reduceLeft(_.+(_)))
    heading = output
    output
  }
}