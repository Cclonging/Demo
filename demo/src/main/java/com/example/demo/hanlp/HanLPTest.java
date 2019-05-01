package com.example.demo.hanlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.util.List;

public class HanLPTest {


    public static void main(String[] args) {
        List<Term> list = HanLP.segment("你好啊， 大宝贝， 我爱死你了呀");
        for (Term term : list) {
            if (term.nature == Nature.n) {
                System.out.println(term.word);
            }
        }
//        System.out.println(NLPTokenizer.segment("我新造一个词叫幻想乡你能识别并标注正确词性吗？"));
//// 注意观察下面两个“希望”的词性、两个“晚霞”的词性
//        System.out.println(NLPTokenizer.analyze("我的希望是希望张晚霞的背影被晚霞映红").translateLabels());
//        System.out.println(NLPTokenizer.analyze("支援臺灣正體香港繁體：微软公司於1975年由比爾·蓋茲和保羅·艾倫創立。"));

        List<Term> termList = StandardTokenizer.segment("商品和服务");
        System.out.println(termList);

        List<Term> termList2 = IndexTokenizer.segment("主副食品");
        for (Term term : termList2)
        {
            System.out.println(term + " [" + term.offset + ":" + (term.offset + term.word.length()) + "]");
        }
    }
}
