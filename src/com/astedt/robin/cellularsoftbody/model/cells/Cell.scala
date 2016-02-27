package com.astedt.robin.cellularsoftbody;
package model.cells;

import phenotypes.Photosynthesis;
import model.data._;


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
