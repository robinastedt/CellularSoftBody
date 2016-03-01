package com.astedt.robin.cellularsoftbody
package view

import util._
import model._
import model.cells._

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.canvas.Canvas


class Drawing(val model : Model, val canvas : Canvas) {
  
  def width = canvas.getWidth;
  def height = canvas.getHeight;
  

  var viewTransform = new Matrix3x3(
      height/10.0, 0, width/2.0,
      0, -height/10.0, height,
      0, 0, 1.0)
  
  def updateCanvas {
    val gc = canvas.graphicsContext2D
    gc.clearRect(0,0,width,height)
    
    model.cells.foreach { cell => 
      val mPoint1 = new Vector3(
          (cell.getPosition.x - cell.getRadius).toDouble, 
          (cell.getPosition.y - cell.getRadius).toDouble, 
          1)
      val mPoint2 = new Vector3(
          (cell.getPosition.x + cell.getRadius).toDouble, 
          (cell.getPosition.y + cell.getRadius).toDouble, 
          1)
      
      val vPoint1 = viewTransform * mPoint1;
      val vPoint2 = viewTransform * mPoint2;

      val x = Math.min(vPoint1.x, vPoint2.x)
      val y = Math.min(vPoint1.y, vPoint2.y)
      val w = Math.abs(vPoint1.x - vPoint2.x)
      val h = Math.abs(vPoint1.y - vPoint2.y)
      
      gc.fillOval(x, y, w, h)
    }
  }
  
  def jbox2dVec2ToVector3(vec : org.jbox2d.common.Vec2) = new Vector3(vec.x.toDouble, vec.y.toDouble, 1.0)
  
}