package com.fc.controller;

import com.fc.entity.TbNote;
import com.fc.entity.TbUser;
import com.fc.service.ReportService;
import com.fc.vo.ResultInfoVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author: T21
 * @date: 2022/5/8.
 */
@Controller
@RequestMapping("report")
@Api(tags = "报表模块", description = "报表的所有操作")
public class ReportController {
    @Autowired
    private ReportService reportService;


    @GetMapping("info")
    public ModelAndView reportInfo(ModelAndView mv) {
        mv.addObject("menu_page", "report");
        mv.addObject("changePage", "/report/info.jsp");

        mv.setViewName("forward:/index.jsp");

        return mv;
    }

    @GetMapping("location")
    @ResponseBody
    public ResultInfoVo location(HttpSession session) {
        ResultInfoVo vo = new ResultInfoVo();

        TbUser user = (TbUser)session.getAttribute("user");

        List<TbNote> notes = reportService.getLocation(user.getId());

        if (notes != null && notes.size() > 0) {
            vo.setData(notes);
            vo.setCode(1);
            vo.setSuccess(true);
        }

        return vo;
    }

    @GetMapping("month")
    @ResponseBody
    public ResultInfoVo getMonth(HttpSession session) {
        ResultInfoVo vo = new ResultInfoVo();

        TbUser user = (TbUser)session.getAttribute("user");

        Map<String, Object> map = reportService.getMonth(user.getId());

        if (map != null){
            vo.setData(map);
            vo.setCode(1);
        }

        return vo;
    }
}
