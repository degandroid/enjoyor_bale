package com.enjoyor.healthhouse.bean;

import java.util.List;

/**
 * Created by YuanYuan on 2016/5/24.
 */
public class HealthSuggest {

    /**
     * disease : hypertension
     * diseaseId : 1
     * level : 2
     * content : 正常----------------------
     */

    private ResultEntity result;
    /**
     * name : 马路散步
     * sort : 1
     */

    private List<RecommendExercisesEntity> recommendExercises;
    /**
     * name : 蜂蜜
     * sort : 1
     */

    private List<RecommendFoodsEntity> recommendFoods;
    /**
     * recommendExercises : [{"name":"马路散步","sort":1},{"name":"太极拳","sort":1}]
     * recommendFoods : [{"name":"蜂蜜","sort":1},{"name":"西瓜子（炒）","sort":1}]
     * result : {"disease":"hypertension","diseaseId":1,"level":2,"content":"正常----------------------"}
     * tabooExercises : []
     * tabooFoods : [{"name":"猪肉（脖子，猪脖）","sort":1}]
     */

    private List<?> tabooExercises;
    /**
     * name : 猪肉（脖子，猪脖）
     * sort : 1
     */

    private List<TabooFoodsEntity> tabooFoods;

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public List<RecommendExercisesEntity> getRecommendExercises() {
        return recommendExercises;
    }

    public void setRecommendExercises(List<RecommendExercisesEntity> recommendExercises) {
        this.recommendExercises = recommendExercises;
    }

    public List<RecommendFoodsEntity> getRecommendFoods() {
        return recommendFoods;
    }

    public void setRecommendFoods(List<RecommendFoodsEntity> recommendFoods) {
        this.recommendFoods = recommendFoods;
    }

    public List<?> getTabooExercises() {
        return tabooExercises;
    }

    public void setTabooExercises(List<?> tabooExercises) {
        this.tabooExercises = tabooExercises;
    }

    public List<TabooFoodsEntity> getTabooFoods() {
        return tabooFoods;
    }

    public void setTabooFoods(List<TabooFoodsEntity> tabooFoods) {
        this.tabooFoods = tabooFoods;
    }

    public static class ResultEntity {
        private String disease;
        private int diseaseId;
        private int level;
        private String content;

        public String getDisease() {
            return disease;
        }

        public void setDisease(String disease) {
            this.disease = disease;
        }

        public int getDiseaseId() {
            return diseaseId;
        }

        public void setDiseaseId(int diseaseId) {
            this.diseaseId = diseaseId;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class RecommendExercisesEntity {
        private String name;
        private int sort;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }

    public static class RecommendFoodsEntity {
        private String name;
        private int sort;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }

    public static class TabooFoodsEntity {
        private String name;
        private int sort;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }
}
