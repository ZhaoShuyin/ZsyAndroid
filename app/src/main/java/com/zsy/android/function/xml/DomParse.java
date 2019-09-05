package com.zsy.android.function.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

/**
 * 这个DOM解析,树形结构,整体载入,方便增删改查
 * @author Administrator
 *
 */
public class DomParse {

    public static String domParseTest(File file, String 书) throws Exception{

        //①获取DocumentBuilderFactory
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        //②获取DocumentBuilder
        DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
        //③获取Document
        Document document = documentBuilder.parse("book.xml");
        //通过节点的名字 "书名" 找到所有的同名的节点
        NodeList nodeList = document.getElementsByTagName("书名");
        //获取第二本书 书名对应的节点
        Node item = nodeList.item(0);
        //通过节点获取节点的内容
        String textContent = item.getTextContent();
        System.out.println("textConent==="+textContent);
        return textContent;
    }

    /**
     * 这个方法实现修改
     * @throws Exception
     */
    public void domModifyTest() throws Exception{
        //获取DocumentBuilderFactory
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        //获取DocumentBuilder
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        //通过DocumentBuilder 解析文件 拿到document对象
        Document document = documentBuilder.parse("book.xml");
        //找到售价节点
        NodeList nodeList = document.getElementsByTagName("售价");
        //找到第一本书的售价节点
        Node node = nodeList.item(0);
        //设置文本内容
        node.setTextContent("99.00");

        //transformer
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        //通过工厂获得transformer
        Transformer transformer = transformerFactory.newTransformer();
        //通过transformer 把内存中的修改保存到文件中

        //指定数据源  把刚解析出来的document对象作为数据源
        Source arg0 = new DOMSource(document);
        //指定 修改之后保存结果的对象    创建一个StreamResult(流结果) 保存到本地文件 book.xml
        Result arg1 = new StreamResult("book.xml");
        transformer.transform(arg0, arg1);

    }

}