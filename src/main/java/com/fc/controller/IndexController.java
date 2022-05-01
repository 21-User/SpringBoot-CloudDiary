package com.fc.controller;

import com.fc.entity.TbNote;
import com.fc.entity.TbNoteType;
import com.fc.entity.TbUser;
import com.fc.service.IndexService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("index")
public class IndexController {
    @Autowired
    private IndexService indexService;

    @RequestMapping(value = "page", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView page(ModelAndView mv,
                             HttpSession session,
                             Integer id,
                             String title,
                             String date,
                             @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "6") Integer pageSize) {

        //获取查询出来的User对象的域对象user
        TbUser user =(TbUser)session.getAttribute("user");

        //将userId（必填）
        Integer userId = user.getId();

        //将userId（必填）,typeId（选填）,title（选填）,date（选填）
        // 作为条件使用分页插件进行分页查询获取pageInfo，泛型为TbNote
        PageInfo<TbNote> pageInfo = indexService.page(userId, id, title, date, pageNum, pageSize);

        //设置请求域对象page的值为pageInfo
        mv.addObject("page", pageInfo);

        //设置请求域对象changePage的值为note目录下的list.jsp
        mv.addObject("changePage", "/note/list.jsp");

        //转发至index.jsp页面
        mv.setViewName("forward:/index/page");

        return mv;
    }
}
