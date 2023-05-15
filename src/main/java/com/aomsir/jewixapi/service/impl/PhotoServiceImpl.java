package com.aomsir.jewixapi.service.impl;

import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.PhotoMapper;
import com.aomsir.jewixapi.pojo.entity.Photo;
import com.aomsir.jewixapi.pojo.vo.PhotoDeleteVo;
import com.aomsir.jewixapi.service.PhotoService;
import com.aomsir.jewixapi.util.PageUtils;
import com.upyun.RestManager;
import com.upyun.UpException;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.aomsir.jewixapi.constant.CommonConstants.PARAMETER_ERROR;
import static com.aomsir.jewixapi.constant.PhotoConstants.PHOTO_DELETE_FAILED;

/**
 * @Author: Aomsir
 * @Date: 2023/2/28
 * @Description: 相册业务实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Service
public class PhotoServiceImpl implements PhotoService {

    private static final Logger log = LoggerFactory.getLogger(PhotoServiceImpl.class);
    @Resource
    private RestManager restManager;

    @Resource
    private PhotoMapper photoMapper;

    @Value("${cloud.upyun.url}")
    private String upUrl;

    @Value("${jewix.basePath}")
    private String basePath;

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
            File dir = new File(this.basePath + location);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try {
                file.transferTo(new File(this.basePath + location + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type == 1) {
            response  = restManager.writeFile(location + fileName, file.getBytes(), null);
        } else if (type == 2) {
            // TODO: 上传到阿里云OSS
        } else if (type == 3) {
            // TODO: 上传到腾讯云COS
        }

        int role = 0;
        if (type == 1 && response != null && response.isSuccessful()) {
            return this.photoMapper.insertPhoto(param);  // TODO:新增用户id字段
        } else if (type == 2) {
            // TODO
        } else if (type == 3) {
            // TODO
        } else if (type == 0) {
            return this.photoMapper.insertPhoto(param);
        }

        return role;
    }

    @Override
    public PageUtils searchPhotoByPage(Map<String, Object> param) {
        int count = this.photoMapper.queryPhotoCountByType((Integer) param.get("type"));

        ArrayList<Photo> list = null;
        if (count > 0) {
            list = this.photoMapper.selectPhotoListByPage(param);
        } else {
            list = new ArrayList<>();
        }

        for (Photo photo : list) {
            if (photo.getType() == 0) {
                // 服务器本地文件则返回fileName,前端再单独请求接口
                photo.setFileName(photo.getLocation().substring(1)  + photo.getFileName());
            } else if (photo.getType() == 1) {
                photo.setFileName(photo.getLocation()  + photo.getFileName());
                photo.setUrl(this.upUrl + photo.getFileName());
            } else if (photo.getType() == 2) {
                // TODO：完成阿里云
            } else {
                // TODO：完成腾讯云
            }
        }

        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list, count, start,length);
        return pageUtils;
    }


    @Override
    @Transactional
    public int deletePhoto(PhotoDeleteVo photoDeleteVo) throws UpException, IOException {
        if (Objects.isNull(photoDeleteVo.getFileName()) || Objects.isNull(photoDeleteVo.getType())) {
            throw new CustomerException(PARAMETER_ERROR);
        }

        String voFileName = photoDeleteVo.getFileName();
        String fileName = voFileName.substring(voFileName.lastIndexOf("/") + 1);
        String location = voFileName.substring(0, voFileName.lastIndexOf("/") + 1);
        Integer type = photoDeleteVo.getType();
        Map<String, Object> param = new HashMap(){{
           put("fileName", fileName);
           put("location", location);
           put("type", type);
        }};

        int role = 0;
        role = this.photoMapper.deletePhoto(param);   // 先删数据库,数据库没问题再删具体文件,以免会回滚

        // 数据库成功删除再删文件
        if (0 == role) {
            if (type == 0) {
                // 删除本地文件
                File file = new File(this.basePath + location + fileName);
                if (!file.delete()) {
                    throw new CustomerException(PHOTO_DELETE_FAILED);
                }
            } else if (type == 1) {
                try (Response response = this.restManager.deleteFile(location + fileName, null)) {
                    if (!response.isSuccessful()) {
                        throw new CustomerException(PHOTO_DELETE_FAILED);
                    }
                }
            } else if (type == 2) {
                // TODO
            } else {
                // TODO
            }
        }

        return role;
    }
}
