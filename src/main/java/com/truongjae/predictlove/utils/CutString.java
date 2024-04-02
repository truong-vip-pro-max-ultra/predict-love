package com.truongjae.predictlove.utils;

public class CutString {
    public static String cut(String value,String key,boolean option){
        value = value.replace("\n","");
        int index = value.indexOf(key);
        String result;
        int keyLength = key.length();
        if(option)
            result = value.substring(index+keyLength);
        else
            result = value.substring(0,index);
        return result;
    }
}
