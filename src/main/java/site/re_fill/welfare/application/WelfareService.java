package site.re_fill.welfare.application;

import site.re_fill.welfare.dto.response.GetWelfareList;

public interface WelfareService {
    GetWelfareList getWelfareList(int age);
}
