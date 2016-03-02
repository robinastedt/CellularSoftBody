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
  
  def updateCanvas(time : Long) {
    val gc = canvas.graphicsContext2D
    gc.restore
    gc.clearRect(0, 0, height, width)
    val polygons = model.cells.par.map { cell => 
        cell.getPositions.
        par.map(jbox2dVec2ToVector3).
        par.map(v => viewTransform * v).
        par.map(v => (v.x, v.y))
    }
    
    polygons.seq.foreach(p => gc.strokePolygon(p.seq))
    
    //Testing...
    val test = model.cells(0).getSegmentRadius * 60
    polygons.seq.foreach(p => p.seq.foreach(p => gc.strokeOval(p._1-test, p._2-test, test*2, test*2)))
    gc.stroke
  }
  
  def jbox2dVec2ToVector3(vec : org.jbox2d.common.Vec2) = new Vector3(vec.x.toDouble, vec.y.toDouble, 1.0)
  
}