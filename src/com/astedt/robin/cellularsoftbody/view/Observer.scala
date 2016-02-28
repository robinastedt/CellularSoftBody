package com.astedt.robin.cellularsoftbody;
package view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent

trait Observer {
  def TestRectangleMouseHandler : EventHandler[MouseEvent] = EmptyMouseEvent;
  def OtherTestHandler : EventHandler[MouseEvent] = EmptyMouseEvent;
}

private object EmptyMouseEvent extends EventHandler[MouseEvent] {
  override def handle(event : MouseEvent) {
    //Do nothing
  }
}
    