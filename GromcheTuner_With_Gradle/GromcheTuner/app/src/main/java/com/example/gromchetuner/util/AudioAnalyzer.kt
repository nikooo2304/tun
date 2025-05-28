package com.example.gromchetuner.util

import be.tarsos.dsp.AudioDispatcher
import be.tarsos.dsp.io.android.AudioDispatcherFactory
import be.tarsos.dsp.pitch.PitchDetectionHandler
import be.tarsos.dsp.pitch.PitchProcessor
import kotlin.math.abs

object AudioAnalyzer {

    private val notes = listOf(
        "E2" to 82.41f,
        "A2" to 110.00f,
        "D3" to 146.83f,
        "G3" to 196.00f,
        "B3" to 246.94f,
        "E4" to 329.63f
    )

    fun start(onResult: (Float, String) -> Unit) {
        val dispatcher: AudioDispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0)
        val pdh = PitchDetectionHandler { result, _ ->
            val pitchInHz = result.pitch
            if (pitchInHz > 0) {
                val note = closestNote(pitchInHz)
                onResult(pitchInHz, note)
            }
        }
        dispatcher.addAudioProcessor(PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.YIN, 22050f, 1024, pdh))
        Thread(dispatcher, "Audio Dispatcher").start()
    }

    private fun closestNote(freq: Float): String {
        return notes.minByOrNull { (_, f) -> abs(f - freq) }?.first ?: "—"
    }
}