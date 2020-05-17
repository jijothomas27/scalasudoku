package controllers

import javax.inject._
import play.api.libs.json.{JsArray, JsString, Json, Writes}
import play.api.mvc._
import services.{SudokuGrid}

@Singleton
class SudokuController @Inject()(cc: ControllerComponents, sudokuGrid: SudokuGrid) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.sudoku("Sudoku"))
  }

  implicit object RowWrites extends Writes[List[Char]] {
    def writes(row:List[Char]) = JsArray(row.map(c => JsString(c.toString)))
  }

  implicit object GridWrites extends Writes[List[List[Char]]] {
    def writes(grid:List[List[Char]]) = JsArray(grid.map(row => Json.toJson(row)(RowWrites)))
  }

  def sudoku(level:Int=1) = Action {
    val (solution, puzzle) = sudokuGrid.createPuzzle(level%3)
    //val jsonSudoku = Json.toJson(puzzle)(GridWrites)
    val sudoku = Map("puzzle" -> puzzle, "solution" -> solution)

    Ok(Json.toJson(sudoku))
  }
}