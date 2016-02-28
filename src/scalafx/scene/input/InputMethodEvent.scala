/*
 * Copyright (c) 2011-2014, ScalaFX Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the ScalaFX Project nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE SCALAFX PROJECT OR ITS CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package scalafx.scene.input

import javafx.scene.{input => jfxsi}

import scala.language.implicitConversions
import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.delegate.SFXDelegate
import scalafx.event.EventType

object InputMethodEvent {
  implicit def sfxInputMethodEvent2jfx(ime: InputMethodEvent): jfxsi.InputMethodEvent = if (ime != null) ime.delegate else null

  val InputMethodTextChanged: EventType[jfxsi.InputMethodEvent] = jfxsi.InputMethodEvent.INPUT_METHOD_TEXT_CHANGED
}

class InputMethodEvent(override val delegate: jfxsi.InputMethodEvent) extends InputEvent(delegate) with SFXDelegate[jfxsi.InputMethodEvent] {

  /**
   * The input method caret position within the composed text.
   */
  def caretPosition: Int = delegate.getCaretPosition

  /**
   * Gets the text that is committed by the input method as the result of the composition.
   */
  def committed: String = delegate.getCommitted

  /**
   * Gets the text under composition.
   */
  def composed: ObservableBuffer[jfxsi.InputMethodTextRun] = delegate.getComposed

}