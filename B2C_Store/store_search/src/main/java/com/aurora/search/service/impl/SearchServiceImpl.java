package com.aurora.search.service.impl;

import com.aurora.doc.ProductDoc;
import com.aurora.parama.ProductSearchParam;
import com.aurora.pojo.Product;
import com.aurora.search.service.SearchSecrvice;
import com.aurora.utils.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 16:05
 * @ DESCRIPTION
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchSecrvice {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 16:04
     * @ param
     * @ return
     * @ description 根据关键字和分页进行数据库数据查询
     * @ note 1.判断关键字是否为空 空则查询全部
     *        2.添加分页属性
     *        3.ES查询
     *        4.结果处理
     */
    @Override
    public R search(ProductSearchParam productSearchParam) {

        //1.查询条件
        SearchRequest searchRequest = new SearchRequest("product");

        //获取查询关键字
        String search = productSearchParam.getSearch();
        if (StringUtils.isEmpty(search)) {
            //为空 不添加all关键字, 查询全部
            searchRequest.source().query(QueryBuilders.matchAllQuery());
        }
        else {
            //不为空 添加all匹配 根据关键字字段在all里进行匹配
            searchRequest.source().query(QueryBuilders.matchQuery("all", search));
        }

        //进行分页数据添加
        searchRequest.source().from((productSearchParam.getCurrentPage()-1)*productSearchParam.getPageSize());  //偏移量 (当前页数-1)*页容量
        searchRequest.source().size(productSearchParam.getPageSize());

        //2.数据库查询
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("查询错误");
        }

        //返回对象 R msg total(符合的数量) 商品集合
        //查询所得数据一层套一层
        SearchHits hits = searchResponse.getHits();

        //查询符合的数量 total
        long value = hits.getTotalHits().value;

        //数据集合
        SearchHit[] hitsHits = hits.getHits();

        //JSON处理器
        ObjectMapper objectMapper = new ObjectMapper();

        List<Product> productList = new ArrayList<>();
        for (SearchHit hitsHit : hitsHits) {
            //查询的内容数据 productDoc模型对应的JSON数据
            String sourceAsString = hitsHit.getSourceAsString();

            Product product = null;
            try {
                //productDoc 多一个属性all product没有all, 此处会报错 解决:jakson提供忽略属性的注解
                product = objectMapper.readValue(sourceAsString, Product.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            productList.add(product);
        }

        //3.结果处理
        R ok = R.ok(null, productList, value);
        log.info("SearchServiceImpl.search业务结束, 结果{}",ok);
        return ok;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 15:37
     * @ param product 商品
     * @ return
     * @ description 商品同步: 插入和更新时
     */
    @Override
    public R save(Product product) throws IOException {
        //查询条件
        IndexRequest indexRequest = new IndexRequest("product").id(product.getProductId().toString());

        //product对象转为 ES数据库需要的带有额外all参数的 productDoc对象
        ProductDoc productDoc = new ProductDoc(product);

        //将productDoc转为JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productDoc);

        indexRequest.source(json, XContentType.JSON);

        //ES数据库执行操作
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        return R.ok("ES数据库数据同步成功");
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 15:42
     * @ param productId
     * @ return
     * @ description 进行ES数据库的商品删除
     */
    @Override
    public R remove(Integer productId) throws IOException {
        //查询条件
        DeleteRequest request = new DeleteRequest("product").id(productId.toString());


        //ES数据库执行操作
        restHighLevelClient.delete(request,RequestOptions.DEFAULT);

        return R.ok("ES数据库数据删除成功");
    }
}

/*
* 查询所得JSON数据格式
* {
  "took" : 0,
  "timed_out" : false,
  "_shards" : {
    "total" : 1,
    "successful" : 1,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 5,
      "relation" : "eq"
    },
    "max_score" : 2.5164092,
    "hits" : [
      {
        "_index" : "product",
        "_type" : "_doc",
        "_id" : "12",
        "_score" : 2.5164092,
        "_source" : {
          "all" : "小米电视4X 43英寸人工智能语音系统 | FHD全高清屏 | 64位四核处理器 | 海量片源 | 1GB+8GB大内存 | 钢琴烤漆FHD全高清屏，人工智能语音",
          "product_id" : 12,
          "product_name" : "小米电视4X 43英寸",
          "category_id" : "2",
          "product_title" : "FHD全高清屏，人工智能语音",
          "product_intro" : "人工智能语音系统 | FHD全高清屏 | 64位四核处理器 | 海量片源 | 1GB+8GB大内存 | 钢琴烤漆",
          "product_picture" : "public/imgs/appliance/MiTv-4X-43.png",
          "product_price" : 1499.0,
          "product_selling_price" : 1299.0,
          "product_num" : 10,
          "product_sales" : 0
        }
      }
    ]
  }
}
*
* */
