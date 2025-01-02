package com.movelog.domain.record.service;

import com.movelog.domain.record.domain.Keyword;
import com.movelog.domain.record.domain.Record;
import com.movelog.domain.record.dto.req.CreateRecordReq;
import com.movelog.domain.record.repository.KeywordRepository;
import com.movelog.domain.record.repository.RecordRepository;
import com.movelog.domain.user.domain.User;
import com.movelog.domain.user.domain.repository.UserRepository;
import com.movelog.global.DefaultAssert;
import com.movelog.global.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final KeywordRepository keywordRepository;
    private final S3Util s3Util;

    @Transactional
    public void createRecord(Long userId, CreateRecordReq createRecordReq, MultipartFile img) {
        User user = validUserById(userId);
        String recordImgUrl = s3Util.upload(img);

        Keyword keyword = Keyword.builder()
                .keyword(createRecordReq.getNoun())
                .build();

        keywordRepository.save(keyword);

        Record record = Record.builder()
                .user(user)
                .keyword(keyword)
                .verbType(createRecordReq.getVerbType())
                .recordImage(recordImgUrl)
                .build();

        recordRepository.save(record);
    }
    private User validUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        DefaultAssert.isOptionalPresent(userOptional);
        return userOptional.get();
    }
}
