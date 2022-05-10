package com.fc.service.impl;

import com.fc.dao.TbNoteMapper;
import com.fc.entity.TbNote;
import com.fc.entity.TbNoteType;
import com.fc.service.NoteService;
import com.fc.service.TbNoteTypeService;
import com.fc.vo.NoteVo;
import com.fc.vo.ResultInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: T21
 * @date: 2022/5/9.
 */
@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private TbNoteMapper noteMapper;

    @Override
    public NoteVo findById(Integer id) {
        return noteMapper.findById(id);
    }

    @Override
    public ResultInfoVo add(TbNote tbNote) {
        ResultInfoVo vo;

        tbNote.setPubTime(new Date());

        int affectedRows = noteMapper.insertSelective(tbNote);

        if (affectedRows > 0) {
            vo = new ResultInfoVo(1, "日记添加成功", true, tbNote);
        } else {
            vo = new ResultInfoVo(0, "日记添加失败", false, null);
        }

        return vo;
    }

    @Override
    public ResultInfoVo update(TbNote tbNote) {
        ResultInfoVo vo;

        //更新发布时间
        tbNote.setPubTime(new Date());

        int affectedRows = noteMapper.updateByPrimaryKeyWithBLOBs(tbNote);

        if (affectedRows > 0) {
            vo = new ResultInfoVo(1, "日记修改成功", true, tbNote);
        } else {
            vo = new ResultInfoVo(0, "日记修改失败", false, null);
        }

        return vo;
    }

    @Override
    public ResultInfoVo delete(Integer id) {
        ResultInfoVo infoVo = new ResultInfoVo();

        int affectedRows = noteMapper.deleteByPrimaryKey(id);

        if (affectedRows > 0) {
            infoVo.setCode(1);
        } else {
            infoVo.setCode(0);
        }

        return infoVo;
    }

}
