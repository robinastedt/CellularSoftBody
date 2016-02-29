package com.astedt.robin.cellularsoftbody;
package model.cells;

import phenotypes._;

//import model.physics._;

import org.jbox2d.dynamics._
import org.jbox2d.common._
import org.jbox2d.collision.shapes._;

object Cell {
  def testCell1(world : World) = new Cell(new Vec2(0,0), world);
  def testCell2(world : World) = new Cell(new Vec2(20,0), world);
  def testCell3(world : World) = new Cell(new Vec2(0,20), world);
  def testCell4(world : World) = new Cell(new Vec2(20,20), world);
  
  //TODO: Update physics properties
  def createBodyDef(_position : Vec2) = new BodyDef {
    userData = null;
  	position = new Vec2(_position);
  	angle = 0f;
  	linearVelocity = new Vec2(10,0);
  	angularVelocity = 0f;
  	linearDamping = 0f;
  	angularDamping = 0f;
  	allowSleep = true;
  	awake = true;
  	fixedRotation = false;
  	bullet = false;
  	`type` = BodyType.DYNAMIC;
  	active = true;
  	gravityScale = 1.0f;
  }
}

class Cell(position : Vec2, physicsWorld : World) {
  private val physicsBody = physicsWorld.createBody(Cell.createBodyDef(position));
  private val dynamicCicle = new CircleShape();
  dynamicCicle.setRadius(5);
  private val fixtureDef = new FixtureDef();
  fixtureDef.shape = dynamicCicle;
  fixtureDef.density = 1;
  fixtureDef.friction = 0.3f;
  physicsBody.createFixture(fixtureDef);
  
  def getPosition = physicsBody.getPosition
}


//class Cell extends Physical[MyFrame] with Derivative[MyFrame] {
//  def frame = new MyFrame(1,2,3,4)
//  def position = (0,0)
//  def velocity = (0,1)
//  def acceleration = (1,1)
//  def d = new MyFrame(2,1,3,4)
//}