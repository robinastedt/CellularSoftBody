package com.astedt.robin.cellularsoftbody
package view

import util._
import model._
import model.cells._

import scalafx.Includes._
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.canvas.Canvas

import scalafx.scene.shape._
import scalafx.scene._

import org.jbox2d.common.Vec2


class Drawing(val model : Model, val canvas : Canvas) {
  
  def width = canvas.width.value;
  def height = canvas.height.value;
  
  val zoom = 20.0;
  
  var viewTransform = new Matrix3x3(
      height/zoom, 0, width/2.0,
      0, -height/zoom, height,
      0, 0, 1.0)
  
  def zoomMatrix(amount: Double) : Matrix3x3[Double] = {
    new Matrix3x3(
        1 + amount, 0, 0,
        0, 1 + amount, 0,
        0, 0 , 1)
  }
  
  def translateMatrix(x : Double, y : Double) : Matrix3x3[Double] = {
    new Matrix3x3(
        1, 0, x,
        0, 1, y,
        0, 0, 1)
  }
      
      
  //TODO: Fix this, not working
  //Only works with default transformation matrix
  def isInsideDrawing(cell : Cell) : Boolean = {
    //val mp0 = cell.getCenterPostion.sub(new Vec2(-cell.getRadius*2, +cell.getRadius*2))
    //val mp1 = cell.getCenterPostion.sub(new Vec2(+cell.getRadius*2, -cell.getRadius*2))
    //val vp0 = jbox2dVec2ToTransformedVector3(mp0)
    //val vp1 = jbox2dVec2ToTransformedVector3(mp1)
    //(vp0.x >= 0 && vp1.x < width && vp0.y >= 0 && vp1.y < height)
    true
  }
  
  def translate(x : Double, y : Double) {
    viewTransform = translateMatrix(x, y) * viewTransform
  }
  
  def zoom(amount: Double, xCenter : Double, yCenter : Double) {
    viewTransform = 
      translateMatrix(xCenter, yCenter) * 
      zoomMatrix(amount) * 
      translateMatrix(-xCenter, -yCenter) * 
      viewTransform
  }
  
  def updateCanvas(time : Long) {
    
    val gc = canvas.graphicsContext2D
    
    gc.restore
    gc.clearRect(0, 0, width, height)
    
    gc.fill_=(paint.Color.Beige)
    
    
    val polygons = model.cells.par.filter(isInsideDrawing).
    map { cell => 
        cell.getSegmentVerticesQuad.
        par.map(jbox2dVec2ToTransformedTuple)
    }
    
    //polygons.seq.foreach(p => gc.strokePolygon(p.seq))
    polygons.seq.foreach(poly => {
      val p = poly.seq
      
      gc.beginPath()
      gc.moveTo(p(p.length-1)._1, p(p.length-1)._2)
      for (i <- 0 until p.length / 2) {
        val xc = p((i*2) % p.length)._1
        val yc = p((i*2) % p.length)._2
        val x1 = p((i*2+1) % p.length)._1
        val y1 = p((i*2+1) % p.length)._2
        gc.quadraticCurveTo(xc, yc, x1, y1)
      }
      //gc.closePath()
      gc.fillPath()
    })
    
    /*
    //Testing...
    val test = model.cells(0).getSegmentRadius * 80
    
    val cellSegments = model.cells.par.map { cell => 
        cell.getSegmentPositions.
        par.map(jbox2dVec2ToVector3).
        par.map(v => viewTransform * v).
        par.map(v => (v.x, v.y))
    }
    
    cellSegments.seq.foreach(seg => 
      seg.seq.foreach(p => 
        gc.strokeOval(p._1-test, p._2-test, test*2, test*2)))
    */
    
  }
  
  def jbox2dVec2ToTransformedTuple(vec : org.jbox2d.common.Vec2) = 
    {val v = jbox2dVec2ToTransformedVector3(vec); (v.x, v.y)}
  
  def jbox2dVec2ToTransformedVector3(vec : org.jbox2d.common.Vec2) = 
    viewTransform * jbox2dVec2ToVector3(vec)
    
  def jbox2dVec2ToVector3(vec : org.jbox2d.common.Vec2) = 
    new Vector3(vec.x.toDouble, vec.y.toDouble, 1.0)
  
}