package com.diva.appdemo.Entity;

/**
 * Created by 刘迪 on 2018/8/7 18:05.
 * 邮箱：divaliu1408@qq.com
 */

public class StudentInfo {
    /**
     * id : 1
     * name : 张三
     * sex : male
     * score : {"math":80,"Chinese":81,"English":85}
     */
    private int id;
    private String name;
    private String sex;
    private ScoreBean score;

    public StudentInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public ScoreBean getScore() {
        return score;
    }

    public void setScore(ScoreBean score) {
        this.score = score;
    }

    public static class ScoreBean {
        /**
         * math : 80
         * Chinese : 81
         * English : 85
         */

        private int math;
        private int Chinese;
        private int English;

        public int getMath() {
            return math;
        }

        public void setMath(int math) {
            this.math = math;
        }

        public int getChinese() {
            return Chinese;
        }

        public void setChinese(int Chinese) {
            this.Chinese = Chinese;
        }

        public int getEnglish() {
            return English;
        }

        public void setEnglish(int English) {
            this.English = English;
        }
    }
    public String toString(){
        return "name:"+getName()
                +",sex:"+getSex()
                +",English:"+getScore().getEnglish()
                +",Math:"+getScore().getMath()
                + ",Chinese"+getScore().getChinese();
    }
}
