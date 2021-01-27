import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import jxl.*;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class 天涯IDF {
    public static Map<String, Integer> map; //停用词map表
    public static String stop_word_path = "C://Users//CWH2019//Desktop//my_stop.txt";
    /**
     * 获取停用词的map形式
     * @return
     * @throws IOException
     */
    public static Map<String, Integer> getMap() throws IOException {
        Map<String, Integer> Dic = new HashMap<String, Integer>();
        // 加载字典
        InputStreamReader isr = new InputStreamReader(new FileInputStream(stop_word_path), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        while ((line = br.readLine()) != null) {
            Dic.put(line.trim(), 1);
        }
        // 关闭文件
        br.close();
        isr.close();

        return Dic;
    }

    public static String doWord(String path, int t, int s) throws IOException {
        int i;
        Sheet sheet;
        Workbook book;
        Cell cell1;
        ArrayList<String> sum = new ArrayList();
        try {
            book = Workbook.getWorkbook(new File(path));
            //获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
            sheet = book.getSheet(t);
            //获取左上角的单元格
            //cell1 = sheet.getCell(4, 0);
            i = 1;
            while (true) {
                //获取每一行的单元格
                cell1 = sheet.getCell(s, i);//（列，行）
                if ("".equals(cell1.getContents()) == true)    //如果读取的数据为空
                    break;
                String content = cell1.getContents();
                List<Term> termList = StandardTokenizer.segment(content);
                for(int j = 0; j < termList.size(); j++){
                    sum.add(termList.get(j).toString());
                }
                i++;
            }
            book.close();
        } catch (Exception e) {
        }
        map = getMap();
        String newString = "";
        int x = 0;
        for (int n = 0; n < sum.size(); n++) {
            String word = sum.get(n); // 拿到词
            if (!map.containsKey(word)){ // 判断该词是否在停用词字典内
                String temp = " " + word;
                newString += temp;
                x++;
            }
        }
        return newString;
    }

    public static int deter(String word, String wordinput) throws IOException{
        String[] words = wordinput.split(" ");
        List<String> list=Arrays.asList(words);
        int n = 0;
        if(list.contains(word)){
            n = 1;
        }
        return n;
    }

    public static void doIDF(String word, String path, int t) throws IOException{
        int i = 0;
        int m = 0;
        while(i < t){
            String wordinput = doWord(path, i, 1);
            int n = deter(word, wordinput);
            m = m +n;
            i++;
        }
        if(m == 0){
            System.out.println("无此词");
        }else {
            DecimalFormat df = new DecimalFormat("0.00000");//设置保留位数
            String re = df.format((float) t/ m);
            double num1 = Math.log(Double.valueOf(re.toString()));
            double num2 = Math.log(10);
            String ret = df.format((float) num1 / num2);
            System.out.println(ret);
            System.out.println(m);
        }
    }

    public static String wordProperty(String word) throws IOException{
        List<Term> termList = StandardTokenizer.segment(word);
        String result = termList.get(0).toString();
        return result;
    }

    public static void main(String[] args) throws IOException{
        String word = wordProperty("家");
        doIDF(word, "C://Users//CWH2019//Desktop//天涯.xls", 27);
    }
}
