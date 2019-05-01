//public static void main(String[] args) {
//        MongoClient  mongoClient=new MongoClient("localhost",27017);
//        MongoDatabase mongoDatabase = mongoClient.getDatabase("student");
//        MongoCollection<Document> collection = mongoDatabase.getCollection("student");
//        MongoCursor<Document>  cursor=collection.find( new Document("name","scofield")).
//        projection(new Document("score",1).append("_id", 0)).iterator();
//        while(cursor.hasNext())
//        System.out.println(cursor.next().toJson());
//        }
