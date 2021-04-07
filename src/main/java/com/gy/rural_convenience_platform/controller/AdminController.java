package com.gy.rural_convenience_platform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.gy.rural_convenience_platform.config.CosConfig;
import com.gy.rural_convenience_platform.entity.Notice;
import com.gy.rural_convenience_platform.entity.User;
import com.gy.rural_convenience_platform.service.AdminService;
import com.gy.rural_convenience_platform.utils.CurrentUser;
import com.gy.rural_convenience_platform.utils.ResponseCode;
import com.gy.rural_convenience_platform.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class AdminController {

    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private AdminService adminService;
    @Resource
    private ObjectMapper objectMapper;
    @Autowired
    private CosConfig cosConfig;

    @PostMapping("/addNotice")
    public Map<String, Object> addNotice(@RequestParam("noticeDesc") String noticeDesc,@RequestParam("noticeImg") MultipartFile noticeImg,HttpServletRequest request) throws IOException, URISyntaxException {
        User user = currentUser.currentUser(request);
        if(user == null) return ResponseCode.error("用户未登录");
        String username = user.getUsername();
//        将文件上传至腾讯云cos，并获取访问地址
        String url = UploadUtil.uploadToOos(noticeImg, username, cosConfig);
//        将json转换成map
        Map map = objectMapper.readValue(noticeDesc, Map.class);
        map.put("url", url);
        map.put("username", username);

//        将数据存入数据库
        boolean result = adminService.addNotice(map);
        if (result) return ResponseCode.ok("添加成功");
        return ResponseCode.error("添加失败");
    }


    @GetMapping("/getNoticePage/{pageNum}/{pageSize}")
    public Map<String,Object> getNoticePage(
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("pageSize") Integer pageSize,
            String keyStr,
            HttpServletRequest request){
        User user = currentUser.currentUser(request);
        if(user == null) return ResponseCode.error("用户未登录");

        PageInfo<Notice> noticePage = adminService.getNoticePage(pageNum, pageSize, keyStr);
        return ResponseCode.ok(noticePage);
    }

}
