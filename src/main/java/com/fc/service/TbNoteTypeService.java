package com.fc.service;

import com.fc.entity.TbNoteType;
import com.fc.vo.ResultInfoVo;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface TbNoteTypeService {
    List<TbNoteType> findAllById(HttpSession session, Integer id);

    ResultInfoVo addOrUpdate(TbNoteType tbNoteType);
}
