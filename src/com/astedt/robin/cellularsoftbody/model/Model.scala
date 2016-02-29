package com.astedt.robin.cellularsoftbody;
package model;

import cells.Cell

import org.jbox2d.dynamics._;
import org.jbox2d.common._;
import org.jbox2d.collision.shapes._;

import environment.Environment;

private object Physics {
    val velocityIterations = 6;
    val positionIterations = 2;
    val gravity = new Vec2(0,9.82f)
  }

class Model {
  
  val physicsWorld = new World(Physics.gravity);
  initPhysicsWorld(physicsWorld);
  val environment = new Environment();
  val cells : List[Cell] = List(
      Cell.testCell1(physicsWorld), 
      Cell.testCell2(physicsWorld), 
      Cell.testCell3(physicsWorld), 
      Cell.testCell4(physicsWorld)
    );
  
  println("Model: Initialized")
  
  
  def step(dt : Double) {
    physicsWorld.step(dt.toFloat, 
        Physics.velocityIterations, 
        Physics.positionIterations)
  }
  
  def initPhysicsWorld(physicsWorld : World) {
    val groundBodyDef = new BodyDef();
    groundBodyDef.position.set(0, 100);
    val groundBody = physicsWorld.createBody(groundBodyDef);
    val groundBox = new PolygonShape();
    groundBox.setAsBox(50, 10);
    groundBody.createFixture(groundBox, 0);
  }
 
}
