package com.yhb.tired.sys.controller;

import com.yhb.tired.article.pojo.ArticleContent;
import com.yhb.tired.article.pojo.ArticleContentWithBLOBs;
import com.yhb.tired.article.service.ArticleContentService;
import com.yhb.tired.common.pojo.common.JsonMessage;
import com.yhb.tired.common.utils.CommonUtil;
import com.yhb.tired.sys.pojo.User;
import com.yhb.tired.sys.service.UserService;
import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

/**
 * @Auther: Administrator
 * @Date: 2018/11/23 11:50
 * @Description:
 */
@RestController
@RequestMapping("test")
public class TestController extends BaseController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleContentService articleContentService;
    /**
     * 对elasticsearch的客户端操作
     */
    @Autowired
    private JestClient jestClient;
    /**
     *  spring-data-elasticsearch的操作
     */
    private ElasticsearchTemplate elasticsearchTemplate;
    /**
     * rabbitmq test -start
     */
    @RequestMapping("test1")
    public String test1() {
        String s = CommonUtil.getUUID();
        System.out.println(s);
        try {
            rabbitTemplate.convertAndSend("exchange-direct", "atguigu", s);
            return "";
        } catch (AmqpException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @RequestMapping("test2")
    public JsonMessage test2() {
        JsonMessage jsonMessage = new JsonMessage();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            Object o = rabbitTemplate.receiveAndConvert("atguigu");
            if (o == null) {
                return new JsonMessage(false, "连接超时,请重试", "");
            }
            list.add(o.toString());
        }
        jsonMessage.setSuccess(true);
        jsonMessage.setMessage("查询秒杀队列成功");
        jsonMessage.setObject(list);
        return jsonMessage;
    }
    /**
     * rabbitmq test -end
     */

    /**
     * elasticsearch jest -start
     * 向elasticsearch添加用户数据
     */
    @RequestMapping("test3")
    public void test3() {
        List<User> list = userService.getAllUsers();
        Index build = new Index.Builder(list).index("all").type("users").build();
        try {
            DocumentResult result = jestClient.execute(build);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对users的查询
     */
    @RequestMapping("test4")
    public void test4() {
        //查询表达式
        String json = "{\n" +
                "    \"query\" : {\n" +
                "        \"match_phrase\" : {\n" +
                "            \"nickname\" : \"浪里小白龙\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"highlight\": {\n" +
                "        \"fields\" : {\n" +
                "            \"nickname\" : {}\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "'\n";
        //构造搜索条件
        Search search = new Search.Builder(json).addIndex("all").addType("users").build();
        //执行查询
        try {
            SearchResult result = jestClient.execute(search);
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("test5")
    public void test5() {
        List<ArticleContentWithBLOBs> list = articleContentService.getAll();
        Index build = new Index.Builder(list).index("blog").type("articles").build();
        try {
            DocumentResult result = jestClient.execute(build);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对blog内容的查询
     */
    @RequestMapping("test6")
    public void test6() {
        //查询表达式
        String json = "{\n" +
                "    \"query\" : {\n" +
                "        \"match_phrase\" : {\n" +
                "            \"htmlContent\" : \"47.99.84.178\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"highlight\": {\n" +
                "        \"fields\" : {\n" +
                "            \"htmlContent\" : {}\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "'\n";
        //构造搜索条件
        Search search = new Search.Builder(json).addIndex("blog").addType("articles").build();
        //执行查询
        try {
            SearchResult result = jestClient.execute(search);
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * elasticsearch jest -end
     */

    /**
     * springdata-elasticsearch test start
     */
    @RequestMapping("test7")
    public void test7(){
    }
    /**
     * springdata-elasticsearch test end
     */
}
