import com.vpiaotong.openapi.OpenApi;
import com.vpiaotong.openapi.util.HttpUtils;
import com.vpiaotong.openapi.util.JsonUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * @author CTX
 * @date 2020年1月6日
 * @desc
 */
public class Qrcode {

    //私钥(与发给票通的公钥为一对)
    private String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIVLAoolDaE7m5oMB1ZrILHkMXMF6qmC8I/FCejz4hwBcj59H3rbtcycBEmExOJTGwexFkNgRakhqM+3uP3VybWu1GBYNmqVzggWKKzThul9VPE3+OTMlxeG4H63RsCO1//J0MoUavXMMkL3txkZBO5EtTqek182eePOV8fC3ZxpAgMBAAECgYBp4Gg3BTGrZaa2mWFmspd41lK1E/kPBrRA7vltMfPj3P47RrYvp7/js/Xv0+d0AyFQXcjaYelTbCokPMJT1nJumb2A/Cqy3yGKX3Z6QibvByBlCKK29lZkw8WVRGFIzCIXhGKdqukXf8RyqfhInqHpZ9AoY2W60bbSP6EXj/rhNQJBAL76SmpQOrnCI8Xu75di0eXBN/bE9tKsf7AgMkpFRhaU8VLbvd27U9vRWqtu67RY3sOeRMh38JZBwAIS8tp5hgcCQQCyrOS6vfXIUxKoWyvGyMyhqoLsiAdnxBKHh8tMINo0ioCbU+jc2dgPDipL0ym5nhvg5fCXZC2rvkKUltLEqq4PAkAqBf9b932EpKCkjFgyUq9nRCYhaeP6JbUPN3Z5e1bZ3zpfBjV4ViE0zJOMB6NcEvYpy2jNR/8rwRoUGsFPq8//AkAklw18RJyJuqFugsUzPznQvad0IuNJV7jnsmJqo6ur6NUvef6NA7ugUalNv9+imINjChO8HRLRQfRGk6B0D/P3AkBt54UBMtFefOLXgUdilwLdCUSw4KpbuBPw+cyWlMjcXCkj4rHoeksekyBH1GrBJkLqDMRqtVQUubuFwSzBAtlc";

    //票通公钥(票通提供)
    private  String ptPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJkx3HelhEm/U7jOCor29oHsIjCMSTyKbX5rpoAY8KDIs9mmr5Y9r+jvNJH8pK3u5gNnvleT6rQgJQW1mk0zHuPO00vy62tSA53fkSjtM+n0oC1Fkm4DRFd5qJgoP7uFQHR5OEffMjy2qIuxChY4Au0kq+6RruEgIttb7wUxy8TwIDAQAB";

    //3DES秘钥(票通提供)
    private final static String password = "mj65Oe4U0cu63EOedl82W95g";

    //请更换请求平台简称(票通提供)
    private  final static String platform_alias = "DEMO";

    //请更换请求平台编码(票通提供)
    private  final static String platform_code = "YnZ1r980";

    /**
     * 随机发票流水号生成
     */
    public  String date(){
        Date date = new Date();
        SimpleDateFormat sdf =new SimpleDateFormat("YYYYMMddHHmmss");
        String str=platform_alias+sdf.format(date)+(int)(Math.random()*90+10);
        System.out.println(str);
        return str;
    }



    /**
     * TODO 多票种二维码开票
     *
     * @author 丛铁心
     */
    @org.junit.Test
    public void getInvoiceIssueQrcode() {
        String url = "http://fpkj.testnw.vpiaotong.cn/tp/openapi/getInvoiceIssueQrcode.pt";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("taxpayerNum", "50010201910210010");    //销方纳税人识别号
        map.put("enterpriseName", "电子收购成品油");		//销方企业名称
        map.put("qrcodeNo", date());					//开票二维码编号(唯一)
        map.put("shopNum",null);					//门店编号 非必填
        map.put("tradeNo", date());						//开票二维码编号(唯一)
        map.put("tradeTime", "2017-06-26 09:15:54");	//交易时间
        map.put("invoiceAmount", "9.90");				//发票金额(含税)
        map.put("sellerAddress", "某某某某某某某某某");		//销售方地址
        map.put("sellerTel", "13000000000");			//销售方电话
        map.put("sellerBankName", "北京支行");				//销售方银行名称
        map.put("sellerBankAccount", "123456789");		//销售方银行账户
        map.put("casherName", "收款人A");					//收款人姓名(校验规则: 中文/字母大小写/及其两者组合)
        map.put("reviewerName", "审核人A");				//审核人姓名(校验规则: 中文/字母大小写/及其两者组合)
        map.put("drawerName", "开票人A");					//开票人姓名(校验规则: 中文/字母大小写/及其两者组合)
        map.put("allowInvoiceCount", "1");				//允许开票张数(非必填  默认值:1)
        map.put("mobileRequiredFlag", true);			//手机号是否必填
        map.put("smsFlag", "false");					//是否发送短信 (非必填  默认值:false 测试环境不发送短信)
        map.put("emailRequiredFlag", "true");			//邮箱是否必填
        map.put("emailSendFlag", "true");				//是否发送邮箱
        map.put("expireTime", "2020-03-26 19:07:00");	//有效时间 (非必填  默认值:永久有效  填写格式 yyyy-MM-dd HH:mm:ss)
        map.put("email","767034475@qq.com");			//二维码发送邮箱地址(非必填)
        map.put("invoiceRemark","备注");
        //可选发票种类及分机列表信息
        List<Map<String, Object>> invoiceIssueOptions = new ArrayList<Map<String, Object>>();
        Map<String, Object> invoiceIssueOption = new HashMap<String, Object>();
        invoiceIssueOption.put("invoiceType", "01");
        invoiceIssueOption.put("extensionNum", "1");
        invoiceIssueOption.put("machineCode", null);
        invoiceIssueOptions.add(invoiceIssueOption);
        Map<String, Object> invoiceIssueOption2 = new HashMap<String, Object>();
        invoiceIssueOption2.put("invoiceType", "10");
        invoiceIssueOption2.put("extensionNum", "0");
        invoiceIssueOption2.put("machineCode", null);
        invoiceIssueOptions.add(invoiceIssueOption2);
        map.put("invoiceIssueOptions",invoiceIssueOptions);
        //其他参数见接口文档
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> listMapOne = new HashMap<String, Object>();
        listMapOne.put("itemName", "*餐饮服务费*餐费");  				//开票项目名
        listMapOne.put("taxRateValue", "0");			//税率
        listMapOne.put("taxClassificationCode", "3070401000000000000");//税收分类编码
        listMapOne.put("quantity", "1");				//数量
        listMapOne.put("unitPrice", "10");				//单价
        listMapOne.put("invoiceItemAmount", "10");		//金额
        listMapOne.put("invoiceItemDisAmount", "-0.1");
        listMapOne.put("zeroTaxFlag", "1");
        list.add(listMapOne);
        map.put("itemList", list);

        String content = JsonUtil.toJson(map);
        //OpenApi参数内容(3des秘钥(票通提供),平台编码(票通提供),平台前缀(票通提供),私钥)
        String buildRequest = new OpenApi(password, platform_code, platform_alias, privateKey).buildRequest(content);
        System.out.println(buildRequest);
        String response = HttpUtils.postJson(url, buildRequest);
        System.out.println(response+"123");
        System.out
                .println(new OpenApi(password, platform_code, platform_alias, privateKey).disposeResponse(response, ptPublicKey));

    }
    /**
     *
     *  @title: batchDelInvoiceQrcode
     * @description: 作废二维码接口
     */
    @org.junit.Test
    public void batchDelInvoiceQrcode(){
        String url = "http://fpkj.testnw.vpiaotong.cn/tp/openapi/batchDelInvoiceQrcode.pt";
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("taxpayerNum", "50010201910210010");    //销方纳税人识别号
        map.put("qrcodeNo", "DEMO2020010610003239");//与开票二维码的订单号一致
        map.put("invoiceAmount", "9.9");//金额一致
        list.add(map);
        String content = JsonUtil.toJson(list);
        //OpenApi参数内容(3des秘钥(票通提供),平台编码(票通提供),平台前缀(票通提供),私钥)
        String buildRequest = new OpenApi(password, platform_code, platform_alias, privateKey).buildRequest(content);
        System.out.println(buildRequest);
        String response = HttpUtils.postJson(url, buildRequest);
        System.out.println(response);
        System.out
                .println(new OpenApi(password, platform_code, platform_alias, privateKey).disposeResponse(response, ptPublicKey));
    }

    /**
     *
     *  @title: queryQrcodeInvoiceInfo
     * @description: 查询二维码接口
     */
    @org.junit.Test
    public void queryQrcodeInvoiceInfo(){
        String url = "http://fpkj.testnw.vpiaotong.cn/tp/openapi/queryQrcodeInvoiceInfo.pt";
        Map<String, String> map = new HashMap<String, String>();
        map.put("taxpayerNum", "50010201910210010");    //销方纳税人识别号
        map.put("qrcodeNo", "DEMO2020010610003239");//与开票二维码的订单号一致
        map.put("invoiceAmount", "9.90");//金额一致
        String content = JsonUtil.toJson(map);
        //OpenApi参数内容(3des秘钥(票通提供),平台编码(票通提供),平台前缀(票通提供),私钥)
        String buildRequest = new OpenApi(password, platform_code, platform_alias, privateKey).buildRequest(content);
        System.out.println(buildRequest);
        String response = HttpUtils.postJson(url, buildRequest);
        System.out.println(response);
        System.out
                .println(new OpenApi(password, platform_code, platform_alias, privateKey).disposeResponse(response, ptPublicKey));
    }

}
