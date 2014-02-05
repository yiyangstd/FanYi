package com.fanyi.use;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class fanyi {

	private String name = "youdaofanyi1111";
	private String key = "1065174416";
	private String tranSouce;
	private URLConnection connection;
	
	public static void main(String[] args){
		fanyi test = new fanyi("resource");
	}
	
	public fanyi(String tran){
		this.tranSouce = tran;
		String url = this.setURL();
		StringBuffer buffer = null;
		try{
			this.connection = new URL(url).openConnection();
			this.connection.connect();
			InputStream fin = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(fin));
			buffer = new StringBuffer();
			String temp = null;
			while((temp = br.readLine()) != null){
				buffer.append(temp);
			}
			//System.out.println(buffer.toString());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(this.xmltoString(buffer.toString()));
		
	}
	
	private String setURL(){
		String url = null;
		String tranSouceUTF = null;
		try {
			tranSouceUTF = URLEncoder.encode(this.tranSouce, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url = "http://fanyi.youdao.com/openapi.do?keyfrom=" + this.name + "&key=" + this.key + "&type=data&doctype=xml&version=1.1&q=" + tranSouceUTF;
		return url;
	}
	
	private String xmltoString(String xml){
		StringBuffer tran = new StringBuffer("Translation:");
		Document doc = null;
		Element expEle = null;
		try {
			doc = DocumentHelper.parseText(xml);
			Element rootElt = doc.getRootElement();//获取根节点
			//System.out.println("根节点： " + rootElt.getName());
			Iterator iter = rootElt.elementIterator("translation");
			while (iter.hasNext()){
				Element recordEle = (Element) iter.next();
				String ele = recordEle.elementTextTrim("paragraph");
				tran.append("\n" + ele);
			}
			expEle = rootElt.element("basic");
			String ele = expEle.elementTextTrim("phonetic");
			tran.append("\n" + "<" + ele + ">\n");
			Element exEl = expEle.element("explains");
			Iterator iterExp = exEl.elementIterator("ex");
			while(iterExp.hasNext()){
				Element recordEle = (Element) iterExp.next();
				String exp = recordEle.getStringValue();
				tran.append(exp + "\n");
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tran.toString();
	}
	
	private String xmltoString1(String xml){
		StringBuffer tran = new StringBuffer("Translation:");
		Document doc = null;
		Element expEle = null;
		try {
			doc = DocumentHelper.parseText(xml);
			Element rootElt = doc.getRootElement();//获取根节点
			//System.out.println("根节点： " + rootElt.getName());
			Iterator iter = rootElt.elementIterator("translation");
			while (iter.hasNext()){
				Element recordEle = (Element) iter.next();
				String ele = recordEle.elementTextTrim("paragraph");
				tran.append("\n" + ele);
			}
			expEle = rootElt.element("basic");
			Iterator iterBas = rootElt.elementIterator("basic");
			while(iterBas.hasNext()){
				Element recordEle = (Element) iterBas.next();
				String ele = recordEle.elementTextTrim("phonetic");
				tran.append("\n" + "音标：" + ele + "\n");
				//expEle = recordEle.element("explains");
			}
			Iterator iterExp = expEle.elementIterator("explains");
			while(iterExp.hasNext()){
				Element recordEle = (Element) iterExp.next();
				String exp = recordEle.elementTextTrim("ex");
				System.out.println(exp);
				tran.append(exp + " ");
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tran.toString();
	}
}
