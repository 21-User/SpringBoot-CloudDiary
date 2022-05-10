package com.fc.service;

import com.fc.entity.TbNote;

import java.util.List;
import java.util.Map;

/**
 * @author: T21
 * @date: 2022/5/8.
 */
public interface ReportService {
    List<TbNote> getLocation(Integer id);

    Map<String, Object> getMonth(Integer id);
}
