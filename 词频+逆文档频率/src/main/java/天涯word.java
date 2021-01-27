import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class 天涯word {
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
            if (!map.containsKey(word)){// 判断该词是否在停用词字典内
                String s1 = word.replaceAll("[^\\u4e00-\\u9fa5]", "");
                if (s1 != "") {
                    String temp = " " + s1;
                    newString += temp;
                    x++;
                }

            }
        }
        System.out.println(x);
        return newString;
    }

    public static void saveAsFileWriter(String content, String path) throws IOException{
        FileWriter fileWriter = null;
        String[] words = content.split(" ");
        try {
            fileWriter = new FileWriter(path);//创建文本文件
            int i=0;
            while(i < words.length){//循环写入
                fileWriter.write(words[i] + "\r\n");//写入 \r\n换行
                i++;
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        String re = null;
        for(int t = 0;t < 27; t++){
            String content = doWord("C://Users//CWH2019//Desktop//天涯第二阶段.xls", t, 1);
            re += content;
        }
        saveAsFileWriter(re, "C://Users//CWH2019//Desktop//天涯第二阶段词.txt");

    }

}
