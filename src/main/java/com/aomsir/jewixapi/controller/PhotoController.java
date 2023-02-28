package com.aomsir.jewixapi.controller;

import com.aomsir.jewixapi.pojo.vo.PhotoUpdateVo;
import com.aomsir.jewixapi.service.PhotoService;
import com.aomsir.jewixapi.utils.R;
import com.upyun.RestManager;
import com.upyun.UpException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: Aomsir
 * @Date: 2023/2/27
 * @Description: 相册控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@RestController
public class PhotoController {

    @Resource
    private PhotoService photoService;

    @PostMapping("/photo/update")
    public R updatePhoto(MultipartFile file,
                         @Validated PhotoUpdateVo photoUpdateVo) throws UpException, IOException {
        int role = this.photoService.updatePhoto(file,photoUpdateVo.getType());
        return R.ok()
                .put("role", role);
    }
}
