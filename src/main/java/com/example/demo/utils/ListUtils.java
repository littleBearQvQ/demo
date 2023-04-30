package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ListUtils {

    public static List<Object> removeDuplicate(List<Object> list){
        return list.stream().distinct().collect(Collectors.toList());
    }

    public static void showListContent(List<Object> list){
        list.forEach(infos->{
            log.info("info:{}",infos);
        });
    }

    public static void listFrequency(List<Object> list1){
        List list2 = removeDuplicate(list1);
        for(int i = 0 ;  i < list2.size();i++){
            log.info("{}出现的次数为:{}次",list2.get(i), Collections.frequency(list1,list2.get(i)));
        }
    }
}
