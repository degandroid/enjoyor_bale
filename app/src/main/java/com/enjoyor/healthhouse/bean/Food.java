package com.enjoyor.healthhouse.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class Food {

    private Page page;
    private List<FoodList> list;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<FoodList> getList() {
        return list;
    }

    public void setList(List<FoodList> list) {
        this.list = list;
    }

    public class FoodList{
        private Long id;//食物ID
        private String name;//食物名称
        private String calories;//卡路里
        private String unit;//单位

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCalories() {
            return calories;
        }

        public void setCalories(String calories) {
            this.calories = calories;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
