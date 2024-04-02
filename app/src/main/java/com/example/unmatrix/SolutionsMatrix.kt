package com.example.unmatrix

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.unmatrix.R
import android.content.Intent

class SolutionsMatrix : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.matrix_solutions)

        val matrixString1 = intent.getStringExtra("matrix_string1")
        val matrixString2 = intent.getStringExtra("matrix_string2")
        val matrixGrid: TextView = findViewById(R.id.matrixGrid)
        matrixGrid.text = matrixString2


        val languages = resources.getStringArray(R.array.spinner_tools)
        val spinner = findViewById<Spinner>(R.id.toolsSpinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, languages
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: android.view.View?, position: Int, id: Long
                ) {
                    when (languages[position]) {
                        "Matrix I" -> matrixGrid.text = matrix1(matrixString1)
                        "Matrix II" -> matrixGrid.text = matrix2(matrixString2)
                        "Addition" -> performMatrixOperation(matrixString1, matrixString2, true)
                        "Subtraction" -> performMatrixOperation(matrixString1, matrixString2, false)
                        "Multiplication" -> performMatrixMultiplication(matrixString1, matrixString2)
                        "Transpose I" -> performMatrixTranspose(matrixString1)
                        "Transpose II" -> performMatrixTranspose(matrixString2)
                        "Determinant I" -> calculateDeterminant(matrixString1)
                        "Determinant II" -> calculateDeterminant(matrixString2)
                        "Adjoint I" -> calculateAdjoint(matrixString1)
                        "Adjoint II" -> calculateAdjoint(matrixString2)
                        "Inverse I" -> calculateInverse(matrixString1)
                        "Inverse II" -> calculateInverse(matrixString2)
                        "Trace of Matrix" -> calculateTrace(matrixString1)
                        "Rank of Matrix" -> calculateRank(matrixString1)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
    }

    private fun matrix1(matrixString1: String?): String? {
        return matrixString1
    }

    private fun matrix2(matrixString2: String?): String? {
        if(matrixString2 == "") return "NULL"
        return matrixString2
    }
    private fun performMatrixOperation(matrixString1: String?, matrixString2: String?, isAddition: Boolean) {
        val matrix1 = stringToMatrix(matrixString1)
        val matrix2 = stringToMatrix(matrixString2)
        val resultMatrix = if (isAddition) {
            matrixAddition(matrix1, matrix2)
        } else {
            matrixSubtraction(matrix1, matrix2)
        }
        val resultString = matrixToString(resultMatrix)
        val matrixGrid: TextView = findViewById(R.id.matrixGrid)
//        if (resultMatrix != null) {
//            matrixGrid.maxLines = resultMatrix.size
//        }
        matrixGrid.text = resultString
    }

    private fun performMatrixMultiplication(matrixString1: String?, matrixString2: String?) {
        val matrix1 = stringToMatrix(matrixString1)
        val matrix2 = stringToMatrix(matrixString2)
        if (matrix1.isNotEmpty() && matrix2.isNotEmpty() && matrix1[0].size == matrix2.size) {
            val resultMatrix = matrixMultiplication(matrix1, matrix2)
            val resultString = matrixToString(resultMatrix)
            val matrixGrid: TextView = findViewById(R.id.matrixGrid)
            matrixGrid.maxLines = resultMatrix.size
            matrixGrid.text = resultString
        } else {
            Toast.makeText(this, "Matrices are incompatible for multiplication", Toast.LENGTH_SHORT).show()
        }
    }

    private fun performMatrixTranspose(matrixString: String?) {
        val matrix = stringToMatrix(matrixString)
        if (matrix.isNotEmpty()) {
            val resultMatrix = matrixTranspose(matrix)
            val resultString = matrixToString(resultMatrix)
            val matrixGrid: TextView = findViewById(R.id.matrixGrid)
            matrixGrid.maxLines = resultMatrix.size
            matrixGrid.text = resultString
        } else {
            Toast.makeText(this, "Matrix is empty or invalid", Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculateDeterminant(matrixString: String?) {
        val matrix = stringToMatrix(matrixString)
        if (matrix.isNotEmpty() && matrix.size == matrix[0].size) {
            val determinant = computeDeterminant(matrix)
            val determinantTextView: TextView = findViewById(R.id.matrixValue)
            determinantTextView.text = determinant.toString()
        } else {
            Toast.makeText(this, "Matrix is either empty or not square", Toast.LENGTH_SHORT).show()
        }
    }


    private fun matrixAddition(matrix1: Array<Array<Int>>, matrix2: Array<Array<Int>>): Array<Array<Int>>? {
        if (matrix1.size != matrix2.size || matrix1[0].size != matrix2[0].size) {
            // Matrices have incompatible dimensions
            return null
        }
        return Array(matrix1.size) { i ->
            Array(matrix1[0].size) { j ->
                matrix1[i][j] + matrix2[i][j]
            }
        }
    }

    private fun matrixSubtraction(matrix1: Array<Array<Int>>, matrix2: Array<Array<Int>>): Array<Array<Int>>? {
        if (matrix1.size != matrix2.size || matrix1[0].size != matrix2[0].size) {
            // Matrices have incompatible dimensions
            return null
        }
        return Array(matrix1.size) { i ->
            Array(matrix1[0].size) { j ->
                matrix1[i][j] - matrix2[i][j]
            }
        }
    }

    private fun calculateAdjoint(matrixString: String?) {
        if (matrixString.isNullOrEmpty()) {
            Toast.makeText(this, "Matrix string is null or empty", Toast.LENGTH_SHORT).show()
            return
        }
        val matrix = stringToMatrix(matrixString)
        if (matrix.isEmpty()) {
            Toast.makeText(this, "Invalid matrix format", Toast.LENGTH_SHORT).show()
            return
        }
        val adjointMatrix = adjoint(matrix)
        val resultString = matrixToString(adjointMatrix)
        val matrixGrid: TextView = findViewById(R.id.matrixGrid)
        matrixGrid.text = resultString
    }

    private fun calculateInverse(matrixString: String?) {
        if (matrixString.isNullOrEmpty()) {
            Toast.makeText(this, "Matrix string is null or empty", Toast.LENGTH_SHORT).show()
            return
        }
        val matrix = stringToMatrix(matrixString)
        if (matrix.isEmpty()) {
            Toast.makeText(this, "Invalid matrix format", Toast.LENGTH_SHORT).show()
            return
        }
        val inverseMatrix = inverse(matrix)
        if (inverseMatrix != null) {
            val resultString = matrixToString(inverseMatrix.map { it.map { it.toInt() }.toTypedArray() }.toTypedArray())
            val matrixGrid: TextView = findViewById(R.id.matrixGrid)
            matrixGrid.text = resultString
        } else {
            Toast.makeText(this, "Matrix is singular, inverse does not exist", Toast.LENGTH_SHORT).show()
        }
    }

    private fun matrixMultiplication(matrix1: Array<Array<Int>>, matrix2: Array<Array<Int>>): Array<Array<Int>> {
        require(matrix1[0].size == matrix2.size) {
            "The number of columns of the first matrix must be equal to the number of rows of the second matrix"
        }
        val result = Array(matrix1.size) { Array(matrix2[0].size) { 0 } }
        for (i in matrix1.indices) {
            for (j in matrix2[0].indices) {
                for (k in matrix2.indices) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j]
                }
            }
        }
        return result
    }

    private fun matrixTranspose(matrix: Array<Array<Int>>): Array<Array<Int>> {
        val numRows = matrix.size
        val numCols = matrix[0].size
        val result = Array(numCols) { Array(numRows) { 0 } }
        for (i in 0 until numRows) {
            for (j in 0 until numCols) {
                result[j][i] = matrix[i][j]
            }
        }
        return result
    }

    private fun computeDeterminant(matrix: Array<Array<Int>>): Int {
        require(matrix.size == matrix[0].size) { "Matrix must be square for determinant calculation" }
        return when (matrix.size) {
            1 -> matrix[0][0] // Base case for 1x1 matrix
            2 -> matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0] // Base case for 2x2 matrix
            else -> {
                var determinant = 0
                for (j in matrix.indices) {
                    determinant += matrix[0][j] * cofactor(matrix, 0, j)
                }
                determinant
            }
        }
    }

    private fun cofactor(matrix: Array<Array<Int>>, row: Int, col: Int): Int {
        val subMatrix = subMatrix(matrix, row, col)
        return if ((row + col) % 2 == 0) computeDeterminant(subMatrix) else -computeDeterminant(subMatrix)
    }

    private fun subMatrix(matrix: Array<Array<Int>>, row: Int, col: Int): Array<Array<Int>> {
        val subMatrix = Array(matrix.size - 1) { Array(matrix.size - 1) { 0 } }
        var rowIndex = 0
        for (i in matrix.indices) {
            if (i == row) continue
            var colIndex = 0
            for (j in matrix[i].indices) {
                if (j == col) continue
                subMatrix[rowIndex][colIndex] = matrix[i][j]
                colIndex++
            }
            rowIndex++
        }
        return subMatrix
    }

    // Function to compute the adjoint of a matrix
    private fun adjoint(matrix: Array<Array<Int>>): Array<Array<Int>> {
        require(matrix.size == matrix[0].size) { "Matrix must be square for adjoint calculation" }
        val numRows = matrix.size
        val adj = Array(numRows) { Array(numRows) { 0 } }

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                val sign = if ((i + j) % 2 == 0) 1 else -1
                val cofactorMatrix = subMatrix(matrix, i, j)
                adj[j][i] = sign * computeDeterminant(cofactorMatrix)
            }
        }

        return adj
    }

    // Function to compute the inverse of a matrix
    private fun inverse(matrix: Array<Array<Int>>): Array<Array<Float>>? {
        require(matrix.size == matrix[0].size) { "Matrix must be square for inverse calculation" }
        val numRows = matrix.size
        val det = computeDeterminant(matrix)

        // Check if the matrix is invertible
        if (det == 0) {
            Toast.makeText(this, "Inverse does not exist", Toast.LENGTH_SHORT).show()
            return null
        }

        // Compute the adjoint of the matrix
        val adj = adjoint(matrix)

        // Compute the inverse by dividing each element of the adjoint by the determinant
        val inverse = Array(numRows) { Array(numRows) { 0f } }
        for (i in 0 until numRows) {
            for (j in 0 until numRows) {
                inverse[i][j] = adj[i][j] / det.toFloat()
            }
        }

        return inverse
    }

    private fun calculateTrace(matrixString: String?) {
        val matrix = stringToMatrix(matrixString)
        if (matrix.isNotEmpty()) {
            val traceValue = trace(matrix)
            val matrixValueTextView: TextView = findViewById(R.id.matrixValue)
            val matrixKeyTextView: TextView = findViewById(R.id.matrixKey)
            matrixKeyTextView.text = "Trace of Matrix : "
            matrixValueTextView.text = "Trace: $traceValue"
        } else {
            Toast.makeText(this, "Matrix is empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun trace(matrix: Array<Array<Int>>): Int {
        require(matrix.size == matrix[0].size) { "Matrix must be square for trace calculation" }
        var trace = 0
        for (i in matrix.indices) {
            trace += matrix[i][i] // Add the diagonal element
        }
        return trace
    }

    private fun calculateRank(matrixString: String?) {
        val matrix = stringToMatrix(matrixString)
        val rankValue = rank(matrix)
        val matrixValueTextView: TextView = findViewById(R.id.matrixValue)
        val matrixKeyTextView: TextView = findViewById(R.id.matrixKey)
        matrixKeyTextView.text = "Rank of Matrix : "
        matrixValueTextView.text = "Rank: $rankValue"
    }

    private fun rank(matrix: Array<Array<Int>>): Int {
        val numRows = matrix.size
        val numCols = matrix[0].size
        var rank = 0
        val visited = Array(numRows) { false }

        for (col in 0 until numCols) {
            var pivotRow = -1

            // Find the first non-zero element in the current column
            for (row in 0 until numRows) {
                if (!visited[row] && matrix[row][col] != 0) {
                    pivotRow = row
                    break
                }
            }

            // If a non-zero element is found, increment rank and mark the row as visited
            if (pivotRow != -1) {
                rank++
                visited[pivotRow] = true

                // Eliminate non-zero elements below the pivot element
                for (row in pivotRow + 1 until numRows) {
                    val factor = matrix[row][col] / matrix[pivotRow][col]
                    for (colIndex in col until numCols) {
                        matrix[row][colIndex] -= factor * matrix[pivotRow][colIndex]
                    }
                }
            }
        }

        return rank
    }

    private fun stringToMatrix(matrixString: String?): Array<Array<Int>> {
        val rows = matrixString?.trim()?.split("\n") ?: return emptyArray()
        return rows.map { row ->
            row.trim().split("\\s+".toRegex()).map { it.toInt() }.toTypedArray()
        }.toTypedArray()
    }


    private fun matrixToString(matrix: Array<Array<Int>>?): String {
        if (matrix != null) {
            return matrix.joinToString("\n") { row ->
                row.joinToString("  ")
            }
        }
        return "NULL"
    }

    override fun finish() {
        super.finish()
        val intent = Intent(this, CalculationMatrix::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
