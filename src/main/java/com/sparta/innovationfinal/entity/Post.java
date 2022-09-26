package com.sparta.innovationfinal.entity;

import com.sparta.innovationfinal.dto.requestDto.PostRequestDto;
import com.sparta.innovationfinal.dto.responseDto.PostResponseDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private String postCategory;

    @Column(nullable = false)
    private String postContent;

    @Column(nullable = false)
    private int likeNum;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne
    private Member member;

//    public boolean validateMember(Member member) {
//        return this.member.getId() == (member.getId());
//    }

    public void update(PostRequestDto requestDto) {
        this.postTitle = requestDto.getPostTitle();
        this.postContent = requestDto.getPostContent();
        this.postCategory = requestDto.getPostCategory();
    }
    public void pushLike() {
        this.likeNum++;
    }

    public void pushDislike() {
        if(this.likeNum>0)
        {
            this.likeNum--;
        }
    }

//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Post> posts;
}
