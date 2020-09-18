package com.yj.springboot.service.jsonSerialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @author yujiang
 * @version 2020.4.26
 * 使用时在实体字段上添加注解 @JsonSerialize(using = EnumJsonSerializer.class)
 * 前端就会返回
 * allotWorkMode: "SMART"
 * allotWorkModeRemark: "智能派单"
 * 这样的类型
 * 想返回另外的结构可以看浏览器书签
 */
public class EnumJsonSerializer extends JsonSerializer<Enum> {

    @Override
    public void serialize(Enum value, JsonGenerator generator, SerializerProvider serializers)
            throws IOException {
//        generator.writeStartObject();
        //自身的值
        generator.writeString(value.name());
        //新增属性：枚举类型+Remark（多个字段用到这个枚举值的话，生成的都是枚举值类的名字+remark，只会显示一个的；可以优化，也可以参考书签修改成其他的徐序列化格式）
        generator.writeFieldName(StringUtils.uncapitalize(value.getClass().getSimpleName()) + "Remark");
        //新增属性值
        // todo
       // generator.writeString(EnumUtils.getEnumItemRemark(value.getClass(), value));
        generator.writeString("remark的值");
//        generator.writeEndObject();
    }
}
