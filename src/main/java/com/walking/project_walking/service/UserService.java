package com.walking.project_walking.service;

import com.walking.project_walking.domain.Users;
import com.walking.project_walking.domain.userdto.UserSignUpDto;
import com.walking.project_walking.domain.userdto.UserUpdate;

import com.walking.project_walking.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Transactional
    public Users saveUser(UserSignUpDto dto) {
        String encodedPassword = encoder.encode(dto.getPassword());

        Users user = Users.builder()
                .email(dto.getEmail())
                .password(encodedPassword)
                .name(dto.getName())
                .phone(dto.getPhone())
                .nickname(dto.getNickname())
                .gender(dto.getGender())
                .birth(dto.getBirth())
                .joinDate(LocalDate.now())
                .build();

        return userRepository.save(user);
    }

    public boolean checkEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkNicknameExists(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    // 유저 정보 전체 조회
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    // 유저 한 명 조회
    public Users findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }

    // 실제 존재하는 userId인지 검증하는 메소드
    public boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }

    // 유저 정보 수정
    @Transactional
    public ResponseEntity<String> updateById(Long id, UserUpdate update) {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        users.update(update.getPassword(), update.getPhone(), update.getNickname(), update.getProfileImage());

        userRepository.save(users);

        return new ResponseEntity<>("수정이 완료되었습니다 :)", HttpStatus.OK);
    }

    @Transactional
    public void softDeleteUser(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        user.deactivate();
        userRepository.save(user);
    }

}
