package com.newland.ignite.datasource.utils.boss;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Properties;

/**
 * Created by xz on 2020/3/31.
 */
public class CNLDBConnectMgr {
    private static DealXMLMgr dealXMLMgr;
    // 数据库连接池集合
    private static String appCheckCode;
    private static String appName;
    private static String serverUrl;

    //TT数据库

    /**
     * 初始化工具，加载配置文件
     * @return
     *
     * @return
     */
    public static void init(String dbAuthPath) throws Exception
    {
        Properties prop = new Properties();
        InputStream in = new BufferedInputStream(new FileInputStream(dbAuthPath));
        prop.load(new InputStreamReader(in, "UTF-8"));
        prop.load(in);     ///加载属性列表
        in.close();

        appCheckCode = prop.getProperty("appCheckCode");
        appName = prop.getProperty("appName");
        serverUrl = prop.getProperty("url");
    }


    public static String getPasswd(int nDbType, String tns, String sUser,String passwd)
    {
        dealXMLMgr = new DealXMLMgr();
        //初始化REQ
        ReqInfoBean reqInfoBean = setReq(nDbType , tns , sUser , passwd);
        reqInfoBean.setCfgFileFlag("1");//表示配置文件存在
        System.out.println("------------reqInfoBean"+reqInfoBean);
        try {
            String reqStr = dealXMLMgr.encodeXML(reqInfoBean);
            UigClient uigClient = new UigClient(reqInfoBean.getUrl());
            //发送报文
            String resMsg = uigClient.requestService(reqStr);
            //解析返回报文
            ResInfoBean resInfoBean = dealXMLMgr.decodeXML(XMLUtil.fromXML(resMsg , "UTF-8"));

            if(resInfoBean.getResultCode() == 1)
            {
                String randomCode = resInfoBean.getRandomCode();
                String desKey = randomCode + reqInfoBean.getAppCode();
                String dbPassword = resInfoBean.getDbUserpwd();
                String decodePassword = DecodeUtil.decrypt(dbPassword , desKey);

                return decodePassword;
            }
            else
            {

                throw new DecodeException("获取密码失败！！，错误信息：：" + resInfoBean.getErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //初始化REQ
    private static ReqInfoBean setReq(int nDbType, String tns, String sUser, String passwd) {
        ReqInfoBean reqInfoBean=new ReqInfoBean();
        reqInfoBean.setDbType(nDbType);
        reqInfoBean.setDbTns(tns);
        reqInfoBean.setDbUser(sUser);
        reqInfoBean.setDbUserpwd(passwd);
        reqInfoBean.setUrl(serverUrl);
        reqInfoBean.setHostApp(appName);
        reqInfoBean.setAppCode(appCheckCode);
        //获取部署主机IP+主机名
        InetAddress netAddress = HostUtil.getInetAddress();
        //IP
        String hostIp = HostUtil.getHostIp(netAddress);
        reqInfoBean.setHostIp(hostIp);
        //主机名
        String hostName = HostUtil.getHostName(netAddress);
        reqInfoBean.setHostName(hostName);
        return reqInfoBean;
    }
}
