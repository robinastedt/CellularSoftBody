package com.astedt.robin.cellularsoftbody;
package model;

import cells.Cell

import org.jbox2d.dynamics.World;
import org.jbox2d.common._;

import environment.Environment;

object Model {
  object Physics {
    val velocityIterations = 6;
    val positionIterations = 2;
    val gravity = new Vec2(0,9.82f)
  }
}

class Model {
  println("Model initialized");
  
  val physicsWorld = new World(Model.Physics.gravity);
  val environment = new Environment();
  val cells : List[Cell] = List(Cell.testCell(physicsWorld));
  
  var test = 0;
  def testing = test;
  
  def step(dt : Double) {
    physicsWorld.step(dt.toFloat, 
        Model.Physics.velocityIterations, 
        Model.Physics.positionIterations)
  }
}
