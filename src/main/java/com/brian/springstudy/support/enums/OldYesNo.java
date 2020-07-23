package com.brian.springstudy.support.enums;

import com.brian.springstudy.exception.UnknownEnumCodeException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum OldYesNo implements BaseEnum {
    YES("Y"),
    NO("N");

    @JsonValue // Serialize
    private String code;

    private static final Map<String, OldYesNo> CODE_LOOKUP = new HashMap<>();
    static {
        Arrays.stream(OldYesNo.values()).forEach(e -> CODE_LOOKUP.put(e.code, e));
    }

    @JsonCreator // Deserialize
    public static OldYesNo getByCode(String code) {
        OldYesNo e =  CODE_LOOKUP.get(code);

        if (e == null) throw new UnknownEnumCodeException(OldYesNo.class, code);
        return e;
    }

//    @JsonCreator // Deserialize
//    public static OldYesNo getByCode(String code) {
//        for (OldYesNo e : OldYesNo.class.getEnumConstants()) {
//            if (e.getCode().equals(code)) return e;
//        }
//        throw new UnknownEnumCodeException(OldYesNo.class, code);
//
//        // 이놈은 valueOf 기반이라 code 값 처리가 불가.
////        return Enum.valueOf(OldYesNo.class, code);
//    }
}