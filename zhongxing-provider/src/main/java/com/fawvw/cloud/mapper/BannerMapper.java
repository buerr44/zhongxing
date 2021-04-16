package com.fawvw.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fawvw.cloud.common.api.dto.BannerDTO;
import com.fawvw.cloud.common.api.dto.FlowerPicDTO;
import com.fawvw.cloud.entity.Banner;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author HuangGang
 * @create 2021-03-31 19:00
 **/
public interface BannerMapper extends BaseMapper<Banner> {
    @Select("select maquee_id as banner_id,pic from tb_maquee")
    List<BannerDTO> listBanner();
}
