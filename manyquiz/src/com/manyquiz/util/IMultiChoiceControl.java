package com.manyquiz.util;

import java.util.Collection;

public interface IMultiChoiceControl {

    String[] getNames();

    boolean[] getStates();

    void setState(int which, boolean enabled);

    Collection<String> getSelectedNames();

    void saveSelection();
}
