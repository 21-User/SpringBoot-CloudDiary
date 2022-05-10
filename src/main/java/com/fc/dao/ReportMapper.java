package com.fc.dao;

import com.fc.entity.TbNote;
import com.fc.vo.NoteVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: T21
 * @date: 2022/5/8.
 */
@Mapper
public interface ReportMapper {
    List<TbNote> getLocation(@Param("id") Integer id);

    List<NoteVo> getMonth(@Param("userId") Integer userId);
}
