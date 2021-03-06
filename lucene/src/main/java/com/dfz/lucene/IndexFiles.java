package com.dfz.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.packed.DirectWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @version V1.0
 * @author: DFZ
 * @description: 读取docs目录下面的所有文件，解析后，生成索引文件，并存入index目录下。
 *               这里注意目录的路径，项目中定义的路径默认是从项目所在目录作为根路径开始的。
 * @date: 2020/8/28 16:22
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class IndexFiles {

    public static void main(String[] args) {
        String indexPath = "lucene/src/main/resources/index";
        String docsPath = "lucene/src/main/resources/docs";

        Path docDir = Paths.get(docsPath);
        try {
            // 存储索引数据的目录
            Directory indexDirectory = FSDirectory.open(Paths.get(indexPath));
            // 创建分词器
            Analyzer analyzer = new StandardAnalyzer();
            // 索引写出工具的配置对象
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

            // 创建索引的写出工具类。参数：索引的目录和配置信息
            IndexWriter indexWriter = new IndexWriter(indexDirectory, indexWriterConfig);
            indexDocs(indexWriter, docDir);
            // 关闭
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void indexDocs(IndexWriter indexWriter, Path path) throws IOException {
        if (Files.isDirectory(path)) {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    indexDoc(indexWriter, file);
                    return FileVisitResult.CONTINUE;
                }
            });
        } else {
            indexDoc(indexWriter, path);
        }
    }

    private static void indexDoc(IndexWriter indexWriter, Path file) throws IOException {
        try (InputStream inputStream = Files.newInputStream(file)) {
            // 创建一个新的空文档
            Document document = new Document();
            // 创建并添加字段信息。参数：字段的名称、字段的值、是否存储，这里选Store.YES代表存储到文档列表。Store.NO代表不存储
            Field pathField = new StringField("path", file.toString(), Field.Store.YES);
            document.add(pathField);
            // 这里我们title字段需要用TextField，即创建索引又会被分词。StringField会创建索引，但是不会被分词
            TextField contentsField = new TextField("contents", new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)));
            document.add(contentsField);
            System.out.println("adding " + file);
            // 写文档
            indexWriter.addDocument(document);
            // 提交
            indexWriter.commit();
        }
    }

}
