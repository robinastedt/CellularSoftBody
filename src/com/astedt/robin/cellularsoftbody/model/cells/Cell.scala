package com.astedt.robin.cellularsoftbody;
package model.cells;

import phenotypes._;

//import model.physics._;

import org.jbox2d.dynamics._
import org.jbox2d.common._
import org.jbox2d.collision.shapes._
import org.jbox2d.dynamics.joints._

object Cell {
  
  def testCells(world : World) = for (ix <- 0 to 0; iy <- 3 to 24) yield {
    new Cell(new Vec2(ix*2.5f, iy*5f), world);
  }

}

class Cell(position : Vec2, physicsWorld : World) {
  
  private val radius = 1f;
  private val segments = 32;
  
  private val centerBody = physicsWorld.createBody(createCenterBodyDef(position))
  
  private val segmentRadius = Math.PI.toFloat*radius/segments
  private val segmentBodies = for (i <- 0 until segments) yield {
    physicsWorld.createBody(createSegmentBodyDef(position, radius, i, segments))
  }
  private val segmentShape = new CircleShape()
  segmentShape.setRadius(segmentRadius)
  
  private val centerShape = new CircleShape()
  centerShape.setRadius(radius/2)
  
  private val segmentFixtureDef = new FixtureDef();
  segmentFixtureDef.shape = segmentShape
  segmentFixtureDef.density = 1;
  segmentFixtureDef.friction = 0.3f;
  segmentBodies.foreach { b => b.createFixture(segmentFixtureDef) }
  
  private val centerFixtureDef = new FixtureDef();
  centerFixtureDef.shape = centerShape
  centerFixtureDef.density = 1;
  centerFixtureDef.friction = 0.3f;
  centerBody.createFixture(centerFixtureDef)
  
  for (i <- 0 until segments) {
    val segjd = new DistanceJointDef
    val bodyA = segmentBodies(i)
    val bodyB = segmentBodies((i+1) % segments)
    segjd.initialize(bodyA, bodyB, bodyA.getPosition, bodyB.getPosition)
    segjd.length = segmentRadius*2
    segjd.collideConnected = false
    physicsWorld.createJoint(segjd)
    
    val centerjd = new DistanceJointDef
    centerjd.initialize(bodyA, centerBody, bodyA.getPosition, centerBody.getPosition)
    centerjd.length = radius
    centerjd.dampingRatio = 1f
    centerjd.frequencyHz = 5f
    centerjd.collideConnected = true
    physicsWorld.createJoint(centerjd)
  }
  
  
 
  private def createSegmentBodyDef(pos : Vec2, radius : Float, index : Int, indexCount : Int) = new BodyDef {
    userData = null;
    angle = (2 * Math.PI * index / indexCount).toFloat;
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
  
  private def createCenterBodyDef(pos : Vec2) = new BodyDef {
    userData = null;
    angle = 0
  	position = pos;
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
  
  def getSegmentPositions = segmentBodies.par.map { body => 
    body.getPosition
  }
  
  def getSegmentVertices = segmentBodies.par.map { body => 
    val dirVec = body.getPosition.sub(centerBody.getPosition)
    val len = dirVec.length()
    body.getPosition.add(new Vec2(dirVec.x/len*segmentRadius, dirVec.y/len*segmentRadius))
    //body.getPosition
    //new Vec2(body.getPosition.x + segmentRadius*Math.cos(body.getAngle).toFloat,
    //    body.getPosition.y + segmentRadius*Math.sin(body.getAngle).toFloat)
  }
 	
  
  
}
