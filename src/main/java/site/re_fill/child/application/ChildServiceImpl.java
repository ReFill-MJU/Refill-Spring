package site.re_fill.child.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.re_fill.child.domain.Child;
import site.re_fill.child.dto.ChildDto;
import site.re_fill.child.dto.request.CreateChild;
import site.re_fill.child.dto.response.GetChildren;
import site.re_fill.member.application.MemberModuleService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChildServiceImpl implements ChildService {

    private final ChildModuleService childModuleService;
    private final MemberModuleService memberModuleService;

    @Override
    @Transactional
    public void createChild(final Long authId, final CreateChild request) {
        Child child = request.toEntity();
        child.setMember(memberModuleService.findMemberById(authId)
                .orElseThrow(RuntimeException::new));
        childModuleService.saveChild(request.toEntity());
    }

    @Override
    public GetChildren getChildren(final Long authId) {
        List<ChildDto> children = childModuleService.findAllByMemberId(authId).stream()
                .map(ChildDto::of)
                .toList();

        return GetChildren.of(children);
    }
}
