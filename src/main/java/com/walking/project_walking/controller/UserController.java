package com.walking.project_walking.controller;

import com.walking.project_walking.domain.MyGoods;
import com.walking.project_walking.domain.PointLog;
import com.walking.project_walking.domain.Users;
import com.walking.project_walking.domain.userdto.*;
import com.walking.project_walking.repository.UserRepository;
import com.walking.project_walking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userservice;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserSignUpDto request) {
        Users user = userservice.saveUser(request);
        String redirectUri = "/auth/login";

        UserResponse response = new UserResponse(user, redirectUri);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/auth/check-email")
    public ResponseEntity<String> checkEmail(@RequestParam String email) {
        boolean exists = userservice.checkEmailExists(email);
        if (exists) {
            return ResponseEntity.badRequest().body("이미 사용 중인 이메일입니다.");
        }
        return ResponseEntity.ok("사용 가능한 이메일입니다.");
    }

    @GetMapping("/auth/check-nickname")
    public ResponseEntity<String> checkNickname(@RequestParam String nickname) {
        boolean exists = userservice.checkNicknameExists(nickname);
        if (exists) {
            return ResponseEntity.badRequest().body("이미 사용 중인 닉네임입니다.");
        }
        return ResponseEntity.ok("사용 가능한 닉네임입니다.");
    }

    @PostMapping("/auth/request-password-reset")
    public ResponseEntity<String> recoverPassword(@RequestParam String email) {
        boolean exists = userservice.checkEmailExists(email);
        if (!exists) {
            return ResponseEntity.badRequest().body("등록된 이메일이 아닙니다.");
        }
        userservice.sendPasswordRecoveryEmail(email);

        return ResponseEntity.ok("비밀번호 재설정 이메일이 발송되었습니다.");
    }

    @PostMapping("/auth/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        try {
            Users user = userservice.findUserByToken(resetPasswordDto.getToken());
            user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
            userRepository.save(user);
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 변경 중 오류가 발생했습니다.");
        }
    }

    // (Admin only) User 전체 조회
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> findUsers() {
        List<UserResponse> list = userservice.findAll().stream()
                .map(UserResponse::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    // (Admin only) User 한 명 조회
    @GetMapping("/users/{usersId}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long usersId) {
        Users users = userservice.findById(usersId);
        return ResponseEntity.ok(new UserResponse(users));
    }

    // User 정보 수정
    @PutMapping("/users/{userId}")
    public ResponseEntity<String> modifyUsersById(
            @PathVariable Long userId,
            @RequestBody UserUpdate update
    ) {
        return userservice.updateById(userId, update);
    }

    // User Soft Delete
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUserById(
            @PathVariable Long userId
    ) {
        userservice.softDeleteUser(userId);
        return ResponseEntity.ok("사용자가 비활성화 되었습니다");
    }

    // (User) 유저 조회
    @GetMapping("/users/{userId}/info")
    public ResponseEntity<UserDetailDto> getUserDetail(
            @PathVariable Long userId
    ) {
        UserDetailDto userDetail = userservice.userDetail(userId);
        return ResponseEntity.ok(userDetail);
    }

    // myPage 조회
    @GetMapping("/users/{userId}/mypage")
    public ResponseEntity<UserPageDto> getMyPage(
            @PathVariable Long userId
    ) {
        UserPageDto userPageDto = userservice.getInfo(userId);

        return ResponseEntity.ok(userPageDto);
    }

    // 유저 포인트 로그 조회
    // todo 포인트 획득, 감소 시 코드상에서 처리?
    @GetMapping("/users/{userId}/points")
    public ResponseEntity<List<UserPointLogDto>> getPointView(
            @PathVariable Long userId
    ) {
        List<PointLog> pointLogs = userservice.getPointLog(userId);
        List<UserPointLogDto> userPointLogDtos = pointLogs.stream()
                .map(UserPointLogDto::new)
                .toList();
        return ResponseEntity.ok(userPointLogDtos);
    }

    // 유저 아이템 조회
    @GetMapping("/users/{userId}/items")
    public ResponseEntity<List<MyGoods>> getMyItems(
            @PathVariable Long userId
    ) {
        List<MyGoods> myGoods = userservice.getGoods(userId);
        return ResponseEntity.ok(myGoods);
    }


}