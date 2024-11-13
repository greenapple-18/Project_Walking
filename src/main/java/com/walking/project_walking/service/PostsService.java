package com.walking.project_walking.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.walking.project_walking.domain.LikeLog;
import com.walking.project_walking.domain.PostImages;
import com.walking.project_walking.domain.Posts;

import com.walking.project_walking.domain.dto.NoticeResponseDto;
import com.walking.project_walking.domain.dto.PostCreateResponseDto;
import com.walking.project_walking.domain.dto.PostRequestDto;
import com.walking.project_walking.domain.dto.PostResponseDto;
import com.walking.project_walking.domain.dto.PostSummuryResponseDto;
import com.walking.project_walking.repository.CommentsRepository;
import com.walking.project_walking.repository.PostImagesRepository;
import com.walking.project_walking.repository.PostsRepository;
import com.walking.project_walking.repository.UserLikeLogRepository;
import com.walking.project_walking.repository.UserRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class PostsService {
    private final AmazonS3 amazonS3Client;
    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;
    private final PostImagesRepository postImagesRepository;
    private final UserLikeLogRepository userLikeLogRepository;

    // S3 버킷 이름 주입
    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    private static final double THRESHOLD = 100.0;

    // 특정 게시판, 특정 페이지의 게시물을 가져오는 메소드 (가져오는 게시글 갯수는 6개로 정의)
    public List<PostResponseDto> getPostsByBoardId(Long boardId, PageRequest pageRequest) {

        return postsRepository.findByBoardId(boardId, pageRequest)
                .map(post -> {
                    Integer commentsNumber = commentsRepository.countCommentsByPostId(
                            post.getPostId());
                    String postNickname = userRepository.getNicknameByUserId(post.getUserId());
                    List<String> imageUrl = postImagesRepository.findImageUrlsByPostId(
                            post.getPostId());
                    return PostResponseDto.fromEntity(post, commentsNumber, postNickname, imageUrl);
                }).toList();
    }

    // 공지사항만 불러오는 메소드 (2개)
    public List<NoticeResponseDto> getNoticePosts(PageRequest pageRequest) {
        return postsRepository.findByBoardId(1L, pageRequest)
                .map(NoticeResponseDto::fromEntity).toList();

    }

    // boardId와 제목, 내용, 글쓴이를 통해 특정 게시물을 조회하는 메소드
    public List<PostResponseDto> searchPosts(Long boardId, String title, String content,
            String nickname, PageRequest pageRequest) {
        Long userId = userRepository.getUserIdByNickname(nickname);
        Page<Posts> postsPage = postsRepository.searchPosts(boardId, title, content, userId,
                pageRequest);
        return postsPage.getContent().stream()
                .map(post -> {
                    Integer commentsNumber = commentsRepository.countCommentsByPostId(
                            post.getPostId());
                    String postNickname = userRepository.getNicknameByUserId(post.getUserId());
                    List<String> imageUrl = postImagesRepository.findImageUrlsByPostId(
                            post.getPostId());
                    return PostResponseDto.fromEntity(post, commentsNumber, postNickname, imageUrl);
                })
                .toList();
    }

    // 전체 게시판의 게시물 중 인기 게시글(weightValue가 특정 임계값을 넘은 게시글)을 모두 조회하는 메소드
    public List<PostResponseDto> getHotPosts() {
        List<Posts> hotPosts = postsRepository.findHotPosts(THRESHOLD);
        return hotPosts.stream()
                .map(post -> {
                    Integer commentsNumber = commentsRepository.countCommentsByPostId(
                            post.getPostId());
                    String postNickname = userRepository.getNicknameByUserId(post.getUserId());
                    List<String> imageUrl = postImagesRepository.findImageUrlsByPostId(
                            post.getPostId());
                    return PostResponseDto.fromEntity(post, commentsNumber, postNickname, imageUrl);
                })
                .toList();
    }

    // 특정 게시판 중 가장 인기 있는(weightValue 값이 가장 큰) 게시물을 조회하는 메소드
    public PostResponseDto getOneHotPost(Long boardId) {
        List<Posts> hotPosts = postsRepository.findHotPosts(THRESHOLD);

        return hotPosts.stream()
                .filter(post -> post.getBoardId().equals(boardId))
                .max(Comparator.comparing(Posts::getWeightValue))
                .map(post -> {
                    Integer commentsNumber = commentsRepository.countCommentsByPostId(
                            post.getPostId());
                    String postNickname = userRepository.getNicknameByUserId(post.getUserId());
                    List<String> imageUrl = postImagesRepository.findImageUrlsByPostId(
                            post.getPostId());
                    return PostResponseDto.fromEntity(post, commentsNumber, postNickname, imageUrl);
                })
                .orElse(null);
    }

    // 특정 유저가 작성한 게시글을 조회하는 메소드
    public List<PostSummuryResponseDto> getPostsByUserId(Long userId) {
        List<Posts> posts = postsRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return posts.stream()
                .map(post -> {
                    Integer commentsNumber = commentsRepository.countCommentsByPostId(
                            post.getPostId());
                    return PostSummuryResponseDto.fromEntity(post, commentsNumber);
                })
                .toList();
    }

    // 검색 시 결과의 페이지를 구하는 메소드
    public int getTotalPages(Long boardId, String title, String content, String nickname,
            int pageSize) {
        Long userId = userRepository.getUserIdByNickname(nickname);
        long totalPosts = postsRepository.countBySearchCriteria(boardId, title, content, userId);
        return (int) Math.ceil((double) totalPosts / pageSize);
    }

    //게시글 생성
    public PostCreateResponseDto savePost(PostRequestDto postRequestDto, List<MultipartFile> files)
            throws IOException {
        Posts post = Posts.builder()
                .userId(postRequestDto.getUserId())
                .boardId(postRequestDto.getBoardId())
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .viewCount(0)
                .likes(0)
                .weightValue(0)
                .isDeleted(false)
                .build();

        // 게시글 저장
        Posts savedPost = postsRepository.save(post);

        // 이미지 업로드 및 URL 저장
        List<PostImages> postImagesList = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String fileUrl = uploadFileToS3(file);
                PostImages postImage = new PostImages(savedPost.getPostId(), fileUrl);
                postImagesList.add(postImage);
            }
        }

        // 이미지 저장
        postImagesRepository.saveAll(postImagesList);

        return new PostCreateResponseDto(
                savedPost.getPostId(),
                savedPost.getUserId(),
                savedPost.getBoardId(),
                savedPost.getTitle(),
                savedPost.getContent(),
                savedPost.getViewCount(),
                savedPost.getCreatedAt(),
                savedPost.getModifiedAt(),
                savedPost.getIsDeleted()
        );
    }

    // S3에 파일을 업로드하는 메서드
    public String uploadFileToS3(MultipartFile file) throws IOException {
        try {
            String uniqueFileName =
                    "uploads/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            amazonS3Client.putObject(
                    new PutObjectRequest(bucketName, uniqueFileName, file.getInputStream(),
                            metadata));

            // S3에 업로드된 파일의 URL 반환
            return amazonS3Client.getUrl(bucketName, uniqueFileName).toString();
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.", e);
        }
    }


    //게시글 수정 (작성자만 가능, 기본 이미지 유지/삭제 가능)
    public PostCreateResponseDto modifyPost(Long postId, Long userId, PostRequestDto postRequestDto,
            List<MultipartFile> files, boolean deleteExistingImages) throws IOException {
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        if (!post.getUserId().equals(userId)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        // 기존 이미지 삭제 옵션 처리
        if (deleteExistingImages) {
            List<String> existingImageUrls = getPostImageUrls(postId);
            for (String imageUrl : existingImageUrls) {
                deleteFileFromS3(imageUrl);
            }
            postImagesRepository.deleteByPostId(postId);  // 데이터베이스에서 기존 이미지 정보 삭제
        }

        // 새 이미지 업로드 및 저장
        if (files != null && !files.isEmpty()) {
            List<PostImages> postImagesList = new ArrayList<>();
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileUrl = uploadFileToS3(file);
                    PostImages postImage = new PostImages(postId, fileUrl);
                    postImagesList.add(postImage);
                }
            }
            postImagesRepository.saveAll(postImagesList);  // 데이터베이스에 새 이미지 정보 저장
        }
        // 게시글 수정 후 저장
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        Posts updatedPost = postsRepository.save(post);

        return new PostCreateResponseDto(
                updatedPost.getPostId(),
                updatedPost.getUserId(),
                updatedPost.getBoardId(),
                updatedPost.getTitle(),
                updatedPost.getContent(),
                updatedPost.getViewCount(),
                updatedPost.getCreatedAt(),
                updatedPost.getModifiedAt(),
                updatedPost.getIsDeleted()
        );
    }


    //게시글 삭제 (작성자만 가능)
    public void deletePost(Long postId, Long userId) {
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        if (!post.getUserId().equals(userId)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        // 게시글에 연관된 이미지 URL 가져오기 및 삭제
        List<String> imageUrls = getPostImageUrls(postId);
        for (String imageUrl : imageUrls) {
            deleteFileFromS3(imageUrl);
        }
        postImagesRepository.deleteByPostId(postId);  // 데이터베이스에서 이미지 정보 삭제

        // 게시글 삭제
        postsRepository.delete(post);
    }

    // 게시글에 연관된 이미지 URL 가져오기
    public List<String> getPostImageUrls(Long postId) {
        return postImagesRepository.findImageUrlsByPostId(postId);
    }

    // S3에서 파일 삭제 메서드 (지정된 파일 삭제)
    public void deleteFileFromS3(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);  // URL에서 파일 이름 추출
        amazonS3Client.deleteObject(bucketName, "uploads/" + fileName);
    }

    // 데이터베이스에서 연관된 파일 삭제
    public void deletePostImages(Long postId) {
        postImagesRepository.deleteByPostId(postId);
    }

    public void savePostImages(Long postId, List<PostImages> postImagesList) {
        postImagesRepository.saveAll(postImagesList);
    }

    //게시글 상세 조회

    public PostResponseDto getPostById(Long postId) {
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        Integer commentsNumber = commentsRepository.countCommentsByPostId(postId);
        String postNickname = userRepository.getNicknameByUserId(post.getUserId());
        List<String> imageUrl = postImagesRepository.findImageUrlsByPostId(postId);
        post.setViewCount(post.getViewCount() + 1);

        return PostResponseDto.fromEntity(post, commentsNumber, postNickname, imageUrl);
    }

    // 유저가 해당 게시글에 좋아요를 눌렀는지 확인하는 메소드
    public boolean hasLiked(Long userId, Long postId) {
        return userLikeLogRepository.findByUserIdAndPostId(userId, postId).isPresent();
    }

    // 좋아요 버튼을 클릭 시
    @Transactional
    public void likePost(Long userId, Long postId) {
        // 좋아요 로그 확인
        boolean hasLiked = hasLiked(userId, postId);

        // 게시글 조회
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        if (hasLiked) {
            // 유저가 이미 좋아요를 눌렀다면, 좋아요 로그 삭제하고 게시글 좋아요 수 감소
            userLikeLogRepository.deleteByUserIdAndPostId(userId, postId);  // 좋아요 로그 삭제
            post.setLikes(post.getLikes() - 1);  // 게시글 좋아요 수 감소
        } else {
            // 유저가 좋아요를 안 눌렀다면, 새로운 좋아요 로그 추가하고 게시글 좋아요 수 증가
            LikeLog newLikeLog = new LikeLog();
            newLikeLog.setUserId(userId);
            newLikeLog.setPostId(postId);
            userLikeLogRepository.save(newLikeLog);  // 새로운 좋아요 로그 추가
            post.setLikes(post.getLikes() + 1);  // 게시글 좋아요 수 증가
        }

        // 게시글 정보 저장
        postsRepository.save(post);  // 게시글 저장
    }

}