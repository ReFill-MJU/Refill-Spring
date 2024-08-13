package site.re_fill.child.application;

import site.re_fill.child.dto.request.CreateChild;
import site.re_fill.child.dto.request.UpdateAnswer;
import site.re_fill.child.dto.response.GetChild;
import site.re_fill.child.dto.response.GetChildren;

public interface ChildService {
    void createChild(Long authId, CreateChild request);

    GetChild getChild(Long childId);

    GetChildren getChildren(Long authId);

    void updateAnswer(Long childId, Integer answerNumber, UpdateAnswer updateAnswer);
}
