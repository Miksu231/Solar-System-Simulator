package Simulator

class Planet(objectMass: Double, objectRadius: Double, objectVelocity: Double, objectName: String, objectDirection: Double, objectLocation: (Double, Double), objectSystem: SolarSystem)  extends CelestialObject(objectMass, objectRadius, objectVelocity, objectName, objectDirection, objectLocation, objectSystem){
  // Planet here represents different planets orbiting the star(s) of the system.
 var collisionRange = this.objectRadius/1000
}
