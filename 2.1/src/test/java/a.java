import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class a {
    public static void main(String[] args) throws IOException {

        printlog("E:/Hesicare/java项目/logs/bp.txt","qwe");
    }
    public static void  printlog(String path,String context) throws IOException {
        File file = new File(path);
        //如果文件不存在，则自动生成文件;
        File parent=file.getParentFile();
        if (!parent.exists()){
            parent.mkdirs();}
        if (!file.exists()){
            file.createNewFile();}
        //引入输出流
        OutputStream outPutStream =new FileOutputStream(path,true);
        try{
            byte[]  bytes = context.getBytes("UTF-8");//因为中文可能会乱码，这里使用了转码，转成UTF-8；
            outPutStream.write(bytes);//开始写入内容到文件；
            outPutStream.write("\r\n".getBytes());
            outPutStream.flush();
            outPutStream.close();//一定要关闭输出流；
        }catch(Exception e){
            e.printStackTrace();//获取异常
        }
    }
}
