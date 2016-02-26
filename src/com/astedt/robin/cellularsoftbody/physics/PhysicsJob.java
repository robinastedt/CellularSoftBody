/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astedt.robin.cellularsoftbody.physics;

import com.astedt.robin.cellularsoftbody.world.organisms.Cell;

/**
 *
 * @author robin
 */
public class PhysicsJob {
    public enum JobType {
        CELL_GROW,
        CONNECTION_BREAK,
        CELL_DESTROY
    }
    
    public final JobType type;
    public final Cell[] cells;
    
    public PhysicsJob(JobType type, Cell[] cells) {
        this.type = type;
        this.cells = cells;
    }
}
