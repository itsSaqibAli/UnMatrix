package com.example.unmatrix

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import android.widget.Button
import androidx.core.view.WindowInsetsCompat



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton: Button = findViewById(R.id.StartButton)

        // Set click listener for the start button
        startButton.setOnClickListener {
            // Start the SolutionActivity
            val intent = Intent(this, CalculationMatrix::class.java)
            startActivity(intent)
        }

        // Alternatively, if you want to show the matrix_calculation layout on button click
//        startButton.setOnClickListener {
//            // Change visibility of matrix_calculation layout to VISIBLE
//            setContentView(R.layout.matrix_calculation)
//        }
    }
}
