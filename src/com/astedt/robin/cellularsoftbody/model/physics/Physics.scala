package com.astedt.robin.cellularsoftbody;
package model.physics;

import data.structures.Module;

// A Physical object is one which which has a physical manifestation
// in the world. It can move, it reacts to forces, and it's got an
// energy (which you can calculate).
trait Physical[M <: Module[M, Double]] {
  type Position = (Double, Double)
  type Velocity = (Double, Double)
  type Acceleration = (Double, Double)

  def frame : M
  def position() : Position
  def velocity() : Velocity
  def acceleration() : Acceleration 
}


// Steppable is something that can be stepped forward in time. An object
// can choose to implement this itself, or it can choose to implement
// the derivative trait, and use an ODE solver to step.
trait Steppable {
  def step(dt : Double) : Unit
}

// Derivatives are used for ODE solvers.
trait Derivative[M <: Module[M, Double]] {
  def d() : M
}

// This is an ODE solver. It calculates trajectory of something that is
// a physical object, and has a derivative.
trait EulerForward[M <: Module[M,Double]] extends Steppable {
  this : Physical[M] with Derivative[M] =>
  def step(dt : Double) {
    frame += d() * dt
  }
}


// This is a simple physical frame, which a 'Physical' object can use.
class MyFrame (var x : Double,
               var y : Double,
               var vx : Double,
               var vy : Double)
extends Module[MyFrame, Double] { 
  type M = MyFrame
  def +=(other : MyFrame) {
    x += other.x
    y += other.y
    vx += other.vx
    vy += other.vy
  }
  def +(other : MyFrame) = {
    new MyFrame(x + other.x, y + other.y, vx + other.vx, vy + other.vy)
  }
  def *(s : Double) = {
    new MyFrame(x * s, y * s, vx * s, vy * s)
  }

  override def toString = x.toString + " " + y.toString
}
