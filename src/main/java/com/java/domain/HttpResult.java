package com.java.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HttpResult {

    private Integer resultCode;
    private String msg;
    private Object object;

}
