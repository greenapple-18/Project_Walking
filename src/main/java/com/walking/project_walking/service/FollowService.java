package com.walking.project_walking.service;

import com.walking.project_walking.domain.Follow;
import com.walking.project_walking.domain.Users;
import com.walking.project_walking.domain.followdto.FollowProfileDto;
import com.walking.project_walking.domain.followdto.FollowerProfileDto;
import com.walking.project_walking.repository.FollowRepository;
import com.walking.project_walking.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@RequestMapping("/api")
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    // 사용자 팔로우, 언팔로우 토글 기능
    @Transactional
    public ResponseEntity<String> toggleFollowUser(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("자기 자신은 팔로우할 수 없습니다.");
        }

        // 팔로워와 팔로우할 사용자 확인
        Users follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("팔로워를 찾을 수 없습니다."));
        Users following = userRepository.findById(followingId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우할 사용자를 찾을 수 없습니다."));

        // 팔로우 관계 확인
        Optional<Follow> existingFollow = followRepository.findByFollowUserAndFollowingUser(follower, following);

        if (existingFollow.isPresent()) {
            // 이미 팔로우 관계가 존재하면 언팔로우 처리
            followRepository.delete(existingFollow.get());
            return ResponseEntity.ok("언팔로우가 완료되었습니다.");
        } else {
            // 팔로우 관계가 존재하지 않으면 팔로우 추가
            Follow follow = new Follow();
            follow.setFollowUser(follower);
            follow.setFollowingUser(following);
            followRepository.save(follow);
            return ResponseEntity.ok("팔로우가 완료되었습니다.");
        }
    }

    // 팔로우 조회 (현재 사용자의 팔로잉 목록)
    public List<Follow> getFollowing(Long userId) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return followRepository.findFollowingByFollowerId(userId);
    }

    // 팔로워 조회 (자기 자신을 제외하고 조회)
    public List<FollowerProfileDto> getFollowers(Long userId) {
        List<Follow> follows = followRepository.findFollowersExcludingSelf(userId);

        // FollowerProfileDto로 변환하여 반환
        return follows.stream()
                .map(FollowerProfileDto::new)
                .toList();
    }

    // 팔로잉 수 조회
    public Long countFollowing(Long userId) {
        // 유저가 존재하는지 확인
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return followRepository.countFollowing(userId);
    }

    // 팔로워 수 조회
    public Long countFollowers(Long userId) {
        // 유저가 존재하는지 확인
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return followRepository.countFollowers(userId);
    }

}
