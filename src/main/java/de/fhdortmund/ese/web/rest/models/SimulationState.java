package de.fhdortmund.ese.web.rest.models;

public class SimulationState {

    public boolean state;
    public double energy;
    public SimulationState(boolean state, double energy) {
        this.state = state;
        this.energy = energy;
    }
    public boolean isState() {
        return state;
    }
    public void setState(boolean state) {
        this.state = state;
    }
    public double isEnergy() {
        return energy;
    }
    public void setEnergy(double energy) {
        this.energy = energy;
    }

    
    
}
