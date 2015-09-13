
package com.astedt.robin.cellularsoftbody.world.organisms;

import com.astedt.robin.cellularsoftbody.Config;
import com.astedt.robin.cellularsoftbody.world.genetics.Dna;
import java.awt.Color;
import java.util.ArrayList;

public class Organism
{
    public static final int cellArrayWidth = Config.CELL_GRID_WIDTH;
    public static final int cellArrayHeight = Config.CELL_GRID_HEIGHT;
    public Color color;
    private Cell[][] cells;
    public ArrayList<Cell> cellsList;


    private static final double PI = Math.PI;
    private static final double PIhalf = Math.PI / 2;
    private static final double PIquarter = Math.PI / 4;
    private static final double PI3quarter = Math.PI * 3 / 4;
    private static final double PIthird = Math.PI / 3;
    private static final double PIsixth = Math.PI / 6;
    private static final double PI5sixth = Math.PI * 5 / 6;
    private static final double PI2third = Math.PI * 2 / 3;
    private static final double PIdouble = Math.PI * 2;


    public Organism()
    {
        cells = new Cell[cellArrayWidth][cellArrayHeight];
        cellsList = new ArrayList<Cell>();
        color = Color.white;
    }
    
    public Organism(int x, int y, Dna dna) {
        cells = new Cell[cellArrayWidth][cellArrayHeight];
        cellsList = new ArrayList<Cell>();
        color = Color.white;
        
        int cx = x;
        int cy = y;
        int ox = cellArrayWidth / 2;
        int oy = cellArrayHeight / 2;
        
        
    }

    public void addCell(int x, int y, Cell cell)
    {
        cell.o = this;
        cells[x][y] = cell;
        cellsList.add(cell);
        cell.ox = x;
        cell.oy = y;
    }

    public void removeCell(int x, int y)
    {
        Cell c = cells[x][y];
        cells[x][y] = null;
        cellsList.remove(c);
        c.ox = 0;
        c.oy = 0;
    }

    public Cell GetCell(int x, int y)
    {
        return cells[x][y];
    }

    public void PreTick()
    {
        for (Cell c : cellsList)
        {
            c.PreTick();
        }
    }

    public void Tick()
    {
        for (Cell c : cellsList)
        {
            c.Tick();
        }

    }

    public void Init()
    {
        for (Cell c : cellsList)
        {
            c.Init();
            c.color = color;
        }
    }
}
