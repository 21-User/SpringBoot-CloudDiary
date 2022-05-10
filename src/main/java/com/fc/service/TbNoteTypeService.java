package com.fc.service;

import com.fc.entity.TbNoteType;
import com.fc.vo.ResultInfoVo;

import java.util.List;

public interface TbNoteTypeService {
    List<TbNoteType> findNoteType(Integer id);

    ResultInfoVo add(TbNoteType noteType);

    ResultInfoVo update(TbNoteType noteType);

    ResultInfoVo delete(Integer id);
}
