import java.io.File;
import java.io.IOException;
import java.util.List;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class demo {
    public static void main(String[] args) throws IOException, BiffException {
        File xlsFile= new File("D:/request/TianYaComments.xls");
        Workbook workbook = Workbook.getWorkbook(xlsFile);
        Sheet sheet = workbook.getSheet(0);
        String tmp=sheet.getCell(1,1707).getContents();
        List<Term> termList = StandardTokenizer.segment(tmp);
        System.out.println(tmp);
        String content = tmp;
        List<String> keywordList = HanLP.extractKeyword(content, 20);
        System.out.println(keywordList);
    }
}
