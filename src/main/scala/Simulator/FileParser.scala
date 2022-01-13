package Simulator

import java.io.{BufferedReader, IOException, Reader}

object FileParser {  // this object parses files to a Solar System object, i.e. loading a simulation from a file

 def loadSimulation(input: BufferedReader): SolarSystem = {


  var header: String = ""
  var chunkHeader = new Array[Char](3)
  var currentLine = input.readLine()


try {
       var solarSystem: SolarSystem = new SolarSystem(100, 10)

       header = currentLine.take(10)

      if(!header.mkString.startsWith("SIMULATION")) throw new CorruptedSimulatorFileException("Unknown File type")
        var systemFound = false
        var endFound = false
    while (!endFound) {
      currentLine.take(3) match {
        case "SYS" => {
          currentLine = input.readLine()
          systemFound = true
          val SystemInfo = Array[String]()
          val system = currentLine
          val attributes = system.split(";")
          var length: Double = 0
          var interval: Double = 0

            for(string <- attributes) {
              string.charAt(0) match {
                case 'L' => length = string.drop(1).toDouble
                case 'I' => interval = string.drop(1).toDouble
                  }
                }
          solarSystem = new SolarSystem(length, interval)
          currentLine = input.readLine()
        }
        case "OBJ" => {
          if(!systemFound) throw new CorruptedSimulatorFileException("Incorrect segment order")
          val Info = Array[String]()
          currentLine = input.readLine()
          val celObject = currentLine

          val ObjectType = celObject.split(";").takeRight(1)
          val attributes = celObject.split(";").dropRight(1)
          var mass: Double = 0
          var name: String = ""
          var direction: Double = 0
          var radius: Double = 0
          var velocity: Double = 0
          var location: (Double, Double) = (0, 0)
          var massFound = false
          var nameFound = false
          var directionFound = false
          var radiusFound = false
          var velocityFound = false
          var locationFound = false
            for(string <- attributes) {
              string.charAt(0) match {
                case 'M' => {
                  mass = string.drop(1).toDouble
                  massFound = true
                }
                case 'N' => {
                  name = string.drop(1).replace('_', ' ')
                  nameFound = true
                }
                case 'D' => {
                  direction = string.drop(1).toDouble
                  directionFound = true
                }
                case 'R' => {
                  radius = string.drop(1).toDouble
                  radiusFound = true
                }
                case 'V' => {
                  velocity = string.drop(1).toDouble
                  velocityFound = true
                }
                case 'L' => {
                  if(string.drop(1).split(",").length!=2) throw new CorruptedSimulatorFileException("An object's location information is invalid")
                  location = (string.drop(1).split(",").head.toDouble, string.split(",").apply(1).toDouble)
                  if(location._1<0 || location._2<0 || location._1>999 || location._2>999) throw new CorruptedSimulatorFileException("Invalid coordinates")
                  locationFound = true
                }
              }
            }
             if(!massFound || !nameFound || !directionFound || !radiusFound || !velocityFound || !locationFound) throw new CorruptedSimulatorFileException("An object is missing information")
             ObjectType.head match {
               case "STR" => solarSystem.addStar(new Star(mass, radius, velocity, name, direction, location, solarSystem))
               case "PLT" => solarSystem.addPlanet(new Planet(mass, radius, velocity, name, direction, location, solarSystem))
               case "AST" => solarSystem.addAsteroid(new Asteroid(mass, radius, velocity, name, direction, location, solarSystem))
               case "MON" => solarSystem.addMoon(new Moon(mass, radius, velocity, name, direction, location, solarSystem))
               case _ => throw new CorruptedSimulatorFileException("Some celestial objects missing type")
            }
          currentLine = input.readLine()
        }
        case "END" => {
          endFound = true
          input.close()
        }
        case _ => {
            currentLine = input.readLine()
        }
      }
    }




 solarSystem
}
   catch {
            case e:IOException =>


            // To test this part the stream would have to cause an
            // IOException. That's a bit complicated to test. Therefore we have
            // given you a "secret tool", class BrokenReader, which will throw
            // an IOException at a requested position in the stream.
            // Throw the exception inside any chunk, but not in the chunk header.

            val SimExc = new CorruptedSimulatorFileException("Reading the data failed.")

            // Append the information about the initial cause for use in
            // debugging. Otherwise the programmer cannot know the method or
            // line number causing the problem.

            SimExc.initCause(e)

            throw SimExc
        }
 }
}


