package com.astedt.robin.cellularsoftbody;
package model.cells;

import phenotypes._;

import model.data._;


object Cell {
  def testCell = new Cell(new Position(0,0)) with Photosynthesis;
}

class Cell(private var position : Position) {
  private var velocity = Velocity.none;
  private var acceleration = Acceleration.none;
  
  def step(dt : Double) {
    velocity += (acceleration, dt);
    position += (velocity, dt);
  }
}
