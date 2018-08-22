package org.zuhlke;

public class Player {
    private final String name;
    private boolean isInPenaltyBox;
    private int purse;

    public Player(String name, boolean isInPenaltyBox, int purse) {
        this.name = name;
        this.isInPenaltyBox = isInPenaltyBox;
        this.purse = purse;
    }

    public String getName() {
        return name;
    }

    public boolean isInPenaltyBox() {
        return isInPenaltyBox;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        isInPenaltyBox = inPenaltyBox;
    }

    public int getPurse() {
        return purse;
    }

    public void setPurse(int purse) {
        this.purse = purse;
    }

    @Override
    public String toString() {
        return name;
    }
}
