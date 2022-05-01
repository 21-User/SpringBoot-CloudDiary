package com.fc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultInfoVo {
    private Integer code;
    private String message;
    private Boolean success;
    private Object data;

}
