package com.xml.visionapp.presentation

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import net.gotev.speech.SpeechDelegate

class VoiceCommandHandler(
    private val doSomeThingWithRecognisedWord: () -> Unit
) : SpeechDelegate {

    private var _recognisedText = MutableStateFlow("")
    val recognisedText = _recognisedText

    override fun onStartOfSpeech() {}

    override fun onSpeechRmsChanged(value: Float) {}

    override fun onSpeechPartialResults(results: MutableList<String>?) {}

    override fun onSpeechResult(result: String?) {
        Log.d("xml22", "onSpeechResult: $result")
        _recognisedText.value = result ?: ""
        if (result != null) doSomeThingWithRecognisedWord()
    }
}