package site.re_fill.childcare.application;

import site.re_fill.childcare.dto.request.Question;
import site.re_fill.childcare.dto.response.Answer;

public interface ChildcareService {
    Answer interpretQuestion(Question request);
}
