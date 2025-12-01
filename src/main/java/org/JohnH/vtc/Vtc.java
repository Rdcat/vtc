/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.JohnH.vtc;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 *
 * @author johnhunt
 */

public class Vtc {
    public static void main(String[] args) {

        JFrame drawFrame = new JFrame ("Voice Code");

        VoiceCodePanel vCodePanel = new VoiceCodePanel();
        drawFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event){
                vCodePanel.transcriber.StopRecording();
                System.exit(0);
            }
        });

        try{
        drawFrame.getContentPane().add(vCodePanel);}
        catch(Exception e){
            return;}

        drawFrame.pack();
        drawFrame.setVisible(true);
        

    }
}
