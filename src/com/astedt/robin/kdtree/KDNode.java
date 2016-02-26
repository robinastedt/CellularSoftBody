
package com.astedt.robin.kdtree;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Robin
 */
public class KDNode {
    private final int k, d;
    private KDNode fstNode, sndNode;
    
    private final double[][] bounds;
    
    private final List<KDObject> objects;
    
    public void draw(Graphics2D g) {
        if (fstNode != null) fstNode.draw(g);
        if (sndNode != null) sndNode.draw(g);
        if (fstNode == null || sndNode == null) g.drawRect((int)bounds[0][0], (int)bounds[1][0], (int)(bounds[0][1] - bounds[0][0]), (int)(bounds[1][1] - bounds[1][0]));
    }
    
    public List<KDObject> getObjects() {
        return objects;
    }
    
    public KDNode(List<KDObject> objects, double[][] bounds, int dimension, double objectRadius) {
        this.objects = objects;
        this.bounds = bounds;
        k = bounds.length;
        d = dimension;
        
        if (objects.size() <= 1) {
            for (KDObject obj : objects) {
                obj.setNode(this);
            }
        }
        else {
            double[][] boundsFst = new double[k][2];
            double[][] boundsSnd = new double[k][2];
            
            double threshold = bounds[d][0] + (bounds[d][1] - bounds[d][0]) / 2;

            for (int i = 0; i < k; i++) {
                if (i == d) {
                    boundsFst[i][0] = bounds[i][0];
                    boundsFst[i][1] = threshold;
                    boundsSnd[i][0] = threshold;
                    boundsSnd[i][1] = bounds[i][1];
                }
                else {
                    boundsFst[i] = bounds[i];
                    boundsSnd[i] = bounds[i];
                }
            }
            
            List<KDObject> objFst = null, 
                           objSnd = null;
            
            
            for (KDObject obj : objects) {
                if (obj.getCoord(d) < threshold - objectRadius) {
                    if (objFst == null) objFst = new ArrayList<>();
                    objFst.add(obj);
                }
                else if (obj.getCoord(d) >= threshold + objectRadius) {
                    if (objSnd == null) objSnd = new ArrayList<>();
                    objSnd.add(obj);
                }
                else {
                    obj.setNode(this);
                }

            }
            
            if (objFst != null) {
                fstNode = new KDNode(objFst, boundsFst, (d + 1) % k, objectRadius);
            }
            
            if (objSnd != null) {
                sndNode = new KDNode(objSnd, boundsSnd, (d + 1) % k, objectRadius);
            }
        }
        
        
    }
}
