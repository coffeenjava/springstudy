package com.brian.springstudy.support.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YesNo implements BaseEnum {
    YES("Y"),
    NO("N"),
    NONE(null);

    @JsonValue // Serialize & Deserialize
    private String code;
}
