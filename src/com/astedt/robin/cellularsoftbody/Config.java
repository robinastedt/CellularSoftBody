
package com.astedt.robin.cellularsoftbody;


public class Config {
    //CONSTANTS
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int FPS_MAX = 60;
    
    public static final int CELL_GRID_WIDTH = 40;
    public static final int CELL_GRID_HEIGHT = 30;
    
    public static final double FORCE_TRANSLATION = 0.005;
    public static final double FORCE_ROTATION = 0.0001;
    public static final double FORCE_GRAVITY = 0.0;
    public static final double FRICTION_TRANSLATION = 0.005;
    public static final double FRICTION_ROTATION = 0.001;
    
    public static final int CELL_PAIN_DURATION = 100;
    public static final int CELL_PAIN_SPREAD = 30;
    public static final double CELL_PAIN_THRESHOLD = 5.0;
    public static final int CELL_PLEASURE_DURATION = 100;
    public static final int CELL_PLEASURE_SPREAD = 30;
    public static final double CELL_PLEASURE_THRESHOLD = 5.0;
    
    public static final double CELL_GROWTH_RATE = 0.0001;
    public static final double CELL_INIT_SIZE = 1 / Math.sqrt(2);
    
    public static final int DNA_INIT_LENGTH = 50000;
    
    //DEBUG
    public static final boolean DEBUG = true;
    public static boolean DRAW_BORDER = true;
    public static boolean DRAW_SKELETON = false;
    public static final double DEBUG_SHAKE_SPEED = 0.25;
    public static final double TEMP_SPEED = 0.01;
    
    
    //TEXT
    public static final String TITLE_WINDOW = "Cellular Soft Body";
    public static final String TITLE_CONSOLE = "Cellular Soft Body: Console";
    
    
    public static final String WELCOME_MESSAGE = "\n"
                            + "  ############################################\n"
                            + "  ##                                        ##\n"
                            + "  ##         [ CELLULAR SOFT BODY ]         ##\n"
                            + "  ##             Version: 0.3.2             ##\n"
                            + "  ##          Author: Robin Åstedt          ##\n"
                            + "  ##        http://robin.astedt.com         ##\n"
                            + "  ##           Copyright \u00a9 2015             ##\n"
                            + "  ##                                        ##\n"
                            + "  ############################################\n"
                            + "  \n"
                            + "  Type \"help\" for available commands.\n\n";
    
    public static final String CONSOLE_HELP
            = "exit                     : Terminates the application\n"
            + "clear                    : Clears the console of all text\n"
            + "help                     : Shows this text\n"
            + "get <variable>           : Returns the specified variable\n"
            + "                         : fps: Frames per second\n"
            + "                         : tps: Ticks per second\n"
            + "                         : cellcount: amount of cells in environment\n"
            + "set <variable> <value>   : Sets the variable to the specified value\n"
            + "                         : fps: Frames per second. Range: [10-200]\n"
            + "debug <function>         : Executes debug functions\n"
            + "                         : shake: sets all cells speeds to a random value\n"
            + "                         : drawborder: toggles drawing of cell border\n"
            + "                         : drawskeleton: toggles drawing of cell skeleton";
    
}