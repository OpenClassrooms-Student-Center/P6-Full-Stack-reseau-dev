package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dtos.MddUserDto;
import com.openclassrooms.mddapi.mappers.MddUserMapper;
import com.openclassrooms.mddapi.service.MddUserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
@RequestMapping("users/user")
public class MddUserController {

    @Autowired
    MddUserService mddUserService;

    @Autowired
    private MddUserMapper mddUserMapper;

    @GetMapping("/users")
    public ResponseEntity<List<MddUserDto>> getUsers() {
        return ResponseEntity.ok(mddUserMapper.toDto(mddUserService.findAllUsers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MddUserDto> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(mddUserMapper.toDto(mddUserService.findUserById(id)));
    }

    @PutMapping("/create")
    public ResponseEntity<MddUserDto> create(@RequestBody MddUserDto mddUserDto) {
        return ResponseEntity.ok(mddUserMapper.toDto(mddUserService.createUser(mddUserMapper.toEntity(mddUserDto))));
    }

    @PostMapping("update")
    public ResponseEntity<MddUserDto> update(@RequestBody MddUserDto mddUserDto) {
        return ResponseEntity.ok(mddUserMapper.toDto(mddUserService.updateUser(mddUserMapper.toEntity(mddUserDto))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(mddUserService.deleteUser(id));
    }
}