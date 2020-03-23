package com.hesicare.common.jobs;

import com.hesicare.common.utils.HospitalEnum;
import com.hesicare.common.utils.wonders.InterfaceEnCode;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.*;
@Component
public class JobUtils {
    public HospitalEnum info(String comcode){
        HospitalEnum hospitalEnum=null;
		switch (comcode) {
			case "349":
				hospitalEnum=HospitalEnum.yp_yh;
			case "350":
                hospitalEnum=HospitalEnum.yp_yh;
			case "351":
                hospitalEnum=HospitalEnum.yp_yh;
			case "352":
                hospitalEnum=HospitalEnum.yp_yh;
			case "353":
                hospitalEnum=HospitalEnum.yp_yh;
				break;
			default:
				break;
		}
        return  hospitalEnum;
    }
    public String sendHttpPost(String url, String body) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String header = InterfaceEnCode.getInterfaceKey();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.setHeader("access-token", header);
        httpPost.setEntity(new StringEntity(body, "UTF-8"));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        response.close();
        httpClient.close();
        return responseContent;
    }
    public void  printlog(String path,String context) throws IOException {
        File file = new File(path);
        //如果文件不存在，则自动生成文件;
        File parent=file.getParentFile();
        if (!parent.exists()){
            parent.mkdirs();}
        if (!file.exists()){
            file.createNewFile();}
        //引入输出流
        OutputStream outputStream =new FileOutputStream(path,true);;
        try{
            //将可变字符串变为固定长度的字符串，方便下面的转码；
            byte[]  bytes = context.getBytes("UTF-8");//因为中文可能会乱码，这里使用了转码，转成UTF-8；
            outputStream.write(bytes);//开始写入内容到文件；
            outputStream.write("\r\n".getBytes());
            outputStream.close();//一定要关闭输出流；
        }catch(Exception e){
            e.printStackTrace();//获取异常
        }
    }
}
