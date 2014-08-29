// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2008-4-25 14:46:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Call.java

/**
 * 包中提供的同等类有编码问题（79），这里修改了少许代码。
 */
package com.founder.e5.trans.saver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.logging.Log;

import com.founder.e5.imp.util.LogUtil;
import com.founder.e5.trans.util.ConstantsInfo;

public class Call
{
	private Log log = LogUtil.log;
	
    public Call()
    {
        strUserName = "";
        strPassword = "";
        strOperateType = "";
        strMethodName = "";
        strParameter = "";
    }

    public void setUserName(String strUserName)
    {
        this.strUserName = "<UserName>" + strUserName + "</UserName>";
    }

    public void setPassword(String strPassword)
    {
        this.strPassword = "<Password>" + strPassword + "</Password>";
    }

    public void setOperateType(String strOperateType)
    {
        this.strOperateType = "<OperateType>" + strOperateType + "</OperateType>";
    }

    public void setMethodName(String strMethodName)
    {
        this.strMethodName = "<MethodName>" + strMethodName + "</MethodName>";
    }

    public void setParameters(String strParameter, String strValue)
    {
        this.strParameter = this.strParameter + "<" + strParameter + ">" + strValue + "</" + strParameter + ">";
    }

    public String getXMLString()
    {
        String strXMLString = "<?xml version=\"1.0\" encoding=\"GBK\" ?><Inter-Xinhua>";
        strXMLString = strXMLString + strUserName + strPassword + strOperateType + strMethodName;
        strXMLString = strXMLString + "<Parameters>" + strParameter + "</Parameters></Inter-Xinhua>";
        log.info( "com.founder.e5.trans.saver.Call send to Trs XMLString="+ strXMLString );
        return strXMLString;
    }

    public String send(URL url)
    {
        StringBuffer strReturnXMLStringBuf = new StringBuffer();
        try
        {
            URLConnection connection = url.openConnection();            
            HttpURLConnection httpConn = (HttpURLConnection)connection;
            String strXML = getXMLString();
            byte b[] = strXML.getBytes();
            httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpConn.setRequestProperty("Content-Type", "text/xml;charset="+ConstantsInfo.SENDENCODING);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            
            OutputStream out = httpConn.getOutputStream();
            out.write(b);
            out.close();
            /**
             * 默认编码为utf-8，而这里只能用GBK处理，所以特别的制定了GBK
             * 原代码未制定编码
             * 
             * 现在待编统一改成utf-8 modify by luozhen 2009-5-26
             */
            InputStreamReader isr = new InputStreamReader(httpConn.getInputStream(), ConstantsInfo.SENDENCODING);
            BufferedReader in = new BufferedReader(isr);

            String inputLine;
            while((inputLine = in.readLine()) != null) 
                strReturnXMLStringBuf.append(inputLine + "\n");
            in.close();
            httpConn.disconnect();
        }
        catch(Exception ex)
        {
        	LogUtil.log.error("出错:\n",ex);
            String str = "<?xml version=\"1.0\" encoding=\""+ConstantsInfo.SENDENCODING+"\"?>";
            str = str + "<Founder-TRS-DraftDoc Version=\"1.0\">";
            str = str + "<Error>true</Error>";
            str = str + "<ErrorMessage> \u53D1\u9001http\u8BF7\u6C42\u7684\u65F6\u5019\u53D1\u751F\u9519\u8BEF\uFF1A" + ex.toString() + "</ErrorMessage>";
            str = str + "</Founder-TRS-DraftDoc>";
            return str;
        }
        return strReturnXMLStringBuf.toString();
    }

    public void send(URL url, String strFileName)
    {
        try
        {
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection)connection;
            String strXML = getXMLString();
            byte b[] = strXML.getBytes();
            httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpConn.setRequestProperty("Content-Type", "text/xml;charset="+ConstantsInfo.SENDENCODING);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            OutputStream out = httpConn.getOutputStream();
            out.write(b);
            out.close();
            InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            File file = new File(strFileName);
            OutputStreamWriter fout = new FileWriter(file);
            String inputLine;
            while((inputLine = in.readLine()) != null) 
                fout.write(inputLine + "\n");
            in.close();
            fout.close();
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }

    public InputStream send1(URL url)
    {
        try
        {
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection)connection;
            String strXML = getXMLString();
            byte b[] = strXML.getBytes();
            httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpConn.setRequestProperty("Content-Type", "text/xml;charset="+ConstantsInfo.SENDENCODING);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            OutputStream out = httpConn.getOutputStream();
            out.write(b);
            out.close();
            return httpConn.getInputStream();
        }
        catch(Exception ex)
        {
            return null;
        }
    }

    String strUserName;
    String strPassword;
    String strOperateType;
    String strMethodName;
    String strParameter;
}