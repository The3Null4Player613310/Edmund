package com.thenullplayer.ai.edmund;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

/**
 * Created by TheNullPlayer on 2/6/2017.
 */

public class Edmund
{

    //instance variables
    private InputParser inputParser;
    private AppCompatActivity activity;
    private TextToSpeech textToSpeech;
    private SpeechRecognizer speechToText;
    private TextView responseText;
    private ImageButton micButton;
    private String userID;
    private boolean isListening;

    public Edmund(AppCompatActivity activityIn,String userIDIn)
    {

        //set activity
        activity = activityIn;

        //set userid
        userID = userIDIn;

        //find controls
        responseText = (TextView) activity.findViewById(R.id.text_view);
        micButton = (ImageButton) activity.findViewById(R.id.mic_button);

        //setup text to speech
        textToSpeech = new TextToSpeech(activity.getApplicationContext(), new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int status)
            {
                if (status != TextToSpeech.ERROR)
                {
                    textToSpeech.setLanguage(Locale.UK);//UK -- male voice
                }
            }
        });

        speechToText = SpeechRecognizer.createSpeechRecognizer(activity);
        speechToText.setRecognitionListener(new SpeechToTextListener());

        //setup InputParser
        inputParser = new InputParser(this,activity,userID);
    }

    //start listening
    public void startListening()
    {
        isListening=true;
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.LANGUAGE_MODEL_FREE_FORM,true);//wip
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,true);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,activity.getPackageName());
        speechToText.startListening(intent);
    }

    //stop listening
    public void stopListening()
    {
        isListening=false;
        speechToText.stopListening();
    }

    //is listening
    public boolean isListening()
    {
        return isListening;
    }

    //respond
    public void respond(String outputIn)
    {
        responseText.setText(outputIn);
        startSpeaking(outputIn);
    }

    //start speaking
    public void startSpeaking(String input)
    {
        textToSpeech.speak(input, TextToSpeech.QUEUE_FLUSH, null);
    }

    //stop speaking
    public void stopSpeaking()
    {
        if (textToSpeech != null)
        {
            textToSpeech.stop();
        }
    }

    //destroy
    public void destroy()
    {
        if (textToSpeech != null)
        {
            textToSpeech.shutdown();
        }
        if (speechToText != null)
        {
            speechToText.destroy();
        }
    }

    //speech recognition listener
    private class SpeechToTextListener implements RecognitionListener
    {
        @Override
        public void onBeginningOfSpeech()
        {
        }

        @Override
        public void onBufferReceived(byte[] arg0)
        {
        }

        @Override
        public void onEndOfSpeech()
        {
            stopListening();
        }

        @Override
        public void onError(int error)
        {
            boolean shouldRespond = false;
            String[] response;
            String errorOut;
            switch (error)
            {
                case SpeechRecognizer.ERROR_AUDIO:
                    response = activity.getResources().getStringArray(R.array.edmund_audio);
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    response = activity.getResources().getStringArray(R.array.edmund_client);
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    response = activity.getResources().getStringArray(R.array.edmund_insufficient_permissions);
                    shouldRespond = true;
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    response = activity.getResources().getStringArray(R.array.edmund_network);
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    response = activity.getResources().getStringArray(R.array.edmund_network_timeout);
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    response = activity.getResources().getStringArray(R.array.edmund_no_match);
                    shouldRespond = true;
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    response = activity.getResources().getStringArray(R.array.edmund_recognizer_busy);
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    response = activity.getResources().getStringArray(R.array.edmund_server);
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    response = activity.getResources().getStringArray(R.array.edmund_speech_timeout);
                    shouldRespond = true;
                    break;
                default:
                    response = activity.getResources().getStringArray(R.array.edmund_speech_timeout);
                    break;
            }
            errorOut = response[(new Random()).nextInt(response.length)];
            if (shouldRespond)
            {
                responseText.setText(errorOut);
                startSpeaking(errorOut);
            }
        }

        @Override
        public void onEvent(int arg0, Bundle arg1)
        {
        }

        @Override
        public void onPartialResults(Bundle partialResults)
        {
            ArrayList data = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String inputText = (String) data.get(0);
            responseText.setText(inputText);
        }

        @Override
        public void onReadyForSpeech(Bundle params)
        {
            responseText.setText(activity.getString(R.string.edmund_listening));
        }

        @Override
        public void onResults(Bundle results)
        {
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String inputText = (String) data.get(0);
            //think about using all possibilities here...
            responseText.setText(inputText);
            micButton.setEnabled(false);
            inputParser.parseInput(inputText);
        }

        @Override
        public void onRmsChanged(float rmsdB)
        {
        }
    }
}
