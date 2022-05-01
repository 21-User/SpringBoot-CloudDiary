package com.fc.service;

import com.fc.entity.TbUser;
import com.fc.vo.ResultInfoVo;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    ResultInfoVo login(String username, String password);

    ResultInfoVo update(MultipartFile img, String nick, String mood, TbUser user);


}
