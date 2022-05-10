package com.fc.controller;

import com.fc.entity.TbNoteType;
import com.fc.entity.TbUser;
import com.fc.service.TbNoteTypeService;
import com.fc.vo.ResultInfoVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("type")
@Api(tags = "类别管理模块", description = "类别的所有操作")
public class TypeController {
    @Autowired
    private TbNoteTypeService noteTypeService;

    @RequestMapping("list")
    public ModelAndView list(ModelAndView mv,
                             HttpSession session) {
        TbUser user = (TbUser)session.getAttribute("user");

        //根据用户的id获取所有日记类型
        List<TbNoteType> tbNoteTypes = noteTypeService.findNoteType(user.getId());

        session.setAttribute("list", tbNoteTypes);
        session.setAttribute("menu_page", "type");
        session.setAttribute("changePage", "/type/list.jsp");

        mv.setViewName("forward:/index.jsp");

        return mv;
    }

    @PostMapping("addOrUpdate")
    @ResponseBody
    public ResultInfoVo addOrUpdate( TbNoteType noteType, HttpSession session) {
        ResultInfoVo vo;

        TbUser user = (TbUser)session.getAttribute("user");

        noteType.setUserId(user.getId());

        if (noteType.getId() == null) {
            vo = noteTypeService.add(noteType);
        } else {
            vo= noteTypeService.update(noteType);
        }

        return vo;
    }

    @GetMapping("delete")
    @ResponseBody
    public ResultInfoVo delete(@RequestParam Integer id) {

        return noteTypeService.delete(id);
    }

}
