package com.sapint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converts {
	/**
	 * 根据输入的参数转换成对应的语言代码
	 * @param language
	 * @return
	 */
	//protected static Encoding enc;
	public static String languageIsotoSap(String language)
    {
        String sap = "e";
        if(language == "EN")
        {
        sap = "e";
        }else if(language == "ZH")
        {
        sap = "1";
        }
        return sap;
//        switch (language)
//        {
//            case "EN":
//                sap = "e"; 
//                break;
//            case "ZH":
//                sap = "1";
//                break;
//            default:
//                sap = "e";
//                break;
//        }
//        return sap;
    }
	
    public static String FormatMessage(String message, String var1, String var2, String var3, String var4)
    {
        var1 = var1.trim();
        var2 = var2.trim();
        var3 = var3.trim();
        var4 = var4.trim();
        String str = message.toString().replace("&1", var1).replace("&2", var2).replace("&3", var3).replace("&4", var4);
        int num = 1;
        for (int i = 0; i < str.length(); i++)
        {
            if (str.substring(i, 1).equals("&"))
            {
                switch (num)
                {
                    case 1:
                        str = str.substring(0, i) + var1 + str.substring(i + 1);
                        num++;
                        break;

                    case 2:
                        str = str.substring(0, i) + var2 + str.substring(i + 1);
                        num++;
                        break;

                    case 3:
                        str = str.substring(0, i) + var3 + str.substring(i + 1);
                        num++;
                        break;

                    case 4:
                        str = str.substring(0, i) + var4 + str.substring(i + 1);
                        num++;
                        break;
                }
            }
        }
        return str;
    }
    
    public static String OriginalNameToXmlName(String origninalName)
    {
        String input = origninalName;
        Pattern pattern = Pattern.compile("^[0-9].*");
        Matcher matcher = pattern.matcher(input);
        boolean b= matcher.matches();
        
        if (input.startsWith("___"))
        {
            input = "_US-" + input;
        }        
        else if (b)
        {
            input = "_NU-" + input;
        }
        return input.replace("_FZ_", "_UFZU-").replace("_ST_", "_USTU-").replace("_x002F_", "_Ux002FU-").replace("?", "_FZ-").replace("/", "_-");
    }
    
    public static String XmlNameToOriginalName(String xmlName)
    {
        String str = xmlName;
        if (xmlName.contains("-"))
        {
            if (str.startsWith("_US-"))
            {
                str = str.substring(4);
            }
            else if (str.startsWith("_NU-"))
            {
                str = str.substring(4);
            }
            return str.replace("_UFZU-", "_FZ_").replace("_USTU-", "_ST_").replace("_Ux002FU-", "_x002F_").replace("_FZ-", "?").replace("_-", "/");
        }
        if (str.startsWith("___"))
        {
            str = str.substring(3);
        }
        return str.replace("_FZ_", "?").replace("_ST_", "/").replace("_x002F_", "/");
    }
}
