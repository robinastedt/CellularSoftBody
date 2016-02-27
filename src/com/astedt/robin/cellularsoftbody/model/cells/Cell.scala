package com.astedt.robin.cellularsoftbody.model.cells

import com.astedt.robin.cellularsoftbody.model.cells.phenotypes.Photosynthesis;

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

object Cell {
  def testCell = new Cell(new Position(0,0)) with Photosynthesis;
  
}

class Cell(private var position : Position) {
  private var velocity = new Velocity(0,0);
  private var acceleration = new Acceleration(0,0);
  
  def step(dt : Double) {
    velocity += (acceleration, dt);
    position += (velocity, dt);
  }
}
