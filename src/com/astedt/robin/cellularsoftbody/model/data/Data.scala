package com.astedt.robin.cellularsoftbody.model.data

class Acceleration(var x : Double, var y : Double);

class Velocity(var x : Double, var y : Double) {
  def +=(acc : Acceleration, dt : Double) {
    x += acc.x * dt;
    y += acc.y * dt;
  }
}

class Position(var x : Double, var y : Double) {
  def +=(vel : Velocity, dt : Double) {
    x += vel.x * dt;
    y += vel.y * dt;
  }
}