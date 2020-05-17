package services

import javax.inject.{Inject, Singleton}

@Singleton
class SudokuGrid @Inject()(sudokuGenerator: SudokuGenerator){

  val easyMask: List[List[Char]] = List(
    List(' ',' ',' ','x','x',' ','x',' ','x'),
    List(' ',' ',' ',' ','x','x',' ',' ',' '),
    List('x','x',' ',' ',' ','x',' ','x',' '),
    List(' ',' ',' ','x',' ','x',' ','x',' '),
    List(' ',' ','x','x',' ','x',' ','x',' '),
    List(' ','x',' ','x',' ','x',' ',' ',' '),
    List(' ','x',' ','x',' ',' ',' ','x','x'),
    List(' ',' ',' ','x','x',' ',' ',' ',' '),
    List('x',' ','x',' ','x','x',' ',' ',' '))

  val mediumMask = List(
    List(' ','x','x',' ','x',' ',' ',' ','x'),
    List(' ',' ','x',' ',' ',' ',' ',' ','x'),
    List(' ',' ','x','x',' ',' ',' ','x','x'),
    List(' ','x','x','x','x',' ',' ',' ',' '),
    List('x',' ',' ',' ','x',' ',' ',' ','x'),
    List(' ',' ',' ',' ','x','x','x','x',' '),
    List('x','x',' ',' ',' ','x','x',' ',' '),
    List('x',' ',' ',' ',' ',' ','x',' ',' '),
    List('x',' ',' ',' ','x',' ','x','x',' '))
  val hardMask = List(
    List(' ',' ','x','x',' ',' ',' ','x',' '),
    List('x',' ',' ','x',' ',' ',' ','x','x'),
    List(' ','x',' ','x','x',' ','x',' ',' '),
    List('x',' ',' ',' ',' ','x',' ','x',' '),
    List(' ',' ',' ',' ',' ',' ',' ',' ',' '),
    List(' ','x',' ','x',' ',' ',' ',' ','x'),
    List(' ',' ','x',' ','x','x',' ','x',' '),
    List('x','x',' ',' ',' ','x',' ',' ','x'),
    List(' ','x',' ',' ',' ','x','x',' ',' '))

  private def maskElement(actual:Char)(mask:Char): Char =
    if(mask == 'x') actual
    else mask

  private def maskOneRow (actual:List[Char],mask:List[Char]): List[Char] =
    (actual zip mask) map (t => maskElement(t._1)( t._2))

  private def convertToPuzzle(actual:List[List[Char]])(mask:List[List[Char]]): List[List[Char]] =
    (actual zip mask) map (t => maskOneRow(t._1, t._2))

  def createPuzzle(level:Int): (List[List[Char]],List[List[Char]]) = {
    val sudoku : List[List[Char]] = sudokuGenerator.generateGrid
    val mask = if (level ==0) easyMask
    else if (level == 1) mediumMask
    else hardMask

    val puzzle = convertToPuzzle(sudoku)(mask)

    return (sudoku,puzzle)
  }
}
