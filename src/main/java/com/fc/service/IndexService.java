package com.fc.service;

import com.fc.entity.TbNote;
import com.github.pagehelper.PageInfo;

public interface IndexService {
    PageInfo<TbNote> page(Integer userId, Integer id, String title, String date, Integer pageNum, Integer pageSize);
}
