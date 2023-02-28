package com.aomsir.jewixapi.service.impl;

import com.aomsir.jewixapi.mapper.PhotoMapper;
import com.aomsir.jewixapi.service.PhotoService;
import com.upyun.RestManager;
import com.upyun.UpException;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: Aomsir
 * @Date: 2023/2/28
 * @Description: 相册业务实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Service
public class PhotoServiceImpl implements PhotoService {

    @Resource
    private RestManager restManager;

    @Resource
    private PhotoMapper photoMapper;

    @Override
    @Transactional
    public int updatePhoto(MultipartFile file, Integer type) throws IOException, UpException {
        String originalFilename = file.getOriginalFilename();

        // 获取源文件后缀
        String suffix = null;
        if (originalFilename != null) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String fileName = UUID.randomUUID() + suffix;   // 文件名

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String location = format.format(date);   // 文件位置
        location = "/" + location + "/";

        Map<String, Object> param = new HashMap<>();
        param.put("type", type);
        param.put("location", location);
        param.put("fileName", fileName);
        param.put("createTime", new Date());


        Response response = null;
        if (type == 0) {
            // TODO: 上传到本地
        } else if (type == 1) {
            response  = restManager.writeFile(location + fileName, file.getBytes(), null);
        } else if (type == 2) {
            // TODO: 上传到阿里云OSS
        } else if (type == 3) {
            // TODO: 上传到腾讯云COS
        }

        int role = 0;
        if (response != null && response.isSuccessful()) {
            role = this.photoMapper.insertPhoto(param);
        }

        return role;
    }
}
