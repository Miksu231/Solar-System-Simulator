package Simulator

class Asteroid(objectMass: Double, objectRadius: Double, objectVelocity: Double, objectName: String, objectDirection: Double, objectLocation: (Double, Double), objectSystem: SolarSystem)  extends CelestialObject(objectMass, objectRadius, objectVelocity, objectName, objectDirection, objectLocation, objectSystem){
  // Asteroid here represents a asteroids, comets and satellites
 var collisionRange = this.objectRadius/2
}


