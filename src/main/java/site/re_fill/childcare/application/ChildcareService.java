package site.re_fill.childcare.application;

import site.re_fill.childcare.dto.request.Question;
import site.re_fill.childcare.dto.response.Answer;
import site.re_fill.childcare.dto.response.SummaryAnswer;

public interface ChildcareService {
    Answer interpretQuestion(Long childId, Question request);

    SummaryAnswer interpretPersonality(Long childId);
}
