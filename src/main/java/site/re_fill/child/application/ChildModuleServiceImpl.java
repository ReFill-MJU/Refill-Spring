package site.re_fill.child.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.re_fill.child.domain.Child;
import site.re_fill.child.repository.ChildRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChildModuleServiceImpl implements ChildModuleService {

    private final ChildRepository childRepository;

    @Override
    public Child saveChild(final Child child) {
        return childRepository.save(child);
    }

    @Override
    public List<Child> findAllByMemberId(final Long memberId) {
        return childRepository.findAllByMemberId(memberId);
    }
}
