package com.astedt.robin.cellularsoftbody;
package model.cells;

import phenotypes._;

import model.physics._;


object Cell {
  def testCell = new Cell with Photosynthesis;
}

class Cell extends Physical[MyFrame] with Derivative[MyFrame] {
  def frame = new MyFrame(1,2,3,4)
  def position = (0,0)
  def velocity = (0,1)
  def acceleration = (1,1)
  def d = new MyFrame(2,1,3,4)
}