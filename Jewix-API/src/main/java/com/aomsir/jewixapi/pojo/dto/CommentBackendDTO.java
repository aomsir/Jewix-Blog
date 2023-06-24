package com.aomsir.jewixapi.pojo.dto;

import com.aomsir.jewixapi.pojo.entity.Comment;
import lombok.Data;

/**
 * @Author: Aomsir
 * @Date: 2023/6/24
 * @Description: 后台评论列表DTO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class CommentBackendDTO extends Comment {
    private String quoteUuid;
    private Long quoteId;
    private String quoteName;
}
