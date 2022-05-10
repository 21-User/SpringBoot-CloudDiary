package com.fc.service.impl;

import com.fc.dao.ReportMapper;
import com.fc.entity.TbNote;
import com.fc.service.ReportService;
import com.fc.vo.NoteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: T21
 * @date: 2022/5/8.
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportMapper repostMapper;

    @Override
    public List<TbNote> getLocation(Integer id) {

        return repostMapper.getLocation(id);
    }

    @Override
    public Map<String, Object> getMonth(Integer id) {
        Map<String, Object> map;

        List<NoteVo> noteVos = repostMapper.getMonth(id);

        map = new HashMap<>();

        if (noteVos != null && noteVos.size() > 0) {
            List<String> months = new ArrayList<>();
            List<Integer> noteCounts = new ArrayList<>();


            for (NoteVo noteVo : noteVos) {
                months.add(noteVo.getGroupName());
                noteCounts.add(noteVo.getNoteCount());
            }

            map.put("monthArray", months);
            map.put("dataArray", noteCounts);
        }

        return map;
    }
}
