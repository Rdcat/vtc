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

        JFrame drawFrame = new JFrame ("Voice to Code Application");

        VoiceCodePanel vCodePanel = new VoiceCodePanel();
        drawFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event){
                System.out.println("system has been stopped");
                vCodePanel.transcriber.StopRecording();
                System.exit(0);
            }
        });

        try{
        drawFrame.getContentPane().add(vCodePanel);}
        catch(Exception e){
            System.err.println("something wrong with the add in vtc");
            return;}

        drawFrame.pack();
        drawFrame.setVisible(true);
        

    }
}
