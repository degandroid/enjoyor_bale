package com.enjoyor.healthhouse.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YuanYuan on 2016/4/11.
 */
public class BCAFragmentbean implements Serializable {

    private int basicMetaBolismBest;
    private double bmiBestMax;
    private double bmiBestMin;
    private double fatBestMax;
    private double fatBestMin;
    private int fatRateBestMax;
    private int fatRateBestMin;
    private String healthSuggest;
    private double muscleBestMax;
    private int muscleBestMin;
    private String recordsTime;
    private int visceraBestMax;
    private int visceraBestMin;
    private int waterRateBestMax;
    private int waterRateBestMin;
    private List<DictionaryFat> dictionaryFatList;
    private List<Fatyear> fatyear;
    private List<Weightyear> weightyear;

    public List<DictionaryFat> getDictionaryFatList() {
        return dictionaryFatList;
    }

    public void setDictionaryFatList(List<DictionaryFat> dictionaryFatList) {
        this.dictionaryFatList = dictionaryFatList;
    }


    public List<Weightyear> getWeightyearList() {
        return weightyearList;
    }

    public void setWeightyearList(List<Weightyear> weightyearList) {
        this.weightyearList = weightyearList;
    }

    private RecordBMI recordBMI;
    private RecordFat recordFat;
    private List<Weightyear> weightyearList;

    public int getBasicMetaBolismBest() {
        return basicMetaBolismBest;
    }

    public List<Fatyear> getFatyear() {
        return fatyear;
    }

    public void setFatyear(List<Fatyear> fatyear) {
        this.fatyear = fatyear;
    }

    public List<Weightyear> getWeightyear() {
        return weightyear;
    }

    public void setWeightyear(List<Weightyear> weightyear) {
        this.weightyear = weightyear;
    }

    public RecordBMI getRecordBMI() {
        return recordBMI;
    }

    public void setRecordBMI(RecordBMI recordBMI) {
        this.recordBMI = recordBMI;
    }

    public RecordFat getRecordFat() {
        return recordFat;
    }

    public void setRecordFat(RecordFat recordFat) {
        this.recordFat = recordFat;
    }

    public void setBasicMetaBolismBest(int basicMetaBolismBest) {
        this.basicMetaBolismBest = basicMetaBolismBest;
    }

    public double getBmiBestMax() {
        return bmiBestMax;
    }

    public void setBmiBestMax(double bmiBestMax) {
        this.bmiBestMax = bmiBestMax;
    }

    public double getBmiBestMin() {
        return bmiBestMin;
    }

    public void setBmiBestMin(double bmiBestMin) {
        this.bmiBestMin = bmiBestMin;
    }

    public double getFatBestMax() {
        return fatBestMax;
    }

    public void setFatBestMax(double fatBestMax) {
        this.fatBestMax = fatBestMax;
    }

    public double getFatBestMin() {
        return fatBestMin;
    }

    public void setFatBestMin(double fatBestMin) {
        this.fatBestMin = fatBestMin;
    }

    public int getFatRateBestMax() {
        return fatRateBestMax;
    }

    public void setFatRateBestMax(int fatRateBestMax) {
        this.fatRateBestMax = fatRateBestMax;
    }

    public int getFatRateBestMin() {
        return fatRateBestMin;
    }

    public void setFatRateBestMin(int fatRateBestMin) {
        this.fatRateBestMin = fatRateBestMin;
    }

    public String getHealthSuggest() {
        return healthSuggest;
    }

    public void setHealthSuggest(String healthSuggest) {
        this.healthSuggest = healthSuggest;
    }

    public double getMuscleBestMax() {
        return muscleBestMax;
    }

    public void setMuscleBestMax(double muscleBestMax) {
        this.muscleBestMax = muscleBestMax;
    }

    public int getMuscleBestMin() {
        return muscleBestMin;
    }

    public void setMuscleBestMin(int muscleBestMin) {
        this.muscleBestMin = muscleBestMin;
    }

    public String getRecordsTime() {
        return recordsTime;
    }

    public void setRecordsTime(String recordsTime) {
        this.recordsTime = recordsTime;
    }

    public int getVisceraBestMax() {
        return visceraBestMax;
    }

    public void setVisceraBestMax(int visceraBestMax) {
        this.visceraBestMax = visceraBestMax;
    }

    public int getVisceraBestMin() {
        return visceraBestMin;
    }

    public void setVisceraBestMin(int visceraBestMin) {
        this.visceraBestMin = visceraBestMin;
    }

    public int getWaterRateBestMax() {
        return waterRateBestMax;
    }

    public void setWaterRateBestMax(int waterRateBestMax) {
        this.waterRateBestMax = waterRateBestMax;
    }

    public int getWaterRateBestMin() {
        return waterRateBestMin;
    }

    public void setWaterRateBestMin(int waterRateBestMin) {
        this.waterRateBestMin = waterRateBestMin;
    }

}
