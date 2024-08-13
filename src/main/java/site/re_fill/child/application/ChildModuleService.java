package site.re_fill.child.application;

import site.re_fill.child.domain.Child;

import java.util.List;

public interface ChildModuleService {
    Child findChildById(Long childId);

    Child saveChild(Child child);

    List<Child> findAllByMemberId(Long memberId);
}
