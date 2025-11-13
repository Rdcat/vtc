package org.JohnH.vtc;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

public class Transcriber{
    public boolean isRecording = false;

    private int sampleRate = 16000;
    private int bufferSize = 4096;
    
    //Start Recording Function
    public void StartRecording()throws IOException, UnsupportedAudioFileException{
        //start countdown

        //after countdown give feedback showing ready to listen
        isRecording = true;
        RecordingLogic();
        
        
    
    }
    private void RecordingLogic() throws IOException, UnsupportedAudioFileException{
        //start recording logic
        System.out.println("Start Recording");try {
            LibVosk.setLogLevel(LogLevel.DEBUG);
            try(Model model = new Model("src/resources/models/vosk-model")) {
                Recognizer recognizer = new Recognizer(model, sampleRate);

                AudioFormat audioFormat = new AudioFormat(sampleRate, 16, 1, true, false);
                DataLine.Info dataLine = new DataLine.Info(TargetDataLine.class, audioFormat);
                try(TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(dataLine)) {
                    microphone.open(audioFormat);
                    microphone.start();

                    byte[] buffer = new byte[bufferSize];
                    while(isRecording){
                    int bytesRead = microphone.read(buffer, 0, bufferSize);
                    if(bytesRead > 0){
                    if(recognizer.acceptWaveForm(buffer, bufferSize)){
                        System.out.println("Final: " + recognizer.getResult());

                    } else{
                        System.out.println("Partial: " + recognizer.getPartialResult());

                    }
                }
                    Thread.sleep(100);
                    UpdateText(recognizer.getPartialResult());
                    

                }
                    microphone.stop();  
                    System.out.println("Final Result: " + recognizer.getFinalResult()); 
                } 
                catch (Exception e) {
                }

                
            } catch (Exception e) {
            }
            
        } catch (Exception e) {
        }

    }
    //Stop recording Function
    public void StopRecording(){
        //stop recording logic
        System.out.println("Stop Recording");
        isRecording = false;


    }
    //writing the audio out
    public void AudioToText(){
        //Audio converter logic
    }
    //taking the writing and outputing it as code
    public void TextToCode(){
        //Coding Converter logic
    }
    public void UpdateText(String currentString){
        

        
    }
}
