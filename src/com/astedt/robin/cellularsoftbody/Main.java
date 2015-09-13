package com.astedt.robin.cellularsoftbody;

import com.astedt.robin.cellularsoftbody.console.Console;
import com.astedt.robin.cellularsoftbody.render.DrawingComponent;
import com.astedt.robin.cellularsoftbody.physics.Physics;
import com.astedt.robin.cellularsoftbody.physics.BenchmarkTickTimerListener;
import com.astedt.robin.cellularsoftbody.render.BenchmarkFrameTimerListener;
import com.astedt.robin.cellularsoftbody.render.UpdateFrameListener;
import java.awt.Dimension;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Main {

    public static int fps;
    public static int tps;
    public static int benchmarkFrameCount;
    public static int benchmarkTickCount;
    private static Timer benchmarkFrameTimer;
    private static Timer benchmarkTickTimer;
    private static Timer updateFrameTimer;
    public static JFrame window;
    public static MainKeyListener keyListener;
    public static Console console;
    public static boolean running;
    
    public static Random rand;
    
    private static Thread threadPhysics;
    public static Object monitor;
    
    public static void main(String[] args) {
        
        //App running
        running = true;
        
        //Start console
        console = new Console();
        //System.out.println(Config.WELCOME_MESSAGE);
        
        //Inititate RNG
        rand = new Random();
        
        //Initiate thread sync object
        monitor = new Object();
        
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
        window.setVisible(true);
        
        //Request focus for console
        console.frame.requestFocus();
        console.textField.requestFocusInWindow();
        
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
        
        if (input[0].equalsIgnoreCase("exit")) {
            System.out.println("Closing application...");
            console.Exit();
        }
        else if (input[0].equalsIgnoreCase("clear")) console.Clear();
        else if (input[0].equalsIgnoreCase("help")) System.out.println(Config.CONSOLE_HELP);
        else if (input[0].equalsIgnoreCase("get")) {
            if (input.length > 1) {
                if (input[1].equalsIgnoreCase("fps")) System.out.println("fps=" + fps);
                else if (input[1].equalsIgnoreCase("tps")) System.out.println("tps=" + tps);
                else if (input[1].equalsIgnoreCase("cellcount")) System.out.println("cellcount=" + Physics.cells.size());
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
                else System.out.println("Error: Unrecognized function: \"" + input[1] + "\"");
            }
            else System.out.println("Error: No function specified!");
        }
        else if (input[0].equalsIgnoreCase("katie") || input[0].equalsIgnoreCase("watson") || input[0].equalsIgnoreCase("archer")) System.out.println("Boo!");
        else if (input[0].equalsIgnoreCase("hello") || input[0].equalsIgnoreCase("hi") || input[0].equalsIgnoreCase("sup")) System.out.println("Yes, hello? This is dog?");
        else System.out.println("Error: Unrecognized command: \"" + input[0] + "\"");
        
    }
    
}







