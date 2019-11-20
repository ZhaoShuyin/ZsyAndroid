package com.zsy.zlib.view.ecg;

import java.io.Serializable;

/**
 * 病例信息Bean
 */
public class CaseBean implements Serializable {

    private static final long serialVersionUID = -3428016453313652815L;
    public int position = -1;
    public String address;
    public String hospital;   //医院
    public String name;       //名称
    public String age;        //年龄
    public String brithday;   //生日
    public String genders;    //性别
    public String department; //科室
    public String type;       //患者来源
    public String outpaitent; //门诊号
    public String inhospital; //住院号
    public String ward;       //病区
    public String bedin;      //床号
    public String date;       //时间
    public int duration;      //记录时长
    //唯一标识
    public String tag;
    //文件路径
    public String c8kPath;    //数据文件路径
    public String xmlFile;    //xml文件路径
    //状态
    public boolean hasUpload; //是否已上传
    public boolean isChecked;
    //
    public CaseBean next;         //上传时使用链表结构依次上传Case


    public CaseBean() {
    }

    public CaseBean(String address,
                    String hospital, String name, String genders, String age, String brithday,
                    String department, String type,
                    String outpaitent, String inhospital, String ward, String bedin, String date) {
        this.address = address;
        this.hospital = hospital;
        this.name = name;
        this.age = age;
        this.brithday = brithday;
        this.genders = genders;
        this.department = department;
        this.type = type;
        this.outpaitent = outpaitent;
        this.inhospital = inhospital;
        this.ward = ward;
        this.bedin = bedin;
        this.date = date;
    }

    @Override
    public String toString() {
        return "CaseBean{" +
                "address='" + address + '\'' +
                ", hospital='" + hospital + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", brithday='" + brithday + '\'' +
                ", genders='" + genders + '\'' +
                ", department='" + department + '\'' +
                ", type='" + type + '\'' +
                ", outpaitent='" + outpaitent + '\'' +
                ", inhospital='" + inhospital + '\'' +
                ", ward='" + ward + '\'' +
                ", bedin='" + bedin + '\'' +
                ", date='" + date + '\'' +
                ", tag='" + tag + '\'' +
                ", c8kPath='" + c8kPath + '\'' +
                ", xmlFile='" + xmlFile + '\'' +
                ", hasUpload=" + hasUpload +
                ", isChecked=" + isChecked +
                ", next=" + next +
                '}';
    }
}