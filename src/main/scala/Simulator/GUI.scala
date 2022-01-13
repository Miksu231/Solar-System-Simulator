package Simulator

import scala.swing.BoxPanel
import java.io._
import scala.swing._
import scala.swing.event._
object startGUI extends SimpleSwingApplication {

 val inputFile = new FileReader("doc\\BaseSystem.txt")
 val reader = new BufferedReader(inputFile)
 val addSystem = Button("Add simulation parameters"){}
 val addStar = Button("Add a new star"){}
 val addPlanet =  Button("Add a new planet"){}
 val addMoon =  Button("Add a new moon"){}
 val addAsteroid = Button("Add a new asteroid"){}
 val loadScenario = Button("Load scenario from a file"){}
 val start =  Button("Start the simulation"){}
 var system = new SolarSystem(100, 1)
 addSystem.visible = true
 loadScenario.visible = true
  addAsteroid.visible = false
  addPlanet.visible = false
  addMoon.visible = false
  start.visible = false
  addStar.visible = false
 def top = new MainFrame {
  title = "Create a new simulation"
  size = maximumSize
  contents = new GridPanel(2, 4) {
  contents += addSystem
  contents += loadScenario
  contents += addStar
  contents += addPlanet
  contents += addMoon
  contents += addAsteroid
  contents += start
    }
  this.listenTo(addSystem)
  this.listenTo(addStar)
  this.listenTo(addPlanet)
  this.listenTo(addMoon)
  this.listenTo(addAsteroid)
  this.listenTo(loadScenario)
  this.listenTo(start)
  this.reactions += {
   case pressed: ButtonClicked => {
      val source = pressed.source
      val text = source.text
      text match {
       case "Add simulation parameters" => {
         var interval = 0.0
         var length = 0.0
         do {
          interval = Dialog.showInput(addSystem, "Insert simulation interval (in days)", "Interval", initial = "0.0").getOrElse("0.0").trim.toDouble
          length = Dialog.showInput(addSystem, "Insert simulation length (in days)", "Length", initial = "0.0").getOrElse("0.0").trim.toDouble
         }
         while(interval>length || length<0 || interval <0)
          addAsteroid.visible = true
          addStar.visible = true
          addPlanet.visible = true
          addMoon.visible = true
          start.visible = true
          loadScenario.visible = false
         system = new SolarSystem(length, interval)
       }
       case "Load scenario from a file" => {
        system = FileParser.loadSimulation(reader)
        start.visible = true
        addSystem.visible = false
       }
       case "Add a new star" =>
         var mass: Double = 0.0
         var radius: Double = 0.0
         var velocity = 0.0
         var direction = 0.0
         var location = Array[String]()
         var location2 = (0.0, 0.0)
         var name = ""
         do {
            mass = Dialog.showInput(addStar, "Insert mass of the star (in kg)", "Mass", initial = "0.0").getOrElse("0.0").trim.toDouble
            radius = Dialog.showInput(addStar, "Insert radius of the star (in km)", "Radius", initial = "0.0").getOrElse("0.0").trim.toDouble
            velocity = Dialog.showInput(addStar, "Insert the velocity of the star (in km/s)", "Velocity", initial = "0.0").getOrElse("0.0").trim.toDouble
            name = Dialog.showInput(addStar, "Insert the name of the star", "Name", initial = "Name").getOrElse("Star").trim
            location = Dialog.showInput(addStar, "Insert the location of the star in the simulation (must be between 0, 0 and 999, 999)", "Location", initial = "0.0 , 0.0").getOrElse("0.0,0.0").split(",").map(_.trim)
                while(location.length != 2) location = Dialog.showInput(addStar, "Insert the location of the star in the simulation (must be between 0, 0 and 999, 999)", "Location", initial = "0.0 , 0.0").getOrElse("0.0,0.0").split(",").map(_.trim)
            location2 = (location.head.toDouble, location.last.toDouble)
            direction = Dialog.showInput(addStar, "Insert the direction of the star's velocity (an angle between 0-360, starting clockwise from straight up)", "Direction", initial = "0.0").getOrElse("0.0").trim.toDouble
      } while(mass<=0 || radius<=0 || velocity<0 || (location2._1<0 || location2._2<0 || location2._1>999 ||location2._2>999) || direction<0 || direction>360)
             system.addStar(new Star(mass, radius, velocity, name, direction, location2, system))
       case "Add a new planet" =>
         var mass: Double = 0.0
         var radius: Double = 0.0
         var velocity = 0.0
         var direction = 0.0
         var location = Array[String]()
         var location2 = (0.0, 0.0)
         var name = ""
         do {
            mass = Dialog.showInput(addPlanet, "Insert mass of the planet (in kg)", "Mass", initial = "0.0").getOrElse("0.0").trim.toDouble
            radius = Dialog.showInput(addPlanet, "Insert radius of the planet (in km)", "Radius", initial = "0.0").getOrElse("0.0").trim.toDouble
            velocity = Dialog.showInput(addPlanet, "Insert the velocity of the planet (in km/s)", "Velocity", initial = "0.0").getOrElse("0.0").trim.toDouble
            name = Dialog.showInput(addPlanet, "Insert the name of the planet", "Name", initial = "Name").getOrElse("Star").trim
            location = Dialog.showInput(addPlanet, "Insert the location of the planet in the simulation (must be between 0, 0 and 999, 999)", "Location", initial = "0.0 , 0.0").getOrElse("0.0,0.0").split(",").map(_.trim)
                while(location.length != 2) location = Dialog.showInput(addPlanet, "Insert the location of the planet in the simulation (must be between 0, 0 and 999, 999)", "Location", initial = "0.0 , 0.0").getOrElse("0.0,0.0").split(",").map(_.trim)
            location2 = (location.head.toDouble, location.last.toDouble)
            direction = Dialog.showInput(addPlanet, "Insert the direction of the star's velocity (an angle between 0-360, starting clockwise from straight up)", "Direction", initial = "0.0").getOrElse("0.0").trim.toDouble
      } while(mass<=0 || radius<=0 || velocity<0 || (location2._1<0 || location2._2<0 || location2._1>999 ||location2._2>999) || direction<0 || direction>360)
             system.addPlanet(new Planet(mass, radius, velocity, name, direction, location2, system))
       case "Add a new moon" =>
         var mass: Double = 0.0
         var radius: Double = 0.0
         var velocity = 0.0
         var direction = 0.0
         var location = Array[String]()
         var location2 = (0.0, 0.0)
         var name = ""
         do {
            mass = Dialog.showInput(addMoon, "Insert mass of the moon (in kg)", "Mass", initial = "0.0").getOrElse("0.0").trim.toDouble
            radius = Dialog.showInput(addMoon, "Insert radius of the moon (in km)", "Radius", initial = "0.0").getOrElse("0.0").trim.toDouble
            velocity = Dialog.showInput(addMoon, "Insert the velocity of the moon (in km/s)", "Velocity", initial = "0.0").getOrElse("0.0").trim.toDouble
            name = Dialog.showInput(addMoon, "Insert the name of the moon", "Name", initial = "Name").getOrElse("Star").trim
            location = Dialog.showInput(addMoon, "Insert the location of the moon in the simulation (must be between 0, 0 and 999, 999)", "Location", initial = "0.0 , 0.0").getOrElse("0.0,0.0").split(",").map(_.trim)
                while(location.length != 2) location = Dialog.showInput(addMoon, "Insert the location of the moon in the simulation (must be between 0, 0 and 999, 999)", "Location", initial = "0.0 , 0.0").getOrElse("0.0,0.0").split(",").map(_.trim)
            location2 = (location.head.toDouble, location.last.toDouble)
            direction = Dialog.showInput(addMoon, "Insert the direction of the moon's velocity (an angle between 0-360, starting clockwise from straight up)", "Direction", initial = "0.0").getOrElse("0.0").trim.toDouble
      } while(mass<=0 || radius<=0 || velocity<0 || (location2._1<0 || location2._2<0 || location2._1>999 ||location2._2>999) || direction<0 || direction>360)
             system.addMoon(new Moon(mass, radius, velocity, name, direction, location2, system))
       case "Add a new asteroid" =>
         var mass: Double = 0.0
         var radius: Double = 0.0
         var velocity = 0.0
         var direction = 0.0
         var location = Array[String]()
         var location2 = (0.0, 0.0)
         var name = ""
         do {
            mass = Dialog.showInput(addAsteroid, "Insert mass of the asteroid (in kg)", "Mass", initial = "0.0").getOrElse("0.0").trim.toDouble
            radius = Dialog.showInput(addAsteroid, "Insert radius of the asteroid (in km)", "Radius", initial = "0.0").getOrElse("0.0").trim.toDouble
            velocity = Dialog.showInput(addAsteroid, "Insert the velocity of the asteroid (in km/s)", "Velocity", initial = "0.0").getOrElse("0.0").trim.toDouble
            name = Dialog.showInput(addAsteroid, "Insert the name of the asteroid", "Name", initial = "Name").getOrElse("Star").trim
            location = Dialog.showInput(addAsteroid, "Insert the location of the asteroid in the simulation (must be between 0, 0 and 999, 999)", "Location", initial = "0.0 , 0.0").getOrElse("0.0,0.0").split(",").map(_.trim)
                while(location.length != 2) location = Dialog.showInput(addAsteroid, "Insert the location of the asteroid in the simulation (must be between 0, 0 and 999, 999)", "Location", initial = "0.0 , 0.0").getOrElse("0.0,0.0").split(",").map(_.trim)
            location2 = (location.head.toDouble, location.last.toDouble)
            direction = Dialog.showInput(addAsteroid, "Insert the direction of the asteroid's velocity (an angle between 0-360, starting clockwise from straight up)", "Direction", initial = "0.0").getOrElse("0.0").trim.toDouble
      } while(mass<=0 || radius<=0 || velocity<0 || (location2._1<0 || location2._2<0 || location2._1>999 ||location2._2>999) || direction<0 || direction>360)
             system.addAsteroid(new Asteroid(mass, radius, velocity, name, direction, location2, system))
       case "Start the simulation" => {
         SimulationGUI
       }
          }
        }
     }
  }
def SimulationGUI {
  val stop = new Button("Stop simulation"){
   preferredSize = new Dimension(300, 100)
  }
  val resume = new Button("Resume simulation"){
   preferredSize = new Dimension(300, 100)
  }
  val pause = new Button("Pause simulation"){
       preferredSize = new Dimension(300, 100)
  }
  val screen = new BoxPanel(Orientation.Vertical)
  val buttons = new GridPanel(1, 3) {
   preferredSize = new Dimension(1000, 100)
  }
  val canvas2 = new Canvas(system) {
   preferredSize = new Dimension(1000, 1000)
  }
   stop.visible = true
   resume.visible = true
   pause.visible = true
   buttons.contents += stop
   buttons.contents += resume
   buttons.contents += pause
   buttons.visible = true
   screen.contents += buttons
   screen.contents += canvas2
  screen.visible = true
 def top = new MainFrame {
  title = "Solar system simulator"
  size = maximumSize
  contents = screen
  }
  this.listenTo(stop)
  this.listenTo(resume)
  this.listenTo(pause)
  this.reactions += {
   case pressed: ButtonClicked => {
      val source = pressed.source
      val text = source.text
      text match {
        case "Stop simulation" => system.endSimulation
        case "Resume simulation" => system.resumeSimulation
        case "Pause simulation" => system.pauseSimulation
          }
       }
     }
  top.visible = true
  }
}



