package com.yj.springboot.service.config;

import java.text.*;
import java.util.Date;

/**
 * @author : Jason
 * @version : 1.0
 * @date : Created in 2018/10/25 11:04
 * @modified by :
 */
public class StandardDateTimeFormat extends DateFormat {

    private DateFormat dateFormat;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public StandardDateTimeFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    //序列化时会执行这个方法
    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        return dateFormat.format(date, toAppendTo, fieldPosition);
    }

    @Override
    public Date parse(String source, ParsePosition pos) {

        Date date;
        try {
            date = format.parse(source, pos);
        } catch (Exception e) {

            date = dateFormat.parse(source, pos);
        }
        return date;
    }

    //反序列化时执行此方法，我们先让他执行我们自己的format，如果异常则执执行他的
    //当然这里只是简单实现，可以有更优雅的方式来处理更多的格式
    @Override
    public Date parse(String source) throws ParseException {

        Date date;
        try {
            // 先按我的规则来
            date = format.parse(source);
        } catch (Exception e) {
            // 不行，那就按原先的规则吧
            date = dateFormat.parse(source);
        }
        return date;
    }

    @Override
    @SuppressWarnings("ALL")
    public Object clone() {
        Object format = dateFormat.clone();
        return new StandardDateTimeFormat((DateFormat) format);
    }
}
