package com.brian.springstudy.support.convert.jpa;


import com.brian.springstudy.support.enums.BaseEnum;
import com.brian.springstudy.support.enums.YesNo;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * YesNo 는 여러군데서 사용되므로 @Type(type = CustomEnumType.NAME) 으로
 * 매번 정의하지 않고 AttributeConverter 로 생성하여 사용
 */
@Converter(autoApply = true) // Entity 의 YesNo 필드에 자동 적용
public class YesNoConverter implements AttributeConverter<YesNo, String> {

    @Override
    public String convertToDatabaseColumn(YesNo e) {
        return e.getCode();
    }

    @Override
    public YesNo convertToEntityAttribute(String c) {
        return BaseEnum.getEnum(YesNo.class, c);
    }
}
