package com.example.unmatrix

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CalculationMatrix : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.matrix_calculation)

        val rowMatrix1: EditText = findViewById(R.id.rowsMatrix1)
        val columnMatrix1: EditText = findViewById(R.id.columnMatrix1)
        val rowMatrix2: EditText = findViewById(R.id.rowsMatrix2)
        val columnMatrix2: EditText = findViewById(R.id.columnMatrix2)
        val rowData: EditText = findViewById(R.id.rowData)
        val rowSubmitButton: Button = findViewById(R.id.rowSubmitButton)
        val calculateButton: Button = findViewById(R.id.calculateButton)

        var matrix1: String? = ""
        var matrix2: String? = ""

        var firstRowInserted = false
        var secondRowInserted = false

        var currRow1 = 0
        var currRow2 = 0

        rowSubmitButton.setOnClickListener {
            val row1 = rowMatrix1.text.toString()
            val row2 = rowMatrix2.text.toString()
            val column1 = columnMatrix1.text.toString()
            val column2 = columnMatrix2.text.toString()

            val inputString = rowData.text.toString()

            if(inputString == "") {
                Toast.makeText(this, "Empty Row, dumpty Row -: Re-Enter!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(secondRowInserted) {
                Toast.makeText(this, "Press Calculate!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if ((row1.isEmpty() || column1.isEmpty()) && (row2.isEmpty() || column2.isEmpty())) {
                Toast.makeText(this, "Specify row and column first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!firstRowInserted) {
                matrix1 += if (currRow1 == row1.toInt()-1) {
                    inputString
                } else {
                    "$inputString\n"
                }
                currRow1++
                if (currRow1 == row1.toInt()) {
                    Toast.makeText(this, "Matrix 1 Successfully Passed \nINSERT MATRIX 2 OR PRESS CALCULATE", Toast.LENGTH_SHORT).show()
                    firstRowInserted = true
                    rowData.text.clear()
                    return@setOnClickListener
                }

            } else {
                matrix2 += if (currRow2 == row2.toInt()-1) {
                    inputString
                } else {
                    "$inputString\n"
                }
                currRow2++
                if (currRow2 == row2.toInt()) {
                    Toast.makeText(this, "Matrix 2 Successfully Passed, PRESS CALCULATE", Toast.LENGTH_SHORT).show()
                    rowData.text.clear()
                    secondRowInserted = true
                    return@setOnClickListener
                }
            }

            rowData.text.clear()

            rowData.text.clear()
        }

        calculateButton.setOnClickListener {
            if (matrix1 == "" || matrix1 == null) {
                Toast.makeText(this, "Matrices are not fully inserted!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Toast.makeText(this, "Matrix 1: $matrix1 Matrix 2: $matrix2", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, SolutionsMatrix::class.java)
            intent.putExtra("matrix_string1", matrix1)
            if (matrix2 != null && matrix2.isNotEmpty()) {
                    intent.putExtra("matrix_string2", matrix2)
            }

            startActivity(intent)
        }
    }
}