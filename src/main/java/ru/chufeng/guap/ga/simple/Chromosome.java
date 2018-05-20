package ru.chufeng.guap.ga.simple;

import java.util.Comparator;

 class Chromosome {
    static final double MIN = -10.0;
    static final double MAX = 10.0;
    static final int CHROMOSOME_SIZE = 63;


    private String binaryValue;
    private long decimalValue;
    private double realValue;
    private double funcValue;
    private double ratio;
    private double positiveValue;

    @Override
    public String toString() {
        return Double.toString(this.getPositiveValue());
    }

    double getPositiveValue() {
        return positiveValue;
    }

    void setPositiveValue(double positiveValue) {
        this.positiveValue = positiveValue;
    }


    Chromosome(int size) {
        binaryValue = getNewChromosome(size);
        decimalValue = getDecimal(binaryValue);
        realValue = getDecimalValue(decimalValue);
    }

    Chromosome(Chromosome chromosome) {
        this.binaryValue = chromosome.binaryValue;
        this.decimalValue = chromosome.decimalValue;
        this.realValue = chromosome.realValue;
        this.funcValue = chromosome.funcValue;
    }


    private static String getNewChromosome(int size) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            result.append((int)Math.round( Math.random()));
        }
        return result.toString();
    }

    private static long getDecimal(String binary) {
        return Long.parseLong(binary, 2);
    }

    private static double getDecimalValue(long partNum) {
        return MIN + partNum * ((MAX-MIN)/(Math.pow(2, CHROMOSOME_SIZE)-1));
    }

    String getBinaryValue() {
        return binaryValue;
    }

    void setBinaryValue(String binaryValue) {
        this.binaryValue = binaryValue;
        decimalValue = getDecimal(binaryValue);
        realValue = getDecimalValue(decimalValue);
    }

    long getDecimalValue() {
        return decimalValue;
    }

    void setDecimalValue(int decimalValue) {
        this.decimalValue = decimalValue;
    }

    double getRealValue() {
        return realValue;
    }

    void setRealValue(double realValue) {
        this.realValue = realValue;
    }

    double getFuncValue() {
        return funcValue;
    }

    void setFuncValue(double funcValue) {
        this.funcValue = funcValue;
    }

    double getRatio() {
        return ratio;
    }

    void setRatio(double ratio) {
        this.ratio = ratio;
    }


    static Comparator<Chromosome> getCompByName()
    {
        Comparator<Chromosome> comp = new Comparator<Chromosome>(){
            @Override
            public int compare(Chromosome s1, Chromosome s2)
            {
                return Double.compare(s1.positiveValue, s2.positiveValue);
            }
        };
        return comp;
    }
}
