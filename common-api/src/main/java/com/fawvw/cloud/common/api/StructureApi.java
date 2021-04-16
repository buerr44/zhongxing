package com.fawvw.cloud.common.api;

import com.fawvw.cloud.common.api.dto.StructureDTO;
import com.fawvw.cloud.common.api.result.ApiResult;

import java.util.List;

/**
 * @author HuangGang
 * @create 2021-01-29 14:45
 **/
public interface StructureApi {
    /**
     * 根据pid查询分类清单
     * @param pid
     * @return
     */
    ApiResult<List<StructureDTO>> listByPid(Long pid);

    /**
     * 分类添加
     * @param structureDTO
     * @return
     */
    ApiResult<String> upload(StructureDTO structureDTO);

    /**
     * 分类修改
     * @param structureDTO
     * @return
     */
    ApiResult<String> edit(StructureDTO structureDTO);

    /**
     * 分类删除
     * @param structureId
     * @param lastUpdateBy
     * @return
     */
    ApiResult<String> delete(Long structureId,Long lastUpdateBy);

    /**
     * 更改分类顺序
     * @param structureId
     * @param sequence
     * @param lastUpdateBy
     * @return
     */
    ApiResult<String> editSequence(Long structureId,Integer sequence,Long lastUpdateBy);
}
