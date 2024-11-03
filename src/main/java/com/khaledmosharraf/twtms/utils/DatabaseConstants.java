package com.khaledmosharraf.twtms.utils;

import org.springframework.data.domain.Sort;

public interface DatabaseConstants {
    Sort sortById_DESC = Sort.by(Sort.Direction.DESC, "id");
    Sort sortById_ASC = Sort.by(Sort.Direction.ASC, "id");
    Sort sortByCreatedDate_DESC = Sort.by(Sort.Direction.DESC, "createdDate");
    Sort sortByCreatedDate_ASC = Sort.by(Sort.Direction.ASC, "createdDate");
}
