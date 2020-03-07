package com.mper.smartschool.controller;

import com.mper.smartschool.service.AvatarStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/smartschool/avatars")
public class AvatarController {

    private final AvatarStorageService avatarStorageService;

    @PostMapping("/upload")
    public String store(@RequestParam("file") MultipartFile file) {
        return avatarStorageService.store(file);
    }

    @GetMapping()
    public List<String> loadAll() {
        return avatarStorageService.loadAllName();
    }

    @GetMapping(value = "/download/{fileName}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public Resource load(@PathVariable String fileName) {
        return avatarStorageService.load(fileName);
    }
}
