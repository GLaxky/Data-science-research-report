import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class TF {
    public static Map<String, Integer> map; //停用词map表
    public static String stop_word_path = "D:/HanLP1/src/main/resources/my_stop.txt";
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
            i = 0;
            while (true) {
                //获取每一行的单元格
                cell1 = sheet.getCell(s, i);//（列，行）
                if ("".equals(cell1.getContents()))    //如果读取的数据为空
                    break;
                String content = cell1.getContents();
                List<Term> termList = StandardTokenizer.segment(content);
                for (Term term : termList) {
                    sum.add(term.toString());
                }
                i++;
            }
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        map = getMap();
        String newString = "";
        // 拿到词
        for (String word : sum) {
            if (!map.containsKey(word)) { // 判断该词是否在停用词字典内
                String temp = " " + word;
                newString += temp;
            }
        }
        return newString;
    }


    public static void doFreq(String word, String wordinput) throws IOException{
        String[] words = wordinput.split(" ");
        HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
        for (String s : words) {
            if (!wordMap.containsKey(s)) {
                wordMap.put(s, 1);
            } else {
                int val = wordMap.get(s);
                wordMap.put(s, val + 1);
            }
        }
        System.out.print("词数:");
        System.out.println(words.length-1);

        List<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(
                wordMap.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });
        for (Map.Entry<String, Integer> ent : infoIds) {
            DecimalFormat df = new DecimalFormat("0.00000");//设置保留位数
            String freq = df.format((float) (ent.getValue()) / (words.length - 1));
            if (ent.getKey().equals(word)) {
                System.out.println(ent.getKey() + "=" + ent.getValue() + "+" + freq);
            }
        }
    }

    public static String wordProperty(String word) throws IOException{
        List<Term> termList = StandardTokenizer.segment(word);
        return termList.get(0).toString();
    }

//    public static void main(String[] args) throws IOException{
////        String content = doWord("D:/request/天涯重点帖子.xls", 0, 3);
////        String word = wordProperty("家");
////        doFreq(word, content);
//        doFreq("过度","过度的恐慌对治疗预防疾病没有半点作用，反而很容易影响睡眠质量，大幅度降低免疫能力，这点不管中医西医都有相关研究。");
//    }
}
