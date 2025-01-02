package com.movelog.domain.record.domain;

import com.movelog.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Keyword")
@NoArgsConstructor
@Getter
public class Keyword extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id", updatable = false)
    private Long keywordId;

    private String keyword;

    @OneToMany(mappedBy = "keyword")
    private List<Record> records = new ArrayList<>();

    @Builder
    public Keyword(String keyword) {
        this.keyword = keyword;
    }
}
