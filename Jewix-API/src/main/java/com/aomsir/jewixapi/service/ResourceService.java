package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.entity.Resource;

import java.util.List;

public interface ResourceService {
    List<Resource> searchResourcesByUserId(Long id);
}
