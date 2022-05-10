package com.fc.service;

import com.fc.entity.TbNote;
import com.fc.entity.TbNoteType;
import com.fc.vo.NoteVo;
import com.fc.vo.ResultInfoVo;

import java.util.List;

/**
 * @author: T21
 * @date: 2022/5/9.
 */
public interface NoteService {

    NoteVo findById(Integer id);

    ResultInfoVo add(TbNote tbNote);

    ResultInfoVo update(TbNote tbNote);

    ResultInfoVo delete(Integer id);
}
