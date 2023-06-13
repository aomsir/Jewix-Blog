package com.aomsir.jewixapi;

import com.aomsir.jewixapi.pojo.vo.RoleOfResourcesAddVo;
import com.aomsir.jewixapi.service.ResourceService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @Author: Aomsir
 * @Date: 2023/6/13
 * @Description: ResourceTests
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@SpringBootTest
public class ResourceTests {

    @Resource
    private ResourceService resourceService;

    @Test
    public void testDoAssign() {
        RoleOfResourcesAddVo roleOfResourcesAddVo = new RoleOfResourcesAddVo();
        roleOfResourcesAddVo.setRoleId(2);
        ArrayList<Integer> resourceIds = new ArrayList<>();
        resourceIds.add(1);
        resourceIds.add(2);
        resourceIds.add(3);
        roleOfResourcesAddVo.setResourceIds(resourceIds);
        this.resourceService.insertReaourceForRole(roleOfResourcesAddVo);
    }
}
