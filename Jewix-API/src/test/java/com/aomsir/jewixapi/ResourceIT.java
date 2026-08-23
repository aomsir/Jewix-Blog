package com.aomsir.jewixapi;

import com.aomsir.jewixapi.pojo.vo.RoleOfResourcesAddVo;
import com.aomsir.jewixapi.service.ResourceService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Requires a live MySQL blog schema and mutates role-resource assignments.
 * Excluded from the default Surefire unit-test phase.
 */
@Tag("integration")
@SpringBootTest
@ActiveProfiles("dev")
public class ResourceIT {

    @Resource
    private ResourceService resourceService;

    @Test
    public void testDoAssign() {
        RoleOfResourcesAddVo roleOfResourcesAddVo = new RoleOfResourcesAddVo();
        roleOfResourcesAddVo.setRoleId(2);
        ArrayList<Integer> resourceIds = new ArrayList<Integer>();
        resourceIds.add(1);
        resourceIds.add(2);
        resourceIds.add(3);
        roleOfResourcesAddVo.setResourceIds(resourceIds);
        this.resourceService.insertResourceForRole(roleOfResourcesAddVo);
    }
}
