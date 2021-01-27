import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.joda.time.DateTime;

import java.io.*;
import java.util.HashMap;

public class AnalyseSina {
    private IClassifier classifier = new NaiveBayesClassifier(DemoTextClassification.trainOrLoadModel());
    private String filePath;
    private HashMap<DateTime,Integer> DateDic0;//信任
    private HashMap<DateTime,Integer> DateDic1;//冷静
    private HashMap<DateTime,Integer> DateDic2;//恐慌
    private HashMap<DateTime,Integer> DateDic3;//愤怒
    private HashMap<DateTime,Integer> DateDic4;//担忧
    private HashMap<DateTime,Integer> DateDic5;//鼓舞

    private DateTime startDate = new DateTime("2019-12-08");
    private DateTime endDate = new DateTime("2020-07-01");


    public AnalyseSina(String filePath) throws IOException {
        this.filePath=filePath;
        DateDic0=new HashMap<DateTime, Integer>();
        DateDic1=new HashMap<DateTime, Integer>();
        DateDic2=new HashMap<DateTime, Integer>();
        DateDic3=new HashMap<DateTime, Integer>();
        DateDic4=new HashMap<DateTime, Integer>();
        DateDic5=new HashMap<DateTime, Integer>();

        //字典初始化
        DateTime targetDate = new DateTime("2019-12-08");
        while ((targetDate.isEqual(startDate)||targetDate.isAfter(startDate))&&(targetDate.isEqual(endDate)||targetDate.isBefore(endDate))){
            DateDic0.put(targetDate,0);
            targetDate=targetDate.plusDays(1);
        }
        targetDate=startDate;
        while ((targetDate.isEqual(startDate)||targetDate.isAfter(startDate))&&(targetDate.isEqual(endDate)||targetDate.isBefore(endDate))){
            DateDic1.put(targetDate,0);
            targetDate=targetDate.plusDays(1);
        }
        targetDate=startDate;
        while ((targetDate.isEqual(startDate)||targetDate.isAfter(startDate))&&(targetDate.isEqual(endDate)||targetDate.isBefore(endDate))){
            DateDic2.put(targetDate,0);
            targetDate=targetDate.plusDays(1);
        }
        targetDate=startDate;
        while ((targetDate.isEqual(startDate)||targetDate.isAfter(startDate))&&(targetDate.isEqual(endDate)||targetDate.isBefore(endDate))){
            DateDic3.put(targetDate,0);
            targetDate=targetDate.plusDays(1);
        }
        targetDate=startDate;
        while ((targetDate.isEqual(startDate)||targetDate.isAfter(startDate))&&(targetDate.isEqual(endDate)||targetDate.isBefore(endDate))){
            DateDic4.put(targetDate,0);
            targetDate=targetDate.plusDays(1);
        }
        targetDate=startDate;
        while ((targetDate.isEqual(startDate)||targetDate.isAfter(startDate))&&(targetDate.isEqual(endDate)||targetDate.isBefore(endDate))){
            DateDic5.put(targetDate,0);
            targetDate=targetDate.plusDays(1);
        }
    }

    public void analyse() throws IOException, BiffException {
        File xlsFile = new File(this.filePath);
        Workbook workbook = Workbook.getWorkbook(xlsFile);
        // 获得所有工作表
        Sheet[] sheets = workbook.getSheets();
        if (sheets != null)
        {
            for (Sheet sheet : sheets)
            {
                // 获得行数
                int rows = sheet.getRows();
                // 读取数据
                for (int row = 1; row < rows; row++)
                {
                    if(sheet.getCell(2,row).getContents()==null){
                        continue;
                    }
                    String[] tmp=sheet.getCell(2,row).getContents().split(" ")[0].split("/");
                    if(tmp.length<3){
                        continue;
                    }
                    if(tmp[0]==null||tmp[1]==null||tmp[2]==null){
                        continue;
                    }
                    DateTime date =new DateTime(Integer.parseInt(tmp[0]),Integer.parseInt(tmp[1]),Integer.parseInt(tmp[2]),0,0,0);
                    if (!((date.isEqual(startDate)||date.isAfter(startDate))&&(date.isEqual(endDate)||date.isBefore(endDate)))){
                        continue;
                    }
                    String content = sheet.getCell(4,row).getContents();
                    if(content.contains("香港暴徒")||content.contains("内地隔离")||content.contains("反恐")||content.contains("肺炎球菌多糖")||content.contains("中办国办")){
                        continue;
                    }

                    //分析情绪（也可以考虑在这里记录一下词频或者逆词频）
                    String emo=classifier.classify(content);


                    if(emo.equals("信任")) {
                        this.DateDic0.put(date,this.DateDic0.get(date)+1);
                    }else if(emo.equals("冷静")){
                        this.DateDic1.put(date,this.DateDic1.get(date)+1);
                    }else if(emo.equals("恐慌")){
                        this.DateDic2.put(date,this.DateDic2.get(date)+1);
                    }else if(emo.equals("愤怒")){
                        this.DateDic3.put(date,this.DateDic3.get(date)+1);
                    }else if(emo.equals("担忧")){
                        this.DateDic4.put(date,this.DateDic4.get(date)+1);
                    }else if(emo.equals("鼓舞")){
                        this.DateDic5.put(date,this.DateDic5.get(date)+1);
                    }
                }
            }
        }
        workbook.close();
    }

    //形成txt文件(信任)
    public void saveData0() throws IOException {
        BufferedWriter bw = null;
        try {
            File file = new File("新浪新闻——信任.txt");
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            DateTime date = new DateTime("2019-12-08");
            while(date.isBefore(endDate)||date.isEqual(endDate)){
                String outValue =date.toString("yyyy-MM-dd")+" "+ DateDic0.get(date);
                bw.write(outValue);  // 将数据写入文件中
                bw.newLine();        // 新建一个换行符
                bw.flush();
                date=date.plusDays(1);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (null != bw) {
                bw.close();
            }
        }
    }

    public void saveData1() throws IOException {
        BufferedWriter bw = null;
        try {
            File file = new File("新浪新闻——冷静.txt");
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            DateTime date = new DateTime("2019-12-08");
            while(date.isBefore(endDate)||date.isEqual(endDate)){
                String outValue =date.toString("yyyy-MM-dd")+" "+ DateDic1.get(date);
                bw.write(outValue);  // 将数据写入文件中
                bw.newLine();        // 新建一个换行符
                bw.flush();
                date=date.plusDays(1);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (null != bw) {
                bw.close();
            }
        }
    }

    public void saveData2() throws IOException {
        BufferedWriter bw = null;
        try {
            File file = new File("新浪新闻——恐慌.txt");
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            DateTime date = new DateTime("2019-12-08");
            while(date.isBefore(endDate)||date.isEqual(endDate)){
                String outValue =date.toString("yyyy-MM-dd")+" "+ DateDic2.get(date);
                bw.write(outValue);  // 将数据写入文件中
                bw.newLine();        // 新建一个换行符
                bw.flush();
                date=date.plusDays(1);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (null != bw) {
                bw.close();
            }
        }
    }

    public void saveData3() throws IOException {
        BufferedWriter bw = null;
        try {
            File file = new File("新浪新闻——愤怒.txt");
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            DateTime date = new DateTime("2019-12-08");
            while(date.isBefore(endDate)||date.isEqual(endDate)){
                String outValue =date.toString("yyyy-MM-dd")+" "+ DateDic3.get(date);
                bw.write(outValue);  // 将数据写入文件中
                bw.newLine();        // 新建一个换行符
                bw.flush();
                date=date.plusDays(1);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (null != bw) {
                bw.close();
            }
        }
    }

    public void saveData4() throws IOException {
        BufferedWriter bw = null;
        try {
            File file = new File("新浪新闻——担忧.txt");
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            DateTime date = new DateTime("2019-12-08");
            while(date.isBefore(endDate)||date.isEqual(endDate)){
                String outValue =date.toString("yyyy-MM-dd")+" "+ DateDic4.get(date);
                bw.write(outValue);  // 将数据写入文件中
                bw.newLine();        // 新建一个换行符
                bw.flush();
                date=date.plusDays(1);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (null != bw) {
                bw.close();
            }
        }
    }

    public void saveData5() throws IOException {
        BufferedWriter bw = null;
        try {
            File file = new File("新浪新闻——鼓舞.txt");
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            DateTime date = new DateTime("2019-12-08");
            while(date.isBefore(endDate)||date.isEqual(endDate)){
                String outValue =date.toString("yyyy-MM-dd")+" "+ DateDic5.get(date);
                bw.write(outValue);  // 将数据写入文件中
                bw.newLine();        // 新建一个换行符
                bw.flush();
                date=date.plusDays(1);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (null != bw) {
                bw.close();
            }
        }
    }
}
