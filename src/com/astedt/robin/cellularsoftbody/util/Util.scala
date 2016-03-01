package com.astedt.robin.cellularsoftbody.util

class Timer(interval: Int, repeats: Boolean = true, op : => Unit) {
  val timeOut = new javax.swing.AbstractAction() {
    def actionPerformed(e : java.awt.event.ActionEvent) = op
  }
  val t = new javax.swing.Timer(interval, timeOut)
  t.setRepeats(repeats)
  
  def stop = t.stop
  def start = t.start
}

class Vector2[@specialized N](val x : N, val y : N)(implicit num: Numeric[N]);

class Matrix2x2[@specialized N](
    val e11 : N, val e12 : N,
    val e21 : N, val e22 : N)
    (implicit num: Numeric[N]) {
  
  /**
   * Scalar multiplication
   */
  def *(scalar : N) : Matrix2x2[N] = {
    new Matrix2x2(
        num.times(e11, scalar),
        num.times(e12, scalar),
        num.times(e21, scalar),
        num.times(e22, scalar)
    )
  }
  
  /**
   * Matrix multiplication
   */
  def *(other : Matrix2x2[N]) : Matrix2x2[N] = {
    new Matrix2x2(
        num.plus(num.times(e11, other.e11), num.times(e12, other.e21)),
        num.plus(num.times(e11, other.e12), num.times(e12, other.e22)),
        num.plus(num.times(e21, other.e11), num.times(e22, other.e21)),
        num.plus(num.times(e21, other.e12), num.times(e22, other.e22))
    )
  }
  
  /**
   * Matrix addition
   */
  def +(other : Matrix2x2[N]) : Matrix2x2[N] = {
    new Matrix2x2(
        num.plus(e11, other.e11),
        num.plus(e12, other.e12),
        num.plus(e21, other.e21),
        num.plus(e22, other.e22)
    )
  }
  
  def *(v : Vector2[N]) : Vector2[N] = {
    new Vector2(
        num.plus(num.times(e11,v.x),num.times(e12,v.y)),
        num.plus(num.times(e21,v.x),num.times(e22,v.y))
    )
  }
}

class Vector3[@specialized N](val x : N, val y : N, val z : N)(implicit num: Numeric[N]) {
  override def toString = "("+x+","+y+","+z+")"
}

class Matrix3x3[@specialized N](
    val e11 : N, val e12 : N, val e13 : N,
    val e21 : N, val e22 : N, val e23 : N,
    val e31 : N, val e32 : N, val e33 : N
    )(implicit num: Numeric[N]) {
  
  import org.jbox2d.common.Vec3
  
  /**
   * Scalar multiplication
   */
  def *(scalar : N) : Matrix3x3[N] = {
    new Matrix3x3(
        num.times(e11, scalar),
        num.times(e12, scalar),
        num.times(e13, scalar),
        num.times(e21, scalar),
        num.times(e22, scalar),
        num.times(e23, scalar),
        num.times(e31, scalar),
        num.times(e32, scalar),
        num.times(e33, scalar)
    )
  }
  
  /**
   * Matrix multiplication
   */
  def *(other : Matrix3x3[N]) : Matrix3x3[N] = {
    new Matrix3x3(
        num.plus(num.plus(num.times(e11, other.e11), num.times(e12, other.e21)), num.times(e13, other.e31)),
        num.plus(num.plus(num.times(e11, other.e12), num.times(e12, other.e22)), num.times(e13, other.e32)),
        num.plus(num.plus(num.times(e11, other.e13), num.times(e12, other.e23)), num.times(e13, other.e33)),
        num.plus(num.plus(num.times(e21, other.e11), num.times(e22, other.e21)), num.times(e23, other.e31)),
        num.plus(num.plus(num.times(e21, other.e12), num.times(e22, other.e22)), num.times(e23, other.e32)),
        num.plus(num.plus(num.times(e21, other.e13), num.times(e22, other.e23)), num.times(e23, other.e33)),
        num.plus(num.plus(num.times(e31, other.e11), num.times(e32, other.e21)), num.times(e33, other.e31)),
        num.plus(num.plus(num.times(e31, other.e12), num.times(e32, other.e22)), num.times(e33, other.e32)),
        num.plus(num.plus(num.times(e31, other.e13), num.times(e32, other.e23)), num.times(e33, other.e33))
    )
  }
  
  /**
   * Matrix addition
   */
  def +(other : Matrix3x3[N]) : Matrix3x3[N] = {
    new Matrix3x3(
        num.plus(e11, other.e11),
        num.plus(e12, other.e12),
        num.plus(e13, other.e13),
        num.plus(e21, other.e21),
        num.plus(e22, other.e22),
        num.plus(e23, other.e23),
        num.plus(e31, other.e31),
        num.plus(e32, other.e32),
        num.plus(e33, other.e33)
    )
  }
  
  def *(v : Vector3[N]) : Vector3[N] = {
    new Vector3(
        num.plus(num.times(e11,v.x),num.plus(num.times(e12,v.y),num.times(e13,v.z))),
        num.plus(num.times(e21,v.x),num.plus(num.times(e22,v.y),num.times(e23,v.z))),
        num.plus(num.times(e31,v.x),num.plus(num.times(e32,v.y),num.times(e33,v.z)))
    )
  }
}



