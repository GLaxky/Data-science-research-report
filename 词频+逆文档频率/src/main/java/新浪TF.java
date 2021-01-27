import java.io.File;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import jxl.*;
import java.text.DecimalFormat;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class 新浪TF {
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
        for (int n = 0; n < sum.size(); n++) {
            String word = sum.get(n); // 拿到词
            if (!map.containsKey(word)){ // 判断该词是否在停用词字典内
                String temp = " " + word;
                newString += temp;
            }
        }
        return newString;
    }


    public static void doFreq(String word, String wordinput) throws IOException{
        String[] words = wordinput.split(" ");
        HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
        for (int i = 0; i < words.length; i++) {
            if (!wordMap.containsKey(words[i])) {
                wordMap.put(words[i], 1);
            } else {
                int val = wordMap.get(words[i]);
                wordMap.put(words[i], val + 1);
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
        for (int i = 0; i < infoIds.size(); i++) {
            Map.Entry<String, Integer> ent = infoIds.get(i);
            DecimalFormat df=new DecimalFormat("0.00000");//设置保留位数
            String freq = df.format((float)(ent.getValue())/(words.length-1));
            if(ent.getKey().equals(word)) {
                System.out.println(ent.getKey() + "=" + ent.getValue()+ "+" + freq);
            }
        }
    }

    public static String wordProperty(String word) throws IOException{
        List<Term> termList = StandardTokenizer.segment(word);
        String result = termList.get(0).toString();
        return result;
    }

    public static void main(String[] args) throws IOException{
        String content = doWord("C://Users//CWH2019//Desktop//新浪新闻.xls", 0, 4);
        String word = wordProperty("家");
        doFreq(word, content);
    }
}




