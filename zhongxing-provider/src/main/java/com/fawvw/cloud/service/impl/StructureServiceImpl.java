package com.fawvw.cloud.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fawvw.cloud.common.api.StructureApi;
import com.fawvw.cloud.common.api.dto.StructureDTO;
import com.fawvw.cloud.common.api.result.ApiResult;
import com.fawvw.cloud.entity.Course;
import com.fawvw.cloud.entity.Structure;
import com.fawvw.cloud.mapper.CourseMapper;
import com.fawvw.cloud.mapper.StructureMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author HuangGang
 * @create 2021-01-29 15:07
 **/
@Slf4j
@Service(version = "${dubbo.provider.version}", application = "${dubbo.application.id}")
public class StructureServiceImpl implements StructureApi {
    @Autowired
    private StructureMapper structureMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public ApiResult<List<StructureDTO>> listByPid(Long pid) {
        ApiResult<List<StructureDTO>> apiResult = new ApiResult<List<StructureDTO>>();

        QueryWrapper<Structure> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",pid).eq("is_valid","0");
        List<Structure> structures = new ArrayList<>();
        try{
            structures = structureMapper.selectList(queryWrapper);
            List<StructureDTO> structureDTOS = new ArrayList<>();
            if(structures != null){
                for (int i = 0; i < structures.size(); i++) {
                    StructureDTO structureDTO = new StructureDTO();
                    Structure structure = structures.get(i);
                    structureDTO.setStructureId(structure.getStructureId());
                    structureDTO.setStructureName(structure.getStructureName());
                    structureDTO.setSequence(structure.getSequence());
                    structureDTO.setHarvest(structure.getHarvest());
                    structureDTO.setDescription(structure.getDescription());
                    structureDTO.setTeacher(structure.getTeacher());
                    structureDTO.setStatus(structure.getStatus());
                    structureDTOS.add(structureDTO);
                }
            }
            apiResult = ApiResult.success(structureDTOS);
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiResult<String> upload(StructureDTO structureDTO) {
        ApiResult<String> apiResult;

        Structure structure = new Structure();
        structure.setStructureName(structureDTO.getStructureName());
        structure.setHarvest(structureDTO.getHarvest());
        structure.setSequence(structureDTO.getSequence());
        structure.setTeacher(structureDTO.getTeacher());
        structure.setPid(structureDTO.getPid());
        structure.setPic1(structureDTO.getPic1());
        structure.setPic2(structureDTO.getPic2());
        structure.setCreateBy(structureDTO.getCreateBy());
        structure.setDescription(structureDTO.getDescription());
        structure.setCreateBy(structureDTO.getCreateBy());
        structure.setStatus(structureDTO.getStatus());
        structure.setCreateDate(new Date());
        //读取顺序生成代码;
        Structure selectById = structureMapper.selectById(structureDTO.getPid());
        if(selectById != null) {
            if (structureDTO.getSequence() < 10) {
                structure.setStructureCode(selectById.getStructureCode() + "0" + structureDTO.getSequence());
            } else {
                structure.setStructureCode(selectById.getStructureCode() + structureDTO.getSequence());
            }
        }
        try{
            int insert = structureMapper.insert(structure);
            if(insert == 1){
                apiResult = ApiResult.success();
            }else{
                apiResult = ApiResult.error("B0300","未正常插入数据");
            }
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiResult<String> edit(StructureDTO structureDTO) {
        ApiResult<String> apiResult;

        UpdateWrapper<Structure> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("structure_name",structureDTO.getStructureName());
        updateWrapper.set("harvest",structureDTO.getHarvest());
        updateWrapper.set("sequence",structureDTO.getSequence());
        updateWrapper.set("teacher",structureDTO.getTeacher());
        updateWrapper.set("pid",structureDTO.getPid());
        updateWrapper.set("description",structureDTO.getDescription());
        updateWrapper.set("last_update_by",structureDTO.getLastUpdateBy());
        updateWrapper.set("status",structureDTO.getStatus());
        updateWrapper.set("last_update_date",new Date());
        if(structureDTO.getPic1() != null && !"".equals(structureDTO.getPic1().trim())){
            updateWrapper.set("pic1",structureDTO.getPic1());
        }
        if(structureDTO.getPic2() != null && !"".equals(structureDTO.getPic2().trim())){
            updateWrapper.set("pic2",structureDTO.getPic2());
        }
        Structure selectById = structureMapper.selectById(structureDTO.getPid());
        String structureCode = "";
        if(selectById != null) {
            if (structureDTO.getSequence() < 10) {
                structureCode = selectById.getStructureCode() + "0" + structureDTO.getSequence();
                updateWrapper.set("structure_code",structureCode);
            }else{
                structureCode = selectById.getStructureCode() + structureDTO.getSequence();
                updateWrapper.set("structure_code",structureCode);
            }
        }
        updateWrapper.eq("structure_id",structureDTO.getStructureId());
        try{
            int update = structureMapper.update(null, updateWrapper);
            if(update == 1){
                //更改相关小类code
                if(structureDTO.getStructureType() == 1){
                    QueryWrapper<Structure> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("pid",structureDTO.getStructureId());
                    List<Structure> structures = structureMapper.selectList(queryWrapper);
                    if(structures != null && !structures.isEmpty()) {
                        for (Structure structure : structures) {
                            UpdateWrapper<Structure> updateWrapper1 = new UpdateWrapper<>();
                            if(structure.getSequence() < 10) {
                                updateWrapper1.set("structure_code", structureCode + "0" + structure.getSequence());
                            }else {
                                updateWrapper1.set("structure_code", structureCode + structure.getSequence());
                            }
                            updateWrapper1.set("last_update_by",structureDTO.getLastUpdateBy());
                            updateWrapper1.set("last_update_date",new Date());
                            updateWrapper1.eq("structure_id",structure.getStructureId());
                            structureMapper.update(null,updateWrapper1);
                        }
                    }
                }
                apiResult = ApiResult.success();
            }else{
                apiResult = ApiResult.error("B0300","未正常修改数据");
            }
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiResult<String> delete(Long structureId, Long lastUpdateBy) {
        ApiResult<String> apiResult;

        UpdateWrapper<Structure> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_valid","-1").set("last_update_by",lastUpdateBy).set("last_update_date",new Date()).eq("structure_id",structureId);
        try {
            int update = structureMapper.update(null, updateWrapper);
            if (update == 1) {
                QueryWrapper<Structure> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("pid",structureId);
                List<Structure> structures = structureMapper.selectList(queryWrapper);
                if(structures != null && !structures.isEmpty()) {
                    for (Structure structure : structures) {
                        UpdateWrapper<Structure> updateWrapper1 = new UpdateWrapper<>();
                        updateWrapper1.set("is_valid","-1");
                        updateWrapper1.set("last_update_by",lastUpdateBy);
                        updateWrapper1.set("last_update_date",new Date());
                        updateWrapper1.eq("structure_id",structure.getStructureId());
                        structureMapper.update(null,updateWrapper1);
                    }
                }
                apiResult = ApiResult.success();
            } else {
                apiResult = ApiResult.error("B0300", "未正常修改数据");
            }
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiResult<String> editSequence(Long structureId, Integer sequence, Long lastUpdateBy) {
        ApiResult<String> apiResult;

        Structure structure1 = structureMapper.selectById(structureId);
        String structureCode = structure1.getStructureCode();
        String newCode = "";
        if(structureCode != null && !"".equals(structureCode.trim())){
            if(sequence < 10){
                newCode = structureCode.substring(0,structureCode.length()-2) + "0" + sequence;
            }else {
                newCode = structureCode.substring(0,structureCode.length()-2) + sequence;
            }
        }
        UpdateWrapper<Structure> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("sequence",sequence);
        updateWrapper.set("structure_code",newCode);
        updateWrapper.set("last_update_by",lastUpdateBy);
        updateWrapper.set("last_update_date",new Date());
        updateWrapper.eq("structure_id",structureId);
        try {
            int update = structureMapper.update(null, updateWrapper);
            if (update == 1) {
                QueryWrapper<Structure> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("pid",structureId);
                List<Structure> structures = structureMapper.selectList(queryWrapper);
                if(structures != null && !structures.isEmpty()) {
                    //为中类,修改小类code
                    for (Structure structure : structures) {
                        //修改类别代码
                        UpdateWrapper<Structure> updateWrapper1 = new UpdateWrapper<>();
                        String oldCode = structure.getStructureCode();
                        String newCode1 = "";
                        if(oldCode != null && !"".equals(oldCode.trim())){
                            newCode1 = newCode + oldCode.substring(oldCode.length()-2,oldCode.length());
                        }
                        updateWrapper1.set("structure_code",newCode1);
                        updateWrapper1.set("last_update_by",lastUpdateBy);
                        updateWrapper1.set("last_update_date",new Date());
                        updateWrapper1.eq("structure_id",structure.getStructureId());
                        structureMapper.update(null,updateWrapper1);
                        //修改小类下课程code
                        structure.setStructureCode(newCode1);
                        changeCourseCode(structure);
                    }
                }else{
                    //为小类, 修改其下课程Code
                    structure1.setStructureCode(newCode);
                    changeCourseCode(structure1);
                }
                apiResult = ApiResult.success();
            } else {
                apiResult = ApiResult.error("B0300", "未正常修改数据");
            }
        }catch (Exception e){
            log.error(e.getMessage());
            apiResult = ApiResult.error("B0300",e.getMessage());
        }

        return apiResult;
    }

    private void changeCourseCode(Structure structure) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if(structure != null) {
            queryWrapper.eq("structure_id", structure.getStructureId());
            List<Course> courses = courseMapper.selectList(queryWrapper);
            for (Course course : courses) {
                String oldCode = course.getCourseCode();
                String newCode = structure.getStructureCode() + oldCode.substring(oldCode.length() - 2, oldCode.length());
                course.setCourseCode(newCode);
                courseMapper.updateById(course);
            }
        }
    }

}


