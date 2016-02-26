package com.astedt.robin.cellularsoftbody.physics;

import com.astedt.robin.cellularsoftbody.world.organisms.Cell;
import com.astedt.robin.cellularsoftbody.Config;
import com.astedt.robin.cellularsoftbody.world.genetics.Genome;
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
    
    private static List<PhysicsJob> physicsJobs;
    
    public Physics() {
        initialized = false;
    }
    
    //Start thread
    @Override
    public void run() {
        init();
        while (Main.running) {
            tick();
            Main.benchmarkTickCount++;
        }
    }
    
    
    //Run once
    void init() {
        cells = new ArrayList<>();
        cellsToRemove = new ArrayList<>();
        cellsToGrow = new ArrayList<>();
        physicsJobs = new ArrayList<>();
        
        int n1 = Config.TEMP_INIT_CELL_SPAWN_WIDTH;
        int n2 = Config.TEMP_INIT_CELL_SPAWN_HEIGHT;
        
        for (int x = 0; x < n1; x++) {
            for (int y = 0; y < n2; y++) {
                Cell c = new Cell((x + 1) * Config.WIDTH / (n1+1), (y + 1) * Config.HEIGHT / (n2+1), new Genome(), 0, null, 0);
                cells.add(c);
            }
        }
        
        System.out.println("Loading complete... " + (System.nanoTime() - Main.loadingStartTime) / 1000000000.0 + " s");
        initialized = true;
    }
    
    
    
    //Runs once per tick
    void tick() {
        
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
            cell.preTick();
            fetchJobs(cell);
        }
        
        for (Cell cell : cells)
        {
            cell.tick();
            fetchJobs(cell);
            cellRequestCounter += cell.getNode().getObjects().size();
        }
        
        
        handleJobs(physicsJobs);
        
        cellRequests = cellRequestCounter;

    }
    
    public static void shakeEm() {
        for (Cell c : cells) {
            c.xv = Config.DEBUG_SHAKE_SPEED * (Math.random() * 2 - 1);
            c.yv = Config.DEBUG_SHAKE_SPEED * (Math.random() * 2 - 1);
        }
    }
    
    private static void fetchJobs(Cell cell) {
        List<PhysicsJob> jobs = cell.getPhysicsJobs();
        if (jobs.isEmpty()) return;
        else {
            physicsJobs.addAll(jobs);
            jobs.clear();
        }
    }
    
    private static void handleJobs(List<PhysicsJob> jobs) {
        for (PhysicsJob job : jobs) {
            switch (job.type) {
                case CELL_GROW:
                synchronized (Main.monitor) {
                    job.cells[0].grow();
                }
                break;
                case CONNECTION_BREAK:
                {
                    Cell c1 = job.cells[0];
                    Cell c2 = job.cells[1];
                    int i = 0;
                    for (; i < 6; i++) {
                        if (c1.n[i] == c2) {
                            c1.n[i] = null;
                            c1.neighbours--;
                            int j = (i + 3) % 6;
                            c2.n[j] = null;
                            c2.neighbours--;
                            break;
                        }
                    }
                }
                break;
                case CELL_DESTROY:
                synchronized (Main.monitor) {
                    Cell cell = job.cells[0];
                    for (int i = 0; i < 6; i++) {
                        cell.n[i].n[(i+3)%6] = null;
                    }
                    cells.remove(cell);
                }
                break;
                default: break;
            }
        }
        jobs.clear();
    }
}
