/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astedt.robin.quadtree;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Robin
 */
public class Node {
    private Node nodeNW;
    private Node nodeNE;
    private Node nodeSW;
    private Node nodeSE;
    
    private List<QuadTreeObject> objects;
    
    private boolean isLeaf;
    
    public List<QuadTreeObject> getObjects() {
        return objects;
    }
    
    public Node(List<QuadTreeObject> objects, double x0, double y0, double x1, double y1, double objectRadius) {
        if (objects.size() <= 1 || x1 - x0 <= objectRadius * 2 || y1 - y0 < objectRadius * 2) {
            isLeaf = true;
            this.objects = objects;
            for (QuadTreeObject object : objects) {
                object.setNode(this);
            }
        }
        else {
            double x0nw = x0;
            double y0nw = y0;
            double x1nw = x0 + (x1-x0) / 2;
            double y1nw = y0 + (y1-y0) / 2;

            double x0ne = x1nw;
            double y0ne = y0;
            double x1ne = x1;
            double y1ne = y1nw;

            double x0sw = x0;
            double y0sw = y1nw;
            double x1sw = x1nw;
            double y1sw = y1;

            double x0se = x1nw;
            double y0se = y1nw;
            double x1se = x1;
            double y1se = y1;
            
            List<QuadTreeObject> objectsNW = null,
                                 objectsNE = null, 
                                 objectsSW = null, 
                                 objectsSE = null;
            
            for (QuadTreeObject object : objects) {
                
                if (object.getX() >= x0nw + objectRadius && object.getX() < x1nw - objectRadius) {
                    //West side
                    if (object.getY() >= y0nw + objectRadius && object.getY() < y1nw - objectRadius) {
                        //North-west side
                        if (objectsNW == null) objectsNW = new ArrayList<>();
                        objectsNW.add(object);
                    }
                    else if (object.getY() >= y0sw + objectRadius && object.getY() < y1sw - objectRadius) {
                        //South-west side
                        if (objectsSW == null) objectsSW = new ArrayList<>();
                        objectsSW.add(object);
                    }
                    else {
                        object.setNode(this);
                    }
                }
                else if (object.getX() >= x0ne + objectRadius && object.getX() < x1ne - objectRadius) {
                    //East side
                    if (object.getY() >= y0ne + objectRadius && object.getY() < y1ne - objectRadius) {
                        //North-east side
                        if (objectsNE == null) objectsNE = new ArrayList<>();
                        objectsNE.add(object);
                    }
                    else if (object.getY() >= y0se + objectRadius && object.getY() < y1se - objectRadius) {
                        //South-east side
                        if (objectsSE == null) objectsSE = new ArrayList<>();
                        objectsSE.add(object);
                    }
                    else {
                        object.setNode(this);
                    }
                }
                else {
                    object.setNode(this);
                }
            }
            
            this.objects = objects;
            
            if (objectsNW != null) {
                nodeNW = new Node(objectsNW, x0nw, y0nw, x1nw, y1nw, objectRadius);
            }
            if (objectsNE != null) {
                nodeNE = new Node(objectsNE, x0ne, y0ne, x1ne, y1ne, objectRadius);
            }
            if (objectsSW != null) {
                nodeSW = new Node(objectsSW, x0sw, y0sw, x1sw, y1sw, objectRadius);
            }
            if (objectsSE != null) {
                nodeSE = new Node(objectsSE, x0se, y0se, x1se, y1se, objectRadius);
            }
        }
        
        
        
    }
}
