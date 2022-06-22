package com.yj.springboot.service.config;

import com.itextpdf.text.Font;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;

/**
 * <p>描述: [实现功能] </p>
 * <p>创建时间: 2021/6/16 11:00 </p>
 *
 * @author <a href="mailto:xiangjie.xie@changhong.com">谢祥杰</a>
 * @version 1.0.0
 */
public class AsianFontProvider extends XMLWorkerFontProvider {

    @Override
    public Font getFont(final String fontname, String encoding, float size, final int style) {
        String fntname = fontname;
        if (fntname == null) {
            /*使用的windows里的宋体，可将其文件放资源文件中引入
             *请确保simsun.ttc字体在windows下支持
             *我是将simsun.ttc字体打进maven的jar包中使用
             * 一定要设置html->body->font-family:SimSun
             */
            fntname = "simsun.ttc";
        }
        if (size == 0) {
            size = 4;
        }
        return super.getFont(fntname, encoding, size, style);
    }
}
