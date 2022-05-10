package com.fc.service.impl;

import com.fc.dao.TbUserMapper;
import com.fc.entity.TbUser;
import com.fc.entity.TbUserExample;
import com.fc.service.UserService;
import com.fc.vo.ResultInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper userDao;

    @Override
    public ResultInfoVo login(String username, String password) {
        ResultInfoVo vo;

        TbUserExample example = new TbUserExample();

        TbUserExample.Criteria criteria = example.createCriteria();

        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(password);

        List<TbUser> users = userDao.selectByExample(example);

        if (users.size() > 0) {
            vo = new ResultInfoVo(200, "登录成功!", true, users.get(0));
        } else {
            vo = new ResultInfoVo(0, "登录失败，用户名或密码错误", false, null);
        }

        return vo;
    }

    @Override
    public ResultInfoVo update(MultipartFile img, TbUser user) {
        ResultInfoVo vo = new ResultInfoVo();

        if (!img.isEmpty() && img != null) {

            String path = "E:\\idea_code\\My_Project\\SpringBoot-CloudDiary\\target\\classes\\META-INF\\resources\\upload";

            File pathFile = new File(path);

            //获取原始图片名
            String filename = img.getOriginalFilename();

            //获取文件的后缀名
            String suffix = filename.substring(filename.lastIndexOf('.'));

            //获取日期时间的格式化器
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

            //格式化之后的时间
            String prefix = formatter.format(new Date());

            filename = prefix + suffix;

            try {
                img.transferTo(new File(pathFile, filename));

                user.setHead(filename);
            } catch (IOException e) {
                e.printStackTrace();

                vo.setMessage("头像上传失败");
                vo.setCode(0);
                vo.setSuccess(false);

                return vo;
            }
        }

        int affectedRows = userDao.updateByPrimaryKeySelective(user);

        if (affectedRows > 0) {
            vo.setCode(1);
            vo.setMessage("个人信息更新成功");
            vo.setSuccess(true);

            //修改完成后重新根据id查询到最新的User对象
            user = userDao.selectByPrimaryKey(user.getId());
            vo.setData(user);
        } else {
            vo.setMessage("个人信息更新失败");
            vo.setCode(0);
            vo.setSuccess(false);
        }

        return vo;
    }

    @Override
    public ResultInfoVo checkNick(String nick) {
        ResultInfoVo vo = new ResultInfoVo();

        TbUserExample example = new TbUserExample();

        TbUserExample.Criteria criteria = example.createCriteria();

        criteria.andNickEqualTo(nick);

        List<TbUser> users = userDao.selectByExample(example);

        if (users.size() > 0) {
            vo.setCode(1);
        } else {
            vo.setCode(0);
        }

        return vo;
    }

}
