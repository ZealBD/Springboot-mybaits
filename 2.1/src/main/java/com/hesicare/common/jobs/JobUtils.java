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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class JobUtils {
    public HospitalEnum info(String comcode){
        HospitalEnum hospitalEnum=null;
		/*switch (comcode) {
			case "349":
				break;
			case "350":
				break;
			case "351":
				break;
			case "352":
				break;
			case "353":
				break;
			default:
				break;
		}*/
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
    public void printwrites(String filename,String content){
        //System.out.println(filename);
        FileWriter fileWriter=null;
        try {
            File f=new File(filename);
            File parent=f.getParentFile();
            if (!parent.exists()){
                parent.mkdirs();}
            if (!f.exists()){
                f.createNewFile();}
            fileWriter=new FileWriter(f,true);
            PrintWriter printWriter=new PrintWriter(fileWriter);
            printWriter.println(content);
            printWriter.flush();
            fileWriter.flush();
            printWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
