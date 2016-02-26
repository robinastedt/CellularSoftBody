
package com.astedt.robin.cellularsoftbody.world.genetics;

import com.astedt.robin.cellularsoftbody.Config;
import com.astedt.robin.cellularsoftbody.Main;
import java.util.ArrayList;
import java.util.List;

public class Genome {
    private final byte[] genome;
    private final List<CellGene> cellGenes;
    private final List<GreaterPhenotype> greaterPhenotypes;
    private final  List<LesserPhenotype> lesserPhenotypes;
    
    //WARNING: AUTO-GENERATED
    //First 2 significant bits must be 1. [11xxxxxx]
    //Then increment value for each of the 6
    //least significant bits that are 1. [xx111111]
    private static final byte[] countBitsIncr = {
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,1,1,2,1,2,2,3,1,2,2,3,2,3,3,4,
        1,2,2,3,2,3,3,4,2,3,3,4,3,4,4,5,
        1,2,2,3,2,3,3,4,2,3,3,4,3,4,4,5,
        2,3,3,4,3,4,4,5,3,4,4,5,4,5,5,6,
    };
    
    //WARNING: AUTO-GENERATED
    //First 2 significant bits must be 1. [11xxxxxx]
    //Then decrement value for each of the next 3
    //significant bits that are 1. [xx111xxx]
    //then increment value for each of the next 3
    //significant bits that are 1. [xxxxx111]
    private static final byte[] countBitsDecrIncr =  {
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        0,-1,-1,-2,-1,-2,-2,-3,1,0,0,-1,0,-1,-1,-2,
        1,0,0,-1,0,-1,-1,-2,2,1,1,0,1,0,0,-1,
        1,0,0,-1,0,-1,-1,-2,2,1,1,0,1,0,0,-1,
        2,1,1,0,1,0,0,-1,3,2,2,1,2,1,1,0,
    };


    
    public Genome() {
        this(getRandomBytes(Config.DNA_INIT_LENGTH));
    }
    
    public Genome(byte[] bytes) {
        genome = bytes;
        cellGenes = new ArrayList<>();
        greaterPhenotypes = new ArrayList<>();
        lesserPhenotypes = new ArrayList<>();
        
        for (int i = 0; i < genome.length; i++) {
            //Scan for cell genes
            if (genome[i] == (byte)0xFF) {
                int index = cellGenes.size();
                byte[] growthIndex = {0, 0, 0, 0, 0, 0};
                byte[] phenotypeIndex = {0, 0, 0, 0, 0, 0};
                for (int seg = 0; seg < Config.DNA_CELLGENE_GROWTH_INDEX_BYTES; seg++) {
                    for (int ii = 0; ii < 6 && i + 1 < genome.length; ii++) {
                        i++;
                        growthIndex[ii] = countBitsIncr[(int)genome[i] & 0xFF];
                    }
                }
                for (int seg = 0; seg < Config.DNA_CELLGENE_GREATER_PHENOTYPE_INDEX_BYTES; seg++) {
                    for (int ii=0; ii < 6 && i + 1 < genome.length; ii++) {
                        i++;
                        phenotypeIndex[ii] = countBitsDecrIncr[(int)genome[i] & 0xFF];
                    }
                }
                cellGenes.add(new CellGene(index, growthIndex, phenotypeIndex));
            }
            
            //Scan for greater phenotypes
            if (genome[i] == (byte)0xFE) {
                int index = greaterPhenotypes.size();
                byte[] phenotypeIndex = {0, 0, 0, 0, 0, 0};
                
                for (int seg = 0; seg < Config.DNA_GREATER_PHENOTYPE_LESSER_PHENOTYPE_INDEX_BYTES; seg++) {
                    for (int ii=0; ii < 6 && i + 1 < genome.length; ii++) {
                        i++;
                        phenotypeIndex[ii] = countBitsDecrIncr[(int)genome[i] & 0xFF];
                    }
                }
                greaterPhenotypes.add(new GreaterPhenotype(index, phenotypeIndex));
            }
            //Scan for lesser phenotypes
            if (genome[i] == (byte)0xFD) {
                
            }
        }
        
        if (cellGenes.isEmpty()) {
            cellGenes.add(new CellGene(0, new byte[] {0,0,0,0,0,0}, new byte[] {0,0,0,0,0,0}));
        }
        if (greaterPhenotypes.isEmpty()) {
            greaterPhenotypes.add(new GreaterPhenotype(0, new byte[] {0,0,0,0,0,0}));
        }
    }
    
    public int cellGeneCount() {
        return cellGenes.size();
    }
    
    public CellGene getCellGene(int index) {
        return cellGenes.get(index);
    }
    
    private static byte[] getRandomBytes(int count) {
        byte[] bytes = new byte[count];
        Main.rand.nextBytes(bytes);
        return bytes;
    }
    
    
}
