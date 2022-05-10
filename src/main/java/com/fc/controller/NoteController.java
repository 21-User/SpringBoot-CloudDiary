package com.fc.controller;

import com.fc.entity.TbNote;
import com.fc.entity.TbNoteType;
import com.fc.entity.TbUser;
import com.fc.service.NoteService;
import com.fc.service.TbNoteTypeService;
import com.fc.vo.NoteVo;
import com.fc.vo.ResultInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author: T21
 * @date: 2022/5/9.
 */
@Controller
@RequestMapping("note")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @Autowired
    private TbNoteTypeService typeService;

    @RequestMapping("view")
    public ModelAndView view(Integer id, ModelAndView mv, HttpSession session) {

        if (id != null) {
            NoteVo note = noteService.findById(id);

            mv.addObject("noteInfo", note);
        }

        //获取域对象中的user
        TbUser user = (TbUser)session.getAttribute("user");

        //根据userId获取该用户包含的所有日记分类
        List<TbNoteType> types = typeService.findNoteType(user.getId());

        session.setAttribute("typeList", types);
        session.setAttribute("changePage", "/note/view.jsp");
        session.setAttribute("menu_page", "note");

        mv.setViewName("forward:/index.jsp");

        return mv;
    }

    @GetMapping("detail")
    public ModelAndView detail(@RequestParam Integer id, ModelAndView mv, HttpSession session) {
        NoteVo noteVo = noteService.findById(id);

        mv.addObject("note", noteVo);

        // 高亮显示
        mv.addObject("menu_page", "note");

        // 包含的页面
        mv.addObject("changePage", "/note/detail.jsp");

        mv.setViewName("forward:/index.jsp");

        return mv;
    }

    @PostMapping("addOrUpdate")
    public ModelAndView addOrUpdate(TbNote tbNote, ModelAndView mv,HttpSession session) {

        ResultInfoVo infoVo;

        //日记id为空就添加
        if (tbNote.getId() == null) {
            infoVo = noteService.add(tbNote);
        } else {
            //日记id不为空就去修改
            infoVo= noteService.update(tbNote);
        }

        if (infoVo.getCode() == 1) {
            mv.setViewName("forward:/index/page");
        } else {

            session.setAttribute("resultInfo", infoVo.getData());
            session.setAttribute("id", tbNote.getId());

            mv.setViewName("forward:/note/view");
        }

        mv.addObject("menu_page", "note");

        return mv;
    }

    @GetMapping("delete")
    @ResponseBody
    public ResultInfoVo delete(@RequestParam Integer id) {

        return noteService.delete(id);
    }
}
