package com.example.unmatrix

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CalculationMatrix : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.matrix_calculation)

        val row: EditText = findViewById(R.id.rowsMatrix)
        val column: EditText = findViewById(R.id.columnMatrix)
        val rowData: EditText = findViewById(R.id.rowData)
        val rowSubmitButton: Button = findViewById(R.id.rowSubmitButton)
        val calculateButton: Button = findViewById(R.id.calculateButton)

        var numberOfRows = 0
        var numberOfColumns = 0
        var currRow = 0
        var matrix: Array<Array<Int>>? = null

        rowSubmitButton.setOnClickListener {
            val rowText = row.text.toString()
            val columnText = column.text.toString()

            if (rowText.isEmpty() || columnText.isEmpty()) {
                Toast.makeText(this, "Please Enter row and column", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Exit the function early if validation fails
            }
            // Convert the input string to an array of integers
            val inputString = rowData.text.toString()
            val rowValues = inputString.split("\\s+".toRegex()).map { it.toInt() }.toTypedArray()

            if (matrix == null) {
                numberOfRows = row.text.toString().toInt()
                numberOfColumns = column.text.toString().toInt()
                matrix = Array(numberOfRows) { Array(numberOfColumns) { 0 } }
            }

                // Store the row values in the matrix
            for (i in rowValues.indices) {
                    matrix!![currRow][i] = rowValues[i]
            }
            currRow++

            // Clear the rowData EditText for the next input
            rowData.text.clear()

            if (currRow == matrix!!.size) {
                Toast.makeText(this, "You have entered all rows, PRESS CALCULATE", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


        }

        calculateButton.setOnClickListener {
            var sum = 0
            if (matrix != null) {
                for (rows in matrix!!) {
                    for (el in rows) {
                        sum += el
                    }
                }
            }

            // Show the sum as a toast message
            Toast.makeText(this, "Sum : $sum", Toast.LENGTH_SHORT).show()

            // Convert the matrix to a string
            val matrixString = matrixToString(matrix)

            // Create an intent to start the SolutionsMatrix activity
            val intent = Intent(this, SolutionsMatrix::class.java)

            // Pass the matrix string as an extra with the intent
            intent.putExtra("matrix_string", matrixString as CharSequence)

            // Start the SolutionsMatrix activity
            startActivity(intent)
        }

    }
    private fun matrixToString(matrix: Array<Array<Int>>?): String {
        if (matrix == null) return ""

        val rows = mutableListOf<String>()
        for (row in matrix) {
            val rowString = row.joinToString(separator = " ")
            rows.add(rowString)
        }
        return rows.joinToString(separator = "\n")
    }
}

