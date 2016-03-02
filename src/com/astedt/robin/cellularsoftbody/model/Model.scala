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
    val gravity = new Vec2(0,-9.82f)
  }

class Model {
  
  val physicsWorld = new World(Physics.gravity);
  initPhysicsWorld(physicsWorld);
  val environment = new Environment();
  val cells = Cell.testCells(physicsWorld)
  
  println("Model: Initialized")
  
  
  def step(dt : Double) {
    physicsWorld.step(dt.toFloat, 
        Physics.velocityIterations, 
        Physics.positionIterations)
  }
  
  def initPhysicsWorld(physicsWorld : World) {
    val groundBodyDef = new BodyDef();
    groundBodyDef.position.set(0, -1);
    val groundBody = physicsWorld.createBody(groundBodyDef);
    val groundBox = new PolygonShape();
    groundBox.setAsBox(5, 1);
    groundBody.createFixture(groundBox, 0);
    
    val wall1BodyDef = new BodyDef();
    wall1BodyDef.position.set(-6, 5);
    val wall1Body = physicsWorld.createBody(wall1BodyDef);
    val wall1Box = new PolygonShape();
    wall1Box.setAsBox(1, 5);
    wall1Body.createFixture(wall1Box, 0);
    
    val wall2BodyDef = new BodyDef();
    wall2BodyDef.position.set(6, 5);
    val wall2Body = physicsWorld.createBody(wall2BodyDef);
    val wall2Box = new PolygonShape();
    wall2Box.setAsBox(1, 5);
    wall2Body.createFixture(wall2Box, 0);
    
  }
 
}
