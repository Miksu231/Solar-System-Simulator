import Simulator._
object SolarSystemTest extends App{

  val system = new SolarSystem(1000, 1)

  val star = new Star(1.9885e30, 696342.0, 5.0, "The Sun", 0, (250, 250), system)

  val planet = new Planet(1000, 6372, 30, "Earth", 90, (100, 100), system)

  println(planet.calculateOrbit.x, planet.calculateOrbit.y)
  planet.calculateOrbit
  planet.move
   println(planet.calculateOrbit.x, planet.calculateOrbit.y)
}
