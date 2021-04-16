package com.fawvw.cloud.common.api.dto;

import lombok.Data;

/**
 * @author HuangGang
 * @create 2021-01-28 20:23
 **/
@Data
public class StructureDTO extends BaseDTO{
    private Long structureId;
    private String structureCode;
    private String structureName;
    private Long pid;
    private String pic1;
    private String pic2;
    private Integer sequence;
    private Long clickNum;
    private String harvest;
    private String teacher;
    private Integer structureType;
}
