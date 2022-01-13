09/03/2021
-Started working on the project. 
-Added the classes and different parameters for those classes and structured the project.
-Added a new class not present in the UML, Vector2D. Vector2D is a helper class to calculate the orbits and perform basic
arithmetic with mathematical vectors.
-Laid out the structure for the file the program reads a scenario from. (see BaseSystem.txt)
21/03/2021
-Added gravitational force calculations to the classes.
-Work to combine all these forces and turning the result into a 2D vector remains to be completed later. 
-Will also have to work out how calculate the different powers of 10 together without overflowing integers.

30/03/2021
-Reworked all the gravitational force calculations
-Reworked functions in Vector2D
-Still not sure if the gravForce function will overflow the Double.
-Will need to work on implementing the move method with the Runge-Kutta 4 algorithm over Easter.
-I will also work on making the program for parsing files over Easter as well.
