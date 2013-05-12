/**
 * 
 */
package com.sapint;

/**
 * @author vincent
 *
 */
import java.io.Reader;
import java.io.StringReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MyStringUtils {
	/*
	 * ==========================================================================
	 * ==
	 */
	/* 常量和singleton。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/** 空字符串。 */
	public static final String EMPTY_STRING = "";

	private static final String FILE_SEPARATOR = "/";

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 判空函数。 */
	/*                                                                              */
	/* 以下方法用来判定一个字符串是否为： */
	/* 1. null */
	/* 2. empty - "" */
	/* 3. blank - "全部是空白" - 空白由Character.isWhitespace所定义。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 将字符串转义以方便html输出
	 */
	public String escapeHtml(String s) {

		return org.apache.commons.lang.StringEscapeUtils.escapeHtml(s);
	}

	public String unescapeHtml(String s) {
		return org.apache.commons.lang.StringEscapeUtils.unescapeHtml(s);
	}

	public String arrayIndex(String[] args, int _index) {
		return args[_index];
	}

	/**
	 * 检查字符串是否为 <code>null</code> 或空字符串 <code>""</code>。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.isEmpty(null)      = true 
	 *    StringUtil.isEmpty("")        = true 
	 *    StringUtil.isEmpty(" ")       = false 
	 *    StringUtil.isEmpty("bob")     = false 
	 *    StringUtil.isEmpty("  bob  ") = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果为空, 则返回 <code>true</code>
	 */
	public static boolean isEmpty(String str) {
		return ((str == null) || (str.length() == 0));
	}

	/**
	 * 检查字符串是否不是 <code>null</code> 和空字符串 <code>""</code>。
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果不为空, 则返回 <code>true</code>
	 * 
	 * @see com.nonfamous.commom.util.StringUtils#isEmpty(String)
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 检查字符串是否是空白： <code>null</code> 、空字符串 <code>""</code> 或只有空白字符。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.isBlank(null)      = true 
	 *    StringUtil.isBlank("")        = true 
	 *    StringUtil.isBlank(" ")       = true 
	 *    StringUtil.isBlank("bob")     = false 
	 *    StringUtil.isBlank("  bob  ") = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果为空白, 则返回 <code>true</code>
	 */
	public static boolean isBlank(String str) {
		int length;

		if ((str == null) || ((length = str.length()) == 0)) {
			return true;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 检查字符串是否不是空白： <code>null</code> 、空字符串 <code>""</code> 或只有空白字符。
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果不是空白, 则返回 <code>true</code>
	 * 
	 * @see com.nonfamous.commom.util.StringUtils#isBlank(String)(String)
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 默认值函数。 */
	/*                                                                              */
	/* 当字符串为null、empty或blank时，将字符串转换成指定的默认字符串。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 如果字符串是 <code>null</code> ，则返回空字符串 <code>""</code> ，否则返回字符串本身。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.defaultIfNull(null)  = "" 
	 *    StringUtil.defaultIfNull("")    = "" 
	 *    StringUtil.defaultIfNull("  ")  = "  " 
	 *    StringUtil.defaultIfNull("bat") = "bat"
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            要转换的字符串
	 * 
	 * @return 字符串本身或空字符串 <code>""</code>
	 */
	public static String defaultIfNull(String str) {
		return (str == null) ? EMPTY_STRING : str;
	}

	/**
	 * 如果字符串是 <code>null</code> ，则返回指定默认字符串，否则返回字符串本身。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.defaultIfNull(null, "default")  = "default" 
	 *    StringUtil.defaultIfNull("", "default")    = "" 
	 *    StringUtil.defaultIfNull("  ", "default")  = "  " 
	 *    StringUtil.defaultIfNull("bat", "default") = "bat"
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            要转换的字符串
	 * @param defaultStr
	 *            默认字符串
	 * 
	 * @return 字符串本身或指定的默认字符串
	 */
	public static String defaultIfNull(String str, String defaultStr) {
		return (str == null) ? defaultStr : str;
	}

	/**
	 * 如果字符串是 <code>null</code> 或空字符串 <code>""</code> ，则返回空字符串 <code>""</code>
	 * ，否则返回字符串本身。
	 * 
	 * <p>
	 * 此方法实际上和 <code>defaultIfNull(String)</code> 等效。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.defaultIfEmpty(null)  = "" 
	 *    StringUtil.defaultIfEmpty("")    = "" 
	 *    StringUtil.defaultIfEmpty("  ")  = "  " 
	 *    StringUtil.defaultIfEmpty("bat") = "bat"
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要转换的字符串
	 * 
	 * @return 字符串本身或空字符串 <code>""</code>
	 */
	public static String defaultIfEmpty(String str) {
		return isEmpty(str) ? EMPTY_STRING : str;
	}

	/**
	 * 如果字符串是 <code>null</code> 或空字符串 <code>""</code> ，则返回指定默认字符串，否则返回字符串本身。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.defaultIfEmpty(null, "default")  = "default" 
	 *    StringUtil.defaultIfEmpty("", "default")    = "default" 
	 *    StringUtil.defaultIfEmpty("  ", "default")  = "  " 
	 *    StringUtil.defaultIfEmpty("bat", "default") = "bat"
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            要转换的字符串
	 * @param defaultStr
	 *            默认字符串
	 * 
	 * @return 字符串本身或指定的默认字符串
	 */
	public static String defaultIfEmpty(String str, String defaultStr) {
		return isEmpty(str) ? defaultStr : str;
	}

	/**
	 * 如果字符串是空白： <code>null</code> 、空字符串 <code>""</code> 或只有空白字符，则返回空字符串
	 * <code>""</code> ，否则返回字符串本身。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.defaultIfBlank(null)  = "" 
	 *    StringUtil.defaultIfBlank("")    = "" 
	 *    StringUtil.defaultIfBlank("  ")  = "" 
	 *    StringUtil.defaultIfBlank("bat") = "bat"
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            要转换的字符串
	 * 
	 * @return 字符串本身或空字符串 <code>""</code>
	 */
	public static String defaultIfBlank(String str) {
		return isBlank(str) ? EMPTY_STRING : str;
	}

	/**
	 * 如果字符串是 <code>null</code> 或空字符串 <code>""</code> ，则返回指定默认字符串，否则返回字符串本身。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.defaultIfBlank(null, "default")  = "default" 
	 *    StringUtil.defaultIfBlank("", "default")    = "default" 
	 *    StringUtil.defaultIfBlank("  ", "default")  = "default" 
	 *    StringUtil.defaultIfBlank("bat", "default") = "bat"
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            要转换的字符串
	 * @param defaultStr
	 *            默认字符串
	 * 
	 * @return 字符串本身或指定的默认字符串
	 */
	public static String defaultIfBlank(String str, String defaultStr) {
		return isBlank(str) ? defaultStr : str;
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 去空白（或指定字符）的函数。 */
	/*                                                                              */
	/* 以下方法用来除去一个字串中的空白或指定字符。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 除去字符串头尾部的空白，如果字符串是 <code>null</code> ，依然返回 <code>null</code>。
	 * 
	 * <p>
	 * 注意，和 <code>String.trim</code> 不同，此方法使用
	 * <code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.trim(null)          = null 
	 *    StringUtil.trim("")            = "" 
	 *    StringUtil.trim("     ")       = "" 
	 *    StringUtil.trim("abc")         = "abc" 
	 *    StringUtil.trim("    abc    ") = "abc"
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要处理的字符串
	 * 
	 * @return 除去空白的字符串，如果原字串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String trim(String str) {
		return trim(str, null, 0);
	}

	/**
	 * 除去字符串头尾部的指定字符，如果字符串是 <code>null</code> ，依然返回 <code>null</code>。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.trim(null, *)          = null 
	 *    StringUtil.trim("", *)            = "" 
	 *    StringUtil.trim("abc", null)      = "abc" 
	 *    StringUtil.trim("  abc", null)    = "abc" 
	 *    StringUtil.trim("abc  ", null)    = "abc" 
	 *    StringUtil.trim(" abc ", null)    = "abc" 
	 *    StringUtil.trim("  abcyx", "xyz") = "  abc"
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @param stripChars
	 *            要除去的字符，如果为 <code>null</code> 表示除去空白字符
	 * 
	 * @return 除去指定字符后的的字符串，如果原字串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String trim(String str, String stripChars) {
		return trim(str, stripChars, 0);
	}

	/**
	 * 除去字符串头部的空白，如果字符串是 <code>null</code> ，则返回 <code>null</code>。
	 * 
	 * <p>
	 * 注意，和 <code>String.trim</code> 不同，此方法使用
	 * <code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.trimStart(null)         = null 
	 *    StringUtil.trimStart("")           = "" 
	 *    StringUtil.trimStart("abc")        = "abc" 
	 *    StringUtil.trimStart("  abc")      = "abc" 
	 *    StringUtil.trimStart("abc  ")      = "abc  " 
	 *    StringUtil.trimStart(" abc ")      = "abc "
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要处理的字符串
	 * 
	 * @return 除去空白的字符串，如果原字串为 <code>null</code> 或结果字符串为 <code>""</code> ，则返回
	 *         <code>null</code>
	 */
	public static String trimStart(String str) {
		return trim(str, null, -1);
	}

	/**
	 * 除去字符串头部的指定字符，如果字符串是 <code>null</code> ，依然返回 <code>null</code>。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.trimStart(null, *)          = null 
	 *    StringUtil.trimStart("", *)            = "" 
	 *    StringUtil.trimStart("abc", "")        = "abc" 
	 *    StringUtil.trimStart("abc", null)      = "abc" 
	 *    StringUtil.trimStart("  abc", null)    = "abc" 
	 *    StringUtil.trimStart("abc  ", null)    = "abc  " 
	 *    StringUtil.trimStart(" abc ", null)    = "abc " 
	 *    StringUtil.trimStart("yxabc  ", "xyz") = "abc  "
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @param stripChars
	 *            要除去的字符，如果为 <code>null</code> 表示除去空白字符
	 * 
	 * @return 除去指定字符后的的字符串，如果原字串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String trimStart(String str, String stripChars) {
		return trim(str, stripChars, -1);
	}

	/**
	 * 除去字符串尾部的空白，如果字符串是 <code>null</code> ，则返回 <code>null</code>。
	 * 
	 * <p>
	 * 注意，和 <code>String.trim</code> 不同，此方法使用
	 * <code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.trimEnd(null)       = null 
	 *    StringUtil.trimEnd("")         = "" 
	 *    StringUtil.trimEnd("abc")      = "abc" 
	 *    StringUtil.trimEnd("  abc")    = "  abc" 
	 *    StringUtil.trimEnd("abc  ")    = "abc" 
	 *    StringUtil.trimEnd(" abc ")    = " abc"
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要处理的字符串
	 * 
	 * @return 除去空白的字符串，如果原字串为 <code>null</code> 或结果字符串为 <code>""</code> ，则返回
	 *         <code>null</code>
	 */
	public static String trimEnd(String str) {
		return trim(str, null, 1);
	}

	/**
	 * 除去字符串尾部的指定字符，如果字符串是 <code>null</code> ，依然返回 <code>null</code>。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.trimEnd(null, *)          = null 
	 *    StringUtil.trimEnd("", *)            = "" 
	 *    StringUtil.trimEnd("abc", "")        = "abc" 
	 *    StringUtil.trimEnd("abc", null)      = "abc" 
	 *    StringUtil.trimEnd("  abc", null)    = "  abc" 
	 *    StringUtil.trimEnd("abc  ", null)    = "abc" 
	 *    StringUtil.trimEnd(" abc ", null)    = " abc" 
	 *    StringUtil.trimEnd("  abcyx", "xyz") = "  abc"
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @param stripChars
	 *            要除去的字符，如果为 <code>null</code> 表示除去空白字符
	 * 
	 * @return 除去指定字符后的的字符串，如果原字串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String trimEnd(String str, String stripChars) {
		return trim(str, stripChars, 1);
	}

	/**
	 * 除去字符串头尾部的空白，如果结果字符串是空字符串 <code>""</code> ，则返回 <code>null</code>。
	 * 
	 * <p>
	 * 注意，和 <code>String.trim</code> 不同，此方法使用
	 * <code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.trimToNull(null)          = null 
	 *    StringUtil.trimToNull("")            = null 
	 *    StringUtil.trimToNull("     ")       = null 
	 *    StringUtil.trimToNull("abc")         = "abc" 
	 *    StringUtil.trimToNull("    abc    ") = "abc"
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要处理的字符串
	 * 
	 * @return 除去空白的字符串，如果原字串为 <code>null</code> 或结果字符串为 <code>""</code> ，则返回
	 *         <code>null</code>
	 */
	public static String trimToNull(String str) {
		return trimToNull(str, null);
	}

	/**
	 * 除去字符串头尾部的空白，如果结果字符串是空字符串 <code>""</code> ，则返回 <code>null</code>。
	 * 
	 * <p>
	 * 注意，和 <code>String.trim</code> 不同，此方法使用
	 * <code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.trim(null, *)          = null 
	 *    StringUtil.trim("", *)            = null 
	 *    StringUtil.trim("abc", null)      = "abc" 
	 *    StringUtil.trim("  abc", null)    = "abc" 
	 *    StringUtil.trim("abc  ", null)    = "abc" 
	 *    StringUtil.trim(" abc ", null)    = "abc" 
	 *    StringUtil.trim("  abcyx", "xyz") = "  abc"
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @param stripChars
	 *            要除去的字符，如果为 <code>null</code> 表示除去空白字符
	 * 
	 * @return 除去空白的字符串，如果原字串为 <code>null</code> 或结果字符串为 <code>""</code> ，则返回
	 *         <code>null</code>
	 */
	public static String trimToNull(String str, String stripChars) {
		String result = trim(str, stripChars);

		if ((result == null) || (result.length() == 0)) {
			return null;
		}

		return result;
	}

	/**
	 * 除去字符串头尾部的空白，如果字符串是 <code>null</code> ，则返回空字符串 <code>""</code>。
	 * 
	 * <p>
	 * 注意，和 <code>String.trim</code> 不同，此方法使用
	 * <code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.trimToEmpty(null)          = "" 
	 *    StringUtil.trimToEmpty("")            = "" 
	 *    StringUtil.trimToEmpty("     ")       = "" 
	 *    StringUtil.trimToEmpty("abc")         = "abc" 
	 *    StringUtil.trimToEmpty("    abc    ") = "abc"
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要处理的字符串
	 * 
	 * @return 除去空白的字符串，如果原字串为 <code>null</code> 或结果字符串为 <code>""</code> ，则返回
	 *         <code>null</code>
	 */
	public static String trimToEmpty(String str) {
		return trimToEmpty(str, null);
	}

	/**
	 * 除去字符串头尾部的空白，如果字符串是 <code>null</code> ，则返回空字符串 <code>""</code>。
	 * 
	 * <p>
	 * 注意，和 <code>String.trim</code> 不同，此方法使用
	 * <code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.trim(null, *)          = "" 
	 *    StringUtil.trim("", *)            = "" 
	 *    StringUtil.trim("abc", null)      = "abc" 
	 *    StringUtil.trim("  abc", null)    = "abc" 
	 *    StringUtil.trim("abc  ", null)    = "abc" 
	 *    StringUtil.trim(" abc ", null)    = "abc" 
	 *    StringUtil.trim("  abcyx", "xyz") = "  abc"
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要处理的字符串
	 * 
	 * @return 除去空白的字符串，如果原字串为 <code>null</code> 或结果字符串为 <code>""</code> ，则返回
	 *         <code>null</code>
	 */
	public static String trimToEmpty(String str, String stripChars) {
		String result = trim(str, stripChars);

		if (result == null) {
			return EMPTY_STRING;
		}

		return result;
	}

	/**
	 * 除去字符串头尾部的指定字符，如果字符串是 <code>null</code> ，依然返回 <code>null</code>。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.trim(null, *)          = null 
	 *    StringUtil.trim("", *)            = "" 
	 *    StringUtil.trim("abc", null)      = "abc" 
	 *    StringUtil.trim("  abc", null)    = "abc" 
	 *    StringUtil.trim("abc  ", null)    = "abc" 
	 *    StringUtil.trim(" abc ", null)    = "abc" 
	 *    StringUtil.trim("  abcyx", "xyz") = "  abc"
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @param stripChars
	 *            要除去的字符，如果为 <code>null</code> 表示除去空白字符
	 * @param mode
	 *            <code>-1</code> 表示trimStart， <code>0</code> 表示trim全部，
	 *            <code>1</code> 表示trimEnd
	 * 
	 * @return 除去指定字符后的的字符串，如果原字串为 <code>null</code> ，则返回 <code>null</code>
	 */
	private static String trim(String str, String stripChars, int mode) {
		if (str == null) {
			return null;
		}

		int length = str.length();
		int start = 0;
		int end = length;

		// 扫描字符串头部
		if (mode <= 0) {
			if (stripChars == null) {
				while ((start < end) && (Character.isWhitespace(str.charAt(start)))) {
					start++;
				}
			} else if (stripChars.length() == 0) {
				return str;
			} else {
				while ((start < end) && (stripChars.indexOf(str.charAt(start)) != -1)) {
					start++;
				}
			}
		}

		// 扫描字符串尾部
		if (mode >= 0) {
			if (stripChars == null) {
				while ((start < end) && (Character.isWhitespace(str.charAt(end - 1)))) {
					end--;
				}
			} else if (stripChars.length() == 0) {
				return str;
			} else {
				while ((start < end) && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
					end--;
				}
			}
		}

		if ((start > 0) || (end < length)) {
			return str.substring(start, end);
		}

		return str;
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 比较函数。 */
	/*                                                                              */
	/* 以下方法用来比较两个字符串是否相同。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 比较两个字符串（大小写敏感）。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.equals(null, null)   = true 
	 *    StringUtil.equals(null, "abc")  = false 
	 *    StringUtil.equals("abc", null)  = false 
	 *    StringUtil.equals("abc", "abc") = true 
	 *    StringUtil.equals("abc", "ABC") = false
	 * 
	 * </pre>
	 * 
	 * @param str1
	 *            要比较的字符串1
	 * @param str2
	 *            要比较的字符串2
	 * 
	 * @return 如果两个字符串相同，或者都是 <code>null</code> ，则返回 <code>true</code>
	 */
	public static boolean equals(String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		}

		return str1.equals(str2);
	}

	/**
	 * 比较两个字符串（大小写不敏感）。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.equalsIgnoreCase(null, null)   = true 
	 *    StringUtil.equalsIgnoreCase(null, "abc")  = false 
	 *    StringUtil.equalsIgnoreCase("abc", null)  = false 
	 *    StringUtil.equalsIgnoreCase("abc", "abc") = true 
	 *    StringUtil.equalsIgnoreCase("abc", "ABC") = true
	 * 
	 * </pre>
	 * 
	 * @param str1
	 *            要比较的字符串1
	 * @param str2
	 *            要比较的字符串2
	 * 
	 * @return 如果两个字符串相同，或者都是 <code>null</code> ，则返回 <code>true</code>
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		}

		return str1.equalsIgnoreCase(str2);
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 字符串类型判定函数。 */
	/*                                                                              */
	/* 判定字符串的类型是否为：字母、数字、空白等 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 判断字符串是否只包含unicode字母。
	 * 
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 * 
	 * <pre>
	 * 
	 *    StringUtil.isAlpha(null)   = false 
	 *    StringUtil.isAlpha("")     = true 
	 *    StringUtil.isAlpha("  ")   = false 
	 *    StringUtil.isAlpha("abc")  = true 
	 *    StringUtil.isAlpha("ab2c") = false 
	 *    StringUtil.isAlpha("ab-c") = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果字符串非 <code>null</code> 并且全由unicode字母组成，则返回 <code>true</code>
	 */
	public static boolean isAlpha(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isLetter(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断字符串是否只包含unicode字母和空格 <code>' '</code>。
	 * 
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 * 
	 * <pre>
	 * 
	 *    StringUtil.isAlphaSpace(null)   = false 
	 *    StringUtil.isAlphaSpace("")     = true 
	 *    StringUtil.isAlphaSpace("  ")   = true 
	 *    StringUtil.isAlphaSpace("abc")  = true 
	 *    StringUtil.isAlphaSpace("ab c") = true 
	 *    StringUtil.isAlphaSpace("ab2c") = false 
	 *    StringUtil.isAlphaSpace("ab-c") = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果字符串非 <code>null</code> 并且全由unicode字母和空格组成，则返回 <code>true</code>
	 */
	public static boolean isAlphaSpace(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isLetter(str.charAt(i)) && (str.charAt(i) != ' ')) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断字符串是否只包含unicode字母和数字。
	 * 
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 * 
	 * <pre>
	 * 
	 *    StringUtil.isAlphanumeric(null)   = false 
	 *    StringUtil.isAlphanumeric("")     = true 
	 *    StringUtil.isAlphanumeric("  ")   = false 
	 *    StringUtil.isAlphanumeric("abc")  = true 
	 *    StringUtil.isAlphanumeric("ab c") = false 
	 *    StringUtil.isAlphanumeric("ab2c") = true 
	 *    StringUtil.isAlphanumeric("ab-c") = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果字符串非 <code>null</code> 并且全由unicode字母数字组成，则返回 <code>true</code>
	 */
	public static boolean isAlphanumeric(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isLetterOrDigit(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断字符串是否只包含unicode字母数字和空格 <code>' '</code>。
	 * 
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 * 
	 * <pre>
	 * 
	 *    StringUtil.isAlphanumericSpace(null)   = false 
	 *    StringUtil.isAlphanumericSpace("")     = true 
	 *    StringUtil.isAlphanumericSpace("  ")   = true 
	 *    StringUtil.isAlphanumericSpace("abc")  = true 
	 *    StringUtil.isAlphanumericSpace("ab c") = true 
	 *    StringUtil.isAlphanumericSpace("ab2c") = true 
	 *    StringUtil.isAlphanumericSpace("ab-c") = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果字符串非 <code>null</code> 并且全由unicode字母数字和空格组成，则返回
	 *         <code>true</code>
	 */
	public static boolean isAlphanumericSpace(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isLetterOrDigit(str.charAt(i)) && (str.charAt(i) != ' ')) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断字符串是否只包含unicode数字。
	 * 
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 * 
	 * <pre>
	 * 
	 *    StringUtil.isNumeric(null)   = false 
	 *    StringUtil.isNumeric("")     = true 
	 *    StringUtil.isNumeric("  ")   = false 
	 *    StringUtil.isNumeric("123")  = true 
	 *    StringUtil.isNumeric("12 3") = false 
	 *    StringUtil.isNumeric("ab2c") = false 
	 *    StringUtil.isNumeric("12-3") = false 
	 *    StringUtil.isNumeric("12.3") = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果字符串非 <code>null</code> 并且全由unicode数字组成，则返回 <code>true</code>
	 */
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断字符串是否只包含unicode数字和空格 <code>' '</code>。
	 * 
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 * 
	 * <pre>
	 * 
	 *    StringUtil.isNumericSpace(null)   = false 
	 *    StringUtil.isNumericSpace("")     = true 
	 *    StringUtil.isNumericSpace("  ")   = true 
	 *    StringUtil.isNumericSpace("123")  = true 
	 *    StringUtil.isNumericSpace("12 3") = true 
	 *    StringUtil.isNumericSpace("ab2c") = false 
	 *    StringUtil.isNumericSpace("12-3") = false 
	 *    StringUtil.isNumericSpace("12.3") = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果字符串非 <code>null</code> 并且全由unicode数字和空格组成，则返回 <code>true</code>
	 */
	public static boolean isNumericSpace(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isDigit(str.charAt(i)) && (str.charAt(i) != ' ')) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断字符串是否只包含unicode空白。
	 * 
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 * 
	 * <pre>
	 * 
	 *    StringUtil.isWhitespace(null)   = false 
	 *    StringUtil.isWhitespace("")     = true 
	 *    StringUtil.isWhitespace("  ")   = true 
	 *    StringUtil.isWhitespace("abc")  = false 
	 *    StringUtil.isWhitespace("ab2c") = false 
	 *    StringUtil.isWhitespace("ab-c") = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果字符串非 <code>null</code> 并且全由unicode空白组成，则返回 <code>true</code>
	 */
	public static boolean isWhitespace(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 大小写转换。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 将字符串转换成大写。
	 * 
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.toUpperCase(null)  = null 
	 *    StringUtil.toUpperCase("")    = "" 
	 *    StringUtil.toUpperCase("aBc") = "ABC"
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要转换的字符串
	 * 
	 * @return 大写字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String toUpperCase(String str) {
		if (str == null) {
			return null;
		}

		return str.toUpperCase();
	}

	/**
	 * 将字符串转换成小写。
	 * 
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.toLowerCase(null)  = null 
	 *    StringUtil.toLowerCase("")    = "" 
	 *    StringUtil.toLowerCase("aBc") = "abc"
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要转换的字符串
	 * 
	 * @return 大写字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String toLowerCase(String str) {
		if (str == null) {
			return null;
		}

		return str.toLowerCase();
	}

	/**
	 * 将字符串的首字符转成大写（ <code>Character.toTitleCase</code> ），其它字符不变。
	 * 
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.capitalize(null)  = null 
	 *    StringUtil.capitalize("")    = "" 
	 *    StringUtil.capitalize("cat") = "Cat" 
	 *    StringUtil.capitalize("cAt") = "CAt"
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要转换的字符串
	 * 
	 * @return 首字符为大写的字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String capitalize(String str) {
		int strLen;

		if ((str == null) || ((strLen = str.length()) == 0)) {
			return str;
		}

		return new StringBuffer(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
	}

	/**
	 * 将字符串的首字符转成小写，其它字符不变。
	 * 
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.uncapitalize(null)  = null 
	 *    StringUtil.uncapitalize("")    = "" 
	 *    StringUtil.uncapitalize("Cat") = "cat" 
	 *    StringUtil.uncapitalize("CAT") = "cAT"
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要转换的字符串
	 * 
	 * @return 首字符为小写的字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String uncapitalize(String str) {
		int strLen;

		if ((str == null) || ((strLen = str.length()) == 0)) {
			return str;
		}

		return new StringBuffer(strLen).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
	}

	/**
	 * 反转字符串的大小写。
	 * 
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.swapCase(null)                 = null 
	 *    StringUtil.swapCase("")                   = "" 
	 *    StringUtil.swapCase("The dog has a BONE") = "tHE DOG HAS A bone"
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要转换的字符串
	 * 
	 * @return 大小写被反转的字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String swapCase(String str) {
		int strLen;

		if ((str == null) || ((strLen = str.length()) == 0)) {
			return str;
		}

		StringBuffer buffer = new StringBuffer(strLen);

		char ch = 0;

		for (int i = 0; i < strLen; i++) {
			ch = str.charAt(i);

			if (Character.isUpperCase(ch)) {
				ch = Character.toLowerCase(ch);
			} else if (Character.isTitleCase(ch)) {
				ch = Character.toLowerCase(ch);
			} else if (Character.isLowerCase(ch)) {
				ch = Character.toUpperCase(ch);
			}

			buffer.append(ch);
		}

		return buffer.toString();
	}

	/**
	 * 将字符串转换成camel case。
	 * 
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.toCamelCase(null)  = null 
	 *    StringUtil.toCamelCase("")    = "" 
	 *    StringUtil.toCamelCase("aBc") = "aBc" 
	 *    StringUtil.toCamelCase("aBc def") = "aBcDef" 
	 *    StringUtil.toCamelCase("aBc def_ghi") = "aBcDefGhi" 
	 *    StringUtil.toCamelCase("aBc def_ghi 123") = "aBcDefGhi123"
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * <p>
	 * 此方法会保留除了下划线和空白以外的所有分隔符。
	 * </p>
	 * 
	 * @param str
	 *            要转换的字符串
	 * 
	 * @return camel case字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String toCamelCase(String str) {
		return CAMEL_CASE_TOKENIZER.parse(str);
	}

	/**
	 * 将字符串转换成pascal case。
	 * 
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.toPascalCase(null)  = null 
	 *    StringUtil.toPascalCase("")    = "" 
	 *    StringUtil.toPascalCase("aBc") = "ABc" 
	 *    StringUtil.toPascalCase("aBc def") = "ABcDef" 
	 *    StringUtil.toPascalCase("aBc def_ghi") = "ABcDefGhi" 
	 *    StringUtil.toPascalCase("aBc def_ghi 123") = "aBcDefGhi123"
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * <p>
	 * 此方法会保留除了下划线和空白以外的所有分隔符。
	 * </p>
	 * 
	 * @param str
	 *            要转换的字符串
	 * 
	 * @return pascal case字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String toPascalCase(String str) {
		return PASCAL_CASE_TOKENIZER.parse(str);
	}

	/**
	 * 将字符串转换成下划线分隔的大写字符串。
	 * 
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.toUpperCaseWithUnderscores(null)  = null 
	 *    StringUtil.toUpperCaseWithUnderscores("")    = "" 
	 *    StringUtil.toUpperCaseWithUnderscores("aBc") = "A_BC" 
	 *    StringUtil.toUpperCaseWithUnderscores("aBc def") = "A_BC_DEF" 
	 *    StringUtil.toUpperCaseWithUnderscores("aBc def_ghi") = "A_BC_DEF_GHI" 
	 *    StringUtil.toUpperCaseWithUnderscores("aBc def_ghi 123") = "A_BC_DEF_GHI_123"
	 *    StringUtil.toUpperCaseWithUnderscores("__a__Bc__") = "__A__BC__"
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * <p>
	 * 此方法会保留除了空白以外的所有分隔符。
	 * </p>
	 * 
	 * @param str
	 *            要转换的字符串
	 * 
	 * @return 下划线分隔的大写字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String toUpperCaseWithUnderscores(String str) {
		return UPPER_CASE_WITH_UNDERSCORES_TOKENIZER.parse(str);
	}

	/**
	 * 将字符串转换成下划线分隔的小写字符串。
	 * 
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.toLowerCaseWithUnderscores(null)  = null 
	 *    StringUtil.toLowerCaseWithUnderscores("")    = "" 
	 *    StringUtil.toLowerCaseWithUnderscores("aBc") = "a_bc" 
	 *    StringUtil.toLowerCaseWithUnderscores("aBc def") = "a_bc_def" 
	 *    StringUtil.toLowerCaseWithUnderscores("aBc def_ghi") = "a_bc_def_ghi" 
	 *    StringUtil.toLowerCaseWithUnderscores("aBc def_ghi 123") = "a_bc_def_ghi_123" 
	 *    StringUtil.toLowerCaseWithUnderscores("__a__Bc__") = "__a__bc__"
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * <p>
	 * 此方法会保留除了空白以外的所有分隔符。
	 * </p>
	 * 
	 * @param str
	 *            要转换的字符串
	 * 
	 * @return 下划线分隔的小写字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String toLowerCaseWithUnderscores(String str) {
		return LOWER_CASE_WITH_UNDERSCORES_TOKENIZER.parse(str);
	}

	/** 解析单词的解析器。 */
	private static final WordTokenizer CAMEL_CASE_TOKENIZER = new WordTokenizer() {
		protected void startSentence(StringBuffer buffer, char ch) {
			buffer.append(Character.toLowerCase(ch));
		}

		protected void startWord(StringBuffer buffer, char ch) {
			if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
				buffer.append(Character.toUpperCase(ch));
			} else {
				buffer.append(Character.toLowerCase(ch));
			}
		}

		protected void inWord(StringBuffer buffer, char ch) {
			buffer.append(Character.toLowerCase(ch));
		}

		protected void startDigitSentence(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void startDigitWord(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void inDigitWord(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void inDelimiter(StringBuffer buffer, char ch) {
			if (ch != UNDERSCORE) {
				buffer.append(ch);
			}
		}
	};

	private static final WordTokenizer PASCAL_CASE_TOKENIZER = new WordTokenizer() {
		protected void startSentence(StringBuffer buffer, char ch) {
			buffer.append(Character.toUpperCase(ch));
		}

		protected void startWord(StringBuffer buffer, char ch) {
			buffer.append(Character.toUpperCase(ch));
		}

		protected void inWord(StringBuffer buffer, char ch) {
			buffer.append(Character.toLowerCase(ch));
		}

		protected void startDigitSentence(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void startDigitWord(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void inDigitWord(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void inDelimiter(StringBuffer buffer, char ch) {
			if (ch != UNDERSCORE) {
				buffer.append(ch);
			}
		}
	};

	private static final WordTokenizer UPPER_CASE_WITH_UNDERSCORES_TOKENIZER = new WordTokenizer() {
		protected void startSentence(StringBuffer buffer, char ch) {
			buffer.append(Character.toUpperCase(ch));
		}

		protected void startWord(StringBuffer buffer, char ch) {
			if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
				buffer.append(UNDERSCORE);
			}

			buffer.append(Character.toUpperCase(ch));
		}

		protected void inWord(StringBuffer buffer, char ch) {
			buffer.append(Character.toUpperCase(ch));
		}

		protected void startDigitSentence(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void startDigitWord(StringBuffer buffer, char ch) {
			if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
				buffer.append(UNDERSCORE);
			}

			buffer.append(ch);
		}

		protected void inDigitWord(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void inDelimiter(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}
	};

	private static final WordTokenizer LOWER_CASE_WITH_UNDERSCORES_TOKENIZER = new WordTokenizer() {
		protected void startSentence(StringBuffer buffer, char ch) {
			buffer.append(Character.toLowerCase(ch));
		}

		protected void startWord(StringBuffer buffer, char ch) {
			if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
				buffer.append(UNDERSCORE);
			}

			buffer.append(Character.toLowerCase(ch));
		}

		protected void inWord(StringBuffer buffer, char ch) {
			buffer.append(Character.toLowerCase(ch));
		}

		protected void startDigitSentence(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void startDigitWord(StringBuffer buffer, char ch) {
			if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
				buffer.append(UNDERSCORE);
			}

			buffer.append(ch);
		}

		protected void inDigitWord(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void inDelimiter(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}
	};

	/**
	 * 解析出下列语法所构成的 <code>SENTENCE</code>。
	 * 
	 * <pre>
	 * 
	 *     SENTENCE = WORD (DELIMITER* WORD)* 
	 *    
	 *     WORD = UPPER_CASE_WORD | LOWER_CASE_WORD | TITLE_CASE_WORD | DIGIT_WORD 
	 *    
	 *     UPPER_CASE_WORD = UPPER_CASE_LETTER+ 
	 *     LOWER_CASE_WORD = LOWER_CASE_LETTER+ 
	 *     TITLE_CASE_WORD = UPPER_CASE_LETTER LOWER_CASE_LETTER+ 
	 *     DIGIT_WORD      = DIGIT+ 
	 *    
	 *     UPPER_CASE_LETTER = Character.isUpperCase() 
	 *     LOWER_CASE_LETTER = Character.isLowerCase() 
	 *     DIGIT             = Character.isDigit() 
	 *     NON_LETTER_DIGIT  = !Character.isUpperCase() &amp;&amp; !Character.isLowerCase() &amp;&amp; !Character.isDigit() 
	 *    
	 *     DELIMITER = WHITESPACE | NON_LETTER_DIGIT
	 * 
	 * </pre>
	 */
	private abstract static class WordTokenizer {
		protected static final char UNDERSCORE = '_';

		/**
		 * Parse sentence。
		 */
		public String parse(String str) {
			if (MyStringUtils.isEmpty(str)) {
				return str;
			}

			int length = str.length();
			StringBuffer buffer = new StringBuffer(length);

			for (int index = 0; index < length; index++) {
				char ch = str.charAt(index);

				// 忽略空白。
				if (Character.isWhitespace(ch)) {
					continue;
				}

				// 大写字母开始：UpperCaseWord或是TitleCaseWord。
				if (Character.isUpperCase(ch)) {
					int wordIndex = index + 1;

					while (wordIndex < length) {
						char wordChar = str.charAt(wordIndex);

						if (Character.isUpperCase(wordChar)) {
							wordIndex++;
						} else if (Character.isLowerCase(wordChar)) {
							wordIndex--;
							break;
						} else {
							break;
						}
					}

					// 1. wordIndex == length，说明最后一个字母为大写，以upperCaseWord处理之。
					// 2. wordIndex == index，说明index处为一个titleCaseWord。
					// 3. wordIndex > index，说明index到wordIndex -
					// 1处全部是大写，以upperCaseWord处理。
					if ((wordIndex == length) || (wordIndex > index)) {
						index = parseUpperCaseWord(buffer, str, index, wordIndex);
					} else {
						index = parseTitleCaseWord(buffer, str, index);
					}

					continue;
				}

				// 小写字母开始：LowerCaseWord。
				if (Character.isLowerCase(ch)) {
					index = parseLowerCaseWord(buffer, str, index);
					continue;
				}

				// 数字开始：DigitWord。
				if (Character.isDigit(ch)) {
					index = parseDigitWord(buffer, str, index);
					continue;
				}

				// 非字母数字开始：Delimiter。
				inDelimiter(buffer, ch);
			}

			return buffer.toString();
		}

		private int parseUpperCaseWord(StringBuffer buffer, String str, int index, int length) {
			char ch = str.charAt(index++);

			// 首字母，必然存在且为大写。
			if (buffer.length() == 0) {
				startSentence(buffer, ch);
			} else {
				startWord(buffer, ch);
			}

			// 后续字母，必为小写。
			for (; index < length; index++) {
				ch = str.charAt(index);
				inWord(buffer, ch);
			}

			return index - 1;
		}

		private int parseLowerCaseWord(StringBuffer buffer, String str, int index) {
			char ch = str.charAt(index++);

			// 首字母，必然存在且为小写。
			if (buffer.length() == 0) {
				startSentence(buffer, ch);
			} else {
				startWord(buffer, ch);
			}

			// 后续字母，必为小写。
			int length = str.length();

			for (; index < length; index++) {
				ch = str.charAt(index);

				if (Character.isLowerCase(ch)) {
					inWord(buffer, ch);
				} else {
					break;
				}
			}

			return index - 1;
		}

		private int parseTitleCaseWord(StringBuffer buffer, String str, int index) {
			char ch = str.charAt(index++);

			// 首字母，必然存在且为大写。
			if (buffer.length() == 0) {
				startSentence(buffer, ch);
			} else {
				startWord(buffer, ch);
			}

			// 后续字母，必为小写。
			int length = str.length();

			for (; index < length; index++) {
				ch = str.charAt(index);

				if (Character.isLowerCase(ch)) {
					inWord(buffer, ch);
				} else {
					break;
				}
			}

			return index - 1;
		}

		private int parseDigitWord(StringBuffer buffer, String str, int index) {
			char ch = str.charAt(index++);

			// 首字符，必然存在且为数字。
			if (buffer.length() == 0) {
				startDigitSentence(buffer, ch);
			} else {
				startDigitWord(buffer, ch);
			}

			// 后续字符，必为数字。
			int length = str.length();

			for (; index < length; index++) {
				ch = str.charAt(index);

				if (Character.isDigit(ch)) {
					inDigitWord(buffer, ch);
				} else {
					break;
				}
			}

			return index - 1;
		}

		protected boolean isDelimiter(char ch) {
			return !Character.isUpperCase(ch) && !Character.isLowerCase(ch) && !Character.isDigit(ch);
		}

		protected abstract void startSentence(StringBuffer buffer, char ch);

		protected abstract void startWord(StringBuffer buffer, char ch);

		protected abstract void inWord(StringBuffer buffer, char ch);

		protected abstract void startDigitSentence(StringBuffer buffer, char ch);

		protected abstract void startDigitWord(StringBuffer buffer, char ch);

		protected abstract void inDigitWord(StringBuffer buffer, char ch);

		protected abstract void inDelimiter(StringBuffer buffer, char ch);
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 字符串分割函数。 */
	/*                                                                              */
	/* 将字符串按指定分隔符分割。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 将字符串按空白字符分割。
	 * 
	 * <p>
	 * 分隔符不会出现在目标数组中，连续的分隔符就被看作一个。如果字符串为 <code>null</code> ，则返回
	 * <code>null</code>。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.split(null)       = null 
	 *    StringUtil.split("")         = [] 
	 *    StringUtil.split("abc def")  = ["abc", "def"] 
	 *    StringUtil.split("abc  def") = ["abc", "def"] 
	 *    StringUtil.split(" abc ")    = ["abc"]
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要分割的字符串
	 * 
	 * @return 分割后的字符串数组，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String[] split(String str) {
		return split(str, null, -1);
	}

	/**
	 * 将字符串按指定字符分割。
	 * 
	 * <p>
	 * 分隔符不会出现在目标数组中，连续的分隔符就被看作一个。如果字符串为 <code>null</code> ，则返回
	 * <code>null</code>。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.split(null, *)         = null 
	 *    StringUtil.split("", *)           = [] 
	 *    StringUtil.split("a.b.c", '.')    = ["a", "b", "c"] 
	 *    StringUtil.split("a..b.c", '.')   = ["a", "b", "c"] 
	 *    StringUtil.split("a:b:c", '.')    = ["a:b:c"] 
	 *    StringUtil.split("a b c", ' ')    = ["a", "b", "c"]
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要分割的字符串
	 * @param separatorChar
	 *            分隔符
	 * 
	 * @return 分割后的字符串数组，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String[] split(String str, char separatorChar) {
		if (str == null) {
			return null;
		}

		int length = str.length();

		if (length == 0) {
			return null;
		}

		List<String> list = new ArrayList<String>();
		int i = 0;
		int start = 0;
		boolean match = false;

		while (i < length) {
			if (str.charAt(i) == separatorChar) {
				if (match) {
					list.add(str.substring(start, i));
					match = false;
				}

				start = ++i;
				continue;
			}

			match = true;
			i++;
		}

		if (match) {
			list.add(str.substring(start, i));
		}

		return (String[]) list.toArray(new String[list.size()]);
	}

	/**
	 * 将字符串按指定字符分割。
	 * 
	 * <p>
	 * 分隔符不会出现在目标数组中，连续的分隔符就被看作一个。如果字符串为 <code>null</code> ，则返回
	 * <code>null</code>。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.split(null, *)                = null 
	 *    StringUtil.split("", *)                  = [] 
	 *    StringUtil.split("abc def", null)        = ["abc", "def"] 
	 *    StringUtil.split("abc def", " ")         = ["abc", "def"] 
	 *    StringUtil.split("abc  def", " ")        = ["abc", "def"] 
	 *    StringUtil.split(" ab:  cd::ef  ", ":")  = ["ab", "cd", "ef"]
	 *    StringUtil.split("abc.def", "")          = ["abc.def"]
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要分割的字符串
	 * @param separatorChars
	 *            分隔符
	 * 
	 * @return 分割后的字符串数组，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String[] split(String str, String separatorChars) {
		return split(str, separatorChars, -1);
	}

	/**
	 * 将字符串按指定字符分割。
	 * 
	 * <p>
	 * 分隔符不会出现在目标数组中，连续的分隔符就被看作一个。如果字符串为 <code>null</code> ，则返回
	 * <code>null</code>。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.split(null, *, *)                 = null 
	 *    StringUtil.split("", *, *)                   = [] 
	 *    StringUtil.split("ab cd ef", null, 0)        = ["ab", "cd", "ef"] 
	 *    StringUtil.split("  ab   cd ef  ", null, 0)  = ["ab", "cd", "ef"] 
	 *    StringUtil.split("ab:cd::ef", ":", 0)        = ["ab", "cd", "ef"] 
	 *    StringUtil.split("ab:cd:ef", ":", 2)         = ["ab", "cdef"] 
	 *    StringUtil.split("abc.def", "", 2)           = ["abc.def"]
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要分割的字符串
	 * @param separatorChars
	 *            分隔符
	 * @param max
	 *            返回的数组的最大个数，如果小于等于0，则表示无限制
	 * 
	 * @return 分割后的字符串数组，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String[] split(String str, String separatorChars, int max) {
		if (str == null) {
			return null;
		}

		int length = str.length();

		if (length == 0) {
			return null;
		}

		List<String> list = new ArrayList<String>();
		int sizePlus1 = 1;
		int i = 0;
		int start = 0;
		boolean match = false;

		if (separatorChars == null) {
			// null表示使用空白作为分隔符
			while (i < length) {
				if (Character.isWhitespace(str.charAt(i))) {
					if (match) {
						if (sizePlus1++ == max) {
							i = length;
						}

						list.add(str.substring(start, i));
						match = false;
					}

					start = ++i;
					continue;
				}

				match = true;
				i++;
			}
		} else if (separatorChars.length() == 1) {
			// 优化分隔符长度为1的情形
			char sep = separatorChars.charAt(0);

			while (i < length) {
				if (str.charAt(i) == sep) {
					if (match) {
						if (sizePlus1++ == max) {
							i = length;
						}

						list.add(str.substring(start, i));
						match = false;
					}

					start = ++i;
					continue;
				}

				match = true;
				i++;
			}
		} else {
			// 一般情形
			while (i < length) {
				if (separatorChars.indexOf(str.charAt(i)) >= 0) {
					if (match) {
						if (sizePlus1++ == max) {
							i = length;
						}

						list.add(str.substring(start, i));
						match = false;
					}

					start = ++i;
					continue;
				}

				match = true;
				i++;
			}
		}

		if (match) {
			list.add(str.substring(start, i));
		}

		return (String[]) list.toArray(new String[list.size()]);
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 字符串连接函数。 */
	/*                                                                              */
	/* 将多个对象按指定分隔符连接成字符串。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 将数组中的元素连接成一个字符串。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.join(null)            = null 
	 *    StringUtil.join([])              = "" 
	 *    StringUtil.join([null])          = "" 
	 *    StringUtil.join(["a", "b", "c"]) = "abc" 
	 *    StringUtil.join([null, "", "a"]) = "a"
	 * 
	 * </pre>
	 * 
	 * @param array
	 *            要连接的数组
	 * 
	 * @return 连接后的字符串，如果原数组为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String join(Object[] array) {
		return join(array, null);
	}

	/**
	 * 将数组中的元素连接成一个字符串。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.join(null, *)               = null 
	 *    StringUtil.join([], *)                 = "" 
	 *    StringUtil.join([null], *)             = "" 
	 *    StringUtil.join(["a", "b", "c"], ';')  = "a;b;c" 
	 *    StringUtil.join(["a", "b", "c"], null) = "abc" 
	 *    StringUtil.join([null, "", "a"], ';')  = ";;a"
	 * 
	 * </pre>
	 * 
	 * @param array
	 *            要连接的数组
	 * @param separator
	 *            分隔符
	 * 
	 * @return 连接后的字符串，如果原数组为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String join(Object[] array, char separator) {
		if (array == null) {
			return null;
		}

		int arraySize = array.length;
		int bufSize = (arraySize == 0) ? 0 : ((((array[0] == null) ? 16 : array[0].toString().length()) + 1) * arraySize);
		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = 0; i < arraySize; i++) {
			if (i > 0) {
				buf.append(separator);
			}

			if (array[i] != null) {
				buf.append(array[i]);
			}
		}

		return buf.toString();
	}

	/**
	 * 将数组中的元素连接成一个字符串。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.join(null, *)                = null 
	 *    StringUtil.join([], *)                  = "" 
	 *    StringUtil.join([null], *)              = "" 
	 *    StringUtil.join(["a", "b", "c"], "--")  = "a--b--c" 
	 *    StringUtil.join(["a", "b", "c"], null)  = "abc" 
	 *    StringUtil.join(["a", "b", "c"], "")    = "abc" 
	 *    StringUtil.join([null, "", "a"], ',')   = ",,a"
	 * 
	 * </pre>
	 * 
	 * @param array
	 *            要连接的数组
	 * @param separator
	 *            分隔符
	 * 
	 * @return 连接后的字符串，如果原数组为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}

		if (separator == null) {
			separator = EMPTY_STRING;
		}

		int arraySize = array.length;

		// ArraySize == 0: Len = 0
		// ArraySize > 0: Len = NofStrings *(len(firstString) + len(separator))
		// (估计大约所有的字符串都一样长)
		int bufSize = (arraySize == 0) ? 0 : (arraySize * (((array[0] == null) ? 16 : array[0].toString().length()) + ((separator != null) ? separator.length() : 0)));

		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = 0; i < arraySize; i++) {
			if ((separator != null) && (i > 0)) {
				buf.append(separator);
			}

			if (array[i] != null) {
				buf.append(array[i]);
			}
		}

		return buf.toString();
	}

	/**
	 * 将 <code>Iterator</code> 中的元素连接成一个字符串。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.join(null, *)                = null 
	 *    StringUtil.join([], *)                  = "" 
	 *    StringUtil.join([null], *)              = "" 
	 *    StringUtil.join(["a", "b", "c"], "--")  = "a--b--c" 
	 *    StringUtil.join(["a", "b", "c"], null)  = "abc" 
	 *    StringUtil.join(["a", "b", "c"], "")    = "abc" 
	 *    StringUtil.join([null, "", "a"], ',')   = ",,a"
	 * 
	 * </pre>
	 * 
	 * @param iterator
	 *            要连接的 <code>Iterator</code>
	 * @param separator
	 *            分隔符
	 * 
	 * @return 连接后的字符串，如果原数组为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String join(Iterator iterator, char separator) {
		if (iterator == null) {
			return null;
		}

		StringBuffer buf = new StringBuffer(256); // Java默认值是16, 可能偏小

		while (iterator.hasNext()) {
			Object obj = iterator.next();

			if (obj != null) {
				buf.append(obj);
			}

			if (iterator.hasNext()) {
				buf.append(separator);
			}
		}

		return buf.toString();
	}

	/**
	 * 将 <code>Iterator</code> 中的元素连接成一个字符串。
	 * 
	 * <pre>
	 * 
	 *    StringUtil.join(null, *)                = null 
	 *    StringUtil.join([], *)                  = "" 
	 *    StringUtil.join([null], *)              = "" 
	 *    StringUtil.join(["a", "b", "c"], "--")  = "a--b--c" 
	 *    StringUtil.join(["a", "b", "c"], null)  = "abc" 
	 *    StringUtil.join(["a", "b", "c"], "")    = "abc" 
	 *    StringUtil.join([null, "", "a"], ',')   = ",,a"
	 * 
	 * </pre>
	 * 
	 * @param iterator
	 *            要连接的 <code>Iterator</code>
	 * @param separator
	 *            分隔符
	 * 
	 * @return 连接后的字符串，如果原数组为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String join(Iterator iterator, String separator) {
		if (iterator == null) {
			return null;
		}

		StringBuffer buf = new StringBuffer(256); // Java默认值是16, 可能偏小

		while (iterator.hasNext()) {
			Object obj = iterator.next();

			if (obj != null) {
				buf.append(obj);
			}

			if ((separator != null) && iterator.hasNext()) {
				buf.append(separator);
			}
		}
		return buf.toString();
	}
}
