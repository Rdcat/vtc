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

    private final int sampleRate = 16000;
    private final int bufferSize = 4096;
     

    //listener
    private TranscriberListener tListener;



    //setting the listener
    public void setTranscriberListener(TranscriberListener lTranscriber){
        this.tListener = lTranscriber;
    }
    private void notifyPartialResult(String text){
        if(tListener != null){
            tListener.onPartialResult(text);
        }
    }
    private void notifyFinalResult(String text){
        if(tListener != null){
            tListener.onFinalResult(text);
            System.out.println(text);

            
        }
    }
    private void notifyError(String text){
            tListener.onError(text);
        
    }

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
            //the library shows logs for devugging
            LibVosk.setLogLevel(LogLevel.DEBUG);
            //finds the model at this file path
            try(Model model = new Model("src/resources/models/vosk-model")) {
                //recognizer is an object that will "recognize" the raw audio
                Recognizer recognizer = new Recognizer(model, sampleRate);

                //specifies
                AudioFormat audioFormat = new AudioFormat(sampleRate, 16, 1, true, false);

                DataLine.Info dataLine = new DataLine.Info(TargetDataLine.class, audioFormat);

                try(TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(dataLine)) {
                    microphone.open(audioFormat);
                    microphone.start();

                    byte[] buffer = new byte[bufferSize];
                    int bytesRead = 0;

                    
                    while(isRecording = true){
                    
                    bytesRead = microphone.read(buffer, 0, bufferSize);

                    
                    if(recognizer.acceptWaveForm(buffer, bytesRead)){
                        //System.out.println("not the true final");
                        String result = recognizer.getResult();

                        //System.out.println("Final: " + recognizer.getResult());
                        notifyFinalResult(result);
                        //isRecording = false;
                        

                    } else{
                        //System.out.println("Partial: " + recognizer.getPartialResult());
                        notifyPartialResult(recognizer.getPartialResult());
                        
                    }
                    
                    System.out.println("is recording = " + isRecording);
                
                    

                
                    Thread.sleep(100);


                }
                    microphone.stop();  
                    //System.out.println("Real Final Result: " + recognizer.getFinalResult()); 
                    notifyFinalResult(recognizer.getFinalResult());
                } 
                catch (Exception e) {
                    notifyError("transcription error " + e.getMessage());
                }

                
            } catch (Exception e) {
                notifyError("initialization error " + e.getMessage());

            }
            
        } catch (Exception e) {
        }

    }
    public class RecordingThread extends Thread{
        public void run(){
            
        }
    }

    //Stop recording Function
    public void StopRecording(){
        //stop recording logic
        //System.out.println("Stop Recording");
        isRecording = false;


    }
    
    //taking the writing and outputing it as code
    public String TextToCode(String preCodeString){
        //Coding Converter logic
        preCodeString = preCodeString.replace("the", "");
        preCodeString = preCodeString.replace(" dot ", ".");
        preCodeString = preCodeString.replace(" that ", ".");
        preCodeString = preCodeString.replace(" docked ", ".");

        preCodeString = preCodeString.replace(" thought ", ".");
        preCodeString = preCodeString.replace(" if ", "if( ");
        preCodeString = preCodeString.replace(" print ", "println");
        preCodeString = preCodeString.replace("while", "while( ");
        preCodeString = preCodeString.replace("equals", "=");
        preCodeString = preCodeString.replace(" end", ")");
        preCodeString = preCodeString.replace(" and", ")");

        preCodeString = preCodeString.replace("start curly", "{");
        preCodeString = preCodeString.replace("end curly", "}");
        preCodeString = preCodeString.replace("start", "(");
        preCodeString = preCodeString.replace(" text ", "\" ");
        preCodeString = preCodeString.replace(" and sign", "&&");
        preCodeString = preCodeString.replace(" or ", "||");

        preCodeString = preCodeString.replace(" at ", "@");


        preCodeString = preCodeString.concat( ";");

        return preCodeString;



    }
    
    
}
