
package com.astedt.robin.kdtree;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

/**
 *
 * @author Robin
 */
public class KDTree {
    private final KDNode topNode;
    
    private final double[][] bounds;
    
    public KDTree(List<KDObject> objects, double[][] boundaries, double objectRadius){
        bounds = boundaries;
        topNode = new KDNode(objects, boundaries, 0, objectRadius);
    }
    
    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        topNode.draw(g);
    }
}
