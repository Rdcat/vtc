/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.JohnH.vtc;

/**
 *
 * @author johnhunt
 */
public interface  TranscriberListener {
    void onPartialResult(String tString);
    void onFinalResult(String tString);
    void onError(String errorrString);



}
