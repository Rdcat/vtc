package org.JohnH.vtc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
public class VoiceCodePanel extends JPanel {



    int yLimit = 720;
    int xLimit = 1040;

    public boolean currentlyCoding = false;
    public boolean stopButtonPressed = false;
    Transcriber transcriber = new Transcriber();
    

    JLabel recordLabel = new JLabel("Press to record");
    JButton recordButton = new JButton("Record");
    JButton stopRecorButton = new JButton("Stop Recording");
    JTextArea recordedJTextArea = new JTextArea("this is the area");

    
    public VoiceCodePanel(){
        super();
        InitializeComponents();
    }

    
    public void  InitializeComponents(){
        System.out.println("gets to Gpraphics");


        // TODO: implement panel functionality

        //mouse click listener
        MouseInteract mInteract = new MouseInteract();

        // Set the properties of the panel
        setLayout(null);
        setPreferredSize(new Dimension(xLimit, yLimit));

        stopRecorButton.setBounds(200, 420, 100, 50);
        stopRecorButton.setOpaque(!currentlyCoding);
        stopRecorButton.setBackground(Color.white);
        

        recordButton.setText("RECORD");
        recordButton.setBounds(200, 360, 100, 50);
        recordButton.setOpaque(currentlyCoding);
        recordButton.setBackground(Color.white);

        recordLabel.setBounds(310, 375, 200, 20);

        add(recordLabel);
        add(recordButton);
        add(stopRecorButton);

        recordButton.addMouseListener(mInteract);
        stopRecorButton.addMouseListener(mInteract);


    }

    private class MouseInteract extends MouseAdapter{

        @Override
        public void mouseClicked(MouseEvent event){
            
            if(currentlyCoding == true){
                //logic for when the recording is stopped

                recordButton.setText("Record");
                recordButton.setBackground(Color.white);
                currentlyCoding = !currentlyCoding;
                recordButton.setOpaque(currentlyCoding);
                transcriber.StopRecording();



            }
            else{
                //logic for when the recording function is started
                recordButton.setText("!!Recording!!");
                recordButton.setBackground(Color.red);
                currentlyCoding = !currentlyCoding;
                recordButton.setOpaque(currentlyCoding);
                try{
                transcriber.StartRecording();
                }
                catch(IOException | UnsupportedAudioFileException exception){

                }


                

            }

        }

    }

    public void addTextSubject(String subject){
        recordedJTextArea.setText(recordedJTextArea.getText() + " " + subject);
    }
    
}