package com.astedt.robin.cellularsoftbody
package view

trait ViewObserver {
  def receiveInput(event : InputEvent);
}