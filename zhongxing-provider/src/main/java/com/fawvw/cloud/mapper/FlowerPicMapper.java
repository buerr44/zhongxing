package com.fawvw.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fawvw.cloud.common.api.dto.FlowerPicDTO;
import com.fawvw.cloud.entity.FlowerPic;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author HuangGang
 * @create 2021-03-31 10:56
 **/
public interface FlowerPicMapper extends BaseMapper<FlowerPic> {
    @Select("select * from tb_flowerpic")
    List<FlowerPicDTO> listPic();
}
