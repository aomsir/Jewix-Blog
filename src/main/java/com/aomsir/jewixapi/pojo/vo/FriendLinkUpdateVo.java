package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/2/27
 * @Description: 友情链接更新VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class FriendLinkUpdateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空")
    @Min(value = 1)
    private Integer id;

    @NotNull(message = "标题不能为空")
    private String title;

    @NotNull(message = "链接不能为空")
    private String link;

    @NotNull(message = "头像不能为空")
    private String photo;

    @NotNull(message = "描述不能为空")
    private String description;

    @NotNull(message = "位置不能为空")
    @Range(min = 1, max = 3)
    private Integer location;
}
