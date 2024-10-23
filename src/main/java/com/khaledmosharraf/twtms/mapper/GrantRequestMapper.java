package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.GrantDTO;
import com.khaledmosharraf.twtms.dto.GrantRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Mapper(componentModel = "spring")
public interface GrantRequestMapper {


    @Mapping(source = "userId", target = "user.id")
   // @Mapping(source = "application", target = "application", qualifiedByName = "multipartToString")
   // @Mapping(source = "attachment", target = "attachment", qualifiedByName = "multipartToString")
    @Mapping(source = "application", target = "application", ignore = true)
    @Mapping(source = "attachment", target = "attachment", ignore = true)
    GrantDTO toGrantDTO(GrantRequestDTO grantRequestDTO);

    @Mapping(source = "user.id", target = "userId")
   // @Mapping(source = "application", target = "application", qualifiedByName = "stringToMultipart")
   // @Mapping(source = "attachment", target = "attachment", qualifiedByName = "stringToMultipart")
    @Mapping(source = "application", target = "application", ignore = true)
    @Mapping(source = "attachment", target = "attachment", ignore = true)
    GrantRequestDTO toGrantRequestDTO(GrantDTO grantDTO);

    /*@Named("multipartToString")
    default String multipartToString(MultipartFile file) {
        return file != null && !file.isEmpty() ? file.getOriginalFilename() : null;
    }

    @Named("stringToMultipart")
    default MultipartFile stringToMultipart(String fileName) {
        return null;
    }*/
}
