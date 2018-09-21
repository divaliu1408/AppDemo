package com.diva.appdemo.Entity;

/**
 * Created by 刘迪 on 2018/8/7 17:35.
 * 邮箱：divaliu1408@qq.com
 */

public class AppInfo {

    /**
     * appVersion : {"id":54,"deviceType":3,"name":"beiken_pad","versionNum":7.2,"mainVersionNum":2,"secondaryVersionNum":2,"bugVersionNum":2,"dateVersionNum":20180209,"url":"/res/apk/7.2.pad.apk","description":"心电bug修复","createTime":1518147182000,"phoneVersionNum":0,"urlPhone":null,"timelineItems":null,"systemParams":null}
     * baseData : {"id":2,"versionNum":2494,"url":"/res/json/2275a925-9308-43af-9330-0c2f61c08791","createTime":1531732360000,"environments":null,"quickReplys":null,"webHospitals":null,"embulances":null,"departments":null,"registerItems":null,"testItems":null,"treatments":null,"useMethods":null,"units":null,"centralHospitals":null,"timelineItems":null}
     */

    private AppVersionBean appVersion;
    private BaseDataBean baseData;

    public AppVersionBean getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(AppVersionBean appVersion) {
        this.appVersion = appVersion;
    }

    public BaseDataBean getBaseData() {
        return baseData;
    }

    public void setBaseData(BaseDataBean baseData) {
        this.baseData = baseData;
    }

    public static class AppVersionBean {
        /**
         * id : 54
         * deviceType : 3
         * name : beiken_pad
         * versionNum : 7.2
         * mainVersionNum : 2
         * secondaryVersionNum : 2
         * bugVersionNum : 2
         * dateVersionNum : 20180209
         * url : /res/apk/7.2.pad.apk
         * description : 心电bug修复
         * createTime : 1518147182000
         * phoneVersionNum : 0
         * urlPhone : null
         * timelineItems : null
         * systemParams : null
         */

        private int id;
        private int deviceType;
        private String name;
        private double versionNum;
        private int mainVersionNum;
        private int secondaryVersionNum;
        private int bugVersionNum;
        private int dateVersionNum;
        private String url;
        private String description;
        private long createTime;
        private int phoneVersionNum;
        private Object urlPhone;
        private Object timelineItems;
        private Object systemParams;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(int deviceType) {
            this.deviceType = deviceType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getVersionNum() {
            return versionNum;
        }

        public void setVersionNum(double versionNum) {
            this.versionNum = versionNum;
        }

        public int getMainVersionNum() {
            return mainVersionNum;
        }

        public void setMainVersionNum(int mainVersionNum) {
            this.mainVersionNum = mainVersionNum;
        }

        public int getSecondaryVersionNum() {
            return secondaryVersionNum;
        }

        public void setSecondaryVersionNum(int secondaryVersionNum) {
            this.secondaryVersionNum = secondaryVersionNum;
        }

        public int getBugVersionNum() {
            return bugVersionNum;
        }

        public void setBugVersionNum(int bugVersionNum) {
            this.bugVersionNum = bugVersionNum;
        }

        public int getDateVersionNum() {
            return dateVersionNum;
        }

        public void setDateVersionNum(int dateVersionNum) {
            this.dateVersionNum = dateVersionNum;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getPhoneVersionNum() {
            return phoneVersionNum;
        }

        public void setPhoneVersionNum(int phoneVersionNum) {
            this.phoneVersionNum = phoneVersionNum;
        }

        public Object getUrlPhone() {
            return urlPhone;
        }

        public void setUrlPhone(Object urlPhone) {
            this.urlPhone = urlPhone;
        }

        public Object getTimelineItems() {
            return timelineItems;
        }

        public void setTimelineItems(Object timelineItems) {
            this.timelineItems = timelineItems;
        }

        public Object getSystemParams() {
            return systemParams;
        }

        public void setSystemParams(Object systemParams) {
            this.systemParams = systemParams;
        }
    }

    public static class BaseDataBean {
        /**
         * id : 2
         * versionNum : 2494
         * url : /res/json/2275a925-9308-43af-9330-0c2f61c08791
         * createTime : 1531732360000
         * environments : null
         * quickReplys : null
         * webHospitals : null
         * embulances : null
         * departments : null
         * registerItems : null
         * testItems : null
         * treatments : null
         * useMethods : null
         * units : null
         * centralHospitals : null
         * timelineItems : null
         */

        private int id;
        private int versionNum;
        private String url;
        private long createTime;
        private Object environments;
        private Object quickReplys;
        private Object webHospitals;
        private Object embulances;
        private Object departments;
        private Object registerItems;
        private Object testItems;
        private Object treatments;
        private Object useMethods;
        private Object units;
        private Object centralHospitals;
        private Object timelineItems;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getVersionNum() {
            return versionNum;
        }

        public void setVersionNum(int versionNum) {
            this.versionNum = versionNum;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public Object getEnvironments() {
            return environments;
        }

        public void setEnvironments(Object environments) {
            this.environments = environments;
        }

        public Object getQuickReplys() {
            return quickReplys;
        }

        public void setQuickReplys(Object quickReplys) {
            this.quickReplys = quickReplys;
        }

        public Object getWebHospitals() {
            return webHospitals;
        }

        public void setWebHospitals(Object webHospitals) {
            this.webHospitals = webHospitals;
        }

        public Object getEmbulances() {
            return embulances;
        }

        public void setEmbulances(Object embulances) {
            this.embulances = embulances;
        }

        public Object getDepartments() {
            return departments;
        }

        public void setDepartments(Object departments) {
            this.departments = departments;
        }

        public Object getRegisterItems() {
            return registerItems;
        }

        public void setRegisterItems(Object registerItems) {
            this.registerItems = registerItems;
        }

        public Object getTestItems() {
            return testItems;
        }

        public void setTestItems(Object testItems) {
            this.testItems = testItems;
        }

        public Object getTreatments() {
            return treatments;
        }

        public void setTreatments(Object treatments) {
            this.treatments = treatments;
        }

        public Object getUseMethods() {
            return useMethods;
        }

        public void setUseMethods(Object useMethods) {
            this.useMethods = useMethods;
        }

        public Object getUnits() {
            return units;
        }

        public void setUnits(Object units) {
            this.units = units;
        }

        public Object getCentralHospitals() {
            return centralHospitals;
        }

        public void setCentralHospitals(Object centralHospitals) {
            this.centralHospitals = centralHospitals;
        }

        public Object getTimelineItems() {
            return timelineItems;
        }

        public void setTimelineItems(Object timelineItems) {
            this.timelineItems = timelineItems;
        }
    }
}
