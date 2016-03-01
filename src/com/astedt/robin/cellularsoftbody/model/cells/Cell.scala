package com.astedt.robin.cellularsoftbody;
package model.cells;

import phenotypes._;

//import model.physics._;

import org.jbox2d.dynamics._
import org.jbox2d.common._
import org.jbox2d.collision.shapes._;

object Cell {
  
  def testCells(world : World) = for (ix <- -4 to 4; iy <- 5 to 10) yield {
    new Cell(new Vec2(ix+Math.random().toFloat, iy+Math.random().toFloat), world);
  }
  /*
  def createBodyDef(pos : Vec2, radius : Float, index : Int, indexCount : Int) = new BodyDef {
    userData = null;
    angle = Math.PI.toFloat * 2 * index / indexCount;
  	position = new Vec2(
  	    (pos.x + radius * Math.cos(angle)).toFloat, 
  	    (pos.y + radius * Math.sin(angle)).toFloat
    );
  	linearVelocity = new Vec2();
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
  */
  
  //TODO: Update physics properties
  def createBodyDef(_position : Vec2) = new BodyDef {
    userData = null;
  	position = new Vec2(_position);
  	angle = 0f;
  	linearVelocity = new Vec2();
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
  /*
  private val radius = 0.5f;
  private val segments = 12;
  private val physicsBodies = for (i <- 0 until segments) yield {
    physicsWorld.createBody(Cell.createBodyDef(position, radius, i, segments))
  }
  private val segmentShape = new PolygonShape();
  segmentShape.setAsBox(2*Math.PI.toFloat*radius/segments, radius/10)
  */
 	
  
  private val physicsBody = physicsWorld.createBody(Cell.createBodyDef(position));
  private val dynamicCicle = new CircleShape();
  dynamicCicle.setRadius(0.5f);
  private val fixtureDef = new FixtureDef();
  fixtureDef.shape = dynamicCicle;
  fixtureDef.density = 1;
  fixtureDef.friction = 0.3f;
  physicsBody.createFixture(fixtureDef);
  
  def getPosition = physicsBody.getPosition
  def getRadius = dynamicCicle.getRadius
  
  
}


//class Cell extends Physical[MyFrame] with Derivative[MyFrame] {
//  def frame = new MyFrame(1,2,3,4)
//  def position = (0,0)
//  def velocity = (0,1)
//  def acceleration = (1,1)
//  def d = new MyFrame(2,1,3,4)
//}