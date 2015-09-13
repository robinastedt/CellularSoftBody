
package com.astedt.robin.quadtree;

import java.util.List;

/**
 *
 * @author Robin
 */
public class QuadTree {
    
    
    
    private final Node topNode;
    
    public QuadTree(List<QuadTreeObject> objects, double x0, double y0, double x1, double y1, double objectRadius) {
        topNode = new Node(objects, x0, y0, x1, y1, objectRadius);
    }
    
    
}
