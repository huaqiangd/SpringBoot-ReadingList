/**
 * @Title: TOTO
 * @Description: TOTO
 * @author donghuaqiang
 * @date 19-2-27 17:09
 */

package com.example;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//在服务器里运行测试(在1.4之前 @WebIntegrationTest)
@ContextConfiguration(locations = {"classpath*:scan.xml", "classpath*:mybatis/persist-mybatis.xml"})  //加载上下文
@AutoConfigureMockMvc     //自动 加载 MockMvc
@MapperScan({"com.example.**.dao"})
public class BasicTest {

    @Before
    public void start() {
        System.out.println("----------开始测试----------");
    }


    @After
    public void end() {
        System.out.println("----------结束测试----------");
    }
}
