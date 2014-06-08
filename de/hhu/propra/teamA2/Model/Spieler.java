package de.hhu.propra.teamA2.Model;

public class Spieler {

    enum SpielerType {HOST, CLIENT}

    private SpielerType type;
    private String name;
    private boolean ready;

    public Spieler(final SpielerType type, final String name) {
        this.type = type;
        this.name = name;
    }

    public SpielerType getType() {
        return type;
    }

    public void setType(final SpielerType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(final boolean ready) {
        this.ready = ready;
    }
}
