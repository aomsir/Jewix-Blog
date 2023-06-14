package com.aomsir.jewixapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.annotation.OperateLog;
import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.pojo.vo.PhotoDeleteVo;
import com.aomsir.jewixapi.pojo.vo.PhotoPageVo;
import com.aomsir.jewixapi.pojo.vo.PhotoUpdateVo;
import com.aomsir.jewixapi.service.PhotoService;
import com.aomsir.jewixapi.util.PageUtils;
import com.aomsir.jewixapi.util.R;
import com.upyun.UpException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
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

@Api(tags = "相册控制器")
@RestController
public class PhotoController {

    @Resource
    private PhotoService photoService;

    @Value("${jewix.basePath}")
    private String basePath;



    /**
     * 上传照片到云端
     * @param file 上传的文件
     * @param photoUpdateVo 上传相册VO对象
     * @return 上传相册所影响的行数
     * @throws UpException 又拍云异常
     * @throws IOException IO异常
     */
    @OperateLog(optType = "上传照片")
    @PreAuthorize("hasAuthority('ADMIN_PHOTO_ADD')")
    @ApiOperation(value = "上传照片到云端")
    @PostMapping("/admin/photos")
    public R updatePhoto(MultipartFile file,
                         @Validated PhotoUpdateVo photoUpdateVo) throws UpException, IOException {
        // TODO: 修改为权限校验
        int role = this.photoService.updatePhoto(file,photoUpdateVo.getType());
        return R.ok()
                .put("role", role);
    }

    /**
     * 根据类型分页查询相册列表
     * @param photoPageVo 分页获取相册VO对象
     * @return 相册分页列表
     */
    @PreAuthorize("hasAuthority('ADMIN_PHOTO_LIST')")
    @ApiOperation(value = "根据类型分页查询相册列表")
    @GetMapping("/admin/photos")
    public R getPhotoListByPage(PhotoPageVo photoPageVo) {
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
     * @param response ServletResponse
     * @param name 上传后更新的文件名
     */
    @ApiOperation(value = "根据文件名获取服务器本地相册文件")
    @GetMapping("/photos/download")
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
            throw new CustomerException("图片加载异常");
        }
    }


    /**
     * 根据文件名与类型删除相册信息
     * @param photoDeleteVo 删除相册VO对象
     * @return 删除所影响的行数
     * @throws UpException 又拍云异常
     * @throws IOException IO异常
     */
    @OperateLog(optType = "删除照片")
    @PreAuthorize("hasAuthority('ADMIN_PHOTO_DELETE')")
    @ApiOperation(value = "根据文件名与类型删除相册信息")
    @DeleteMapping("/admin/photos")
    public R deletePhoto(PhotoDeleteVo photoDeleteVo) throws UpException, IOException {
        int role = this.photoService.deletePhoto(photoDeleteVo);
        return R.ok()
                .put("role", role);
    }
}
