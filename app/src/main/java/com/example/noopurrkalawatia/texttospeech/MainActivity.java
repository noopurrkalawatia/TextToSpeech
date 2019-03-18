package com.example.noopurrkalawatia.texttospeech;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private Button btn;
    private EditText editTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.button);

        editTxt = (EditText) findViewById(R.id.editText);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int status)
            {
                if(status == TextToSpeech.SUCCESS)
                {
                    int ttsLang = textToSpeech.setLanguage(Locale.US);
                    if(ttsLang == TextToSpeech.LANG_MISSING_DATA ||
                            ttsLang == TextToSpeech.LANG_NOT_SUPPORTED)
                    {
                        Log.e("TTS","The language is not supported");
                    }
                    else
                    {
                        Log.i("TTS","Language Supported");
                    }
                    Log.e("TTS","Initialization process is completed");
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"TTS initialization failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                String data = editTxt.getText().toString();
                Log.i("TTS","Button clicked : " + data);
                int speechStatus = textToSpeech.speak(data,TextToSpeech.QUEUE_FLUSH,null);

                if(speechStatus == TextToSpeech.ERROR)
                {
                    Log.e("TTS","Error in converting Text to Speech!");
                }
            }

        });
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(textToSpeech != null)
        {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
