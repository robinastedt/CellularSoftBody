/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astedt.robin.kdtree;

/**
 *
 * @author Robin
 */
public interface KDObject {
    public double getCoord(int d);
    
    public void setNode(KDNode node);
}
