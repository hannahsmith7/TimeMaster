package com.part.myapplication.ui.Fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.part.myapplication.R

class TimeScreenFragment : Fragment() {
    private lateinit var timePicker: TimePicker
    private lateinit var buttonStart: Button
    private lateinit var buttonPause: Button
    private lateinit var buttonReset: Button

    private var handler: Handler = Handler()
    private var startTime: Long = 0
    private var elapsedTime: Long = 0
    private var isTimerRunning: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_timer_screen, container, false)
        timePicker = view.findViewById(R.id.timePicker)
        buttonStart = view.findViewById(R.id.button1)
        buttonPause = view.findViewById(R.id.button2)
        buttonReset = view.findViewById(R.id.button3)

        buttonStart.setOnClickListener { startStopwatch() }
        buttonPause.setOnClickListener { pauseStopwatch() }
        buttonReset.setOnClickListener { resetStopwatch() }

        return view
    }

    private fun startStopwatch() {
        if (!isTimerRunning) {
            startTime = System.currentTimeMillis()
            handler.post(stopwatchRunnable)
            isTimerRunning = true
        }
    }

    private fun pauseStopwatch() {
        if (isTimerRunning) {
            handler.removeCallbacks(stopwatchRunnable)
            isTimerRunning = false
        }
    }

    private fun resetStopwatch() {
        handler.removeCallbacks(stopwatchRunnable)
        elapsedTime = 0
        setTimePickerValues(0, 0)
        isTimerRunning = false
    }

    private fun setTimePickerValues(minutes: Int, seconds: Int) {
        timePicker.setIs24HourView(true)
        timePicker.currentHour = minutes
        timePicker.currentMinute = seconds
    }

    private val stopwatchRunnable = object : Runnable {
        override fun run() {
            val currentTime = System.currentTimeMillis()
            elapsedTime += currentTime - startTime
            startTime = currentTime

            val minutes = (elapsedTime / (1000 * 60)) % 60
            val seconds = (elapsedTime / 1000) % 60

            setTimePickerValues(minutes.toInt(), seconds.toInt())

            handler.postDelayed(this, 1000)
        }
    }
}
