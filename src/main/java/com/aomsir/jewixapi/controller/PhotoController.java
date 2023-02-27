package com.aomsir.jewixapi.controller;

import com.aomsir.jewixapi.pojo.vo.PhotoUpdateVo;
import com.aomsir.jewixapi.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Aomsir
 * @Date: 2023/2/27
 * @Description: 相册控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@RestController
public class PhotoController {

    @PostMapping("/admin/photo/update")
    public R updatePhoto(MultipartFile file,
                         @RequestBody PhotoUpdateVo photoUpdateVo) {

        return R.ok();
    }
}
