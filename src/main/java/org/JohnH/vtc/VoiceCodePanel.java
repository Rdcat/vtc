package org.JohnH.vtc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
public class VoiceCodePanel extends JPanel  implements TranscriberListener{



    int yLimit = 720;
    int xLimit = 1040;

    //swing worker object
    RecordWorker recordWorker;

    public boolean currentlyCoding = false;
    public boolean stopButtonPressed = false;
    Transcriber transcriber = new Transcriber();
    
    JTextArea  UserJGuide = new JTextArea("Voice to code Commands:\n" + 
    "dot → . \n" +
    "if → if( \n" + 
    "while → while( \n" +
    "print → println \n" + 
    "equals → = \n" + 
    "end → ) \n" + 
    "start → ( \n" + 
    "start curly → { \n" + 
    "end curly → } \n" + 
    "text → \" \n" + 
    "and sign → && \n" + 
    "or → || \n" +
    "MUST SAY CODE FOR THE TEXT TO BE PUT TO CODE");
    JLabel recordLabel = new JLabel("Press to record");
    JButton recordButton = new JButton("Record");
    JButton stopRecorButton = new JButton("Stop Recording");
    JButton clearButton = new  JButton("Clear The Code");
    JTextArea recordedJTextArea = new JTextArea("this is the area");
    JTextArea codeArea = new JTextArea("this is the code area");
    JScrollPane codeArScrollPane;

    
    public VoiceCodePanel(){
        super();
        InitializeComponents();
    }

    
    public void  InitializeComponents(){
        transcriber.setTranscriberListener(this);
        //System.out.println("gets to Gpraphics");


        // TODO: implement panel functionality

        //mouse click listener
        MouseInteract mInteract = new MouseInteract();
        clearMouse cMouse = new clearMouse();

        

        // Set the properties of the panel and its objects in it
        setLayout(null);
        setPreferredSize(new Dimension(xLimit, yLimit));

        stopRecorButton.setBounds(200, 420, 100, 50);
        stopRecorButton.setOpaque(!currentlyCoding);
        stopRecorButton.setBackground(Color.white);
        
        recordedJTextArea.setBounds(100, 500, 500, 150);
        recordedJTextArea.setLineWrap(true);
        add(recordedJTextArea);

        

        clearButton.setBounds(840, 420, 100, 50);

        recordButton.setText("RECORD");
        recordButton.setBounds(200, 360, 100, 50);
        recordButton.setOpaque(currentlyCoding);
        recordButton.setBackground(Color.white);

        recordLabel.setBounds(310, 375, 200, 20);

        codeArScrollPane = new JScrollPane(codeArea, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        codeArScrollPane.setBounds(620, 30, 200, 500);
        
        UserJGuide.setBounds(20, 20, 200, 400);
        UserJGuide.setLineWrap(true);
        UserJGuide.setWrapStyleWord(true);
        UserJGuide.setEditable(false);
        UserJGuide.setBackground(this.getBackground());
        
        codeArea.setLineWrap(true);
        codeArea.setWrapStyleWord(true);

        add(codeArScrollPane);
        add(clearButton);
        add(recordLabel);
        add(UserJGuide);
        add(recordButton);
        add(stopRecorButton);

        recordButton.addMouseListener(mInteract);
        stopRecorButton.addMouseListener(mInteract);
        clearButton.addMouseListener(cMouse);
        

    }

    @Override
    public void onPartialResult(String tString) {

        SwingUtilities.invokeLater(() -> {
            recordedJTextArea.setText(tString);
        });



    }

    @Override
    public void onFinalResult(String tString) {
        SwingUtilities.invokeLater(() -> {
            recordedJTextArea.setText(tString);
            String extracString = tString;
            System.out.println(extracString);
            String newString = ExtractionString(tString);
            codeArea.append(newString + "\n    ");
        });    
    }

    @Override
    public void onError(String errorrString) {
        SwingUtilities.invokeLater(() -> {
            recordedJTextArea.setText(errorrString);
        });    }

    private class MouseInteract extends MouseAdapter{

        @Override
        public void mouseClicked(MouseEvent event){
            
            if(currentlyCoding == true){
                //logic for when the recording is stopped

                recordButton.setText("Record");
                recordButton.setBackground(Color.white);
                currentlyCoding = !currentlyCoding;
                recordButton.setOpaque(currentlyCoding);
                recordWorker.cancel(true);
                transcriber.StopRecording();

                try {
                    wait(200);
                } catch (InterruptedException ex) {
                }
                recordedJTextArea.setText("recording stopped!");


            }
            else{
                //logic for when the recording function is started
                recordButton.setText("!!Recording!!");
                recordButton.setBackground(Color.red);
                currentlyCoding = !currentlyCoding;
                recordButton.setOpaque(currentlyCoding);
                try{

                    recordWorker = new RecordWorker();

                    recordWorker.execute();
                
                }
                catch(Exception exception){

                }


                

            }

        }

    }
    private class clearMouse extends MouseAdapter{
        @Override
        public void  mouseClicked(MouseEvent event){
            codeArea.setText(""); 
        }
    }
    private class RecordWorker extends SwingWorker<Void, String>{
        
            

        @Override
        protected Void doInBackground() throws Exception {
            //System.out.println("gangasta paradise sing it!!!!!");
            transcriber.StartRecording();

            return null;
            
        }

        
        
    }
    

    public String ExtractionString(String subject){

        if(subject.contains("\"text\"") && subject.contains("\"") && subject.contains("code")){

            subject = subject.replace("code", "");
            int start = (subject.indexOf("\"text\"") + 7);
            start = subject.indexOf("\"", start) + 1;
            int end = subject.indexOf("\"", start);
            if(start > 0 && end > start){
            String preCodedString = subject.substring(start, end);
            String postCodedString = transcriber.TextToCode(preCodedString);
            return postCodedString;

            }
        }

        subject = "";
        return  subject;

    }
    
}