package com.ooftf.widget.statelayout;

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/5/8 0008
 */
public interface IStateLayout {
    void switchToEmpty();

    void switchToLoading();

    void switchToError();

    void switchToSuccess();

    void switchToUndefinedFirst();

    void switchToUndefinedSecond();

    void switchToUndefinedThird();

    int STATE_SUCCESS = 0;
    int STATE_LOAD = 1;
    int STATE_EMPTY = 2;
    int STATE_ERROR = 3;
    int STATE_UNDEFINED_FIRST = 10;
    int STATE_UNDEFINED_SECOND = 11;
    int STATE_UNDEFINED_THIRD = 12;
}
