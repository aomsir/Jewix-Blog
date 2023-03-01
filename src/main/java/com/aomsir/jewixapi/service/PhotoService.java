package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.vo.PhotoDeleteVo;
import com.aomsir.jewixapi.utils.PageUtils;
import com.upyun.UpException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface PhotoService {
    int updatePhoto(MultipartFile file, Integer type) throws IOException, UpException;

    PageUtils searchPhotoByPage(Map<String, Object> param);

    int deletePhoto(PhotoDeleteVo photoDeleteVo) throws UpException, IOException;
}
