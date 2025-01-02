package com.movelog.domain.record.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateRecordReq {
    @Schema(type = "Long", example = "0", description = "0 -> 했어요, 1 -> 먹었어요, 2 -> 갔어요")
    private Long verbType;

    @Schema(type = "String", example = "헬스", description = "명사 작성")
    private String noun;
}
