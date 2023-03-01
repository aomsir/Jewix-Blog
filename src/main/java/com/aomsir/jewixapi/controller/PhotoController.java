package com.aomsir.jewixapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.pojo.vo.PhotoDeleteVo;
import com.aomsir.jewixapi.pojo.vo.PhotoPageVo;
import com.aomsir.jewixapi.pojo.vo.PhotoUpdateVo;
import com.aomsir.jewixapi.service.PhotoService;
import com.aomsir.jewixapi.utils.PageUtils;
import com.aomsir.jewixapi.utils.R;
import com.upyun.UpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/2/27
 * @Description: 相册控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@RestController
public class PhotoController {

    private static final Logger log = LoggerFactory.getLogger(PhotoController.class);
    @Resource
    private PhotoService photoService;

    @Value("${jewix.basePath}")
    private String basePath;



    /**
     * 上传照片到云端
     * @param file
     * @param photoUpdateVo
     * @return
     * @throws UpException
     * @throws IOException
     */
    @PostMapping("/photo/update")
    public R updatePhoto(MultipartFile file,
                         @Validated PhotoUpdateVo photoUpdateVo) throws UpException, IOException {
        // TODO: 修改为权限校验
        int role = this.photoService.updatePhoto(file,photoUpdateVo.getType());
        return R.ok()
                .put("role", role);
    }

    /**
     * 根据类型分页查询相册列表
     * @param photoPageVo
     * @return
     */
    @PostMapping("/photo/list")
    public R getPhotoListByPage(@RequestBody @Validated PhotoPageVo photoPageVo) {
        Map<String, Object> param = BeanUtil.beanToMap(photoPageVo);

        int page = photoPageVo.getPage();
        int length = photoPageVo.getLength();
        int start = (page - 1) * length;
        param.put("start", start);
        PageUtils pageUtils = this.photoService.searchPhotoByPage(param);
        return R.ok()
                .put("result", pageUtils);
    }


    /**
     * 根据文件名获取服务器本地相册文件
     * @param response
     * @param name
     */
    @GetMapping("/photo/download")
    public void download(ServletResponse response,
                         @RequestParam("name") String name) {
        try {
            // 参数传递的有前端通过数据库获取来的参数
            FileInputStream fileInputStream = new FileInputStream(new File(this.basePath) + "/" + name);

            // 必须设置的返回类型
            response.setContentType("image/jpeg");

            // 获取相应的输出流，用它将数据回显
            ServletOutputStream outputStream = response.getOutputStream();

            byte[] bytes = new byte[1024];
            int len;
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据文件名与类型删除相册信息
     * @param photoDeleteVo
     * @return
     * @throws UpException
     * @throws IOException
     */
    @PostMapping("/photo/delete")
    public R deletePhoto(@RequestBody @Validated PhotoDeleteVo photoDeleteVo) throws UpException, IOException {
        int role = this.photoService.deletePhoto(photoDeleteVo);
        return R.ok()
                .put("role", role);
    }
}
