function SudokuController ($scope,$http)  {

	$scope.message = "hello";
	$scope.selectedClass = "active";
	
	$scope.level = 1;
	$scope.puzzle = null;
	$scope.answer = null;

	$scope.grid = null;

	$scope.showSolution = false;

	$scope.showResult = false;
	$scope.correct = false;

	$scope.reset = function () {
		$scope.showResult = false;
		$scope.correct = false;
	}
	$scope.hintLabel = function () {
		return $scope.showSolution ? "Hide Solution" : "Show Solution"
	}

	$scope.toggleHint = function () {
		$scope.showSolution = !$scope.showSolution
		$scope.setGrid()
	}

	$scope.setGrid = function () {
		if($scope.showSolution) {
			$scope.grid = $scope.answer
		} else {
			$scope.grid = $scope.puzzle
		}
	}


	$scope.getSelectedClass = function (lvl) {
		if(lvl == $scope.level)
			return "active";
		else
			return '';
	};
	
	$scope.setLevel = function (lvl) {
		$scope.level = lvl;
		$scope.getPuzzle();
	};
	
	$scope.getPuzzle = function () {
		$scope.reset()
		$http.get('/sudoku/'+$scope.level).success(function (data) {
			$scope.puzzle = data.puzzle;
			$scope.answer = data.solution;
			$scope.setGrid()
		});
	};
	
	$scope.isEditable = function (cell) {
		return !$scope.showSolution && (cell == '' || cell == ' ')
	}
	
	$scope.getCellContent = function (cell) {
	   var numPat = /[0-9]/;
	   
	   if (numPat.test(cell)) {
	   	return cell;
	   }
	   else {
	   	return '';
	   }
	}
	
	$scope.onChange = function (item) {
		alert("something changed");
	}
	
	$scope.checkSomething = function () {
		var grid = [...document.getElementsByClassName('sudoku-answer')].map(node => node.value)
		var answer = $scope.answer.flat(2)


		$scope.correct = JSON.stringify(grid) == JSON.stringify(answer)
		$scope.showResult = true
	}
	$scope.getPuzzle();
}