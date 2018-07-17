package org.gajanlee.sailors.buss

import java.text.DecimalFormat

/**
 * 根据文件大小转换为B、KB、MB、GB单位字符串显示
 * @param filesize 文件的大小（long型）
 * @return 返回 转换后带有单位的字符串
 */
fun GetLength(filesize: Long): String {

    lateinit var strFileSize: String
    if (filesize < 1024) {
        strFileSize = filesize.toString() + "B"
        return strFileSize
    }

    val df = DecimalFormat("######0.00")

    if (filesize >= 1024 && filesize < 1024 * 1024) {//KB
        strFileSize = df.format(filesize.toDouble() / 1024) + "KB"
    } else if (filesize >= 1024 * 1024 && filesize < 1024 * 1024 * 1024) {//MB
        strFileSize = df.format(filesize.toDouble() / (1024 * 1024)) + "MB"
    } else {//GB
        strFileSize = df.format(filesize.toDouble() / (1024 * 1024 * 1024)) + "GB"
    }
    return strFileSize
}
