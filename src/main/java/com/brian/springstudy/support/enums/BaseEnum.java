package com.brian.springstudy.support.enums;

import com.brian.springstudy.exception.UnknownEnumCodeException;

/**
 * Enum 공통 활용에 사용할 인터페이스
 */
public interface BaseEnum {
    String getCode();

    static <T extends BaseEnum> T getEnum(Class<T> cls, String code) {
        for (BaseEnum e : cls.getEnumConstants()) {
            if (code.equals(e.getCode())) return (T) e;
        }
        
        throw new UnknownEnumCodeException(cls, code);
    }
}
