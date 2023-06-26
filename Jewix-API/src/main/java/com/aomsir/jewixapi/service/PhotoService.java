package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.vo.PhotoDeleteVo;
import com.aomsir.jewixapi.util.PageUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


/**
 * @Author: Aomsir
 * @Date: 2023/6/26
 * @Description: 相册业务接口
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public interface PhotoService {

    /**
     * 上传照片
     * @param file 文件
     * @param type 类型
     * @return 上传所影响的行数
     */
    int updatePhoto(MultipartFile file, Integer type);

    /**
     * 分页查询照片列表
     * @param param 参数列表
     * @return 分页数据
     */
    PageUtils searchPhotoByPage(Map<String, Object> param);

    /**
     * 删除照片
     * @param photoDeleteVo 封装对象
     * @return 删除所影响的行数
     */
    int deletePhoto(PhotoDeleteVo photoDeleteVo);
}
