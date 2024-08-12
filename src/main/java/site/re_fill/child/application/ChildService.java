package site.re_fill.child.application;

import site.re_fill.child.dto.request.CreateChild;
import site.re_fill.child.dto.response.GetChildren;

public interface ChildService {
    void createChild(Long authId, CreateChild request);

    GetChildren getChildren(Long authId);
}
