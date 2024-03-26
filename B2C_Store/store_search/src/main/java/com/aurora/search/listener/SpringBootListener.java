package com.aurora.search.listener;

import com.aurora.clients.ProductClient;
import com.aurora.doc.ProductDoc;
import com.aurora.pojo.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 15:11
 * @ DESCRIPTION 监控boot程序启动, 完成ES数据同步操作 (服务启动的时候执行)
 */
@Component      //加入ioc容器
@Slf4j
public class SpringBootListener implements ApplicationRunner {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ProductClient productClient;

    private String indexStr = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"productId\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productName\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"categoryId\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productTitle\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"productIntro\":{\n" +
            "        \"type\":\"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"productPicture\":{\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"productPrice\":{\n" +
            "        \"type\": \"double\",\n" +
            "        \"index\": true\n" +
            "      },\n" +
            "      \"productSellingPrice\":{\n" +
            "        \"type\": \"double\"\n" +
            "      },\n" +
            "      \"productNum\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productSales\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"all\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}\n";

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 15:13
     * @ param args
     * @ return none
     * @ description 在此方法, 完成ES数据同步操作
     * @ note 1.判断ES中的product索引是否存在
     *        2.不存在, 则创建1个
     *        3.存在, 删除原有数据
     *        4.查询商品全部数据
     *        5.进行ES数据库的更新工作
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        //1.判断ES中的product索引是否存在

        //查询条件 判断是否存在
        GetIndexRequest getIndexRequest = new GetIndexRequest("product");
        //执行数据库操作
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);

        //2.判断处理
        if (exists) {
            //存在 删除原有数据

            //查询条件 全部删除
            DeleteByQueryRequest queryRequest = new DeleteByQueryRequest("product");
            queryRequest.setQuery(QueryBuilders.matchAllQuery());
            //执行数据库操作
            restHighLevelClient.deleteByQuery(queryRequest, RequestOptions.DEFAULT);
        }
        else {
            //不存在 创建新的索引表

            //查询条件 创建新表
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("product");
            createIndexRequest.source(indexStr, XContentType.JSON);
            //执行数据库操作
            restHighLevelClient.indices().create(createIndexRequest,RequestOptions.DEFAULT);
        }

        //3.查询商品全部数据
        List<Product> productList = productClient.allList();

        //4.进行ES数据库的更新工作 批量插入数据

        //查询条件
        BulkRequest request = new BulkRequest();

        //JSON转换对象
        ObjectMapper objectMapper = new ObjectMapper();

        for (Product product : productList) {
            //将product 转换为 productDoc 对象, 多了一个all变量
            ProductDoc productDoc = new ProductDoc(product);

            //用于插入数据
            IndexRequest indexRequest = new IndexRequest("product").id(productDoc.getProductId().toString());
            //将productDoc转成JSON数据
            String json = objectMapper.writeValueAsString(productDoc);
            indexRequest.source(json,XContentType.JSON);

            request.add(indexRequest);
        }

        //执行数据库操作
        restHighLevelClient.bulk(request,RequestOptions.DEFAULT);
    }
}
