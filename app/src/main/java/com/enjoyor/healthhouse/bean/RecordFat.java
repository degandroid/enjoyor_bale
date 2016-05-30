package com.enjoyor.healthhouse.bean;

import java.io.Serializable;

/**
 * Created by YuanYuan on 2016/4/11.
 */
public class RecordFat implements Serializable {
    private int basicMetaBolism;
    private double bmc;
    private int bodyFat;
    private double bodyMuscle;
    private double exceptFat;
    private double fat;
    private double fatAdjust;
    private int fatRate;
    private double fic;
    private int foc;
    private int id;
    private double leftArmFat;
    private double leftArmMuscle;
    private int leftLegFat;
    private double leftLegMuscle;
    private double minerals;
    private double muscle;
    private double muscleAdjust;
    private double muscleRate;
    private double protein;
    private int recordId;
    private String result;
    private double rightArmFat;
    private double rightArmMuscle;
    private int rightLegFat;
    private double rightLegMuscle;
    private int viscera;
    private double water;
    private double waterRate;
    private double weightAdjust;

    public RecordFat() {
    }

    public RecordFat(int basicMetaBolism, double bmc, int bodyFat, double bodyMuscle, double exceptFat, double fat, double fatAdjust, int fatRate, double fic, int foc, int id, double leftArmFat, double leftArmMuscle, int leftLegFat, double leftLegMuscle, double minerals, double muscle, double muscleAdjust, double muscleRate, double protein, int recordId, String result, double rightArmFat, double rightArmMuscle, int rightLegFat, double rightLegMuscle, int viscera, double water, double waterRate, double weightAdjust) {
        this.basicMetaBolism = basicMetaBolism;
        this.bmc = bmc;
        this.bodyFat = bodyFat;
        this.bodyMuscle = bodyMuscle;
        this.exceptFat = exceptFat;
        this.fat = fat;
        this.fatAdjust = fatAdjust;
        this.fatRate = fatRate;
        this.fic = fic;
        this.foc = foc;
        this.id = id;
        this.leftArmFat = leftArmFat;
        this.leftArmMuscle = leftArmMuscle;
        this.leftLegFat = leftLegFat;
        this.leftLegMuscle = leftLegMuscle;
        this.minerals = minerals;
        this.muscle = muscle;
        this.muscleAdjust = muscleAdjust;
        this.muscleRate = muscleRate;
        this.protein = protein;
        this.recordId = recordId;
        this.result = result;
        this.rightArmFat = rightArmFat;
        this.rightArmMuscle = rightArmMuscle;
        this.rightLegFat = rightLegFat;
        this.rightLegMuscle = rightLegMuscle;
        this.viscera = viscera;
        this.water = water;
        this.waterRate = waterRate;
        this.weightAdjust = weightAdjust;
    }

    public int getBasicMetaBolism() {
        return basicMetaBolism;
    }

    public void setBasicMetaBolism(int basicMetaBolism) {
        this.basicMetaBolism = basicMetaBolism;
    }

    public double getBmc() {
        return bmc;
    }

    public void setBmc(double bmc) {
        this.bmc = bmc;
    }

    public int getBodyFat() {
        return bodyFat;
    }

    public void setBodyFat(int bodyFat) {
        this.bodyFat = bodyFat;
    }

    public double getBodyMuscle() {
        return bodyMuscle;
    }

    public void setBodyMuscle(double bodyMuscle) {
        this.bodyMuscle = bodyMuscle;
    }

    public double getExceptFat() {
        return exceptFat;
    }

    public void setExceptFat(double exceptFat) {
        this.exceptFat = exceptFat;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getFatAdjust() {
        return fatAdjust;
    }

    public void setFatAdjust(double fatAdjust) {
        this.fatAdjust = fatAdjust;
    }

    public int getFatRate() {
        return fatRate;
    }

    public void setFatRate(int fatRate) {
        this.fatRate = fatRate;
    }

    public double getFic() {
        return fic;
    }

    public void setFic(double fic) {
        this.fic = fic;
    }

    public int getFoc() {
        return foc;
    }

    public void setFoc(int foc) {
        this.foc = foc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLeftArmFat() {
        return leftArmFat;
    }

    public void setLeftArmFat(double leftArmFat) {
        this.leftArmFat = leftArmFat;
    }

    public double getLeftArmMuscle() {
        return leftArmMuscle;
    }

    public void setLeftArmMuscle(double leftArmMuscle) {
        this.leftArmMuscle = leftArmMuscle;
    }

    public int getLeftLegFat() {
        return leftLegFat;
    }

    public void setLeftLegFat(int leftLegFat) {
        this.leftLegFat = leftLegFat;
    }

    public double getLeftLegMuscle() {
        return leftLegMuscle;
    }

    public void setLeftLegMuscle(double leftLegMuscle) {
        this.leftLegMuscle = leftLegMuscle;
    }

    public double getMinerals() {
        return minerals;
    }

    public void setMinerals(double minerals) {
        this.minerals = minerals;
    }

    public double getMuscle() {
        return muscle;
    }

    public void setMuscle(double muscle) {
        this.muscle = muscle;
    }

    public double getMuscleAdjust() {
        return muscleAdjust;
    }

    public void setMuscleAdjust(double muscleAdjust) {
        this.muscleAdjust = muscleAdjust;
    }

    public double getMuscleRate() {
        return muscleRate;
    }

    public void setMuscleRate(double muscleRate) {
        this.muscleRate = muscleRate;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public double getRightArmFat() {
        return rightArmFat;
    }

    public void setRightArmFat(double rightArmFat) {
        this.rightArmFat = rightArmFat;
    }

    public double getRightArmMuscle() {
        return rightArmMuscle;
    }

    public void setRightArmMuscle(double rightArmMuscle) {
        this.rightArmMuscle = rightArmMuscle;
    }

    public int getRightLegFat() {
        return rightLegFat;
    }

    public void setRightLegFat(int rightLegFat) {
        this.rightLegFat = rightLegFat;
    }

    public double getRightLegMuscle() {
        return rightLegMuscle;
    }

    public void setRightLegMuscle(double rightLegMuscle) {
        this.rightLegMuscle = rightLegMuscle;
    }

    public int getViscera() {
        return viscera;
    }

    public void setViscera(int viscera) {
        this.viscera = viscera;
    }

    public double getWater() {
        return water;
    }

    public void setWater(double water) {
        this.water = water;
    }

    public double getWaterRate() {
        return waterRate;
    }

    public void setWaterRate(double waterRate) {
        this.waterRate = waterRate;
    }

    public double getWeightAdjust() {
        return weightAdjust;
    }

    public void setWeightAdjust(double weightAdjust) {
        this.weightAdjust = weightAdjust;
    }
}
