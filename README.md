## TEAM : 걸어서 코딩 속으로

http://ec2-43-201-90-83.ap-northeast-2.compute.amazonaws.com:8080/

## Tech Stack

### Backend
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
  
### Frontend
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)
  
### Infrastructure
![AWS EC2](https://img.shields.io/badge/AWS%20EC2-FF9900?style=for-the-badge&logo=amazon-ec2&logoColor=white)
![AWS S3](https://img.shields.io/badge/Amazon%20S3-569A31?style=for-the-badge&logo=amazon-s3&logoColor=white)
![AWS RDS](https://img.shields.io/badge/AWS%20RDS-527FFF?style=for-the-badge&logo=amazon-rds&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)



## 팀원 소개
<br/>

|                       [김경빈](https://github.com/kkb00714)                        |                       [김근우](https://github.com/kkwggwp)                        |                       [임수응](https://github.com/greenapple-18)                       |                       [안서현](https://github.com/seohyun96)                        |                        [전원용](https://github.com/sqrt3)                         |
|:------------------------------------------------:|:------------------------------------------------:|:-----------------------------------------------:|:-----------------------------------------------:|:--------------------------------------------------:|
| <img src="https://github.com/user-attachments/assets/a5814bf9-cd88-4347-9d1f-e88062f784cf" alt="김경빈" width="100"> | <img src="https://github.com/user-attachments/assets/057a6c1b-3201-4ece-982a-410178ddd86b" alt="김근우" width="100"> | <img src="https://github.com/user-attachments/assets/b68a8a69-0396-4018-b901-83e88c70d435" alt="임수응" width="100"> | <img src="https://github.com/user-attachments/assets/dc5421a6-5917-4814-bb5b-98dcfbfda5aa" alt="안서현" width="100"> | <img src="https://github.com/user-attachments/assets/8e57303d-8c79-41bb-99ff-3723434e1162" alt="전원용" width="100"> |
|                       팀장(대장핑)                       |                        팀원(모르핑)                        |                       팀원(머리가핑)                        |                       팀원(눈물이핑)                        |                         팀원(미아핑)                         |
|        - 마이페이지, 타 유저 페이지 API 및 UI 구현<br/>- 팔로우 API 및 UI 구현        |       - 회원가입 API 및 UI 구현<br/>- 로그인 및 비밀번호 찾기 페이지 API 및 UI 구현        |       - 게시판 페이지 API 및 UI 구현<br/>- 인기 게시글 로직 API 및 UI 구현       |       - 게시글 및 댓글 CRUD API 및 UI 구현        | - 관리자 페이지 API 및 UI 구현<br/>- 아이템 API 및 UI 구현 |


<br/><br/>
# 📚 Project Walking 소개



🏃🏻 현대 사회에서 건강 관리와 운동은 매우 중요하지만, 바쁜 일상 속에서 꾸준히 **운동을 실천하기는 쉽지 않습니다.**
또한, 운동에 대한 동기 부여가 부족하거나 혼자 운동하기 어려운 경우가 많습니다.
이에 따라 운동을 지속해서 할 수 있는 동기와 커뮤니티의 소속감을 제공하는 시스템이 필요하다고 생각했습니다.

### 서비스명 : 거르미 (Geoleumi)



- 목표 : 사용자가 걷기 및 운동을 인증하는 건강한 운동 커뮤니티를 구축하여 사용자의 참여를 유도하고, 건강한 생활 습관을 장려하기 위해 **거르미**를 기획했습니다.

<aside>
💡

거르미는 현재 진행 중인 ‘오르미’의 이름을 따서 만들었습니다.

저희 팀은 부트캠프 오르미의 단어를 더 높은 곳으로 ‘올라간다’는 해석을 적용했습니다.
따라서 진행 중인 프로젝트 또한 더 나은 곳으로 한 발자국 나아감과 동시에
하루 한 거르미(걸음이) 필요한 커뮤니티이기 때문에, *오르미를 변형한 거르미*로 결정했습니다.

</aside>

### 프로젝트 기대 사항



- 간편하게 가입하여 쉽게 접근할 수 있는 커뮤니티
- 하루의 운동을 통해 참여감을 느끼고 꾸준한 실천을 유도
- 커뮤니티 내 운동 인증 및 커뮤니티 활동을 통한 보상으로 동기 부여

### 주요 기능



- 하루 걷기 인증으로 인한 포인트 획득
    - 유저는 게시글 작성 및 댓글 작성을 통해 포인트와 경험치를 획득합니다.
    - 경험치를 획득해 레벨이 점차 높아진다면, 유저의 권한은 순서대로 ‘`PATHFINDER`’, ‘`PIONEER`’, ‘`WALKER_HOLIC`’ 세 단계를 거쳐 최종 권한을 획득합니다.
    - 각 권한은 순서대로 포인트 획득 2배 해금, 비밀 댓글 기능 해금, 아이템 선물 기능 해금.
- 모은 포인트로 아이템 구매
    - 유저는 현재 보유 중인 포인트로 아이템을 구매하여 사용할 수 있습니다.

## ERD



![image](https://github.com/user-attachments/assets/88a0aab3-c5c3-4ca4-95c6-e58269674272)




## 🚀 API 목차

### 📌 참고사항

- 본 API는 RESTful하게 설계되었으며, 메서드와 URL을 통해 기능을 명확히 구분하였습니다.
- 관리자 권한이 필요한 API는 "(Admin Only)"로 명시하였습니다.

1. 일반 유저 API
    - 유저 (Users)
    - 이미지 (Image)
    - 회원가입 및 로그인 (Sign-Up / Sign-In)
    - 상품 (Goods)
2. 관리자 API
    - 관리자 (Administrator)
3. 게시판 API
    - 댓글 (Comments)
    - 게시글 (Posts)


## 🚀 일반 유저 API

### [ 📖 Users ]

사용자 정보 조회 및 수정, 팔로우 기능을 제공합니다.

| 서비스명 | 메서드 | URL | 설명 |
| --- | --- | --- | --- |
| modifyUserById | PUT | /api/users/{userId} | 사용자 정보 수정 |
| deleteUserById | DELETE | /api/users/{userId} | 회원 탈퇴 요청 |
| toggleFollowUser | POST | /api/users/{followerId}/toggle-follow/{followingId} | 특정 사용자 팔로우/언팔로우 |
| getFollowing | GET | /api/users/{userId}/following | 팔로잉 전체 보기 |
| getFollower | GET | /api/users/{userId}/follower | 팔로워 전체 보기 |
| getFollowingCount | GET | /api/users/{userId}/count-following | 팔로잉 수 조회 |
| getFollowerCount | GET | /api/users/{userId}/count-followers | 팔로워 수 조회 |
| getUserDetail | GET | /api/users/{userId}/info | 유저 정보 상세 조회 |
| getMyPage | GET | /api/users/{userId}/myPage | 마이페이지 정보 조회 |
| getPointView | GET | /api/users/{userId}/points | 포인트 내역 조회 |
| getMyItems | GET | /api/users/{userId}/items | 보유 아이템 목록 조회 |
| userItem | POST | /api/{userId}/items/{goodsId}/use | 보유 아이템 사용 |
| getRecentPost | GET | /api/users/{userId}/recent-post | 최근 게시물 조회 |

### [ 📖 Image ]

이미지 업로드 및 삭제를 담당합니다.

| 서비스명 | 메서드 | URL | 설명 |
| --- | --- | --- | --- |
| s3Upload | POST | /s3/upload | 이미지 업로드 |
| s3delete | DELETE | /s3/delete | 이미지 삭제 |

### [ 📖 Sign-Up / Sign-In ]

회원가입, 로그인 및 유저 정보 확인을 위한 API입니다.

| 서비스명 | 메서드 | URL | 설명 |
| --- | --- | --- | --- |
| signup | POST | /auth/signup | 회원가입 |
| login | POST | /auth/login | 로그인 |
| logout | GET | /auth/logout | 로그아웃 |
| checkUseremail | GET | /auth/check-email?email={userEmail} | 이메일 중복 확인 |
| checkNickname | GET | /auth/check-nickname?nickname={userNickname} | 닉네임 중복 확인 |
| checkPhone | GET | /auth/check-phone | 휴대전화 중복 확인 |
| findPassword | POST | /auth/request-password-reset | 비밀번호 재설정 요청 |

### [ 📖 Goods ]

상품 조회 및 구매, 선물하기와 같은 상품 관련 API입니다.

| 서비스명 | 메서드 | URL | 설명 |
| --- | --- | --- | --- |
| getAllGoods | GET | /api/goods | 상품 목록 조회 |
| getGoods | GET | /api/goods/{goodsId} | 상품 단건 조회 |
| purchaseGoods | POST | /api/goods/{goodsId}/purchase | 상품 구매 |
| giftGoods | POST | /api/goods/{goodsId}/gift | 상품 선물 |
| addGoods | POST | /api/goods | 상품 등록 (Admin Only) |
| updateGoods | PUT | /api/goods/{goodsId} | 상품 수정 (Admin Only) |
| deleteGoods | DELETE | /api/goods/{goodsId} | 상품 삭제 (Admin Only) |
| getGoodsDescription | GET | /api/goods/{goodsId}/description | 상품 설명 조회 |

### [ 📖 Comments ]

게시글에 대한 댓글 및 답글 작성, 삭제 기능을 제공합니다.

| 서비스명 | 메서드 | URL | 설명 |
| --- | --- | --- | --- |
| saveComment | POST | /comments/{postId} | 댓글 및 답글 생성 |
| deleteComment | DELETE | /comments/{commentId} | 댓글 및 답글 삭제 |

### [ 📖 Boards ]

게시판 목록 조회 및 공지사항 관련 API입니다.

| 서비스명 | 메서드 | URL | 설명 |
| --- | --- | --- | --- |
| getBoardsList | GET | /api/boards | 게시판 목록 조회 |
| getPosts | GET | /api/boards/posts?boardId={boardId}&page={pageNum} | 특정 게시판의 게시글 조회 |
| getNotices | GET | /api/boards/notices | 공지사항 조회 |
| getPostsCount | GET | /api/boards/{boardId}/pagescount | 게시판 페이지 수 조회 |

### [ 📖 Posts ]

게시글 작성, 조회, 수정, 삭제 기능을 제공합니다.

| 서비스명 | 메서드 | URL | 설명 |
| --- | --- | --- | --- |
| postDetails | GET | /posts/{postId} | 게시글 상세 보기 |
| savePosts | POST | /posts | 게시글 작성 |
| deletePosts | DELETE | /posts/{postId} | 게시글 삭제 |
| modifyPosts | POST | /posts/modify?postId={postId}&userId={userId} | 게시글 수정 |
| searchPosts | GET | /api/posts/search?boardId={boardId}&title={title}&nickname={nickname}&content={content} | 특정 게시글 조회 |
| getHotPost | GET | /api/posts/hot | 모든 게시판의 인기글 조회 |
| getHotPost | GET | /api/posts/hot/{boardId} | 특정 게시판의 인기글 조회 |
| userPosts | GET | /api/posts?userId={userId} | 유저 작성 게시글 목록 조회 |
| likePost | POST | /api/posts?userId={userId}&postId={postId} | 게시글 좋아요 활성화/비활성화 |



## 🚀 관리자 API

### [ 📖 Administrator ]

관리자는 사용자 관리, 게시판 생성, 수정, 삭제 등의 기능을 수행할 수 있습니다.

| 서비스명 | 메서드 | URL | 설명 |
| --- | --- | --- | --- |
| getAllUsers | GET | /api/admin/users | 전체 유저 조회 |
| findUser | POST | /api/admin/users/{userId} | 특정 유저 조회 |
| addBoard | POST | /api/admin/boards | 게시판 생성 |
| deleteBoard | DELETE | /api/admin/boards/{boardId} | 게시판 삭제 |
| modifyBoard | PUT | /api/admin/boards/{boardId} | 게시판 수정 |
| updateUser | POST | /api/admin/user/{userId} | 유저 권한 수정 |

## 화면 구성


### [ 회원가입 및 로그인 화면 ]



| ![image](https://github.com/user-attachments/assets/db57a188-844a-4175-94f1-c41da545684b) | ![image](https://github.com/user-attachments/assets/4ca72716-a002-4a55-a5bd-b19c00a9b93c) | ![image](https://github.com/user-attachments/assets/897870d7-ac2b-4d0d-ae84-a8b741892c4d) |
| --- | --- | --- |
회원가입 시 오류: 비밀번호가 일치 X | 이미 사용중인 닉네임 | 비밀번호 찾기 페이지


- 회원가입 시, 이메일, 닉네임, 전화번호는 중복 체크를 합니다.
- 비밀번호 최소 조건이 맞는지 확인하고 비밀번호 확인 부분에서도 현재 입력한 비밀번호가 맞는지 체크합니다.
- 생년월일은 오늘 이전 날짜까지 선택 가능합니다.
- 비밀번호는 암호화 되어 DB에 저장됩니다.
- 비밀번호 찾기 기능은 해당하는 이메일로 랜덤 비밀번호를 전송하여 초기화 할 수 있도록 합니다.

------------------------------------

### [ 마이페이지 ]

| ![image](https://github.com/user-attachments/assets/c4160238-c40d-4596-9782-e4ae43ab0091) | ![image](https://github.com/user-attachments/assets/1bd0738e-1d69-44fc-bac6-9579fb72f013) | ![image](https://github.com/user-attachments/assets/0ce92e93-1f76-4638-935e-450488db8016) |
| --- | --- | --- |
마이페이지 | 팔로잉 목록 | 다른 유저의 페이지


- 마이페이지에서는 사용자의 정보를 수정할 수 있으며, 보유하고 있는 아이템을 조회할 수 있습니다.
- 최근 본 게시물은 가장 최근에 본 게시글의 상세보기 페이지에 접근하면 갱신됩니다.
- 내가 작성한 게시글을 마이페이지에서 볼 수 있습니다. (클릭하면 해당 상세보기 페이지로 이동)
- 팔로워 / 팔로잉 목록에서 특정 유저를 클릭하면 해당 유저의 페이지로 이동합니다.
- 타 유저 페이지에서는 팔로우 / 언팔로우가 가능하며, 해당 유저가 작성한 게시글을 볼 수 있습니다.

------------------------------------

### [ 인기 게시글 선정 ]

![image](https://github.com/user-attachments/assets/2eb5c026-c332-4d98-a4c0-8ab7aede3f80)


- 인기글 선정 로직을 통해 인기글을 설정합니다.
로직 : `((likes * 5) + (view_count * 2)) * time_weight;`
→ 1시간 동안은 time_weight 값이 1으로 고정되지만, 1시간이 지나면 time_weight는 0.75로 변경되고, 24시간 이후에는 0.5로 고정됩니다.

------------------------------------

### [ 1:1 문의 게시판 ]
| ![image](https://github.com/user-attachments/assets/fa055a0a-4b1a-4b1a-93f5-2151685defe9) | ![image](https://github.com/user-attachments/assets/f0890fee-66d2-4648-a023-de4e16e28f53) |
| --- | --- |
유저의 1대1 문의 화면 | 관리자의 1대1문의 화면


- 유저는 1:1 문의 게시판에서 자신이 작성한 글만 볼 수 있습니다.
- 관리자는 모든 유저가 작성한 1:1 문의 게시판의 게시글을 열람할 수 있습니다.

------------------------------------

### [ 게시글 상세보기 ]
| ![image](https://github.com/user-attachments/assets/2701c270-1494-4c06-9ba7-8af98b88f796) | ![image](https://github.com/user-attachments/assets/1aea4c73-250e-417b-91af-403be087e355) |
| --- | --- |
게시글 상세보기 페이지 | 게시글 하위 댓글 리스트

- 특정 게시글을 클릭하면 상세 보기 페이지로 이동하여 게시글의 제목, 내용, 좋아요 수, 조회수, 댓글 등을 노출합니다.
- 게시글 작성자만이 게시글을 수정 및 삭제가 가능합니다.
- 관리자는 게시글 삭제만 가능하며, 삭제된 댓글을 열람할 수 있습니다.

------------------------------------

### [ 관리자 페이지 ]
| ![image](https://github.com/user-attachments/assets/5a95454d-38f5-4225-84c6-1b2b472c132b) | ![image](https://github.com/user-attachments/assets/16b48547-b066-4193-b7da-fb997388f8e7) | ![image](https://github.com/user-attachments/assets/d601a852-ed41-418a-9c09-ca2d64f9b29a) |
| --- | --- | --- |
관리자 페이지 (유저관리) | 관리자 페이지 (게시판 관리) | 관리자 페이지 (아이템 관리)

- 관리자 페이지에서는 전체 유저를 볼 수 있으며, 특정 유저의 정보를 조작할 수 있습니다.
- 게시판 생성 및 상품을 생성은 오직 관리자만 가능합니다.

<br/>
 
| ![image](https://github.com/user-attachments/assets/fafcf08f-3aea-4b3a-b7de-4aff92a001ad) | ![image](https://github.com/user-attachments/assets/678eb60f-394f-4d91-9a83-3e615f154d6f) |
| --- | --- |
아이템 구매 | 아이템 사용

- 사용자는 보유하고 있는 포인트를 사용해 아이템을 구매할 수 있으며, 아이템을 사용하면 1개씩 차감됩니다. (아이템이 1개 있다면 사용 후 DB에서 삭제됩니다.)
