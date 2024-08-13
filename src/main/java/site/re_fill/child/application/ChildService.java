package site.re_fill.child.application;

import site.re_fill.child.dto.SimpleChildDto;
import site.re_fill.child.dto.request.CreateChild;
import site.re_fill.child.dto.request.UpdateAnswer;
import site.re_fill.child.dto.response.GetChild;
import site.re_fill.child.dto.response.GetChildren;

public interface ChildService {
    Long createChild(Long authId, CreateChild request);

    GetChild getChild(Long childId);

    GetChildren getChildren(Long authId);

    SimpleChildDto updateAnswer(Long childId, Integer answerNumber, UpdateAnswer updateAnswer);
}
