package site.re_fill.welfare.application;

import site.re_fill.welfare.domain.Welfare;

import java.util.List;

public interface WelfareModuleService {
    List<Welfare> findAllByAge(int age);
}
