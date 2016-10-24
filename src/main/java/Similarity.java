
    import java.io.*;  
    import java.util.*;  
      
    public class Similarity extends ArticleInfo {  
        private static final int ARTICLE_KEY_LENGTH = 15; // 文档key的长度  
        private static final double THRESHOLD = 0.2; // 文档长度归一化阀值  
        private static double averageArticleLen; // 文档平均长度  
        private static Set<String> stopwordSet; // 停用词集合  
        private static TreeMap<String, String> articleMap; // 文档key与内容的映射集  
        private static TreeMap<String, TermInfo> termInfoMap; // 词项与其相关统计信息的映射集  
        private static TreeMap<String, ArticleInfo> articleInfoMap; // 文档key与其相关统计信息（长度、词项集）的映射集  
        private static TreeSet<String> termIntersection; // 文档词项交集  
      
        public static void main(String args[]) {  
            setStopwordSet();  
            Similarity s = new Similarity();  
            articleMap = s.setArticleMap();  
            termInfoMap = s.setTermInfoSet();  
            articleInfoMap = s.setArticleInfoMap();  
            long sTime = System.currentTimeMillis();  
            String qKey = null;  
            qKey = articleMap.firstKey();  
            for (int i = 0; i < 10; i++) {  
                outputAnArticleSimArray(qKey);  
                qKey = articleMap.higherKey(qKey);  
            }  
            outputAnArticleSimArray("19980101-03-012");  
            long eTime = System.currentTimeMillis();  
            System.err.println("Computing first 100 Articles' Similarity used "  
                    + (eTime - sTime) + "ms" + '\n');  
            // outputAnArticleSimArray("19980101-01-001");  
            // double similarity =  
            // s.computeSimilarity("19980101-01-001","19980101-01-002");  
            // System.err.println(similarity);  
            // sim.intersectTerm("19980101-01-001","19980101-01-002");  
        }  
      
        public static void setStopwordSet() {  
            long sReadTime = System.currentTimeMillis();  
            BufferedReader bufferedIn = null;  
            String str = "";  
            stopwordSet = new HashSet<String>();  
            try {  
                bufferedIn = new BufferedReader(new FileReader(  
                        "ChineseStopword.txt"));  
                while ((str = bufferedIn.readLine()) != null) {  
                    stopwordSet.add(str);  
                }  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            long eReadTime = System.currentTimeMillis();  
            System.err.println("stopword count is " + stopwordSet.size());  
            System.out  
                    .println("Reading the Chinese stopwords into the Set container used "  
                            + (eReadTime - sReadTime) + "ms" + '\n');  
        }  
      
        public TreeMap<String, String> setArticleMap() {  
            long sReadTime = System.currentTimeMillis();  
            BufferedReader bufferedIn = null;  
            String str = "";  
            TreeMap<String, String> articles = new TreeMap<String, String>();  
            try {  
                bufferedIn = new BufferedReader(new FileReader("Edited.txt"));  
                while ((str = bufferedIn.readLine()) != null) {  
                    if (str.length() > 0) {  
                        String articleKey = str  
                                .substring(0, ARTICLE_KEY_LENGTH);// 前15位字符串做Key  
                        String articleContent = str  
                                .substring(ARTICLE_KEY_LENGTH + 4);  
                        if (articles.isEmpty()  
                                || !articles.containsKey(articleKey))  
                            articles.put(articleKey, articleContent);  
                        else {  
                            String tempStr = articles.get(articleKey)  
                                    + articleContent;  
                            articles.put(articleKey, tempStr);  
                        }  
                    }  
                }  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            long eReadTime = System.currentTimeMillis();  
            System.err.println("Article count is " + articles.size());  
            System.err.println("Building the articleMap used "  
                    + (eReadTime - sReadTime) + "ms" + '\n');  
            return articles;  
        }  
      
        public TreeMap<String, TermInfo> setTermInfoSet() {  
            long sTime = System.currentTimeMillis();  
            TreeMap<String, TermInfo> terms = new TreeMap<String, TermInfo>();// 新建TreeMap对象  
            String currentArticleKey = articleMap.firstKey();// 取文档集最小的键值  
            while (currentArticleKey != null) {  
                String currentArticleContent = articleMap  
                        .get(currentArticleKey);// 文档正文内容  
                @SuppressWarnings("unused")
				int len = 0;// 文档中非空字符数，没有区分字符和汉字，没有排除停用词  
                for (int i = 0; i != currentArticleContent.length() - 1;) {// 遍历文档字符串  
                    String str = currentArticleContent.substring(i, i + 1);  
                    String tempTerm = "";  
                    if (str.equals(" ")) {  
                        i++;  
                        continue;  
                    }  
                    while (!str.equals(" ")) {  
                        tempTerm += str;  
                        len++;  
                        i++;  
                        str = currentArticleContent.substring(i, i + 1);  
                    }  
                    if (stopwordSet.contains(tempTerm))  
                        continue;// 遇到停用词，换下一个非空词项  
                    else {  
                        if (!terms.keySet().contains(tempTerm)) {// 加入新词项  
                            TermInfo aTermInfo = new TermInfo();  
                            aTermInfo.totalCount = 1;  
                            aTermInfo.inDocInfo.put(currentArticleKey, 1);  
                            terms.put(tempTerm, aTermInfo);  
                        } else {// 更新词项信息  
                            TermInfo bTermInfo = terms.get(tempTerm);  
                            bTermInfo.totalCount++;  
                            if (bTermInfo.inDocInfo.get(currentArticleKey) == null)  
                                bTermInfo.inDocInfo.put(currentArticleKey, 1);  
                            else {  
                                Integer tempInDocCount = bTermInfo.inDocInfo  
                                        .get(currentArticleKey) + 1;  
                                bTermInfo.inDocInfo.put(currentArticleKey,  
                                        tempInDocCount);  
                            }  
                            terms.put(tempTerm, bTermInfo);  
                        }  
                    }  
                }  
                currentArticleKey = articleMap.higherKey(currentArticleKey);// 取下一个严格高于当前键值的最小键值  
            }  
            long eTime = System.currentTimeMillis();  
            System.err.println("Term total count is " + terms.size());  
            System.err.println("Building the termInfoMap used "  
                    + (eTime - sTime) + "ms" + '\n');  
            return terms;  
        }  
      
        public TreeMap<String, ArticleInfo> setArticleInfoMap() {  
            long sTime = System.currentTimeMillis();  
            TreeMap<String, ArticleInfo> articleInfos = new TreeMap<String, ArticleInfo>();// 新建TreeMap对象  
            String currentArticleKey = articleMap.firstKey();// 取文档集最小的键值  
            int allArticleLength = 0;  
            int len = 0;  
            while (currentArticleKey != null) {  
                String currentArticleContent = articleMap  
                        .get(currentArticleKey);// 文档正文内容  
                if (articleInfos.get(currentArticleKey) == null) {  
                    len = 0;  
                } else  
                    len = articleInfos.get(currentArticleKey).length;// 文档中非空字符数，没有区分字符和汉字，没有排除停用词  
                for (int i = 0; i != currentArticleContent.length() - 1;) {// 遍历文档字符串  
                    String str = currentArticleContent.substring(i, i + 1);  
                    String tempTerm = "";  
                    if (str.equals(" ")) {  
                        i++;  
                        continue;  
                    }  
                    while (!str.equals(" ")) {  
                        tempTerm += str;  
                        len++;  
                        i++;  
                        str = currentArticleContent.substring(i, i + 1);  
                    }  
                    if (stopwordSet.contains(tempTerm))  
                        continue;// 遇到停用词，换下一个非空词项  
                    else {  
                        if (!articleInfos.keySet().contains(currentArticleKey)) {// 加入新词项  
                            ArticleInfo anArticleInfo = new ArticleInfo();  
                            anArticleInfo.length = len;  
                            anArticleInfo.termVec.add(tempTerm);  
                            articleInfos.put(currentArticleKey, anArticleInfo);  
                        } else {// 更新词项信息  
                            ArticleInfo bArticleInfo = articleInfos  
                                    .get(currentArticleKey);  
                            bArticleInfo.length = len;  
                            bArticleInfo.termVec.add(tempTerm);  
                            articleInfos.put(currentArticleKey, bArticleInfo);  
                        }  
                    }  
                }  
                allArticleLength += articleInfos.get(currentArticleKey).length;  
                currentArticleKey = articleMap.higherKey(currentArticleKey);// 取下一个严格高于当前键值的最小键值  
            }  
            long eTime = System.currentTimeMillis();  
            averageArticleLen = allArticleLength / articleMap.size();  
            System.err.println("Building the articleInfoMap used "  
                    + (eTime - sTime) + "ms" + '\n');  
            return articleInfos;  
        }  
      
        public static void intersectTerm(String q, String d) {// 求词项集合交集  
        // long sTime = System.currentTimeMillis();  
            termIntersection = new TreeSet<String>();  
            TreeSet<String> tempSet = new TreeSet<String>();  
            if (articleInfoMap.get(q) == null || articleInfoMap.get(d) == null) {  
                System.err.println("Invalid article key.");  
                System.exit(0);  
            }  
            for (int i = 0; i != articleInfoMap.get(q).termVec.size(); i++)  
                tempSet.add(articleInfoMap.get(q).termVec.get(i));  
            for (int i = 0; i != articleInfoMap.get(d).termVec.size(); i++) {  
                boolean flag = tempSet.contains(articleInfoMap.get(d).termVec  
                        .get(i));  
                if (flag == true) {  
                    termIntersection.add(articleInfoMap.get(d).termVec.get(i));  
                }  
            }  
            // long eTime = System.currentTimeMillis();  
            // System.err.println("文档"+q+"和文档"+d+"的词项交集有"+termIntersection.size()+"项。");  
            // System.err.println(termIntersection);  
            // System.err.println("Intersecting two termVecs used "+(eTime -  
            // sTime)+"ms"+'\n');  
            // return termIntersection;  
        }  
      
        public static double computeSimilarity(String qKey, String dKey) {// 计算两文档间的相似度，文档Key为参数  
        // long sTime = System.currentTimeMillis();  
            double sim = 0;  
            intersectTerm(qKey, dKey);  
            Iterator<String> it = termIntersection.iterator();  
            while (it.hasNext()) {  
                String commonTerm = it.next();  
                int cwd = termInfoMap.get(commonTerm).inDocInfo.get(dKey);  
                int cwq = termInfoMap.get(commonTerm).inDocInfo.get(qKey);  
                int dfw = termInfoMap.get(commonTerm).inDocInfo.size();  
                sim += (1 + Math.log(1 + Math.log(cwd)))  
                        / (1 - THRESHOLD + THRESHOLD  
                                * articleInfoMap.get(dKey).length  
                                / averageArticleLen) * cwq  
                        * Math.log((articleMap.size() + 1) / dfw);  
            }  
            // long eTime = System.currentTimeMillis();  
            // System.err.println("Computing a similarity used "+(eTime -  
            // sTime)+"ms"+'\n');  
            // System.err.println("Sim is "+sim+'\n');  
            return sim;  
        }  
      
        public static void outputAnArticleSimArray(String articleKey) {  
            // long sTime = System.currentTimeMillis();  
            TreeMap<String, Vector<ArticleSim>> articleSimArray = new TreeMap<String, Vector<ArticleSim>>();  
            Vector<ArticleSim> anArticleSimVec = new Vector<ArticleSim>();  
            double sim = 0;  
            String qKey = articleKey;  
            String dKey = articleMap.firstKey();  
            while (dKey != null) {  
                ArticleSim anArticleSim = new ArticleSim();  
                if (dKey.equals(qKey))  
                    sim = 0;  
                else  
                    sim = computeSimilarity(qKey, dKey);  
                anArticleSim.dKeySim.put(dKey, sim);  
                anArticleSimVec.add(anArticleSim);  
                articleSimArray.put(qKey, anArticleSimVec);  
                dKey = articleMap.higherKey(dKey);  
            }  
            // long eTime = System.currentTimeMillis();  
            // System.err.println("Computing an ArticleSimArray used "+(eTime -  
            // sTime)+"ms"+'\n');  
            int index = 0;  
            dKey = articleMap.firstKey();  
            for (; dKey != null;) {  
                System.err.println(qKey  
                        + "->"  
                        + dKey  
                        + " = "  
                        + articleSimArray.get(qKey).get(index).dKeySim  
                                .get(dKey));  
                dKey = articleMap.higherKey(dKey);  
                index++;  
            }  
        }  
    }  