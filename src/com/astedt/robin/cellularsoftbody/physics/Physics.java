package com.astedt.robin.cellularsoftbody.physics;

import com.astedt.robin.cellularsoftbody.world.organisms.Cell;
import com.astedt.robin.cellularsoftbody.Config;
import com.astedt.robin.cellularsoftbody.world.genetics.Dna;
import com.astedt.robin.cellularsoftbody.Main;
import java.util.ArrayList;

public class Physics implements Runnable {
    
    public static boolean initialized;
    
    public static ArrayList<Cell> cells;
    public static ArrayList<Cell> cellsToRemove;
    public static ArrayList<Cell> cellsToGrow;
    
    public Physics() {
        initialized = false;
    }
    
    //Start thread
    @Override
    public void run() {
        init();
        while (Main.running) {
            tick();
            //for (int i = 0; i < (100000); i++) Math.sqrt(Math.random());
            Main.benchmarkTickCount++;
        }
    }
    
    
    //Run once
    void init() {
        cells = new ArrayList<Cell>();
        cellsToRemove = new ArrayList<Cell>();
        cellsToGrow = new ArrayList<>();
        
        int n1 = 10;
        int n2 = n1 * Config.HEIGHT / Config.WIDTH;
        
        for (int x = 0; x < n1; x++) {
            for (int y = 0; y < n2; y++) {
                Cell c = new Cell((x + 1) * Config.WIDTH / (n1+1), (y + 1) * Config.HEIGHT / (n2+1), new Dna(), 0, null, false, 0);
            }
        }
        
        
        /*
        o1 = new Organism();
        double maxSize = 10;
        double size = maxSize * Config.CELL_INIT_SIZE;

        o1.addCell(1, 0, new Cell(600 + 100 + size * 1, 600 + 100 + size * 0, maxSize));
        o1.addCell(2, 0, new Cell(600 + 100 + size * 2, 600 + 100 + size * 0, maxSize));
        o1.addCell(3, 0, new Cell(600 + 100 + size * 3, 600 + 100 + size * 0, maxSize));

        o1.addCell(0, 1, new Cell(600 + 100 + size * 0 + size / 2, 600 + 100 + size * 1, maxSize));
        o1.addCell(1, 1, new Cell(600 + 100 + size * 1 + size / 2, 600 + 100 + size * 1, maxSize));
        o1.addCell(2, 1, new Cell(600 + 100 + size * 2 + size / 2, 600 + 100 + size * 1, maxSize));

        o1.addCell(3, 1, new Cell(600 + 100 + size * 3 + size / 2, 600 + 100 + size * 1, maxSize));
        o1.addCell(4, 1, new Cell(600 + 100 + size * 4 + size / 2, 600 + 100 + size * 1, maxSize));
        o1.addCell(5, 1, new Cell(600 + 100 + size * 5 + size / 2, 600 + 100 + size * 1, maxSize));
        o1.addCell(6, 1, new Cell(600 + 100 + size * 6 + size / 2, 600 + 100 + size * 1, maxSize));
        o1.addCell(7, 1, new Cell(600 + 100 + size * 7 + size / 2, 600 + 100 + size * 1, maxSize));
        o1.addCell(8, 1, new Cell(600 + 100 + size * 8 + size / 2, 600 + 100 + size * 1, maxSize));
        o1.addCell(9, 1, new Cell(600 + 100 + size * 9 + size / 2, 600 + 100 + size * 1, maxSize));

        o1.addCell(1, 2, new Cell(600 + 100 + size * 1, 600 + 100 + size * 2, maxSize));
        o1.addCell(2, 2, new Cell(600 + 100 + size * 2, 600 + 100 + size * 2, maxSize));
        o1.addCell(3, 2, new Cell(600 + 100 + size * 3, 600 + 100 + size * 2, maxSize));
        o1.color = Color.red;
        o1.Init();





        o2 = new Organism();
        maxSize = 10;
        size = maxSize * Config.CELL_INIT_SIZE;
        for (int x = 0; x < 7; x++)
        {
            for (int y = 0; y < 5; y++)
            {
                if (!((x == 0 && y == 0) || (x == 0 && y == 4)))
                {
                    Cell c = new Cell(600 + size * (x + (double)(y % 2) / 2), 100 + size * y, maxSize);
                    o2.addCell(x, y, c);
                }
            }
        }
        for (int x = 7; x < 21; x++)
        {
            Cell c = new Cell(600 + size * x, 100 + size * 2, maxSize);
            o2.addCell(x, 2, c);
        }
        o2.color = Color.blue;
        o2.Init();
        
        
        double basesize = 10;
        double randomsize = 0;
        for (int i = 0; i < 0; i++)
        {
            Organism o = new Organism();
            double s = basesize + randomsize * Main.rand.nextDouble();
            double r = s / 2;
            Cell c = new Cell((float)(r + Main.rand.nextDouble() * (Config.WIDTH - s)), (float)(r + Main.rand.nextDouble() * (Config.HEIGHT - s)), s);
            o.addCell(0, 0, c);
            o.Init();

            int ii = 0;
            boolean removed = false;
            while (ii < cells.size() && !removed)
            {
                Cell c2 = cells.get(ii);

                if (c2 != c)
                {
                    double d = c.DistanceTo(c2);
                    if (d < (c.size + c2.size) / 2)
                    {
                        c.x -= Math.cos(Math.atan2(c2.y - c.y, c2.x - c.x)) * ((c.size + c2.size) / 2 - d);
                        c.y -= Math.sin(Math.atan2(c2.y - c.y, c2.x - c.x)) * ((c.size + c2.size) / 2 - d);

                    }
                }
                ii++;
            }
            o.Init();
        }
        */
        initialized = true;
    }
    
    
    
    //Runs once per tick
    synchronized void tick() {
        
        for (Cell cell : cells)
        {
            cell.PreTick();
        }
        for (Cell cell : cells)
        {
            cell.Tick();
        }
        
        if (cellsToGrow.size() > 0 || cellsToRemove.size() > 0) {
            synchronized (Main.monitor) 
            {
                if (cellsToGrow.size() > 0) {
                    for (Cell cell : cellsToGrow) {
                        cell.grow();
                    }
                    cellsToGrow.clear();
                }

                if (cellsToRemove.size() > 0)
                {
                    for (Cell cell : cellsToRemove)
                    {
                        for (int i = 0; i < 6; i++)
                        {
                            if (cell.n[i] != null)
                            {
                                int ne = (i + 3) % 6; //opposite neighbour
                                if (cell.n[i].n[ne] != null)
                                {
                                    cell.n[i].n[ne] = null;
                                    cell.n[i].neighbours--;
                                }
                                cell.n[i] = null;
                            }
                        }
                        cell.neighbours = 0;
                    }
                    cellsToRemove.clear();
                }
            }
        }
        
        
        
        
        
        /*
        Cell c;
        double speed = Config.TEMP_SPEED;

        c = o1.GetCell(3, 0);
        if (c != null)
        {
            c.xv -= speed * 1.1 * c.size / c.maxSize * Math.cos(c.dir);
            c.yv -= speed * 1.1 * c.size / c.maxSize * Math.sin(c.dir);
        }


        c = o1.GetCell(3, 2);
        if (c != null)
        {
            c.xv -= speed * c.size / c.maxSize * Math.cos(c.dir);
            c.yv -= speed * c.size / c.maxSize * Math.sin(c.dir);
        }

        if (Main.keyListener.keyUp && (Main.keyListener.keyLeft || !Main.keyListener.keyRight))
        {
            c = o2.GetCell(6, 0);
            if (c != null)
            {
                c.xv -= speed * c.size / c.maxSize * 1.5 * Math.cos(c.dir);
                c.yv -= speed * c.size / c.maxSize * 1.5 * Math.sin(c.dir);
            }
            c = o2.GetCell(6, 1);
            if (c != null)
            {
                c.xv -= speed * c.size / c.maxSize * 1.5 * Math.cos(c.dir);
                c.yv -= speed * c.size / c.maxSize * 1.5 * Math.sin(c.dir);
            }
        }

        if (Main.keyListener.keyUp && (Main.keyListener.keyRight || !Main.keyListener.keyLeft))
        {
            c = o2.GetCell(6, 3);
            if (c != null)
            {
                c.xv -= speed * c.size / c.maxSize * 1.5 * Math.cos(c.dir);
                c.yv -= speed * c.size / c.maxSize * 1.5 * Math.sin(c.dir);
            }
            c = o2.GetCell(6, 4);
            if (c != null)
            {
                c.xv -= speed * c.size / c.maxSize * 1.5 * Math.cos(c.dir);
                c.yv -= speed * c.size / c.maxSize * 1.5 * Math.sin(c.dir);
            }
        }
        */    
    }
    
    public static void shakeEm() {
        for (Cell c : cells) {
            c.xv = Config.DEBUG_SHAKE_SPEED * (Math.random() * 2 - 1);
            c.yv = Config.DEBUG_SHAKE_SPEED * (Math.random() * 2 - 1);
        }
    }
}
