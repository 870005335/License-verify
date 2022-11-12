package com.secretty.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>常用判断和集合转换工具类</p>
 *
 * @author maktub
 */
public class CommonUtils {

    public CommonUtils() {
    }

    public static String validStringValue(Object object) {
        return object == null ? "" : object.toString();
    }

    public static int validIntValue(Object object) {
        return object == null ? 0 : Integer.parseInt(object.toString());
    }

    public static long validLongValue(Object object) {
        return object == null ? 0L : Long.parseLong(object.toString());
    }

    public static boolean isNotEmpty(Object object) {
        if (object == null) {
            return false;
        } else if (object instanceof Integer) {
            return Integer.parseInt(object.toString()) > 0;
        } else if (object instanceof Long) {
            return Long.parseLong(object.toString()) > 0L;
        } else if (object instanceof String) {
            return ((String)object).trim().length() > 0;
        } else if (object instanceof StringBuffer) {
            return ((StringBuffer)object).toString().trim().length() > 0;
        } else if (object instanceof Boolean) {
            return Boolean.parseBoolean(object.toString());
        } else if (object instanceof List) {
            return ((List<?>)object).size() > 0;
        } else if (object instanceof Set) {
            return ((Set<?>)object).size() > 0;
        } else if (object instanceof Map) {
            return ((Map<?, ?>)object).size() > 0;
        } else if (object instanceof Iterator) {
            return ((Iterator<?>)object).hasNext();
        } else if (object.getClass().isArray()) {
            return true;
        } else {
            return true;
        }
    }

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof Integer) {
            return Integer.parseInt(object.toString()) == 0;
        } else if (object instanceof Long) {
            return Long.parseLong(object.toString()) == 0L;
        } else if (object instanceof String) {
            return ((String)object).trim().length() == 0;
        } else if (object instanceof StringBuffer) {
            return ((StringBuffer)object).toString().trim().length() == 0;
        } else if (object instanceof Boolean) {
            return Boolean.parseBoolean(object.toString());
        } else if (object instanceof List) {
            return ((List<?>)object).size() == 0;
        } else if (object instanceof Set) {
            return ((Set<?>)object).size() == 0;
        } else if (object instanceof Map) {
            return ((Map<?, ?>)object).size() == 0;
        } else if (object instanceof Iterator) {
            return !((Iterator<?>)object).hasNext();
        } else if (object.getClass().isArray()) {
            return false;
        } else {
            return false;
        }
    }


    public static Set<Long> list2Set(List<String> list) {
        HashSet<Long> result = new HashSet<>();

        try {
            if (list.size() > 0) {
                list.forEach((s) -> {
                    result.add(Long.parseLong(s));
                });
            }
        } catch (Exception var3) {
            System.err.println("List转换失败");
        }

        return result;
    }

    public static Set<Long> hashSetToSet(HashSet<?> sets) {
        Set<Long> result = new HashSet<>();
        if (sets != null && sets.size() > 0) {

            for (Object o : sets) {
                Long id = Long.valueOf(o.toString());
                result.add(id);
            }

            return result;
        } else {
            return null;
        }
    }

    public static List<String> setToList(Set<?> set) {
        List<String> result = new ArrayList<>();
        try {
            if (set.size() > 0) {
                set.forEach((s) -> {
                    result.add(s.toString());
                });
            }
        } catch (Exception var3) {
            System.err.println("HashSet转换失败");
        }
        return result;
    }

    public static List<String> listToList(List<?> list) {
        List<String> result = new ArrayList<>();
        try {
            if (list.size() > 0) {
                list.forEach((s) -> {
                    result.add(s.toString());
                });
            }
        } catch (Exception var3) {
            System.err.println("List转换失败");
        }
        return result;
    }

    public static String listToString(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return String.join(",", list);
        }
        return "";
    }


    public static List<String> StringToList(String str) {
        return StringToList(str, ",");
    }

    public static List<String> StringToList(String str, String separator) {
        List<String> result = new ArrayList<>();
        if (StringUtils.isNotBlank(str)) {
            result = Arrays.stream(separator.split(separator)).collect(Collectors.toList());
        }
        return result;
    }


}
