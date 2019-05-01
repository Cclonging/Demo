package com.example.demo.mongo;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.beans.Transient;
import java.util.*;

/**
 * test mongdb connector
 */
@Slf4j
public class Demo {

    private static String BAN_WORDS = null;

    private static MongoClient mongoClient = null;

    private static MongoDatabase mongodb = null;

    private static MongoCollection<Document> hannah = null;

    private static List<Document> banWords= null;
    static {
        //TODO 从数据里面拿出数据
        BAN_WORDS = "毛泽东胡锦涛江泽民邓小平习近平fuck";

        banWords= new ArrayList<>(2);

        //连接MongoDB
        try {
            // 无密码登陆
            mongoClient = new MongoClient("localhost", 27017);
            mongodb = mongoClient.getDatabase("runoob");
            hannah = mongodb.getCollection("hannah");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    /**
     * 分词检测
     *
     * @param words
     */
    public static String fenci(String words) {
        List<Term> terms = HanLP.segment(words);
        //拿出名词
        if (!terms.isEmpty()) {
            ListIterator<Term> iterable = terms.listIterator();
            StringBuilder sb = new StringBuilder();
            while (iterable.hasNext()) {
                Term item = iterable.next();
                //System.out.println(item.nature + item.word);
                if (Objects.equals(item.nature, Nature.n) ||
                        Objects.equals(item.nature, Nature.nr) ||
                        Objects.equals(item.nature, Nature.nx)) {
                    //调用违规词库检测代码， 用**替换
                    item.word = generatorStars(item.word);
                }
                sb.append(item.word);
            }
            if (!banWords.isEmpty()){
                hannah.insertMany(banWords);

            }
            return sb.toString();
        }
        return words;
    }

    /**
     * 生成**
     *
     * @param n
     * @return
     */
    @Transient
    public static String generatorStars(String n) {
        if (isBan(n)) {
            Document document = new Document("title", "违规言论")
                    .append("senderId" ,"")
                    .append("receiverId", "")
                    .append("content", n)
                    .append("date",new Date());
            banWords.add(document);
            int len = n.matches("[a-zA-Z]+") ? 1 : n.length();
            StringBuilder sb = new StringBuilder(len);
            for (int i = 0; i < len; i++) {
                sb.append("*");
            }
            return sb.toString();
        }
        return n;
    }

    /**
     *
     */
    public static boolean isBan(String word) {
        //从缓存中拿出数据
        if (BAN_WORDS != null) {
            if (BAN_WORDS.indexOf(word) != -1)
                return true;
        }
        //TODO 从数据库里面提取数据
        return false;
    }

    public static void con2Mongo() {
        MongoClient mongoClient = null;
        try {
            // 无密码登陆
            mongoClient = new MongoClient("localhost", 27017);
            MongoDatabase mongoDatabase = mongoClient.getDatabase("runoob");
            System.out.println(" Connect to database " + mongoDatabase.getName() + " successfully");
            //创建集合
            mongoDatabase.createCollection("test");
            System.out.println("集合创建成功");
            //连接集合
            mongoDatabase.createCollection("hannah");
            MongoCollection<Document> hannah = mongoDatabase.getCollection("hannah");
            //插入文档
            Document document = new Document("title", "Mongo java connection")
                    .append("version", "3.10.x")
                    .append("like", 100);
            List<Document> list = new ArrayList<>(2);
            list.add(document);
            hannah.insertMany(list);
            //检索文档
            MongoCursor<Document> iterable = hannah.find().iterator();
            while (iterable.hasNext()) {
                System.out.println(iterable.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mongoClient.close();
        }
    }

    public static void main(String[] args) {
        List<Thread> list = new ArrayList<>(10000);
        for (int i = 0; i < 10000; i ++) {
            list.add(new Thread(() -> {
                long start = System.currentTimeMillis();
                String word = fenci("这是我最后一天在西溪园看阿里的灯火通明了，想来思绪万千，百感交集。\n" +
                        "远先生说，多少人渴望着进来，多少人渴望着出去。\n" +
                        "这个故事始于18年4月26日，云栖大会.南京，向往英雄主义，向往从无到有的传奇人生，向往阿里云所构建的未来。进来之时，才发现充满理想主义的地方没有我想的这么理想，到19年，面对繁重的工作，不堪重负，虽飞速成长，却与梦想背道而驰。我在思考，是日日拼着小命加班加班，是为了每年的3.25而呕心沥血，还是继续追着梦想，出去，放手一搏！我有勇气选择后者。\n" +
                        "吴先生找到我，我们聊了很久的图数据库与DT时代的认知计算。肖先生说，我应当升学，我说，正因为没升学，才能遇到你们。所以，很高兴能加入这个创业团队，不敢戴高帽，不敢。或许，我真的该升学，或许，这次博弈是错的，又或许，真的错了，但是，万一猪在风口被吹飞呢。当然，这个故事不是童话，却是属于我这个“疯子”。\n" +
                        "于此，只能\n" +
                        "感谢，苦难日子里给我无上鼓励的任小姐，\n" +
                        "感谢，孤独日子里给我欢笑哀愁的孟小姐，\n" +
                        "感谢，陈先生的子子教诲与鼎力相助，\n" +
                        "感谢，听我讲述这段历程的朋友们，\n" +
                        "感谢，给我第一份工作的孔先生，\n" +
                        "感谢，为我指引前路的李先生，\n" +
                        "感谢，关心帮助我的兄弟们，\n" +
                        "感谢，周同志的肺腑之言，\n" +
                        "感谢，一禺的奋飞不辍\n" +
                        "\n" +
                        "远先生说，你小子真会玩\n" +
                        "我说，开心就好");
                long end = System.currentTimeMillis();
                System.out.println((end - start));
            }));
        }

        for (Thread t : list){
            t.start();
        }

    }
}
