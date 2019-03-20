/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund.Object.Menu;

/**
 *
 * just a delegate class used on the button and toggle objects
 * 
 * 
 * @author Liam Woolley 1748910
 */
public abstract class HUDdelegate {

    /**
     *
     * @param b is the button that this is attached to
     */
    public void OnClick (Button b){}

    /**
     *
     * @param b is the toggle that this is attached to
     */
    public void OnClick (toggle b){}

}
