
package com.astedt.robin.cellularsoftbody.world.genetics;

import com.astedt.robin.cellularsoftbody.Config;
import com.astedt.robin.cellularsoftbody.Main;
import java.util.ArrayList;

public class Dna {
    private byte[] genome;
    private ArrayList<Chromosome> chromosomes;
    public Dna() {
        genome = new byte[Config.DNA_INIT_LENGTH];
        Main.rand.nextBytes(genome);
        chromosomes = new ArrayList<>();
        
        for (int i = 0; i < genome.length; i++) {
            if (genome[i] == (byte)0xFF) {
                int index = chromosomes.size();
                int[] growthIndex = {0, 0, 0, 0, 0, 0};
                for (int ii = 0; ii < 6 && i + 1 < genome.length; ii++) {
                    i++;
                    if ((genome[i] & 0xC0) == 0xC0) {
                        growthIndex[ii]++;
                        if ((genome[i] & 0x01) != 0) growthIndex[ii]++;
                        if ((genome[i] & 0x02) != 0) growthIndex[ii]++;
                        if ((genome[i] & 0x04) != 0) growthIndex[ii]++;
                        if ((genome[i] & 0x08) != 0) growthIndex[ii]++;
                    }
                }
                chromosomes.add(new Chromosome(index, growthIndex));
            }
        }
    }
    
    public int chromosomeCount() {
        return chromosomes.size();
    }
    
    public Chromosome getChromosome(int index) {
        return chromosomes.get(index);
    }
}
