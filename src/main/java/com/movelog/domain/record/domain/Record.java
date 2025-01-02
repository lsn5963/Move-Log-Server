package com.movelog.domain.record.domain;

import com.movelog.domain.common.BaseEntity;
import com.movelog.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "Record")
@NoArgsConstructor
@Getter
public class Record extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id", updatable = false)
    private Long recordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "verb_type")
    private VerbType recordType;

    @Column(name = "record_image")
    private String recordImage;

    @Column(name = "action_time")
    private LocalDateTime actionTime;

    @Builder
    public Record(User user, Keyword keyword, VerbType recordType, String recordImage) {
        this.user = user;
        this.keyword = keyword;
        this.recordType = recordType;
        this.recordImage = recordImage;
        this.actionTime = actionTime == null? LocalDateTime.now():actionTime;
    }



}
