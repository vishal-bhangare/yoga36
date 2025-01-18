package com.example.yoga36

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class YogaStepsActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yoga_steps)

        val pose = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("pose", YogaPose::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("pose")
        }
        pose?.let {
            findViewById<TextView>(R.id.poseNameTextView).text = "${it.sanskrit_name} (${it.english_name})"

            // Format steps with numbers
            val formattedSteps = it.steps.split(".")
                .filter { step -> step.isNotBlank() }
                .mapIndexed { index, step ->
                    "Step ${index + 1}: ${step.trim()}"
                }
                .joinToString("\n\n")

            findViewById<TextView>(R.id.stepsTextView).text = formattedSteps

            findViewById<MaterialButton>(R.id.backButton).setOnClickListener {
                finish()
            }

            findViewById<MaterialButton>(R.id.startButton).setOnClickListener {
                val intent = Intent(this, TimerActivity::class.java)
                intent.putExtra("pose", pose)
                startActivity(intent)
            }
        }
    }
}
