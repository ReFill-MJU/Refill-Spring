package site.re_fill.age.application;

import site.re_fill.age.domain.Age;
import site.re_fill.age.domain.Business;

import java.util.List;

public interface WelfareModuleService {

    Age findAgeByValue(int age);

    List<Business> findBusinessByValue(int age);
}
