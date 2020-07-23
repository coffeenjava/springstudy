package com.brian.springstudy.entity;

import com.brian.springstudy.support.convert.jpa.CustomEnumType;
import com.brian.springstudy.support.enums.BaseEnum;
import com.brian.springstudy.support.enums.YesNo;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@DynamicInsert
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Type(type = CustomEnumType.NAME) // JPA <-> DB 컨버팅에 사용될 타입 지정
    private UserGrade userGrade;

    private YesNo deleteYn;

    @Getter
    @AllArgsConstructor
    public enum UserGrade implements BaseEnum {
        NORMAL("N","일반회원"),
        GOLD("G","골드회원");

        @JsonValue
        private String code;
        private String desc;
    }
}
