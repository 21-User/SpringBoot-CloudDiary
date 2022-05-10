package com.fc.service;

import com.fc.entity.TbNote;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IndexService {
    PageInfo<TbNote> page(Integer pageNum, Integer pageSize, Integer userId, Integer typeId, String title, String date);

    List<TbNote> findNoteCountByDate(Integer id);

    List<TbNote> findByType(Integer id);
}
