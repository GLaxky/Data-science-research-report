import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.joda.time.DateTime;

import java.io.*;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class AnalyseZhiHi {
    private IClassifier classifier = new NaiveBayesClassifier(DemoTextClassification.trainOrLoadModel());

    private HashMap<String ,Integer> emoDic1;
    private HashMap<String ,Integer> emoDic2;
    private HashMap<String ,Integer> emoDic3;
    private HashMap<String ,Integer> emoDic4;
    private HashMap<Integer,Integer> keyAnswer;
    private String filePath;
    private float[][] top10=new float[4][6];

    public AnalyseZhiHi(String filePath) throws IOException {
        for(int i=0;i<4;i++){
            for(int j=0;j<6;j++){
                top10[i][j]=0;
            }
        }
        this.filePath=filePath;
        this.keyAnswer=new HashMap<Integer, Integer>();
        emoDic1=new HashMap<String, Integer>();
        emoDic1.put("信任",0);
        emoDic1.put("冷静",0);
        emoDic1.put("恐慌",0);
        emoDic1.put("愤怒",0);
        emoDic1.put("担忧",0);
        emoDic1.put("鼓舞",0);
        emoDic2=new HashMap<String, Integer>();
        emoDic2.put("信任",0);
        emoDic2.put("冷静",0);
        emoDic2.put("恐慌",0);
        emoDic2.put("愤怒",0);
        emoDic2.put("担忧",0);
        emoDic2.put("鼓舞",0);
        emoDic3=new HashMap<String, Integer>();
        emoDic3.put("信任",0);
        emoDic3.put("冷静",0);
        emoDic3.put("恐慌",0);
        emoDic3.put("愤怒",0);
        emoDic3.put("担忧",0);
        emoDic3.put("鼓舞",0);
        emoDic4=new HashMap<String, Integer>();
        emoDic4.put("信任",0);
        emoDic4.put("冷静",0);
        emoDic4.put("恐慌",0);
        emoDic4.put("愤怒",0);
        emoDic4.put("担忧",0);
        emoDic4.put("鼓舞",0);
    }

    public void analyseTop10() throws IOException, BiffException {
        File xlsFile = new File(this.filePath);
        Workbook workbook = Workbook.getWorkbook(xlsFile);
        // 获得所有工作表
        Sheet[] sheets = workbook.getSheets();
        if (sheets != null)
        {
            for (int i=0;i<4;i++)
            {
                Sheet sheet=sheets[i];

                // 读取数据
                for (int row = 1; row < 11; row++){
                    if(sheet.getCell(1,row).getContents().equals("")){
                        continue;
                    }
                    String content = sheet.getCell(4,row).getContents();
//                    Map emotion = classifier.predict(content);
                    Map<String, Double> emo = classifier.predict(content);
                    this.top10[i][0]+=emo.get("信任");
                    this.top10[i][1]+=emo.get("冷静");
                    this.top10[i][2]+=emo.get("恐慌");
                    this.top10[i][3]+=emo.get("愤怒");
                    this.top10[i][4]+=emo.get("担忧");
                    this.top10[i][5]+=emo.get("鼓舞");
                }
            }
        }
        workbook.close();
    }

    public void analyse() throws IOException, BiffException {
        File xlsFile = new File(this.filePath);
        Workbook workbook = Workbook.getWorkbook(xlsFile);
        // 获得所有工作表
        Sheet[] sheets = workbook.getSheets();
        if (sheets != null)
        {
            for (int i=0;i<sheets.length;i++)
            {
                Sheet sheet=sheets[i];
                HashMap<String,Integer> tmp;
                if(i==0){
                    tmp=this.emoDic1;
                }else if(i==1){
                    tmp=this.emoDic2;
                }else if(i==2){
                    tmp=this.emoDic3;
                }else {
                    tmp=this.emoDic4;
                }
                // 获得行数
                int rows = sheet.getRows();
                // 读取数据
                for (int row = 1; row < rows; row++){
                    if(sheet.getCell(1,row).getContents().equals("")){
                        continue;
                    }
                    int likes = Integer.parseInt(sheet.getCell(1,row).getContents().trim());
                    if(likes>=500){
                        keyAnswer.put(i,row);
                    }
                    String content = sheet.getCell(4,row).getContents();
//                    Map emotion = classifier.predict(content);
                    String emo = classifier.classify(content);
                    tmp.put(emo,tmp.get(emo)+1);
                }
            }
        }
        workbook.close();
    }

    public void saveTop10() throws IOException {
        BufferedWriter bw = null;
        try {
            File file = new File("知乎——4个问题回答top10.txt");
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            String outValue;
            for(int i=0;i<4;i++) {
                outValue=top10[i][0]+" "+top10[i][1]+" "+top10[i][2]+" "+top10[i][3]+" "+top10[i][4]+" "+top10[i][5];
                bw.write(outValue);  // 将数据写入文件中
                bw.newLine();        // 新建一个换行符
                bw.flush();
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (null != bw) {
                bw.close();
            }
        }
    }

    public void saveDic1() throws IOException {
        BufferedWriter bw = null;
        try {
            File file = new File("知乎——此次新冠肺炎疫情让你明白了什么道理.txt");
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            String outValue="信任 "+emoDic1.get("信任");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="冷静 "+emoDic1.get("冷静");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="恐慌 "+emoDic1.get("恐慌");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="愤怒 "+emoDic1.get("愤怒");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="担忧 "+emoDic1.get("担忧");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="鼓舞 "+emoDic1.get("鼓舞");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (null != bw) {
                bw.close();
            }
        }
    }

    public void saveDic2() throws IOException {
        BufferedWriter bw = null;
        try {
            File file = new File("知乎——新冠肺炎给中国带来的积极意义是什么 .txt");
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            String outValue="信任 "+emoDic2.get("信任");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="冷静 "+emoDic2.get("冷静");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="恐慌 "+emoDic2.get("恐慌");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="愤怒 "+emoDic2.get("愤怒");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="担忧 "+emoDic2.get("担忧");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="鼓舞 "+emoDic2.get("鼓舞");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (null != bw) {
                bw.close();
            }
        }
    }

    public void saveDic3() throws IOException {
        BufferedWriter bw = null;
        try {
            File file = new File("知乎———新冠肺炎，如果不隔离，人类最坏的结果将会怎样.txt");
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            String outValue="信任 "+emoDic3.get("信任");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="冷静 "+emoDic3.get("冷静");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="恐慌 "+emoDic3.get("恐慌");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="愤怒 "+emoDic3.get("愤怒");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="担忧 "+emoDic3.get("担忧");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="鼓舞 "+emoDic3.get("鼓舞");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (null != bw) {
                bw.close();
            }
        }
    }

    public void saveDic4() throws IOException {
        BufferedWriter bw = null;
        try {
            File file = new File("知乎——感染新冠肺炎是一种怎样的体验.txt");
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            String outValue="信任 "+emoDic3.get("信任");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="冷静 "+emoDic4.get("冷静");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="恐慌 "+emoDic4.get("恐慌");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="愤怒 "+emoDic4.get("愤怒");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="担忧 "+emoDic4.get("担忧");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
            outValue="鼓舞 "+emoDic4.get("鼓舞");
            bw.write(outValue);  // 将数据写入文件中
            bw.newLine();        // 新建一个换行符
            bw.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (null != bw) {
                bw.close();
            }
        }
    }
}
