package com.astedt.robin.cellularsoftbody;
package view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent

trait Observer {
  def TestRectangleMouseHandler : EventHandler[MouseEvent];
  def OtherTestHandler : EventHandler[MouseEvent];
}