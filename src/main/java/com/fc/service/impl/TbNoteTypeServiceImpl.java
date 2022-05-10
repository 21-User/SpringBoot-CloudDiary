package com.fc.service.impl;

import com.fc.dao.TbNoteMapper;
import com.fc.dao.TbNoteTypeMapper;
import com.fc.entity.TbNote;
import com.fc.entity.TbNoteExample;
import com.fc.entity.TbNoteType;
import com.fc.entity.TbNoteTypeExample;
import com.fc.service.TbNoteTypeService;
import com.fc.vo.ResultInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbNoteTypeServiceImpl implements TbNoteTypeService {
    @Autowired
    private TbNoteTypeMapper typeMapper;

    @Autowired
    private TbNoteMapper noteMapper;

    @Override
    public List<TbNoteType> findNoteType(Integer id) {
//        TbNoteTypeExample example = new TbNoteTypeExample();
//
//        TbNoteTypeExample.Criteria criteria = example.createCriteria();
//
//        criteria.andIdEqualTo(id);
//
//        return typeMapper.selectByExample(example);

        return typeMapper.findByUserId(id);
    }

    @Override
    public ResultInfoVo add(TbNoteType noteType) {
        ResultInfoVo vo = new ResultInfoVo();

        int affectedRows = typeMapper.insertSelective(noteType);

        if (affectedRows > 0) {
            vo.setMessage("添加成功");
            vo.setCode(1);
            vo.setSuccess(true);
            vo.setData(noteType.getId());
        } else {
            vo.setMessage("添加失败");
            vo.setCode(0);
            vo.setSuccess(false);
        }

        return vo;
    }

    @Override
    public ResultInfoVo update(TbNoteType noteType) {
        ResultInfoVo vo = new ResultInfoVo();

        int affectedRows = typeMapper.updateByPrimaryKeySelective(noteType);

        if (affectedRows > 0) {
            vo.setMessage("修改成功");
            vo.setCode(1);
            vo.setSuccess(true);
            vo.setData(noteType.getId());
        } else {
            vo.setMessage("修改失败");
            vo.setCode(0);
            vo.setSuccess(false);
        }

        return vo;
    }

    @Override
    public ResultInfoVo delete(Integer id) {
        ResultInfoVo vo = new ResultInfoVo();

        TbNoteExample example = new TbNoteExample();

        TbNoteExample.Criteria criteria = example.createCriteria();

        criteria.andTypeIdEqualTo(id);

        List<TbNote> notes = noteMapper.selectByExample(example);

        if (notes.size() > 0) {
            vo.setMessage("删除失败,当前类型下还有日记不能删除");
            vo.setCode(0);
        } else {
            int affectedRows = typeMapper.deleteByPrimaryKey(id);

            if (affectedRows > 0) {
                vo.setMessage("删除成功");
                vo.setSuccess(true);
                vo.setCode(1);
            } else {
                vo.setMessage("删除失败");
                vo.setSuccess(false);
                vo.setCode(0);
            }
        }

        return vo;
    }
}
