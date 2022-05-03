package com.fc.service.impl;

import com.fc.dao.TbNoteTypeMapper;
import com.fc.entity.TbNoteType;
import com.fc.entity.TbUser;
import com.fc.service.TbNoteTypeService;
import com.fc.vo.ResultInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class TbNoteTypeServiceImpl implements TbNoteTypeService {
    @Autowired
    private TbNoteTypeMapper typeMapper;

    @Override
    public List<TbNoteType> findAllById(HttpSession session, Integer id) {
//        new
//
//        TbNoteType tbNoteType1 = typeMapper.findAllByUserId(tbNoteType.getUserId());

        TbUser user = (TbUser)session.getAttribute("user");

        List<TbNoteType> list = typeMapper.findAllByUserId(user.getId());

        return list;
    }

    @Override
    public ResultInfoVo addOrUpdate(TbNoteType tbNoteType) {
        ResultInfoVo infoVo;

        Integer id = tbNoteType.getId();

        //如果类型id不为空就修改
        if (id != null) {
            int affectedRows = typeMapper.updateByPrimaryKeySelective(tbNoteType);

            if (affectedRows > 0) {
                TbNoteType types = typeMapper.selectByPrimaryKey(tbNoteType.getId());

                infoVo = new ResultInfoVo(1, "修改成功", true, types);
            } else {
                infoVo = new ResultInfoVo(0, "修改失败", false, null);
            }
        } else {
            //如果类型id为空就添加
            int affectedRows = typeMapper.insert(tbNoteType);

            if (affectedRows > 0) {
                TbNoteType types = typeMapper.selectByPrimaryKey(tbNoteType.getId());

                infoVo = new ResultInfoVo(1, "添加成功", true, types);
            } else {
                infoVo = new ResultInfoVo(0, "添加失败", false, null);
            }
        }

        return infoVo;
    }
}
