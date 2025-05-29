package com.erp.Utility;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Builder
public class SetResponseStructure<T> {

    private int status;
    private String message;
    private Set<T> data;
}
