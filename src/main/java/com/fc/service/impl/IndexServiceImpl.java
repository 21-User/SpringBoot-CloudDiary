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
    public PageInfo<TbNote> page(Integer userId, Integer id, String title, String date, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        //通过userId查找
        List<TbNote> notes = noteMapper.findByIdPage(userId, id, title, date);

        return new PageInfo<>(notes);
    }
}
