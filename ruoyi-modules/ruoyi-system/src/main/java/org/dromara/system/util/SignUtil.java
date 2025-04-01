package org.dromara.system.util;

import com.google.common.hash.Hashing;
import kotlin.text.Charsets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.List;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/4/1 14:15
 */
public class SignUtil {

    public static String sign(List<String> values, String ticket) {
        if (CollectionUtils.isEmpty(values)){
            throw new SecurityException("values is null");
        }
        values.removeAll(Collections.singleton(null));
        values.add(ticket);
        Collections.sort(values);
        StringBuilder sb = new StringBuilder();
        values.forEach(value -> sb.append(value));

        return Hashing.sha1().hashString(sb, Charsets.UTF_8).toString().toUpperCase();
    }


    public static String generateRandomString(Integer length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
