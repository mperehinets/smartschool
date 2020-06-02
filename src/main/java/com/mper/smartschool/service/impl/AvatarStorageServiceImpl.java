package com.mper.smartschool.service.impl;


import com.mper.smartschool.dto.UserDto;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.exception.WrongImageTypeException;
import com.mper.smartschool.service.AvatarStorageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AvatarStorageServiceImpl implements AvatarStorageService {

    private static final String AVATAR_FOLDER_NAME = "avatar";
    private final Path fileStorageLocation;

    public AvatarStorageServiceImpl() {
        fileStorageLocation = Paths.get(AVATAR_FOLDER_NAME).toAbsolutePath().normalize();
    }

    @SneakyThrows
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public String store(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (fileName.endsWith(".png") || fileName.endsWith(".jpg")) {
            // Copy file to the target location (Replacing existing file with the same name)
            Files.copy(file.getInputStream(),
                    fileStorageLocation.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);
            log.info("IN store - avatar successfully store by name: {}", fileName);
            return fileName;
        } else {
            throw new WrongImageTypeException("WrongImageTypeException");
        }
    }

    @SneakyThrows
    @Override
    public List<String> loadAllName() {
        var result = Arrays.stream(Objects.requireNonNull(fileStorageLocation.toFile().listFiles()))
                .map(File::getName)
                .collect(Collectors.toList());
        log.info("IN loadAllName - {} avatars found", result.size());
        return result;
    }

    @SneakyThrows
    @Override
    public Resource load(String fileName) {
        String correctFileName = fileName.trim();
        var resource = new UrlResource(fileStorageLocation.resolve(correctFileName).normalize().toUri());
        if (correctFileName.length() != 0 && resource.exists()) {
            log.info("IN load - avatar found by name: {}", fileName);
            return resource;
        } else {
            throw new NotFoundException("AvatarNotFoundException.byName", fileName);
        }
    }

    @Override
    public void resolveAvatar(UserDto userDto) {
        try {
            load(userDto.getAvatarName());
        } catch (NotFoundException | NullPointerException ex) {
            userDto.setAvatarName("default.png");
        }
    }

    @Override
    public String resolveAvatar(String avatarName) {
        try {
            load(avatarName);
            return avatarName;
        } catch (NotFoundException | NullPointerException ex) {
            return "default.png";
        }
    }
}
