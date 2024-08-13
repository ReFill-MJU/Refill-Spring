package site.re_fill.child.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.re_fill.age.application.WelfareModuleService;
import site.re_fill.child.domain.Child;
import site.re_fill.child.dto.ChildDto;
import site.re_fill.child.dto.request.CreateChild;
import site.re_fill.child.dto.request.UpdateAnswer;
import site.re_fill.child.dto.response.GetChild;
import site.re_fill.child.dto.response.GetChildren;
import site.re_fill.member.application.MemberModuleService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChildServiceImpl implements ChildService {

    private final ChildModuleService childModuleService;
    private final MemberModuleService memberModuleService;
    private final WelfareModuleService welfareModuleService;

    @Override
    @Transactional
    public void createChild(final Long authId, final CreateChild request) {
        Child child = request.toEntity();
        child.setMember(memberModuleService.findMemberById(authId)
                .orElseThrow(RuntimeException::new));
        childModuleService.saveChild(request.toEntity());
    }

    @Override
    public GetChild getChild(final Long childId) {
        Child child = childModuleService.findChildById(childId);
        String comment = welfareModuleService.findAgeByValue(child.getAge()).getComment();

        return GetChild.of(ChildDto.of(child, comment));
    }

    @Override
    public GetChildren getChildren(final Long authId) {
        List<Child> children = childModuleService.findAllByMemberId(authId);
        return GetChildren.of(children);
    }

    @Override
    @Transactional
    public void updateAnswer(final Long childId, final Integer answerNumber, final UpdateAnswer updateAnswer) {
        Child child = childModuleService.findChildById(childId);
        child.updateAnswer(updateAnswer.answer(), answerNumber);
    }
}
