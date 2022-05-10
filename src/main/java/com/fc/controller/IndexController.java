package com.fc.controller;

import com.fc.entity.TbNote;
import com.fc.entity.TbNoteType;
import com.fc.entity.TbUser;
import com.fc.service.IndexService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("index")
@Api(tags = "主页模块", description = "主页的所有操作")
public class IndexController {
    @Autowired
    private IndexService indexService;

    @RequestMapping("page")
    public ModelAndView page(Integer id, String title, String date,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             ModelAndView mv, HttpSession session) {

        //获取查询出来的User对象的域对象user
        TbUser user =(TbUser)session.getAttribute("user");

        //将userId（必填）,typeId（选填）,title（选填）,date（选填）
        // 作为条件使用分页插件进行分页查询获取pageInfo，泛型为TbNote
        //分页查询日记
        PageInfo<TbNote> pageInfo = indexService.page(pageNum, pageSize, user.getId(), id, title, date);
        mv.addObject("page", pageInfo);

        //根据id获取日期分类
        List<TbNote> dateInfo = indexService.findNoteCountByDate(user.getId());
        session.setAttribute("dateInfo", dateInfo);

        //根据id获取类型分类
        List<TbNote> typeInfo = indexService.findByType(user.getId());
        session.setAttribute("typeInfo", typeInfo);

        if (id != null) {
            mv.addObject("typeId", id);
        }

        if (title != null && !title.equals("")) {
            mv.addObject("title", title);
        }

        if (date != null && !date.equals("")) {
            mv.addObject("date", date);
        }

        mv.addObject("menu_page", "index");
        mv.addObject("changePage", "/note/list.jsp");
        mv.setViewName("forward:/index.jsp");

        return mv;
    }

    @RequestMapping("searchType")
    public ModelAndView searchType(Integer id, ModelAndView mv) {

        mv.addObject("id", id);
        mv.setViewName("forward:/index/page");

        return mv;
    }

    @RequestMapping("searchTitle")
    public ModelAndView searchTitle(String title, ModelAndView mv) {

        mv.addObject("title", title);
        mv.setViewName("forward:/index/page");

        return mv;
    }

    @RequestMapping("searchDate")
    public ModelAndView searchDate(String date, ModelAndView mv) {

        mv.addObject("pubTime", date);
        mv.setViewName("forward:/index/page");

        return mv;
    }
}
