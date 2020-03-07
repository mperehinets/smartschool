package com.mper.smartschool.service;

import com.mper.smartschool.dto.UserDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AvatarStorageService {

    String store(MultipartFile file);

    List<String> loadAllName();

    Resource load(String fileName);

    void resolveAvatar(UserDto userDto);
}
