package com.walking.project_walking.repository;

import com.walking.project_walking.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<Users, Long> {

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    @Query("SELECT u.userId FROM Users u WHERE u.nickname = :nickname")
    Long getUserIdByNickname(@Param("nickname") String nickname);
}
