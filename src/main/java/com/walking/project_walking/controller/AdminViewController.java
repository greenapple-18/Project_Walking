package com.walking.project_walking.controller;
import com.walking.project_walking.domain.Users;
import com.walking.project_walking.domain.dto.BoardResponseDto;
import com.walking.project_walking.domain.dto.GoodsResponseDto;
import com.walking.project_walking.domain.userdto.UserResponse;
import com.walking.project_walking.service.BoardService;
import com.walking.project_walking.service.GoodsService;
import com.walking.project_walking.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminViewController {
    private final UserService userService;
    private final BoardService boardService;
    private final GoodsService goodsService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public String adminView(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Users currentUser) {
            UserResponse userResponse = new UserResponse(userService.findById(currentUser.getUserId()));
            model.addAttribute("user", userResponse);
        }

        return "admin";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String manageUsers(Model model) {
        List<UserResponse> usersList = userService.findAll().stream()
                .map(UserResponse::new)
                .toList();
        model.addAttribute("userList", usersList);
        return "users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users/{userId}")
    public String manageUser(@PathVariable Long userId, Model model) {
        UserResponse userResponse = new UserResponse(userService.findById(userId));
        model.addAttribute("user", userResponse);
        return "user";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/board")
    public String manageBoard(Model model) {
        List<BoardResponseDto> boardList = boardService.getBoardsList();
        model.addAttribute("boardList", boardList);
        return "board";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/goods")
    public String manageGoods(Model model) {
        List<GoodsResponseDto> goodsList = goodsService.getAllGoods().stream()
                .map(GoodsResponseDto::new)
                .toList();
        model.addAttribute("goodsList", goodsList);
        return "goods-manager";
    }
}
