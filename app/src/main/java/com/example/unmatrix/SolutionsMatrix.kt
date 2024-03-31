package com.example.unmatrix

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.unmatrix.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class SolutionsMatrix : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.matrix_solutions)

        val matrixString = intent.getStringExtra("matrix_string")
        // Set the formatted matrix string to the matrixGrid TextView
        val matrixGrid: TextView = findViewById(R.id.matrixGrid)
        matrixGrid.text = matrixString

        val matrix = stringToMatrix(matrixString)
        // Calculate the determinant of the matrix
        val determinant = calculateDeterminant(matrix)

        // Display the determinant in the TextView
        val determinantTextView: TextView = findViewById(R.id.matrixOperationValue)
        determinantTextView.text = determinant.toString()


        // access the items of the list
        val languages = resources.getStringArray(R.array.spinner_tools)

        // access the spinner
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
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        this@SolutionsMatrix,
                        getString(R.string.selected_item) + " " +
                                "" + languages[position], Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

    }
    private fun stringToMatrix(matrixString: String?): Array<Array<Int>> {
        val rows = matrixString?.trim()?.split("\n") ?: return emptyArray()
        return rows.map { row ->
            row.trim().split("\\s+".toRegex()).map { it.toInt() }.toTypedArray()
        }.toTypedArray()
    }

    private fun calculateDeterminant(matrix: Array<Array<Int>>): Int {
        // Check if the matrix is square
        if (matrix.size != matrix[0].size) {
            throw IllegalArgumentException("Matrix is not square")
        }

        // Base case for 2x2 matrix
        if (matrix.size == 2) {
            return (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0])
        }

        var determinant = 0
        for (i in matrix.indices) {
            determinant += (if (i % 2 == 0) 1 else -1) * matrix[0][i] *
                    calculateDeterminant(matrix.filterIndexed { index, _ -> index != i }
                        .map { it.sliceArray(1 until it.size) }.toTypedArray())
        }
        return determinant
    }
}
    // Function to format the matrix as a grid string
