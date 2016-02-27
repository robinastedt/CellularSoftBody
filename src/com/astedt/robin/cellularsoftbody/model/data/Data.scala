package com.astedt.robin.cellularsoftbody;
package model.data;

class Vector2(var x : Double, var y : Double) {
  def +=(other : Vector2, dt : Double) {
    x += other.x * dt
    y += other.y * dt
  }
}

type Position = Vector2
type Velocity = Vector2
type Acceleration = Vector2
