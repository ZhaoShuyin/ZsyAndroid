package com.zsy.android.function.xml;

import android.app.Activity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;

import com.zsy.android.R;
import com.zsy.android.function.xml.bean.Sms;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:XMl序列化
 * </p>
 *
 * @author Zsy
 * @date 2019/8/28 14:09
 */
public class XmlSeralizerActivity extends Activity {

    private ArrayList<Sms> sms_list = new ArrayList<Sms>();
    private Sms sms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_seralizer);
        //创建模拟的短信数据
        for(int i = 0;i<30;i++){
            sms = new Sms();
            sms.from = "1000"+i;
            sms.content="data"+i;
            sms.date = "06-26 16:16:"+i;
            sms_list.add(sms);
            sms = null;
        }
    }

    /**
     * 创建一个Xml文件
     * @param v
     */
    public void savexml(View v){
        StringBuilder builder = new StringBuilder();
        //首先写文档的声明
        builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        //写根元素 <Smslist>
        builder.append("<Smslist>");
        for(Sms sms: sms_list){
            //写每一个sms节点的开始标签
            builder.append("<Sms>");

            //写<from>
            builder.append("<from>");
            builder.append(sms.from);
            //写</from>
            builder.append("</from>");

            builder.append("<content>");
            builder.append(sms.content);
            builder.append("</content>");

            builder.append("<date>");
            builder.append(sms.date);
            builder.append("</date>");

            //写</Sms>结束标签
            builder.append("</Sms>");

        }
        builder.append("</Smslist>");

        String result = builder.toString();
        try {
            FileOutputStream fos = openFileOutput("sms.xml", MODE_PRIVATE);
            fos.write(result.getBytes());
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void savexml2(View v){
        //获取serializer 对象
        XmlSerializer serializer = Xml.newSerializer();
        try {
            //设置输出流
            serializer.setOutput(openFileOutput("sms2.xml", MODE_PRIVATE), "utf-8");
            //先写文档开始的声明
            serializer.startDocument("utf-8", true);
            //开始写<Smslist>开始标签  第一个参数 就是名称空间  如果有schema约束文档 约束着一份xml 就要传一个名称空间
            //由于当前文档没有约束文件 所以不需要传名称空间  null就可以了
            serializer.startTag(null, "Smslist");
            for(Sms sms:sms_list){
                //写<Sms>
                serializer.startTag(null, "Sms");

                //写<from></from>节点
                serializer.startTag(null, "from");
                serializer.text(sms.from);
                serializer.endTag(null, "from");

                //写<content></content>节点
                serializer.startTag(null, "content");
                serializer.text(sms.content);
                serializer.endTag(null, "content");

                //写<date></date>节点
                serializer.startTag(null, "date");
                serializer.text(sms.date);
                serializer.endTag(null, "date");

                //写</Sms> 闭合标签
                serializer.endTag(null, "Sms");
            }
            //写文档结束标签</Smslist>
            serializer.endTag(null, "Smslist");
            serializer.endDocument();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void parse(View v){
        ArrayList<Sms> smss = null;
        Sms sms = null;
        //获取xml解析器对象
        XmlPullParser pullParser = Xml.newPullParser();
        try {
            //给解析器设置输入源
            pullParser.setInput(openFileInput("sms.xml"), "utf-8");
            //调用getEventType 获取当前解析元素的类型
            int eventType = pullParser.getEventType();
            //判断eventType 只要没结束就继续执行解析
            while(eventType!=XmlPullParser.END_DOCUMENT){
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        //说明解析到了开始标签
                        if("Smslist".equals(pullParser.getName())){
                            //解析到<Smslist>创建集合
                            smss = new ArrayList<Sms>();
                        }else if("Sms".equals(pullParser.getName())){
                            //解析到了<Sms>开始标签 创建sms对象
                            sms = new Sms();
                        }else if("from".equals(pullParser.getName())){
                            //解析到了<from>开始标签  给sms的from属性设置解析出的文本值
                            sms.from  = pullParser.nextText();
                        }else if("content".equals(pullParser.getName())){
                            //解析到了<content>开始标签  给sms的content属性设置解析出的文本值
                            sms.content  = pullParser.nextText();
                        }else if("date".equals(pullParser.getName())){
                            //解析到了<date>开始标签  给sms的date属性设置解析出的文本值
                            sms.date  = pullParser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        //说明解析到了结束标签
                        if("Sms".equals(pullParser.getName())){
                            //说明解析到了</Sms> 说明一条短信已经解析完毕 把sms对象放到集合
                            smss.add(sms);
                            //把sms置空
                            sms = null;
                        }
                        break;

                    default:
                        break;
                }
                //用eventType 接收 解析器解析下一个元素的返回值
                //如果走到了END_DOCUMENT while循环退出
                eventType = pullParser.next();
            }
            for(Sms sms1: smss){
                System.out.println(sms1);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
