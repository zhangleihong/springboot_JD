package com.example.springboot_init.po;

public class SecondInfo {
    private String secondClass;
    private Double avg;
    private Double min;
    private Double max;
    private Double mid;

    public String getSecondClass() {
        return secondClass;
    }

    public void setSecondClass(String secondClass) {
        this.secondClass = secondClass;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMid() {
        return mid;
    }

    public void setMid(Double mid) {
        this.mid = mid;
    }

    @Override
    public String toString() {
        return "SecondInfo{" +
                "secondClass='" + secondClass + '\'' +
                ", avg=" + avg +
                ", min=" + min +
                ", max=" + max +
                ", mid=" + mid +
                '}';
    }
}
