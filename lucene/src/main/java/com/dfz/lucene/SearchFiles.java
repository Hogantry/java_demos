package com.dfz.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * @version V1.0
 * @author: DFZ
 * @description: search files
 * @date: 2020/8/28 16:58
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class SearchFiles {

    public static void main(String[] args) throws IOException, ParseException {
        String indexPath = "lucene/src/main/resources/index";
        String contentField = "contents";
        // 索引目录对象
        Directory directory = FSDirectory.open(Paths.get(indexPath));
        // 索引读取工具
        IndexReader indexReader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        Analyzer standardAnalyzer = new StandardAnalyzer();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        QueryParser queryParser = new QueryParser(contentField, standardAnalyzer);
        System.out.println("Enter query:");
        // 从Console读取要查询的语句
        String line = bufferedReader.readLine();
        if (line == null || line.length() == 0) {
            return;
        }
        line = line.trim();
        if (line.length() == 0) {
            return;
        }
        // 创建查询对象
        Query query = queryParser.parse(line);
        System.out.println("Searching for:" + query.toString(contentField));
        doPagingSearch(indexSearcher, query);
        bufferedReader.close();
        indexReader.close();
    }

    private static void doPagingSearch(IndexSearcher indexSearcher, Query query) throws IOException {
        // 搜索数据,两个参数：查询条件对象,要查询的最大结果条数N
        // 返回的结果是 按照匹配度排名得分前N名的文档信息（包含查询到的总条数信息、所有符合条件的文档的编号信息）。
        TopDocs results = indexSearcher.search(query, 10);
        // 获取得分文档对象（ScoreDoc）数组.ScoreDoc中包含：文档的编号、文档的得分
        ScoreDoc[] scoreDocs = results.scoreDocs;
        // 获取总条数
        int numTotalHits = Math.toIntExact(results.totalHits.value);
        System.out.println(numTotalHits + " total matching documents");
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 取出文档编号
            int docID = scoreDoc.doc;
            // 根据编号去找文档
            Document document = indexSearcher.doc(docID);
            System.out.println("文档:" + document.get("path"));
            // 取出文档得分
            System.out.println("相关度:" + scoreDoc.score);
            System.out.println("================================");
        }
    }

}
