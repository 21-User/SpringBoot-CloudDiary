package com.fc.controller;

import com.fc.entity.TbNoteType;
import com.fc.entity.TbUser;
import com.fc.service.TbNoteTypeService;
import com.fc.vo.ResultInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("type")
public class TypeController {
    @Autowired
    private TbNoteTypeService noteTypeService;

    @RequestMapping("list")
    public ModelAndView list(ModelAndView mv,
                             HttpSession session,
                             Integer id) {
        List<TbNoteType> tbNoteTypes = noteTypeService.findAllById(session, id);

        if (tbNoteTypes.size() > 0) {
            mv.addObject("list", tbNoteTypes);
        }

        mv.addObject("menu_page", "type");

        mv.addObject("changePage", "/type/list.jsp");

        mv.setViewName("forward:/index.jsp");

        return mv;
    }

    @RequestMapping(value = "addOrUpdate",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResultInfoVo addOrUpdate( TbNoteType tbNoteType, HttpSession session) {
        TbUser user = (TbUser)session.getAttribute("user");

        tbNoteType.setUserId(user.getId());

        return noteTypeService.addOrUpdate(tbNoteType);
    }

//    @RequestMapping("delete")
//    @ResponseBody
//    public ResultInfoVo delete() {
//
//
//    }

}
