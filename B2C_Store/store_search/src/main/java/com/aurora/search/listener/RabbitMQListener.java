package com.aurora.search.listener;

import com.aurora.doc.ProductDoc;
import com.aurora.pojo.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 17:06
 * @ DESCRIPTION 监听消息通知
 */
@Component      //加入ioc容器
public class RabbitMQListener {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 17:07
     * @ param product 商品数据
     * @ return none
     * @ description 监听并且插入数据
     * @ note @RabbitListener注解 : Rabbit配置
     */
    @RabbitListener(
            bindings = {
                    @QueueBinding(
                            value = @Queue(name = "insert.queue"),
                            exchange = @Exchange("topic.ex"),
                            key = "product.insert"
                    )
            }
    )
    public void insert(Product product) throws IOException {

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
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 17:19
     * @ param productId 商品ID
     * @ return none
     * @ description 监听并且删除数据
     * @ note @RabbitListener注解 : Rabbit配置
     */
    @RabbitListener(
            bindings = {
                    @QueueBinding(
                            value = @Queue(name = "remove.queue"),
                            exchange = @Exchange("topic.ex"),
                            key = "product.remove"
                    )
            }
    )
    public void remove(Integer productId) throws IOException {

        //查询条件
        DeleteRequest request = new DeleteRequest("product").id(productId.toString());


        //ES数据库执行操作
        restHighLevelClient.delete(request,RequestOptions.DEFAULT);
    }
}
