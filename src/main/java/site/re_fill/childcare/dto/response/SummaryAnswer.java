package site.re_fill.childcare.dto.response;

import lombok.Builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Builder
public record SummaryAnswer(
        List<String> tagList,
        List<String> summaryList
) {
    public static SummaryAnswer from(final String data) {

        // 태그와 나머지 부분 분리
        String[] parts = data.split(", ");
        List<String> tagList = new ArrayList<>();
        List<String> summaryList = new ArrayList<>();
        for (String part : parts) {
            if (part.startsWith("#")) {
                tagList.add(part.replace(",", ""));
            } else {
                summaryList.add(part);
            }
        }

        // 문장 분리
        String[] sentences = String.join(" ", summaryList).split("\\. ");
        summaryList = Arrays.asList(sentences);

        return SummaryAnswer.builder()
                .tagList(tagList)
                .summaryList(summaryList)
                .build();
    }
}
