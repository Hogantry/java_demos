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
        IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        Analyzer standardAnalyzer = new StandardAnalyzer();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
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
        Query query = queryParser.parse(line);
        System.out.println("Searching for:" + query.toString(contentField));
        doPagingSearch(indexSearcher, query);
        bufferedReader.close();
        indexReader.close();
    }

    private static void doPagingSearch(IndexSearcher indexSearcher, Query query) throws IOException {
        TopDocs results = indexSearcher.search(query, 10);
        ScoreDoc[] hits = results.scoreDocs;
        int numTotalHits = Math.toIntExact(results.totalHits.value);
        System.out.println(numTotalHits + " total matching documents");
        for (ScoreDoc hit : hits) {
            Document document = indexSearcher.doc(hit.doc);
            System.out.println("文档:" + document.get("path"));
            System.out.println("相关度:" + hit.score);
            System.out.println("================================");
        }
    }

}
