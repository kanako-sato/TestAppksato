package jp.ac.doshisha.mikilab.testappksato;


public class Light {
    private static int id_counter = 1;
    private int id;

    private int signalA = 0;  // 白色信号値
    private int signalB = 0;  // 昼白色信号値

    private double posX;   // light position x
    private double posY;   // light position y

    private double temperature = 4000;  // 色温度
    private double lumPct = 50.0;       // 光度パーセント

    public Light() {
        id = id_counter++;
    }

    void setSignals(int[] signals) {
        signalA = signals[0];
        signalB = signals[1];
    }

    public void setLumPct(double pct) {this.lumPct = pct;}

    public void setTemperature(double tmp) {this.temperature = tmp;}

    public void setPosX(double x) {posX = x;}

    public void setPosY(double y) {posY = y;}

    public int getId() { return id; }

    public void setId(int id) {this.id = id;}

    public double getLumPct() {
        return lumPct;
    }

    public double getPosX() { return posX;}

    public double getPosY() { return posY;}

    public double getTemperature() {
        return temperature;
    }

    public int[] getSignals() {
        return new int[]{signalA, signalB};
    }
}

