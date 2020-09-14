package com.force.labor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.swing.SortOrder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sort {
    @NonNull
    private String sortBy;
    @NonNull
    private SortOrder type;
}