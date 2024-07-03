package com.inson.ersp.commons.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StringUtil {

    public static String nullToEmpty(String str) {
        return str == null ? "" : str;
    }

}
