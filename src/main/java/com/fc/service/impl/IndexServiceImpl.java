package com.fc.service.impl;

import com.fc.dao.TbNoteMapper;
import com.fc.entity.TbNote;
import com.fc.service.IndexService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private TbNoteMapper noteMapper;

    @Override
    public PageInfo<TbNote> page(Integer pageNum, Integer pageSize, Integer userId, Integer typeId, String title, String date) {
        if (title != null && !title.equals("")) {
            title = "%" + title + "%";
        }

        PageHelper.startPage(pageNum, pageSize);

        List<TbNote> notes = noteMapper.findByIdPage(userId, typeId, title, date);

        return new PageInfo<>(notes);
    }

    @Override
    public List<TbNote> findNoteCountByDate(Integer id) {

        return noteMapper.findNoteCountByDate(id);
    }

    @Override
    public List<TbNote> findByType(Integer id) {

        return noteMapper.findByType(id);
    }
}
