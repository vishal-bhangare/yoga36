package com.example.yoga36

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import java.util.concurrent.TimeUnit

class TimerActivity : AppCompatActivity() {
    private lateinit var timerTextView: TextView
    private lateinit var resetButton: MaterialButton
    private lateinit var backButton: MaterialButton
    private lateinit var countDownTimer: CountDownTimer
    private var timeInMillis: Long = 0
    private var isTimerRunning = false

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        timerTextView = findViewById(R.id.timerTextView)
        resetButton = findViewById(R.id.resetButton)
        backButton = findViewById(R.id.backButton)

        // Get time from pose and convert to milliseconds
        val pose = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("pose", YogaPose::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("pose")
        }
        timeInMillis = parseTimeToMillis(pose?.time ?: "2 min")

        // Initialize timer display
        updateTimerUI(timeInMillis)
        resetButton.visibility = View.GONE

        // Set click listeners
        findViewById<View>(R.id.timerContainer).setOnClickListener {
            if (!isTimerRunning) startTimer()
        }

        resetButton.setOnClickListener {
            resetTimer()
        }

        backButton.setOnClickListener {
            finish()
        }
        showStartMessage()
    }

    private fun parseTimeToMillis(timeString: String): Long {
        val cleanTime = timeString.trim().toLowerCase()

        return when {
            cleanTime.contains(":") -> {
                val timeParts = cleanTime.split(" ")[0].split(":")
                val minutes = timeParts[0].toLong()
                val seconds = timeParts[1].toLong()
                (minutes * 60 + seconds) * 1000L
            }
            cleanTime.contains("min") -> {
                val number = cleanTime.split(" ")[0].toLong()
                number * 60 * 1000L
            }
            else -> {
                cleanTime.split(" ")[0].toLong() * 1000L
            }
        }
    }


    private fun startTimer() {
        isTimerRunning = true
        countDownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerUI(millisUntilFinished)
            }

            override fun onFinish() {
                updateTimerUI(0)
                resetButton.visibility = View.VISIBLE
                isTimerRunning = false
            }
        }.start()
    }

    private fun resetTimer() {
        if (isTimerRunning) {
            countDownTimer.cancel()
            isTimerRunning = false
        }
        updateTimerUI(timeInMillis)
        resetButton.visibility = View.GONE
    }

    private fun updateTimerUI(millis: Long) {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) -
                TimeUnit.MINUTES.toSeconds(minutes)
        timerTextView.text = String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isTimerRunning) {
            countDownTimer.cancel()
        }
    }
    private fun showStartMessage() {
        val customToast = layoutInflater.inflate(
            R.layout.custom_toast,
            findViewById(android.R.id.content),
            false
        )
        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_LONG
        toast.view = customToast
        toast.show()
    }

}
