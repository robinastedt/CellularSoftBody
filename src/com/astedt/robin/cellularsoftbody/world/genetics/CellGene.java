
package com.astedt.robin.cellularsoftbody.world.genetics;



public class CellGene {
    public final int index;
    public final byte[] growthIndex;
    public final byte[] phenotypeIndex;
    
    public CellGene(int index, byte[] growthIndex, byte[] phenotypeIndex) {
        this.index = index;
        this.growthIndex = growthIndex;
        this.phenotypeIndex = phenotypeIndex;
    }
}
