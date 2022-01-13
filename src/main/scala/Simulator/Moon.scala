package Simulator

class Moon(objectMass: Double, objectRadius: Double, objectVelocity: Double, objectName: String, objectDirection: Double, objectLocation: (Double, Double), objectSystem: SolarSystem)  extends CelestialObject(objectMass, objectRadius, objectVelocity, objectName, objectDirection, objectLocation, objectSystem){
  // Moon represent objects larger than asteroids or satellites and smaller than planets. For example, dwarf planets can also fit in this class.
  var collisionRange = this.objectRadius/400
}
