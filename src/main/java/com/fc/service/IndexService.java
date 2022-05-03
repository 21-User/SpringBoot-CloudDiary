package com.fc.service;

import com.fc.entity.TbNote;
import com.github.pagehelper.PageInfo;

public interface IndexService {
    PageInfo<TbNote> page(Integer userId, Integer typeId, String title, String date, Integer pageNum, Integer pageSize);
}
