/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testscrape;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 *
 * @author AY
 */
public class TestScrape {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String baseUrl = "";
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try{
            HtmlPage page = client.getPage(baseUrl);
            List<HtmlElement> itemList = page.getByXPath("//div[@class='fileText']/a");
            URL[] urls = new URL[itemList.size()];
            if(itemList.isEmpty()){
                System.out.println("No items found.");
            }else{
                for(int i = 0; i<itemList.size();i++){
                    urls[i] = new URL("https:" + itemList.get(i).getAttribute("href"));
                }
            }
            saveImages(urls, "C:\\Users\\AY\\Desktop\\testImages\\");
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public static void saveImages(URL[] urls, String folderLocation) throws IOException{
        FileOutputStream fos;
        BufferedInputStream bis;
        for(URL testUrl : urls){
            int beginIndex = testUrl.toString().lastIndexOf("/");
            String testFileName = testUrl.toString().substring(beginIndex);
            bis = new BufferedInputStream(testUrl.openStream());
            fos = new FileOutputStream(folderLocation + testFileName);
            byte[] buffer = new byte[2048];
            int count=0;
            while((count = bis.read(buffer,0,2048)) != -1){
                fos.write(buffer, 0, count);
            }
            System.out.println(testFileName + " downloaded.");
            fos.close();
            bis.close();
        }
    }

}
