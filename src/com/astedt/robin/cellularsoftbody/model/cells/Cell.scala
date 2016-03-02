package com.astedt.robin.cellularsoftbody;
package model.cells;

import phenotypes._;

//import model.physics._;

import org.jbox2d.dynamics._
import org.jbox2d.common._
import org.jbox2d.collision.shapes._
import org.jbox2d.dynamics.joints._

object Cell {
  
  def testCells(world : World) = for (ix <- -3 to 3; iy <- 5 to 6) yield {
    new Cell(new Vec2(ix*1.1f, iy*1.1f), world);
  }

}

class Cell(position : Vec2, physicsWorld : World) {
  
  private val radius = 0.5f;
  private val segments = 12;
  private val segmentRadius = Math.PI.toFloat*radius/segments
  private val segmentBodies = for (i <- 0 until segments) yield {
    physicsWorld.createBody(createSegmentBodyDef(position, radius, i, segments))
  }
  private val segmentShape = new CircleShape()
  segmentShape.setRadius(segmentRadius)
  
  private val segmentFixtureDef = new FixtureDef();
  segmentFixtureDef.shape = segmentShape
  segmentFixtureDef.density = 1;
  segmentFixtureDef.friction = 0.3f;
  segmentBodies.foreach { b => b.createFixture(segmentFixtureDef) }
  
  for (i <- 0 until segments) {
    val jd = new DistanceJointDef
    val bodyA = segmentBodies(i)
    val bodyB = segmentBodies((i+1) % segments)
    jd.initialize(bodyA, bodyB, bodyA.getPosition, bodyB.getPosition)
    jd.length = segmentRadius*2
    jd.collideConnected = false
    physicsWorld.createJoint(jd)
  }
  
  
 
  private def createSegmentBodyDef(pos : Vec2, radius : Float, index : Int, indexCount : Int) = new BodyDef {
    userData = null;
    angle = 0f;
  	position = new Vec2(
  	    (pos.x + radius * Math.cos(2 * Math.PI * index / indexCount)).toFloat, 
  	    (pos.y + radius * Math.sin(2 * Math.PI * index / indexCount)).toFloat
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
  
  def getSegmentRadius = segmentRadius
  
  def getPositions = segmentBodies.par.map { body => 
    body.getPosition
  }
 	
  
  
}
