package de.hhu.propra.teamA2.Model;

import java.util.List;


public class Mannschaft {
    private List<Wurmstand> arraywurm;
    private String name;
    private String farbe;

    public List<Wurmstand> getArraywurm() {
        return arraywurm;
    }
    public void setArraywurm(List<Wurmstand> arraywurm) {
        this.arraywurm = arraywurm;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getFarbe() {
        return farbe;
    }
    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }

}