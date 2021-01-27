
import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import com.hankcs.hanlp.classification.models.NaiveBayesModel;
import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import jxl.read.biff.BiffException;
//import com.hankcs.hanlp.utility.TestUtility;

import java.io.File;
import java.io.IOException;

/**
 * 第一个demo,演示文本分类最基本的调用方式
 *
 * @author hankcs
 */
public class DemoTextClassification
{
    /**
     * 搜狗文本分类语料库5个类目，每个类目下1000篇文章，共计5000篇文章
     */
    public static final String CORPUS_FOLDER = "D:/HanLP1/src/main/resources/训练源文件";
    /**
     * 模型保存路径
     */
    public static final String MODEL_PATH = "D:/HanLP1/classification-model.ser";


    public static void main(String[] args) throws IOException, BiffException {

        IClassifier classifier = new NaiveBayesClassifier(trainOrLoadModel());
        predict(classifier, "卫健委，最大的失职团体，从上到下全撤也不冤枉");
        predict(classifier, "被成都人民骂死了吧，现在才开始采取统一集中隔离措施！ ");
        predict(classifier, "说白了就是欺下瞒上呗，为了帽子不顾老百姓的死活，没想到搞大了控制不住了，帽子还是没有保住，还被查！");
        predict(classifier, "仓促封城，煎熬百姓！");
        predict(classifier, "既然提前一周会通知，那为什么非让陕西外地教师返回学校所在地呢？这些老师返回以后的生活生存谁来保障？住学校宿舍吗？隔离没吃的没换洗衣服，怎么办？");
        predict(classifier,"我说说发生在我身上的事情吧！江苏省南通市第一人民医院为了钱！竟然把魔爪伸向ＩＣＵ里面可怜又无助的危重病人身上！医院里的医生唐志和利用医生的便利，竟然明知故犯的把一次性塑料旧气管导管在病人身上重复使用！");
        predict(classifier, "过度的恐慌对治疗预防疾病没有半点作用，反而很容易影响睡眠质量，大幅度降低免疫能力，这点不管中医西医都有相关研究。");
        predict(classifier, "请继续保持 病毒很狡猾 我们还不能松懈。抗疫之路 任重而道远！" );
        predict(classifier, " pyq发来了菜单，饿啊，疫情结束就想吃一顿正宗的四川#火锅# " );
        predict(classifier, "在路边摆摊给剪短裤子改腰换拉链，确实说不清楚接触了多少人，麻烦了" );
        System.out.println(classifier.predict("1，极度震慑潜在对手。展示了无以伦比的动员力量，封闭了千万级人口的城市，同时还能保障基本供应。短时间内（几周时间），建起两所千张病床医院，可容纳数万人的一系列方舱医院。全国调拨2万名专业医护人员，海陆空运送。展示了无以伦比的民族凝聚力，从一线城市到贫困山区，封锁隔离、无人怨言，捐款捐物、人人踊跃。这样的国家，目前在地球上就是没有对手的。2，摆正产业方向工农业才是基础，才是真正的硬实力。房地产这类数值大，见效快，然而关键时刻真的不顶用。3，遏制医疗产业化公立医院才是保障人民健康的关键。医疗产业化真的不能取。4，扭转科研论文化我们需要的是把论文写在祖国大地上的科研人才。5，树立正确价值观让青少年明白，什么爱豆都是没有用的，也许还会卖你假口罩。关键时刻还是需要科学家。\n"));
        System.out.println(classifier.classify("哎？我发现我忘了一个重要的点忘了说，整个治疗过程我是一分钱都没花这个是排在医护人员敬业态度之后，第二名让我感动的因素，我父母的好多朋友都不太相信，我住院的时候还没毕业，大学生医保只能在武汉报销，所以我当时还有点担心费用的问题，不过主治大夫当时就告诉我，有没有医保都不花钱，也不用垫付，所以从始至终，除了最开始的五块钱挂号费，我真的是一分钱没掏拍片子，采血什么的也都有类似绿色通道那种，因为这个病的传染性很强嘛，所以医院单独划一个ct室给我这样的病人用（不知道为啥这样做的，参考青岛最近的疫情起因），几乎是检查完第一时间出结果，转院坐的是专门拉我这种病人的负压救护车，一路拉笛狂飙，全程除司机以外，还有两名医护人员陪伴，所有人包括我在整个转院过程中，出医院，进医院都必须全身消毒，穿整套的连体防护服，戴医用n95口罩（所以我看了川普没转阴就出院，还摘口罩那个新闻，就nm的离谱）可以说整个过程考虑的非常周到严密了转院时穿的防护服而且我出院之后医院还打电话要给我退当初挂号的5块钱，不过不想再去医院转一圈，就放弃领取这5块钱了……一一一一一一一一分割线一一一一一一一一一头一次点赞破百，比较激动最近看新闻，懂王也感染了，我一直以为美帝的领导人都是说一套做一套，没找到川建国还真是傻实在，病毒终于找到了最懂它的人至于评论里想采访或者做什么调研的旁友们，真的抱歉，我不想接受任何采访啥的，刚出院的时候也有很多媒体想采访，但是都拒绝了，现在这网络社会，根本不存在信任，害怕自己的表述被他人断章取义，所以安安心心过日子，这个玩意就免了吧至于问我身体状况的，吃嘛嘛香，身体倍儿棒，十一还参加了同学的婚礼，周遭生活早就恢复了正常，仿佛做梦一样，真的挺好的端午节的时候还回了一趟武汉，去吃火锅又要排好久的队了，吃饭的时候真的有点想哭二环线上吃饭排队总之我的生活现在非常正常这两天看《在一起》，真的看的我一把鼻涕一把泪，当时生病的时候没啥感觉，回过头来看这部剧真的太想哭了最后补一张我出院时候的新闻一一一一一一一一分割线一一一一一一一一一以下是原答案一一一一一一一一一一一一一一一一一一一一看了一圈竟然这么少回答，而且里面有很多感觉像扯淡的本人2020年1月22日确诊为疑似病例23日正式确诊28号第一次核酸转阴2020年2月5日出院在县城医院隔离这是还没确诊的时候在县城的医院里隔离，第二天就被转去市里的传染病医院了无非就是高烧，无力，呼吸困难，刚住院的两三天得吸氧，我生平第一次能发烧给自己热醒。总之就是很惨，以至于我在医院里每顿吃两盒饭，出院之后还是瘦了七斤半每天早上要采很多血每天早上都要抽血真的很多，不过我是一点心理负担都没有，也不花钱，乖乖听话接受治疗就行了专家开的中药每天吃很多各种各样的药，包括中药照顾我的护士和大夫照顾我的大夫和护士，这只是其中一部分，人都特别特别好，非常的有耐心，医生护士都太伟大了网络专家会诊在此之前我都不知道这屋子可以装wifi，负压病房有两层门，一点信号都没有，好在除夕夜之前有次网络专家会诊，我才知道可以上网，玩手机对我来说也算是辅助治疗手段之一了，要不我真的可能会无聊死在院士说阿比朵尔有效之前，我的主治大夫就买到了，给我吃上了，这个真的牛，点赞住院的时候，最开心的时刻就是去做ct，因为我的病房到ct室之间，要经过两个楼之间几十米的户外路程，我可以在这短短几十步内，贪婪的呼吸着冰爽的新鲜空气，好像要把整个冬天灌进我的肺里，以证明我还真真切切的活着住院没啥感觉，我这人比较看得开，其实我觉得生过病的人最在意的可能是别人会怎样看待他。病人面对的是猜忌和误解，而普通人面对的是未知和恐惧。就我自己而言，出院后身体没什么不适，我现在每天都去健身房，运动强度跟生病之前也没什么区别，出院后做过大概十几次核酸，复查过三次，只有最近一次复查是自费做的，大夫说我恢复的很好，ct上看不出生病的痕迹出院之后我也去献过两次血浆，一共是800cc捐血的针头是真的粗八月底那次复查，我体内还有抗体暂时情况就这么多祝大家身体健康\n"));
        System.out.println(classifier.predict("哎？我发现我忘了一个重要的点忘了说，整个治疗过程我是一分钱都没花这个是排在医护人员敬业态度之后，第二名让我感动的因素，我父母的好多朋友都不太相信，我住院的时候还没毕业，大学生医保只能在武汉报销，所以我当时还有点担心费用的问题，不过主治大夫当时就告诉我，有没有医保都不花钱，也不用垫付，所以从始至终，除了最开始的五块钱挂号费，我真的是一分钱没掏拍片子，采血什么的也都有类似绿色通道那种，因为这个病的传染性很强嘛，所以医院单独划一个ct室给我这样的病人用（不知道为啥这样做的，参考青岛最近的疫情起因），几乎是检查完第一时间出结果，转院坐的是专门拉我这种病人的负压救护车，一路拉笛狂飙，全程除司机以外，还有两名医护人员陪伴，所有人包括我在整个转院过程中，出医院，进医院都必须全身消毒，穿整套的连体防护服，戴医用n95口罩（所以我看了川普没转阴就出院，还摘口罩那个新闻，就nm的离谱）可以说整个过程考虑的非常周到严密了转院时穿的防护服而且我出院之后医院还打电话要给我退当初挂号的5块钱，不过不想再去医院转一圈，就放弃领取这5块钱了……一一一一一一一一分割线一一一一一一一一一头一次点赞破百，比较激动最近看新闻，懂王也感染了，我一直以为美帝的领导人都是说一套做一套，没找到川建国还真是傻实在，病毒终于找到了最懂它的人至于评论里想采访或者做什么调研的旁友们，真的抱歉，我不想接受任何采访啥的，刚出院的时候也有很多媒体想采访，但是都拒绝了，现在这网络社会，根本不存在信任，害怕自己的表述被他人断章取义，所以安安心心过日子，这个玩意就免了吧至于问我身体状况的，吃嘛嘛香，身体倍儿棒，十一还参加了同学的婚礼，周遭生活早就恢复了正常，仿佛做梦一样，真的挺好的端午节的时候还回了一趟武汉，去吃火锅又要排好久的队了，吃饭的时候真的有点想哭二环线上吃饭排队总之我的生活现在非常正常这两天看《在一起》，真的看的我一把鼻涕一把泪，当时生病的时候没啥感觉，回过头来看这部剧真的太想哭了最后补一张我出院时候的新闻一一一一一一一一分割线一一一一一一一一一以下是原答案一一一一一一一一一一一一一一一一一一一一看了一圈竟然这么少回答，而且里面有很多感觉像扯淡的本人2020年1月22日确诊为疑似病例23日正式确诊28号第一次核酸转阴2020年2月5日出院在县城医院隔离这是还没确诊的时候在县城的医院里隔离，第二天就被转去市里的传染病医院了无非就是高烧，无力，呼吸困难，刚住院的两三天得吸氧，我生平第一次能发烧给自己热醒。总之就是很惨，以至于我在医院里每顿吃两盒饭，出院之后还是瘦了七斤半每天早上要采很多血每天早上都要抽血真的很多，不过我是一点心理负担都没有，也不花钱，乖乖听话接受治疗就行了专家开的中药每天吃很多各种各样的药，包括中药照顾我的护士和大夫照顾我的大夫和护士，这只是其中一部分，人都特别特别好，非常的有耐心，医生护士都太伟大了网络专家会诊在此之前我都不知道这屋子可以装wifi，负压病房有两层门，一点信号都没有，好在除夕夜之前有次网络专家会诊，我才知道可以上网，玩手机对我来说也算是辅助治疗手段之一了，要不我真的可能会无聊死在院士说阿比朵尔有效之前，我的主治大夫就买到了，给我吃上了，这个真的牛，点赞住院的时候，最开心的时刻就是去做ct，因为我的病房到ct室之间，要经过两个楼之间几十米的户外路程，我可以在这短短几十步内，贪婪的呼吸着冰爽的新鲜空气，好像要把整个冬天灌进我的肺里，以证明我还真真切切的活着住院没啥感觉，我这人比较看得开，其实我觉得生过病的人最在意的可能是别人会怎样看待他。病人面对的是猜忌和误解，而普通人面对的是未知和恐惧。就我自己而言，出院后身体没什么不适，我现在每天都去健身房，运动强度跟生病之前也没什么区别，出院后做过大概十几次核酸，复查过三次，只有最近一次复查是自费做的，大夫说我恢复的很好，ct上看不出生病的痕迹出院之后我也去献过两次血浆，一共是800cc捐血的针头是真的粗八月底那次复查，我体内还有抗体暂时情况就这么多祝大家身体健康\n"));
        System.out.println(classifier.predict("我是海外的，忘了备注不好意思。我们是全家四个人感染的，我们每个人症状不一样但是基本上就是会发烧感冒咳嗽，我刚开始是头痛，浑身不舒服……到了晚上开始发烧没胃口连续几天这样，发烧好了就没什么严重症状。最明显的应该就是闻不到气味也尝不到食物，口干，没力气现在已经是我第十一天已经慢慢恢复了正常但是我爸妈可能年纪大了还一直有咳嗽。我们没住院就在家隔离因为医生说没必要严重情况的话就不要。但愿疫情快点结束吧！" ));

//        predict(classifier, "" );
//        predict(classifier,"+1");
//        AnalyseTianYa analyseTianYa = new AnalyseTianYa();
//        analyseTianYa.analyse();
//        AnalyseTianYa analyseTianYa= new AnalyseTianYa("D:\\request\\天涯重点帖子.xls");
//        analyseTianYa.analyse();
//        analyseTianYa.saveData0();
//        analyseTianYa.saveData1();
//        analyseTianYa.saveData2();
//        analyseTianYa.saveData3();
//        analyseTianYa.saveData4();
//        analyseTianYa.saveData5();

//        AnalyseZhiHi analyseZhiHi = new AnalyseZhiHi("D:/Documents/Tencent Files/1351039686/FileRecv/知乎回答.xls");
//        analyseZhiHi.analyseTop10();
//        analyseZhiHi.saveTop10();
//        analyseZhiHi.analyse();
//        analyseZhiHi.saveDic1();
//        analyseZhiHi.saveDic2();
//        analyseZhiHi.saveDic3();
//        analyseZhiHi.saveDic4();

//        AnalyseSina analyseSina = new AnalyseSina("D:\\Documents\\Tencent Files\\1351039686\\FileRecv\\新浪新闻.xls");
//        analyseSina.analyse();
//        analyseSina.saveData0();
//        analyseSina.saveData1();
//        analyseSina.saveData2();
//        analyseSina.saveData3();
//        analyseSina.saveData4();
//        analyseSina.saveData5();

//
//

//        System.out.println(classifier.predict(""));

//        AnalyseWeibo analyseWeibo = new AnalyseWeibo("D:/request/微博重点评论.xls");
//        analyseWeibo.analyse();
//        analyseWeibo.saveData0();
//        analyseWeibo.saveData1();
//        analyseWeibo.saveData2();
//        analyseWeibo.saveData3();
//        analyseWeibo.saveData4();
//        analyseWeibo.saveData5();
    }

    private static void predict(IClassifier classifier, String text)
    {
        System.out.printf("《%s》 属于分类 【%s】\n", text, classifier.classify(text));
    }

    public static NaiveBayesModel trainOrLoadModel() throws IOException
    {
        NaiveBayesModel model = (NaiveBayesModel) IOUtil.readObjectFrom(MODEL_PATH);
        if (model != null) return model;

        File corpusFolder = new File(CORPUS_FOLDER);
        if (!corpusFolder.exists() || !corpusFolder.isDirectory())
        {
            System.err.println("没有文本分类语料，请阅读IClassifier.train(java.lang.String)中定义的语料格式与语料下载：" +
                    "https://github.com/hankcs/HanLP/wiki/%E6%96%87%E6%9C%AC%E5%88%86%E7%B1%BB%E4%B8%8E%E6%83%85%E6%84%9F%E5%88%86%E6%9E%90");
            System.exit(1);
        }

        IClassifier classifier = new NaiveBayesClassifier(); // 创建分类器，更高级的功能请参考IClassifier的接口定义
        classifier.train(CORPUS_FOLDER);                     // 训练后的模型支持持久化，下次就不必训练了
        model = (NaiveBayesModel) classifier.getModel();
        IOUtil.saveObjectTo(model, MODEL_PATH);
        return model;
    }
}