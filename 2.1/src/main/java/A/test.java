package A;

import cn.com.webxml.*;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.List;

public class test {
    public static void main(String[] args) throws RemoteException, UnsupportedEncodingException {
        WeatherWebService ser = new WeatherWebService();
      /*  WeatherWebServiceSoap service = ser.getPort(WeatherWebServiceSoap.class);
        invokeGetSupportProvince(service);
        System.out.println();
        System.out.println("..........................................");
        invokeGetSupportCity(service);
        System.out.println("...........................................");
        invokeGetWeatherByOneCity(service);*/

        IpAddressSearchWebService ipAddressSearchWebService=new IpAddressSearchWebService();
        IpAddressSearchWebServiceSoap soap=ipAddressSearchWebService.getPort(IpAddressSearchWebServiceSoap.class);
        getip(soap);

    }
    public  static  void getip(IpAddressSearchWebServiceSoap ipAddressSearchWebServicesoap) throws UnsupportedEncodingException {

        ArrayOfString arrayOfString=ipAddressSearchWebServicesoap.getCountryCityByIp("192.168.50.133");

        List<String> list = arrayOfString.getString();
        System.out.println("总共有"+list.size()+"个地区".getBytes("UTF-8"));
        int count=0;
        for (String pro : list) {
            if(0!=count&&count%5==0){
                System.out.println();
            }
            System.out.print(pro+"\t");
            count++;
        }
    }

    // 调用获取支持的省份、州接口
    public static void invokeGetSupportProvince(WeatherWebServiceSoap service)throws RemoteException{
        ArrayOfString provinces = service.getSupportProvince();
        List<String> list = provinces.getString();
        System.out.println("总共有"+list.size()+"个地区");
        int count=0;
        for (String pro : list) {
            if(0!=count&&count%5==0){
                System.out.println();
            }
            System.out.print(pro+"\t");
            count++;
        }
    }
    //调某个省城市的接口
    public static void invokeGetSupportCity(WeatherWebServiceSoap service)throws RemoteException{
        String provinceName="吉林";
        ArrayOfString city = service.getSupportCity(provinceName);
        List<String> list = city.getString();
        System.out.println(provinceName+"省有"+list.size()+"个市：");
        for (String s : list) {
            System.out.println(s+"\t");
        }
    }
    //调用某个城市的天气
    public static void invokeGetWeatherByOneCity(WeatherWebServiceSoap service)throws RemoteException {
        String cityName = "大安";
        ArrayOfString weather = service.getWeatherbyCityName(cityName);
        List<String> list = weather.getString();
        for (String we : list) {
            System.out.println(we);
        }
    }
    }
