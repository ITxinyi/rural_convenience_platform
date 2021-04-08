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

    /**
     * 查询公告列表
     * @param pageNum
     * @param pageSize
     * @param keyStr
     * @param
     * @return
     */
    @GetMapping("/getNoticePage/{pageNum}/{pageSize}")
    public Map<String,Object> getNoticePage(
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("pageSize") Integer pageSize,
            String keyStr){

        PageInfo<Notice> noticePage = adminService.getNoticePage(pageNum, pageSize, keyStr);
        return ResponseCode.ok(noticePage);
    }

    /**
     * 停止公告
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/stopNotice/{id}")
    public Map<String,Object> stopNotice(@PathVariable("id") String id,HttpServletRequest request){
        User user = currentUser.currentUser(request);
        if(user == null) return ResponseCode.error("用户未登录");

        Integer result = adminService.stopNotice(id);

        return ResponseCode.ok(result > 0 ? true : false);
    }

    /**
     * 查询公告
     * @param id
     * @param
     * @return
     */
    @GetMapping("/getNotice/{id}")
    public Map<String,Object> getNotice(@PathVariable("id") String id){
        Notice notice = adminService.getNotice(id);

        return ResponseCode.ok(notice == null ? "查询失败" : notice);
    }

    /**
     * 保存公告修改
     * @param noticeDesc
     * @param noticeImg
     * @param request
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    @PostMapping("/saveNotice")
    public Map<String, Object> saveNotice(@RequestParam("noticeDesc") String noticeDesc,@RequestParam(value = "noticeImg",required = false) MultipartFile noticeImg,HttpServletRequest request) throws IOException, URISyntaxException {
        User user = currentUser.currentUser(request);
        if(user == null) return ResponseCode.error("用户未登录");
        String username = user.getUsername();
//        将json转换成map
        Map map = objectMapper.readValue(noticeDesc, Map.class);
        map.put("username", username);
        if (noticeImg != null) {
//        将文件上传至腾讯云cos，并获取访问地址
            String url = UploadUtil.uploadToOos(noticeImg, username, cosConfig);
            map.put("img", url);
        }

//        将数据存入数据库
        boolean result = adminService.saveNotice(map);
        if (result) return ResponseCode.ok("保存成功");
        return ResponseCode.error("保存失败");
    }

}
