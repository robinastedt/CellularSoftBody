
package com.astedt.robin.cellularsoftbody.model
import com.astedt.robin.cellularsoftbody.model.cells.Cell
import com.astedt.robin.cellularsoftbody.model.environment.Environment;

class State {
  val environment = new Environment();
  val cells : List[Cell] = List(Cell.testCell, Cell.testCell, Cell.testCell)
  
}
