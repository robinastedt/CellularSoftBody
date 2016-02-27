package com.astedt.robin.cellularsoftbody;
package model.data;


object Force {
  def none = new Force(0,0);
}
class Force(val x : Double, val y : Double);


object Acceleration {
  def none = new Acceleration(0,0);
}
class Acceleration(val x : Double, val y : Double){
  def this(force : Force, mass : Double) {
    this(force.x / mass, force.y / mass);    
  }
}

object Velocity {
  def none = new Velocity(0,0);
}
class Velocity(var x : Double, var y : Double) {
  
  def +(acc : Acceleration, dt : Double) : Velocity = {
    new Velocity(x + acc.x * dt, y + acc.y * dt);
  }
  
  def +=(acc : Acceleration, dt : Double) {
    x += acc.x * dt;
    y += acc.y * dt;
  }
}

class Position(var x : Double, var y : Double) {
  
  def +(vel : Velocity, dt : Double) : Position = {
    new Position(x + vel.x * dt, y + vel.y * dt);
  }
  
  def +=(vel : Velocity, dt : Double) {
    x += vel.x * dt;
    y += vel.y * dt;
  }
}
