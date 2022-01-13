package Simulator

class Star(objectMass: Double, objectRadius: Double, objectVelocity: Double, objectName: String, objectDirection: Double, objectLocation: (Double, Double), objectSystem: SolarSystem)  extends CelestialObject(objectMass, objectRadius, objectVelocity, objectName, objectDirection, objectLocation, objectSystem){
  // Star represents the central star(s) of the solar system.
 var collisionRange = this.objectRadius/20000
}
