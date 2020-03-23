
package cn.com.webxml;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "WeatherWebServiceSoap", targetNamespace = "http://WebXml.com.cn/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface WeatherWebServiceSoap {


    /**
     * <br /><h3>查询本天气预报Web Services支持的国内外城市或地区信息</h3><p>输入参数：byProvinceName = 指定的洲或国内的省份，若为ALL或空则表示返回全部城市；返回数据：一个一维字符串数组 String()，结构为：城市名称(城市代码)。</p><br />
     * 
     * @param byProvinceName
     * @return
     *     returns cn.com.webxml.ArrayOfString
     */
    @WebMethod(action = "http://WebXml.com.cn/getSupportCity")
    @WebResult(name = "getSupportCityResult", targetNamespace = "http://WebXml.com.cn/")
    @RequestWrapper(localName = "getSupportCity", targetNamespace = "http://WebXml.com.cn/", className = "cn.com.webxml.GetSupportCity")
    @ResponseWrapper(localName = "getSupportCityResponse", targetNamespace = "http://WebXml.com.cn/", className = "cn.com.webxml.GetSupportCityResponse")
    public ArrayOfString getSupportCity(
        @WebParam(name = "byProvinceName", targetNamespace = "http://WebXml.com.cn/")
        String byProvinceName);

    /**
     * <br /><h3>获得本天气预报Web Services支持的洲、国内外省份和城市信息</h3><p>输入参数：无； 返回数据：一个一维字符串数组 String()，内容为洲或国内省份的名称。</p><br />
     * 
     * @return
     *     returns cn.com.webxml.ArrayOfString
     */
    @WebMethod(action = "http://WebXml.com.cn/getSupportProvince")
    @WebResult(name = "getSupportProvinceResult", targetNamespace = "http://WebXml.com.cn/")
    @RequestWrapper(localName = "getSupportProvince", targetNamespace = "http://WebXml.com.cn/", className = "cn.com.webxml.GetSupportProvince")
    @ResponseWrapper(localName = "getSupportProvinceResponse", targetNamespace = "http://WebXml.com.cn/", className = "cn.com.webxml.GetSupportProvinceResponse")
    public ArrayOfString getSupportProvince();

    /**
     * <br><h3>获得本天气预报Web Services支持的洲、国内外省份和城市信息</h3><p>输入参数：无；返回：DataSet 。DataSet.Tables(0) 为支持的洲和国内省份数据，DataSet.Tables(1) 为支持的国内外城市或地区数据。DataSet.Tables(0).Rows(i).Item("ID") 主键对应 DataSet.Tables(1).Rows(i).Item("ZoneID") 外键。<br />Tables(0)：ID = ID主键，Zone = 支持的洲、省份；Tables(1)：ID 主键，ZoneID = 对应Tables(0)ID的外键，Area = 城市或地区，AreaCode = 城市或地区代码。</p><br />
     * 
     * @return
     *     returns cn.com.webxml.GetSupportDataSetResponse.GetSupportDataSetResult
     */
    @WebMethod(action = "http://WebXml.com.cn/getSupportDataSet")
    @WebResult(name = "getSupportDataSetResult", targetNamespace = "http://WebXml.com.cn/")
    @RequestWrapper(localName = "getSupportDataSet", targetNamespace = "http://WebXml.com.cn/", className = "cn.com.webxml.GetSupportDataSet")
    @ResponseWrapper(localName = "getSupportDataSetResponse", targetNamespace = "http://WebXml.com.cn/", className = "cn.com.webxml.GetSupportDataSetResponse")
    public cn.com.webxml.GetSupportDataSetResponse.GetSupportDataSetResult getSupportDataSet();

    /**
     * <br><h3>根据城市或地区名称查询获得未来三天内天气情况、现在的天气实况、天气和生活指数</h3><p>调用方法如下：输入参数：theCityName = 城市中文名称(国外城市可用英文)或城市代码(不输入默认为上海市)，如：上海 或 58367，如有城市名称重复请使用城市代码查询(可通过 getSupportCity 或 getSupportDataSet 获得)；返回数据： 一个一维数组 String(22)，共有23个元素。<br />String(0) 到 String(4)：省份，城市，城市代码，城市图片名称，最后更新时间。String(5) 到 String(11)：当天的 气温，概况，风向和风力，天气趋势开始图片名称(以下称：图标一)，天气趋势结束图片名称(以下称：图标二)，现在的天气实况，天气和生活指数。String(12) 到 String(16)：第二天的 气温，概况，风向和风力，图标一，图标二。String(17) 到 String(21)：第三天的 气温，概况，风向和风力，图标一，图标二。String(22) 被查询的城市或地区的介绍 <br /><a href="http://www.webxml.com.cn/images/weather.zip">下载天气图标<img src="http://www.webxml.com.cn/images/download_w.gif" border="0" align="absbottom" /></a>(包含大、中、小尺寸) <a href="http://www.webxml.com.cn/zh_cn/weather_icon.aspx" target="_blank">天气图例说明</a> <a href="http://www.webxml.com.cn/files/weather_eg.zip">调用此天气预报Web Services实例下载</a> (VB ASP.net 2.0)</p><br />
     * 
     * @param theCityName
     * @return
     *     returns cn.com.webxml.ArrayOfString
     */
    @WebMethod(action = "http://WebXml.com.cn/getWeatherbyCityName")
    @WebResult(name = "getWeatherbyCityNameResult", targetNamespace = "http://WebXml.com.cn/")
    @RequestWrapper(localName = "getWeatherbyCityName", targetNamespace = "http://WebXml.com.cn/", className = "cn.com.webxml.GetWeatherbyCityName")
    @ResponseWrapper(localName = "getWeatherbyCityNameResponse", targetNamespace = "http://WebXml.com.cn/", className = "cn.com.webxml.GetWeatherbyCityNameResponse")
    public ArrayOfString getWeatherbyCityName(
        @WebParam(name = "theCityName", targetNamespace = "http://WebXml.com.cn/")
        String theCityName);

    /**
     * <br><h3>根据城市或地区名称查询获得未来三天内天气情况、现在的天气实况、天气和生活指数（For商业用户）</h3><p>调用方法同 getWeatherbyCityName，输入参数：theUserID = 商业用户ID</p><br />
     * 
     * @param theCityName
     * @param theUserID
     * @return
     *     returns cn.com.webxml.ArrayOfString
     */
    @WebMethod(action = "http://WebXml.com.cn/getWeatherbyCityNamePro")
    @WebResult(name = "getWeatherbyCityNameProResult", targetNamespace = "http://WebXml.com.cn/")
    @RequestWrapper(localName = "getWeatherbyCityNamePro", targetNamespace = "http://WebXml.com.cn/", className = "cn.com.webxml.GetWeatherbyCityNamePro")
    @ResponseWrapper(localName = "getWeatherbyCityNameProResponse", targetNamespace = "http://WebXml.com.cn/", className = "cn.com.webxml.GetWeatherbyCityNameProResponse")
    public ArrayOfString getWeatherbyCityNamePro(
        @WebParam(name = "theCityName", targetNamespace = "http://WebXml.com.cn/")
        String theCityName,
        @WebParam(name = "theUserID", targetNamespace = "http://WebXml.com.cn/")
        String theUserID);

}
