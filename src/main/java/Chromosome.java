public class Chromosome {
    public static final double MIN = -10.0;
    public static final double MAX = 10.0;
    public static final int CHROMOSOME_SIZE = 63;


    private String binaryValue;
    private long decimalValue;
    private double realValue;
    private double funcValue;
    private double ratio;
    private double positiveValue;


    public double getPositiveValue() {
        return positiveValue;
    }

    public void setPositiveValue(double positiveValue) {
        this.positiveValue = positiveValue;
    }


    public Chromosome(int size) {
        binaryValue = getNewChromosome(size);
        decimalValue = getDecimal(binaryValue);
        realValue = getDecimalValue(decimalValue);
    }

    public Chromosome(Chromosome chromosome) {
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

    public String getBinaryValue() {
        return binaryValue;
    }

    public void setBinaryValue(String binaryValue) {
        this.binaryValue = binaryValue;
        decimalValue = getDecimal(binaryValue);
        //System.out.println("decimalValue" + decimalValue);
        realValue = getDecimalValue(decimalValue);
        //System.out.println("realValue" + realValue);
    }

    public long getDecimalValue() {
        return decimalValue;
    }

    public void setDecimalValue(int decimalValue) {
        this.decimalValue = decimalValue;
    }

    public double getRealValue() {
        return realValue;
    }

    public void setRealValue(double realValue) {
        this.realValue = realValue;
    }

    public double getFuncValue() {
        return funcValue;
    }

    public void setFuncValue(double funcValue) {
        this.funcValue = funcValue;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }


}
