package com.brian.springstudy.controller.dto;

import com.brian.springstudy.entity.User;
import com.brian.springstudy.support.enums.YesNo;
import lombok.Data;

@Data
public class UserDto {
    private Long userId;
    private User.UserGrade userGrade;
    private YesNo deleteYn;

    public static UserDto getSample() {
        UserDto dto = new UserDto();
        dto.userGrade = User.UserGrade.GOLD;
        dto.deleteYn = YesNo.NO;
        return dto;
    }
}
