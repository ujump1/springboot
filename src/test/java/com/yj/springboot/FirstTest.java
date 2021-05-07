package com.yj.springboot;

import com.yj.springboot.service.utils.LogUtil;
import org.junit.Test;

public class FirstTest extends BaseTest {

    /**
     * 测试
     */
    @Test
    public void test(){
        LogUtil.info("123");
        System.out.println("1234");
    }
}
