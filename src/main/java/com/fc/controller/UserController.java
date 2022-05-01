package com.fc.controller;

import com.fc.entity.TbUser;
import com.fc.service.UserService;
import com.fc.vo.ResultInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public ModelAndView login(TbUser user,
                              Integer remember,
                              ModelAndView mv,
                              HttpSession session,
                              HttpServletResponse response){


        ResultInfoVo vo = userService.login(user.getUsername(), user.getPassword());

        if (vo.getCode() == 0) {
            //如果查不到，设置请求域对象resultInfo,转发至login.jsp页面
            mv.addObject("resultInfo", vo);

            mv.setViewName("forward:/login.jsp");
        } else {
            //如果能查到，将查询出来的User对象存入session域对象中，键为user
            session.setAttribute("user", vo.getData());

            Cookie cookie;

            if (remember != null && remember == 1) {
                //如果勾选，设置cookie名为JSESSIONID，过期时间为半个小时，并发送到浏览器
                cookie = new Cookie("JSESSIONID", session.getId());

                cookie.setMaxAge(30 * 60);
            } else {
                //如果没有勾选，设置cookie过期时间为负数
                cookie = new Cookie("JSESSIONID", null);

                cookie.setMaxAge(-1);
            }

            response.addCookie(cookie);

            mv.setViewName("forward:/index.jsp");
        }

        return mv;
    }

    @GetMapping("logout")
    public ModelAndView logout(ModelAndView mv,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        session.removeAttribute("user");

        Cookie cookie = new Cookie("JSESSIONID", "");

        //设置cookie的失效时间
        cookie.setMaxAge(0);

        //发送cookie到浏览器
        response.addCookie(cookie);

        mv.setViewName("redirect:/login.jsp");

        return mv;
    }

    @RequestMapping("userCenter")
    public ModelAndView userCenter(ModelAndView mv,
                                   HttpSession session) {
        //获取域对象
        TbUser user = (TbUser)session.getAttribute("user");

        //设置请求域对象menu_page的值为user
        mv.addObject("menu_page", user);

        //设置请求域对象changePage的值为user目录下的info.jsp
        mv.addObject("changePage", "/user/info.jsp");

        //转发至index.jsp页面
        mv.setViewName("forward:/index.jsp");

        return mv;
    }

    @RequestMapping("update")
    public ModelAndView update(@RequestBody MultipartFile img,
                               String nick,
                               String mood,
                               HttpSession session,
                               ModelAndView mv) {

        TbUser user = (TbUser)session.getAttribute("user");

        ResultInfoVo resultInfoVo = userService.update(img, nick, mood, user);

        if (resultInfoVo.getSuccess()) {
            session.setAttribute("user", resultInfoVo.getData());

        }

        //转发执行user/userCenter接口
        mv.setViewName("forward:/user/userCenter");

        return mv;
    }

    @RequestMapping("checkNick")
    public String checkNick(String nick, HttpSession session) {
        TbUser user = (TbUser)session.getAttribute("user");

        Integer code = userService.checkNick(nick, user.getId());

        return code + "";
    }

}
