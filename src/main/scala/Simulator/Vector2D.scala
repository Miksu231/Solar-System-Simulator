package Simulator

import scala.math._

class Vector2D(startX: Double, startY: Double, xCoord: Double, yCoord: Double) {

// This is a helper class for us to calculate with vectors for the speeds and directions of different objects.

val x = this.xCoord

val y = this.yCoord

val length = sqrt((x*x+y*y))

val endX = this.startX + this.x

val endY = this.startY + this.y

def +(another: Vector2D) = new Vector2D(startX, startY, this.x + another.x, this.y + another.y)

def -(another: Vector2D) = new Vector2D(startX, startY, this.x - another.x, this.y - another.y)

def *(number: Double) = new Vector2D(startX, startY, this.x * number, this.y * number)

def /(number:Double) = new Vector2D(startX, startY, this.x / number, this.y / number)
}
