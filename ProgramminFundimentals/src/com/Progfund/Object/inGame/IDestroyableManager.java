/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund.Object.inGame;

import com.Liamengine.Engine.Components.Vector;
import com.Progfund.HashUtils;
import java.util.ArrayList;

/**
 *
 * @author RandomlyFuzzy
 */
public class IDestroyableManager {

    private static ArrayList<IDestroyable> destroyableObjs = new ArrayList<IDestroyable>();

    /*
    * resets the arraylist of objects 
     */

    /**
     *
     */

    public static void Reset() {
        destroyableObjs = new ArrayList<IDestroyable>();
    }

    /*
    * @return a instance of all currently created IDestroyables in the level
     */

    /**
     *
     * @return
     */

    public static ArrayList<IDestroyable> getInstance() {
        return new ArrayList<IDestroyable>(destroyableObjs);
    }

    /*
    * add an IDestroyable to an arraylist 
     */

    /**
     *
     * @param id
     */

    public static void Add(IDestroyable id) {
        destroyableObjs.add(id);
    }

    /*
    * removes and object from an arraylist
    */

    /**
     *
     * @param id
     */

    public static void remove(IDestroyable id) {
        destroyableObjs.remove(id);
    }

    /*
    * simple check of hash's to everything spawned thats an instanceof IDestroyable
    * @return true if not found false if found 
     */

    /**
     *
     * @param a
     * @param b
     * @return
     */

    public static boolean willBeUnique(Vector a, Vector b) {
        for (IDestroyable id : destroyableObjs) {
            if (id.getRefHash() == HashUtils.hash(a, b)) {
                return false;
            }
        }
        return true;
    }

    /*
    * simple check of hash's to everything spawned thats an instanceof IDestroyable
    * @return true if not found false if found 
     */

    /**
     *
     * @param id
     * @return
     */

    public static boolean IsUnique(IDestroyable id) {
        for (IDestroyable id2 : destroyableObjs) {
            if (id2.getRefHash() == id.getRefHash()) {
                return false;
            }
        }
        return true;
    }

}
