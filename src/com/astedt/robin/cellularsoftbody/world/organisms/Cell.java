
package com.astedt.robin.cellularsoftbody.world.organisms;

import com.astedt.robin.cellularsoftbody.Config;
import com.astedt.robin.cellularsoftbody.Main;
import com.astedt.robin.cellularsoftbody.render.DrawingComponent;
import com.astedt.robin.cellularsoftbody.physics.Physics;
import com.astedt.robin.cellularsoftbody.physics.PhysicsJob;
import com.astedt.robin.cellularsoftbody.physics.PhysicsJob.JobType;
import com.astedt.robin.cellularsoftbody.world.genetics.CellGene;
import com.astedt.robin.cellularsoftbody.world.genetics.Genome;
import com.astedt.robin.kdtree.KDNode;
import com.astedt.robin.kdtree.KDObject;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class Cell implements KDObject
{

    public double x, y;
    public double xv, yv;
    private double uvectx;
    private double uvecty;
    public double dir;
    public double dirv;
    public double maxSize;
    public double size;
    public double radius;
    public Genome genome;
    public int cellGeneIndex;
    public Cell parent;
    public int parentBindPoint;
    private boolean grown;
    public int neighbours;
    public Cell[] n;
    private int painCounter;
    private int pleasureCounter;
    public Color color;
    public int gen;
    
    private KDNode node;
    
    private List<PhysicsJob> physicsJobs;
    
    private static final double PI = Math.PI;
    private static final double PIhalf = Math.PI / 2;
    private static final double PIquarter = Math.PI / 4;
    private static final double PI3quarter = Math.PI * 3 / 4;
    private static final double PIthird = Math.PI / 3;
    private static final double PI2third = Math.PI * 2 / 3;
    private static final double PIdouble = Math.PI * 2;
    private static final double PIsixth = Math.PI / 6;
    
    
    
    public Cell(double x, double y, Genome genome, int cellGeneIndex, Cell parent, int parentBindPoint) {
        physicsJobs = new ArrayList<>();
        
        this.genome = genome;
        this.cellGeneIndex = cellGeneIndex;
        this.parent = parent;
        this.parentBindPoint = parentBindPoint;
        this.x = x;
        this.y = y;
        xv = 0;
        yv = 0;
        uvectx = 0.0;
        uvecty = 0.0;
        dir = Main.rand.nextDouble() * PIdouble - PI;
        dirv = 0.0;
        maxSize = Config.CELL_MAX_SIZE;
        size = maxSize * Config.CELL_INIT_SIZE;
        painCounter = 0;
        pleasureCounter = 0;
        n = new Cell[6];
        neighbours = 0;
        if (parent != null) {
            color = parent.color;
        }
        else {
            color = Color.getHSBColor(Main.rand.nextFloat(), 1, 1);
        }
        grown = false;
        
        if (parent == null) gen = 0;
        else gen = parent.gen + 1;
        
    }
    
    public void bind() {
        int bp = parentBindPoint;
        parent.n[bp] = this;
        parent.neighbours++;
        int bpToParent = (bp + 3) % 6;
        n[bpToParent] = parent;
        neighbours++;


        // Here be dragons
        // Start from the parent and work around alternating ccw and cw.
        // Ask each neighbour if the got neighbours the cell can bind to.
        final int[] neighbourIndexArray = {0, 1, -1, 2, -2, 3};
        for (int i = 0; i < 6; i++) {
            int ni = 6 + neighbourIndexArray[i] + bpToParent;

            if (n[ni % 6] != null) {
                Cell cn = n[ni % 6];
                int nbp = (ni + 3) % 6;
                if (cn.n[(nbp + 1) % 6] != null) {
                    Cell cnn = cn.n[(nbp + 1) % 6];
                    if (cnn.n[(nbp + 5) % 6] == null) {
                        cnn.n[(nbp + 5) % 6] = this;
                        cnn.neighbours++;
                        n[(nbp + 2) % 6] = cnn;
                        neighbours++;
                    }
                }
                if (cn.n[(nbp + 5) % 6] != null) {
                    Cell cnn = cn.n[(nbp + 5) % 6];
                    if (cnn.n[(nbp + 1) % 6] == null) {
                        cnn.n[(nbp + 1) % 6] = this;
                        cnn.neighbours++;
                        n[(nbp + 4) % 6] = cnn;
                        neighbours++;
                    }
                }
            }
        }
    }
    
    public List<PhysicsJob> getPhysicsJobs() {
        return physicsJobs;
    }
    
    //Concurrently unsafe, only to be call as a PhysicsJob
    public void grow() {
        if (!grown) {
            CellGene cellGene = genome.getCellGene(cellGeneIndex);
            
            for (int i = 0; i < 6; i++) {
                if (n[i] == null && 
                        cellGene.growthIndex[i] > 0 && 
                        cellGene.growthIndex[i] + cellGeneIndex < genome.cellGeneCount())
                {
                    
                    
                    Cell cell = new Cell(
                            x + Math.cos(Math.PI + dir + i * Math.PI / 3) * size * (1 + Config.CELL_INIT_SIZE) / 2, 
                            y + Math.sin(Math.PI + dir + i * Math.PI / 3) * size * (1 + Config.CELL_INIT_SIZE) / 2, 
                            genome, 
                            cellGene.growthIndex[i] + cellGeneIndex, 
                            this,
                            i);
                    cell.bind();
                    Physics.cells.add(cell);
                    
                }
            }
            
            grown = true;
            
        }
    }

    public void preTick()
    {
        double xforce = 0.0;
        double yforce= 0.0;
        uvectx = 0.0;
        uvecty = 0.0;


        for (int i = 0; i < 6; i++)
        {
            Cell c = n[i];
            if (c != null)
            {
                double d = DistanceTo(c);
                if (d > size + c.size)
                {
                    physicsJobs.add(new PhysicsJob(JobType.CONNECTION_BREAK, new Cell[] {this, c}));
                }
                else {
                    double force = d - (size + c.size) / 2;
                    double dx = c.x - x;
                    double dy = c.y - y;
                    xforce += dx / d * force;
                    yforce += dy / d * force;
                    double a = Math.atan2(dy, dx) + PI - PIthird * i;
                    uvectx += Math.cos(a);
                    uvecty += Math.sin(a);
                }
                
            }
        }


        for (KDObject qtObject : node.getObjects()) {
            Cell c = (Cell)qtObject;
            if (c != this)
            {
                double xx = x - c.x;
                double yy = y - c.y;
                double dsqr = xx * xx + yy * yy;
                double size2 = (size + c.size) / 2;
                if (dsqr < size2 * size2)
                {
                    double d = Math.sqrt(dsqr);
                    double force = d - size2;
                    double dx = c.x - x;
                    double dy = c.y - y;
                    xforce += dx / d * force;
                    yforce += dy / d * force;
                    
                    
                    if (c.neighbours == 0)
                    {
                        double pdx = -dy; // x component of vector clockwise perpendicular to distance vector
                        double pdy = dx;  // y component of vector clockwise perpendicular to distance vector
                        double v = (xv * pdx + yv * pdy) / (pdx * pdx + pdy * pdy);
                        double xforcedir = pdx * v;
                        double yforcedir = pdy * v;
                        
                        c.dirv -= (dirv + Math.signum(bearingDiff(Math.atan2(dy, dx), Math.atan2(yv, xv))) * Math.sqrt(xforcedir * xforcedir + yforcedir * yforcedir)) * Config.FORCE_ROTATION;
                    }
                    
                    
                    
                }
            }
        }

        


        double xa = xforce * Config.FORCE_TRANSLATION;
        double ya = yforce * Config.FORCE_TRANSLATION + Config.FORCE_GRAVITY;

        if (xforce * xforce + yforce * yforce > Config.CELL_PAIN_THRESHOLD * Config.CELL_PAIN_THRESHOLD)
        {
            TriggerPain();
        }
        
        xv += xa;
        yv += ya;
        
        xv *= 1.0 - Config.FRICTION_TRANSLATION; //translationFrictionMultiplier; // = 1.0 - (Config.FRICTION_TRANSLATION * (surfaceArea + 6) / 6);
        yv *= 1.0 - Config.FRICTION_TRANSLATION; //translationFrictionMultiplier;
        
        if (x < size / 2) xv = Math.abs(xv) + 0.0001;
        else if (x >= Config.WIDTH - size / 2) xv = -Math.abs(xv) - 0.0001;
        if (y < size / 2) yv = Math.abs(yv) + 0.0001;
        else if (y >= Config.HEIGHT - size / 2) yv = -Math.abs(yv) - 0.0001;
        
        
    }
    
    public void tick()
    {
        if (size < maxSize)
        {
            size += maxSize * Config.CELL_GROWTH_RATE;
            radius = size / 2;
        }
        else if (!grown) {
            physicsJobs.add(new PhysicsJob(JobType.CELL_GROW, new Cell[] {this}));
        }
        
        if (painCounter > 0)
        {
            painCounter--;
            if (painCounter == Config.CELL_PAIN_DURATION - Config.CELL_PAIN_SPREAD)
            {
                for (int i = 0; i < 6; i++)
                {
                    if (n[i] != null)
                    {
                        n[i].TriggerPain();
                    }
                }
            }
        }
        
        if (pleasureCounter > 0)
        {
            pleasureCounter--;
            if (pleasureCounter == Config.CELL_PLEASURE_DURATION - Config.CELL_PLEASURE_SPREAD)
            {
                for (int i = 0; i < 6; i++)
                {
                    if (n[i] != null)
                    {
                        n[i].TriggerPleasure();
                    }
                }
            }
        }

        if (pleasureCounter > 0) pleasureCounter--;
        
        if (neighbours > 0)
        {
            dir = Math.atan2(uvecty, uvectx);
            dirv = 0.0;
        }
        else
        {
            dir += dirv;
            dirv *= 1.0 - Config.FRICTION_ROTATION;
        }

        x += xv;
        y += yv;
    }
    

    public void Draw(Graphics2D g, int layer)
    {
        double r2 = radius / 2;

        if (layer == 0)
        {
            for (int i = 0; i < 3; i++)
            {
                g.setColor(color);
                if (n[i] != null) g.drawLine((int)x, (int)y, (int)n[i].x, (int)n[i].y);
            }
        }
        else if (layer == 1)
        {
            
            Point[] points = new Point[6];
            for (int i = 0; i < 6; i++)
            {
                //if (n[i] == null || n[(i + 1) % 6] == null)
                {
                    int cellsCounted = 1;
                    double direction = dir + (7 + 2 * i) * PIsixth;
                    double pointx = x + radius * Math.cos(direction);
                    double pointy = y + radius * Math.sin(direction);
                    
                    if (n[i] != null)
                    {
                        Cell c = n[i];
                        double direction2 = c.dir + (-1 + 2 * i) * PIsixth;
                        pointx += c.x + c.radius * Math.cos(direction2);
                        pointy += c.y + c.radius * Math.sin(direction2);
                        cellsCounted++;
                    }

                    if (n[(i + 1) % 6] != null)
                    {
                        Cell c = n[(i + 1) % 6];
                        double direction3 = c.dir + (3 + 2 * i) * PIsixth;
                        pointx += c.x + c.radius * Math.cos(direction3);
                        pointy += c.y + c.radius * Math.sin(direction3);
                        cellsCounted++;
                    }

                    pointx /= cellsCounted;
                    pointy /= cellsCounted;

                    points[i] = new Point((int)pointx, (int)pointy);
                }

            }
            

            if (painCounter > 0 || pleasureCounter > 0) {
                g.setColor(new Color(255 * painCounter / Config.CELL_PAIN_DURATION, 255 * pleasureCounter / Config.CELL_PLEASURE_DURATION, 0));
                g.fillPolygon(DrawingComponent.pointsToPolygon(points));
            }

            for (int i = 0; i < 6; i++)
            {
                if (n[i] == null)
                {
                    Point p1 = points[(i + 5) % 6];
                    Point p2 = points[i];
                    g.setColor(color);
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
            
            //g.drawString("" + gen, (float)x, (float)y);

        }
    }

    public double DistanceTo(Cell cell)
    {
        double xx = x - cell.x;
        double yy = y - cell.y;
        double force = Math.sqrt(xx * xx + yy * yy);

        return force;
    }

    public static double bearingDiff(double a, double b)
    {
        double aa = a % PIdouble;
        double bb = b % PIdouble;
        double antiClockwiseDiff = (aa - bb) % PIdouble;
        double clockwiseDiff = (bb + PIdouble - aa) % PIdouble;
        if (antiClockwiseDiff < clockwiseDiff) return -antiClockwiseDiff;
        else return clockwiseDiff;
    }
    
    
    public void TriggerPain()
    {
        if (painCounter == 0)
        {
            painCounter = Config.CELL_PAIN_DURATION;
        }
    }

    public void TriggerPleasure()
    {
        if (pleasureCounter == 0)
        {
            pleasureCounter = Config.CELL_PLEASURE_DURATION;
        }
    }
    
    public KDNode getNode() {
        return node;
    }
    
    @Override
    public double getCoord(int d) {
        switch (d) {
            default:
            case 0:
                return x;
            case 1:
                return y;
        }
    }

    @Override
    public void setNode(KDNode node) {
        this.node = node;
    }

}
