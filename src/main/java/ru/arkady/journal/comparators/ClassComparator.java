package ru.arkady.journal.comparators;

import ru.arkady.journal.entities.SchoolClass;

import java.util.Comparator;

public class ClassComparator implements Comparator<SchoolClass> {
    @Override
    public int compare(SchoolClass o1, SchoolClass o2) {
        return o1.getClassName().compareTo(o2.getClassName());
    }
}
