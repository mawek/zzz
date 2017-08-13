package com.mawek.zzz.rest;

/**
 * Available rest API fields filter operations.
 */
public enum FilterOperation {
    CONTAINS, ICONTAINS,
    STARTSWITH, ISTARTSWITH,
    ENDSWITH, IENDSWITH,
    EQ, NOTEQ, GT, GTE, LT, LTE,
    IN, CONTAINSALL, CONTAINSANY
}
