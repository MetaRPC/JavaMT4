package io.mtapi.mt4.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Lightweight dependency-free JSON parser that maps JSON objects to Java POJOs via reflection.
 * Supports nested objects, arrays, strings, numbers, booleans, and null.
 */
public class JsonReflectionParser {
    private static final Map<Class<?>, Map<String, Field>> FIELD_CACHE = new HashMap<>();

    public static <T> T parse(String json, Class<T> clazz) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return parseObject(new JsonCursor(json), clazz);
    }

    private static Map<String, Field> getFieldMap(Class<?> clazz) {
        return FIELD_CACHE.computeIfAbsent(clazz, c -> {
            Map<String, Field> map = new HashMap<>();
            for (Field f : c.getFields()) {
                map.put(f.getName(), f);
            }
            return map;
        });
    }

    private static <T> T parseObject(JsonCursor cur, Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        cur.skipWs();
        cur.expect('{');
        T instance = clazz.getDeclaredConstructor().newInstance();
        Map<String, Field> fields = getFieldMap(clazz);

        boolean first = true;
        while (true) {
            cur.skipWs();
            if (cur.peek() == '}') {
                cur.pos++;
                break;
            }
            if (!first) cur.expect(',');
            cur.skipWs();

            String key = cur.readString();
            cur.skipWs();
            cur.expect(':');
            cur.skipWs();

            Field field = fields.get(key);
            if (field != null) {
                Object val = parseValue(cur, field.getType(), field.getGenericType());
                field.set(instance, val);
            } else {
                skipValue(cur);
            }
            first = false;
        }
        return instance;
    }

    private static Object parseValue(JsonCursor cur, Class<?> type, Type genType) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        char ch = cur.peek();
        if (ch == '"') {
            String s = cur.readString();
            if (type == String.class) return s;
            if (type == char.class || type == Character.class) return s.isEmpty() ? null : s.charAt(0);
            return null;
        } else if (ch == '{') {
            return parseObject(cur, type);
        } else if (ch == '[') {
            return parseArray(cur, type);
        } else {
            return parseLiteral(cur, type);
        }
    }

    private static Object parseArray(JsonCursor cur, Class<?> type) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        cur.expect('[');
        cur.skipWs();

        if (!type.isArray()) {
            throw new IllegalArgumentException("Expected array type for " + type);
        }
        Class<?> compType = type.getComponentType();

        // First pass: count elements
        int count = 0;
        int savePos = cur.pos;
        boolean first = true;
        while (true) {
            cur.skipWs();
            if (cur.peek() == ']') {
                cur.pos++;
                break;
            }
            if (!first) cur.expect(',');
            skipValue(cur);
            count++;
            first = false;
        }

        // Second pass: parse values
        Object arr = Array.newInstance(compType, count);
        cur.pos = savePos;
        count = 0;
        first = true;
        while (true) {
            cur.skipWs();
            if (cur.peek() == ']') {
                cur.pos++;
                break;
            }
            if (!first) cur.expect(',');
            Array.set(arr, count++, parseValue(cur, compType, compType));
            first = false;
        }
        return arr;
    }

    private static Object parseLiteral(JsonCursor cur, Class<?> type) {
        int start = cur.pos;
        while (cur.pos < cur.len && ",]} \n\r\t".indexOf(cur.json.charAt(cur.pos)) == -1) cur.pos++;
        int end = cur.pos;
        char c0 = cur.json.charAt(start);

        if (type == int.class || type == Integer.class) {
            return parseInt(cur.json, start, end);
        } else if (type == long.class || type == Long.class) {
            return parseLong(cur.json, start, end);
        } else if (type == double.class || type == Double.class) {
            return parseDouble(cur.json, start, end);
        } else if (type == boolean.class || type == Boolean.class) {
            return (c0 == 't' || c0 == 'T');
        }
        return null;
    }

    private static void skipValue(JsonCursor cur) {
        char ch = cur.peek();
        if (ch == '"') {
            cur.readString();
        } else if (ch == '{') {
            cur.pos++;
            boolean first = true;
            while (true) {
                cur.skipWs();
                if (cur.peek() == '}') { cur.pos++; break; }
                if (!first) cur.expect(',');
                cur.skipWs();
                cur.readString();
                cur.skipWs();
                cur.expect(':');
                cur.skipWs();
                skipValue(cur);
                first = false;
            }
        } else if (ch == '[') {
            cur.pos++;
            boolean first = true;
            while (true) {
                cur.skipWs();
                if (cur.peek() == ']') { cur.pos++; break; }
                if (!first) cur.expect(',');
                skipValue(cur);
                first = false;
            }
        } else {
            while (cur.pos < cur.len && ",]} \n\r\t".indexOf(cur.json.charAt(cur.pos)) == -1) cur.pos++;
        }
    }

    private static int parseInt(String s, int start, int end) {
        int res = 0;
        boolean neg = false;
        int i = start;
        if (s.charAt(i) == '-') { neg = true; i++; }
        for (; i < end; i++) res = res * 10 + (s.charAt(i) - '0');
        return neg ? -res : res;
    }

    private static long parseLong(String s, int start, int end) {
        long res = 0;
        boolean neg = false;
        int i = start;
        if (s.charAt(i) == '-') { neg = true; i++; }
        for (; i < end; i++) res = res * 10 + (s.charAt(i) - '0');
        return neg ? -res : res;
    }

    private static double parseDouble(String s, int start, int end) {
        boolean neg = false;
        if (s.charAt(start) == '-') { neg = true; start++; }
        double intPart = 0;
        while (start < end && s.charAt(start) >= '0' && s.charAt(start) <= '9') {
            intPart = intPart * 10 + (s.charAt(start) - '0');
            start++;
        }
        double fracPart = 0;
        if (start < end && s.charAt(start) == '.') {
            start++;
            double divisor = 10;
            while (start < end && s.charAt(start) >= '0' && s.charAt(start) <= '9') {
                fracPart += (s.charAt(start) - '0') / divisor;
                divisor *= 10;
                start++;
            }
        }
        double value = intPart + fracPart;
        if (start < end && (s.charAt(start) == 'e' || s.charAt(start) == 'E')) {
            start++;
            boolean expNeg = false;
            if (s.charAt(start) == '-') { expNeg = true; start++; }
            else if (s.charAt(start) == '+') { start++; }
            int exp = 0;
            while (start < end && s.charAt(start) >= '0' && s.charAt(start) <= '9') {
                exp = exp * 10 + (s.charAt(start) - '0');
                start++;
            }
            value = value * Math.pow(10, expNeg ? -exp : exp);
        }
        return neg ? -value : value;
    }

    private static class JsonCursor {
        final String json;
        final int len;
        int pos;

        JsonCursor(String json) {
            this.json = json;
            this.len = json.length();
        }

        char peek() { return pos < len ? json.charAt(pos) : '\0'; }

        void skipWs() {
            while (pos < len && json.charAt(pos) <= ' ') pos++;
        }

        void expect(char c) {
            if (peek() != c) throw new RuntimeException("Expected '" + c + "' at pos " + pos);
            pos++;
        }

        String readString() {
            expect('"');
            int start = pos;
            while (pos < len && json.charAt(pos) != '"') pos++;
            String s = json.substring(start, pos);
            expect('"');
            return s;
        }
    }
}
