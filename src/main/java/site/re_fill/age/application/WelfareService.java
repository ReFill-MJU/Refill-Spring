package site.re_fill.age.application;

import site.re_fill.age.dto.response.GetBusinessList;

public interface WelfareService {
    GetBusinessList getBusinessList(int age);
}
