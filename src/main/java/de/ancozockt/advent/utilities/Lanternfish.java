package de.ancozockt.advent.utilities;

public class Lanternfish {

    private int timer;
    private long amount;

    public Lanternfish(int timer){
        this.timer = timer;
        this.amount = 1;
    }

    public Lanternfish(int timer, long amount){
        this.timer = timer;
        this.amount = amount;
    }

    public void addAmount(){
        setAmount(getAmount() + 1L);
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int countDown(){
        timer--;
        return timer;
    }

    public boolean isFinished(){
        return timer == 0;
    }

    public int getTimer() {
        return timer;
    }

    public long getAmount() {
        return amount;
    }

    public void setTimer(int timer){
        this.timer = timer;
    }

}
