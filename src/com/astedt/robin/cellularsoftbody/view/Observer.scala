package com.astedt.robin.cellularsoftbody;
package view;

import scalafx.Includes._
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.MouseEvent
import scalafx.scene.input.ScrollEvent
import scalafx.scene.input.MouseDragEvent

trait Observer {
  def handleKeyEvent(event : KeyEvent)
  def handleScrollEvent(event : ScrollEvent)
  def handleMouseDraggedEvent(event : MouseEvent)
  def handleMousePressedEvent(event : MouseEvent)
  def notifyViewClosed()
}

    