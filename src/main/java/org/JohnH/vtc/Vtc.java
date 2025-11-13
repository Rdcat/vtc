/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.JohnH.vtc;

import javax.swing.JFrame;

/**
 *
 * @author johnhunt
 */

public class Vtc {
    public static void main(String[] args) {

        JFrame drawFrame = new JFrame ("Voice to Code Application");

        VoiceCodePanel vCodePanel = new VoiceCodePanel();

        try{
        drawFrame.getContentPane().add(vCodePanel);}
        catch(Exception e){
            System.err.println("something wrong with the add in vtc");
            return;}

        drawFrame.pack();
        drawFrame.setVisible(true);
        

    }
}
