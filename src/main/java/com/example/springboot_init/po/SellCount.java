package com.example.springboot_init.po;

public class SellCount {
    private String skuId;
    private Integer firstQuarter;
    private Integer secondQuarter;
    private Integer thirdQuarter;
    private Integer forthQuarter;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Integer getFirstQuarter() {
        return firstQuarter;
    }

    public void setFirstQuarter(Integer firstQuarter) {
        this.firstQuarter = firstQuarter;
    }

    public Integer getSecondQuarter() {
        return secondQuarter;
    }

    public void setSecondQuarter(Integer secondQuarter) {
        this.secondQuarter = secondQuarter;
    }

    public Integer getThirdQuarter() {
        return thirdQuarter;
    }

    public void setThirdQuarter(Integer thirdQuarter) {
        this.thirdQuarter = thirdQuarter;
    }

    public Integer getForthQuarter() {
        return forthQuarter;
    }

    public void setForthQuarter(Integer forthQuarter) {
        this.forthQuarter = forthQuarter;
    }

    @Override
    public String toString() {
        return "SellCount{" +
                "skuId='" + skuId + '\'' +
                ", firstQuarter=" + firstQuarter +
                ", secondQuarter=" + secondQuarter +
                ", thirdQuarter=" + thirdQuarter +
                ", forthQuarter=" + forthQuarter +
                '}';
    }
}
