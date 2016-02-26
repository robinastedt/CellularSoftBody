package com.astedt.robin.cellularsoftbody;

import com.astedt.robin.cellularsoftbody.console.Console;
import com.astedt.robin.cellularsoftbody.render.DrawingComponent;
import com.astedt.robin.cellularsoftbody.physics.Physics;
import com.astedt.robin.cellularsoftbody.physics.BenchmarkTickTimerListener;
import com.astedt.robin.cellularsoftbody.render.BenchmarkFrameTimerListener;
import com.astedt.robin.cellularsoftbody.render.UpdateFrameListener;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Main {

    public static int fps;
    public static int tps;
    public static long loadingStartTime;
    public static int benchmarkFrameCount;
    public static int benchmarkTickCount;
    private static Timer benchmarkFrameTimer;
    private static Timer benchmarkTickTimer;
    private static Timer updateFrameTimer;
    public static JFrame window;
    public static MainKeyListener keyListener;
    public static Console console;
    public static boolean running;
    
    public static boolean waitingForSeedInput;
    public static String seedString;
    public static long seed;
    public static Random rand;
    
    private static Thread threadPhysics;
    public static final Object monitor = new Object();
    
    
    public static void main(String[] args) {

        
        //App running
        running = true;
        
        //Start console
        console = new Console();
        
        //Inititate RNG
        waitingForSeedInput = true;
        if (args.length > 0) {
            seedString = args[0];
            seed = seedString.hashCode();
        }
        else {
            System.out.println("Enter seed: ");
            seedString = "";
            //Request focus for console
            console.frame.requestFocus();
            console.textField.requestFocusInWindow();
            
            //Wait for input
            while (waitingForSeedInput) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (seedString.isEmpty()) {
                seed = new Random().nextLong();
            }
            else {
                seed = seedString.hashCode();
            }
        }
        if (seedString.isEmpty()) {
            System.out.println("Initializing with seed: " + seed + " (random, none selected)");
        }
        else {
            System.out.println("Initializing with seed: " + seed + " (\"" + seedString + "\")");
        }
        rand = new Random(seed);
        
        //Benchmark loading time
        loadingStartTime = System.nanoTime();
        
        
        
        
        //Start physics thread
        threadPhysics = new Thread(new Physics());
        threadPhysics.setDaemon(true);
        threadPhysics.setName("Physics");
        threadPhysics.start();
        
        //Start main window
        window = new JFrame();
        window.setSize(Config.WIDTH, Config.HEIGHT);
        window.setTitle(Config.TITLE_WINDOW);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        keyListener = new MainKeyListener();
        window.addKeyListener(keyListener);
        DrawingComponent DC = new DrawingComponent();
        DC.setPreferredSize(new Dimension(Config.WIDTH, Config.HEIGHT));
        window.add(DC);
        window.setResizable(false);
        window.pack();
        window.pack(); //Without calling it twice it sometimes creates a window to large... >_>
        window.setVisible(true);
        
        
        //Start frame updating timer
        updateFrameTimer = new Timer(1000 / Config.FPS_MAX, new UpdateFrameListener());
        updateFrameTimer.start();
        
        //Start benchmarking timers
        //Frames per second
        fps = 0;
        benchmarkFrameCount = 0;
        benchmarkFrameTimer = new Timer(1000, new BenchmarkFrameTimerListener());
        benchmarkFrameTimer.start();
        //Ticks per second
        tps = 0;
        benchmarkTickCount = 0;
        benchmarkTickTimer = new Timer(1000, new BenchmarkTickTimerListener());
        benchmarkTickTimer.start();
        
        ////////////////////
        //Main thread done//
        ////////////////////
        
    }
    
    //Called from Console.java when input received
    public static void ConsoleInput(String[] input) {
        
        if (waitingForSeedInput) {
            if (input.length > 0) {
                seedString = input[0];
                for (int i = 1; i < input.length; i++) {
                    seedString += input[i];
                }
            }
            else seedString = "";
            waitingForSeedInput = false;
            return;
        }
        
        if (input[0].equalsIgnoreCase("exit")) {
            System.out.println("Closing application...");
            console.Exit();
        }
        else if (input[0].equalsIgnoreCase("clear")) console.Clear();
        else if (input[0].equalsIgnoreCase("help")) {
            System.out.println(Config.CONSOLE_HELP_1);
            // String literals have a max size of 65535 so we're dividing it up in two constants
            // Console can't handle parsing more than 65535 chars at a time, hence this little hack
            Timer timer = new Timer(1, (ActionEvent ae) -> {
                System.out.println(Config.CONSOLE_HELP_2);
            });
            timer.setRepeats(false);
            timer.start();
            
        }
        else if (input[0].equalsIgnoreCase("get")) {
            if (input.length > 1) {
                if (input[1].equalsIgnoreCase("fps")) System.out.println("fps=" + fps);
                else if (input[1].equalsIgnoreCase("tps")) System.out.println("tps=" + tps);
                else if (input[1].equalsIgnoreCase("cellcount")) System.out.println("cellcount=" + Physics.cells.size());
                else if (input[1].equalsIgnoreCase("treebuildtime")) System.out.println("treebuildtime=" + Physics.treeBuildTime + "/" + 1000000000L / tps + " (" + String.format("%.3f", Physics.treeBuildTime / 10000000.0 * tps) + "%)");
                else if (input[1].equalsIgnoreCase("cellrequests")) {
                    long cellRequests = Physics.cellRequests;
                    long maxRequests = Physics.cells.size() * Physics.cells.size();
                    double effiencyPercent = 100.0 * cellRequests / maxRequests;
                    System.out.println("cellrequests=" + cellRequests + "/" + maxRequests + " (" + String.format("%.3f", effiencyPercent) + "%)");
                }
                else System.out.println("Error: Unrecognized variable: \"" + input[1] + "\"");
            }
            else System.out.println("Error: No variable specified!");
        }
        else if (input[0].equalsIgnoreCase("set")) {
            if (input.length > 1) {
                if (input[1].equalsIgnoreCase("fps")) {
                    if (input.length > 2) {
                        int newFps;
                        try {
                            newFps = Integer.parseInt(input[2]);
                        } catch(Exception e) {
                            newFps = 0;
                        }
                        if (newFps >= 10 && newFps <= 200) updateFrameTimer.setDelay(1000 / newFps);
                        else System.out.println("Error: Invalid value! Range: [10 - 200]");
                    }
                    else System.out.println("Error: No value specified!");
                }
                else System.out.println("Error: Unrecognized or read-only variable: \"" + input[1] + "\"");
            }
            else System.out.println("Error: No variable specified!");
        }
        else if (input[0].equalsIgnoreCase("debug")) {
            if (input.length > 1) {
                if (input[1].equalsIgnoreCase("shake")) {
                    Physics.shakeEm();
                    System.out.println("Rumble!");
                }
                else if (input[1].equalsIgnoreCase("drawborder")) {
                    Config.DRAW_BORDER = !Config.DRAW_BORDER;
                }
                else if (input[1].equalsIgnoreCase("drawskeleton")) {
                    Config.DRAW_SKELETON = !Config.DRAW_SKELETON;
                }
                else if (input[1].equalsIgnoreCase("drawtree")) {
                    Config.DRAW_TREE = !Config.DRAW_TREE;
                }
                else System.out.println("Error: Unrecognized function: \"" + input[1] + "\"");
            }
            else System.out.println("Error: No function specified!");
        }
        else if (input[0].equalsIgnoreCase("katie") || input[0].equalsIgnoreCase("watson") || input[0].equalsIgnoreCase("archer")) System.out.println("Boo!");
        else if (input[0].equalsIgnoreCase("hello") || input[0].equalsIgnoreCase("hi") || input[0].equalsIgnoreCase("sup")) System.out.println("Yes, hello? This is dog?");
        else System.out.println("Error: Unrecognized command: \"" + input[0] + "\"");
        
    }
    
}







