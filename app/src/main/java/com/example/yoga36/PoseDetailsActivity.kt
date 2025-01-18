package com.example.yoga36

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import android.net.Uri
import com.google.android.material.button.MaterialButton

class PoseDetailActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pose_detail)

        val pose = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("pose", YogaPose::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("pose")
        }
        pose?.let {
            GlideToVectorYou
                .init()
                .with(this)
                .load(Uri.parse(it.image), findViewById(R.id.poseImageView))
            findViewById<TextView>(R.id.sanskritNameTextView).text =  it.sanskrit_name
            findViewById<TextView>(R.id.englishNameTextView).text = it.english_name
            findViewById<TextView>(R.id.descriptionTextView).text = it.description
            findViewById<TextView>(R.id.benefitsTextView).text = "Benefits: ${it.benefits}"
            findViewById<TextView>(R.id.timeTextView).text = "Time: ${it.time}"

            findViewById<MaterialButton>(R.id.learnButton).setOnClickListener {
                val intent = Intent(this, YogaStepsActivity::class.java)
                intent.putExtra("pose", pose)
                startActivity(intent)
            }

            findViewById<MaterialButton>(R.id.startButton).setOnClickListener {
                val intent = Intent(this, TimerActivity::class.java)
                intent.putExtra("pose", pose)
                startActivity(intent)
            }
            findViewById<MaterialButton>(R.id.backButton).setOnClickListener {
                finish()
            }
//            val formattedSteps = formatSteps(it.steps)
//            findViewById<TextView>(R.id.stepsTextView).text = "Steps:\n$formattedSteps"
        }
    }
    private fun formatSteps(steps: String): String {
        return steps.split(".")
            .filter { it.isNotBlank() }
            .mapIndexed { index, step ->
                "Step ${index + 1}: ${step.trim()}"
            }
            .joinToString("\n\n")
    }
}
