/*
 * Copyright© 2000 - 2021 SuperMap Software Co.Ltd. All rights reserved.
 * This program are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution and is available at http://www.apache.org/licenses/LICENSE-2.0.html.
*/
package com.supermap.gaf.utils;

import java.util.UUID;

/**
 * 
 * <p>
 * 此类主要是完成主键唯一值 此类使用单例的模式读取，兼容多线程读取的问题
 * </p>
 * 
 * @author ${Author}
 * @version ${Version}
 * @since 1.0.0
 * @date:2021/3/25
 *
 */
public class PrimaryKeyUtil {

	/**
	 * 
	 * <p>
	 * 私有化构造函数。
	 * </p>
	 */
	private PrimaryKeyUtil() {
	}

	/**
	 * 静态的私有变量
	 */
	private volatile static PrimaryKeyUtil m_instance;

	/**
	 * 
	 * <p>
	 * 单例获取工具对象
	 * </p>
	 * 
	 * @return
	 * @since 1.0.0
	 */
	public static PrimaryKeyUtil getInstance() {
		if (m_instance == null) {
			synchronized (PrimaryKeyUtil.class) {
				if (m_instance == null) {
					m_instance = new PrimaryKeyUtil();
				}
			}
		}
		return m_instance;
	}

	/**
	 * 
	 * <p>
	 * 生成主键的值，此方法需要保证表内唯一
	 * </p>
	 * 
	 * @return
	 * @since 1.0.0
	 */
	public String GetPrimaryKeyValue() {

		String value = UUID.randomUUID().toString(); // 获取UUID并转化为String对象
		value = value.replace("-", ""); // 因为UUID本身为32位只是生成时多了“-”，
		return shortUrl(value)[0];
	}

	public String[] shortUrl(String url) {
		// 可以自定义生成 MD5 加密字符传前的混合 KEY
		String key = "supermap";
		// 要使用生成 URL 的字符
		String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
				"i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
				"u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
				"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
				"U", "V", "W", "X", "Y", "Z"
		};
		// 对传入网址进行 MD5 加密
		String sMD5EncryptResult = (Encript.md5(key + url));
		String hex = sMD5EncryptResult;
		String[] resUrl = new String[4];
		//得到 4组短链接字符串
		for (int i = 0; i < 4; i++) {
			// 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
			String sTempSubString = hex.substring(i * 8, i * 8 + 8);
			// 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
			long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
			String outChars = "";
			//循环获得每组6位的字符串
			for (int j = 0; j < 6; j++) {
				// 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引(具体需要看chars数组的长度   以防下标溢出，注意起点为0)
				long index = 0x0000003D & lHexLong;
				// 把取得的字符相加
				outChars += chars[(int) index];
				// 每次循环按位右移 5 位
				lHexLong = lHexLong >> 5;
			}
			// 把字符串存入对应索引的输出数组
			resUrl[i] = outChars;
		}
		return resUrl;
	}
}
