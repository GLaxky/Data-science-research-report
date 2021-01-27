import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.*;

public class MyTF {

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

    public static String doWord(String str) throws IOException {
        ArrayList<String> sum = new ArrayList();
        List<Term> termList = StandardTokenizer.segment(str);
        for(int j = 0; j < termList.size(); j++){
            int tr = detern(termList.get(j).toString());
            if(tr == 0){
                sum.add(termList.get(j).toString());
            }
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

    public static int detern(String str){
        boolean status1 = str.contains("/n");
        boolean status2 = str.contains("/m");
        boolean status3 = str.contains("/w");
        int d = 0;
        if(status1){
            d = 1;
        }else if(status2){
            d = 1;
        }else if(status3){
            d = 1;
        }
        return d;
    }

    public static int doFreq(String wordin, String wordinput) throws IOException{
        String word = wordProperty(wordin);
        String newString = doWord(wordinput);
        String[] words = newString.split(" ");
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
        int res=0;
        for (int i = 0; i < infoIds.size(); i++) {
            Map.Entry<String, Integer> ent = infoIds.get(i);
            DecimalFormat df=new DecimalFormat("0.00000");//设置保留位数
            String freq = df.format((float)(ent.getValue())/(words.length-1));
            if(ent.getKey().equals(word)) {
                System.out.println(ent.getKey() + "=" + ent.getValue()+ "+" + freq);
                res=ent.getValue();
            }
        }
        return res;
    }

    public static String wordProperty(String word) throws IOException{
        List<Term> termList = StandardTokenizer.segment(word);
        String result = termList.get(0).toString();
        return result;
    }

    public static void main(String[] args) throws IOException{
        doFreq("恐慌", "过度的恐慌对治疗预防疾病没有半点作用，反而很容易影响睡眠质量，大幅度降低免疫力，这点不管中医西医都有相关研究。");
    }
}
