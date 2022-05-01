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
    public ResultInfoVo update(MultipartFile img, String nick, String mood, TbUser user) {
        ResultInfoVo infoVo;

        String filename = user.getHead();

        if (!img.isEmpty()) {

            String path = "E:\\idea_code\\My_Project\\SpringBoot-CloudDiary\\src\\main\\resources\\META-INF\\resources\\upload";

            File pathFile = new File(path);

            //如果文件不存在就创建多级路径
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
                //获取原始图片名
            filename = img.getOriginalFilename();

            //获取日期时间的格式化器
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

            //格式化之后的时间
            String formatDate = formatter.format(new Date());

            //获取文件的后缀名
            String suffix = filename.substring(filename.lastIndexOf('.'));

            filename = formatDate + suffix;

            Map<String, Object> map = new HashMap<>();

            try {

                String url = "http://localhost:8080/upload/" + filename;

                map.put("url", url);

                img.transferTo(new File(pathFile, filename));

                infoVo = new ResultInfoVo(1, "成功!", true, map);

            } catch (IOException e) {
                e.printStackTrace();

                infoVo = new ResultInfoVo(-1, "图片修改失败", false, null);

            }

        }

        user.setMood(mood);
        user.setNick(nick);
        user.setHead(filename);

        int affectedRows = userDao.updateByPrimaryKeySelective(user);

        if (affectedRows > 0) {
            //修改完成后重新根据id查询到最新的User对象
            TbUser tbUser = userDao.selectByPrimaryKey(user.getId());

            infoVo = new ResultInfoVo(1, "修改成功", true, tbUser);
        } else {
            infoVo = new ResultInfoVo(-1, "修改失败", true, null);
        }

        return infoVo;
    }

}
