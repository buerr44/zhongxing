package com.fawvw.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fawvw.cloud.common.api.dto.FlowerWordDTO;
import com.fawvw.cloud.entity.FlowerWord;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author HuangGang
 * @create 2021-03-31 15:06
 **/
public interface FlowerWordMapper extends BaseMapper<FlowerWord> {
    @Select("select * from tb_flowerword where is_valid = 0")
    List<FlowerWordDTO> listWord();
}
