package classes;

public class CsvData implements Comparable<CsvData>{
    int month;
    double sales;
    String supplier;

    public CsvData(int month, double sales, String supplier) {
        this.month = month;
        this.sales = sales;
        this supplier = supplier;
    }

    public int compareTo(CsvData other){
        return Integer.compare(this.month, other.month);
    }

    @Override
    public String toString() {
        return  "month=" + month +
                ", sales=" + sales +
                ", supplier=" + supplier;
    }
}