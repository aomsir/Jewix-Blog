package com.aomsir.jewixapi.service;

import com.upyun.UpException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {
    int updatePhoto(MultipartFile file, Integer type) throws IOException, UpException;
}
