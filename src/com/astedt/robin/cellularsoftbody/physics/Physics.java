package com.astedt.robin.cellularsoftbody.physics;

import com.astedt.robin.cellularsoftbody.world.organisms.Cell;
import com.astedt.robin.cellularsoftbody.Config;
import com.astedt.robin.cellularsoftbody.world.genetics.Dna;
import com.astedt.robin.cellularsoftbody.Main;
import com.astedt.robin.kdtree.KDObject;
import com.astedt.robin.kdtree.KDTree;
import java.util.ArrayList;
import java.util.List;

public class Physics implements Runnable {
    
    public static boolean initialized;
    
    public static ArrayList<Cell> cells;
    public static ArrayList<Cell> cellsToRemove;
    public static ArrayList<Cell> cellsToGrow;
    
    public static KDTree tree;
    
    public static long treeBuildTime;
    public static long cellRequests;
    
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
        
        int n1 = 8;
        int n2 = 6;
        
        for (int x = 0; x < n1; x++) {
            for (int y = 0; y < n2; y++) {
                Cell c = new Cell((x + 1) * Config.WIDTH / (n1+1), (y + 1) * Config.HEIGHT / (n2+1), new Dna(), 0, null, false, 0);
            }
        }
        
        
        initialized = true;
    }
    
    
    
    //Runs once per tick
    synchronized void tick() {
        
        long startTimeTree = System.nanoTime();
        List<KDObject> kdtreeObjects = new ArrayList<>();
        kdtreeObjects.addAll(cells);
        double[][] bounds = {
            {0.0, Config.WIDTH},
            {0.0, Config.HEIGHT}
        };
        tree = new KDTree(kdtreeObjects, bounds, Config.CELL_MAX_SIZE / 2);
        treeBuildTime = System.nanoTime() - startTimeTree;
        long cellRequestCounter = 0;
        
        for (Cell cell : cells)
        {
            cell.PreTick();
        }
        for (Cell cell : cells)
        {
            cell.Tick();
            cellRequestCounter += cell.getNode().getObjects().size();
        }
        cellRequests = cellRequestCounter;
        
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
        
        
    }
    
    public static void shakeEm() {
        for (Cell c : cells) {
            c.xv = Config.DEBUG_SHAKE_SPEED * (Math.random() * 2 - 1);
            c.yv = Config.DEBUG_SHAKE_SPEED * (Math.random() * 2 - 1);
        }
    }
}
