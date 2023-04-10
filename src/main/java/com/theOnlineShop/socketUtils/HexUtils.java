package com.theOnlineShop.socketUtils;

//import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;

public class HexUtils {
	/**
	 * 字节数组复制
	 * @param cmd
	 * @param cc
	 * @return
	 */
	public static byte[] copyTo(byte[] cmd,byte[] cc){
		byte[] dist = new byte[cmd.length+cc.length];
		for (int i = 0; i < dist.length; i++) {
			if(i<cmd.length){
				dist[i] = cmd[i];
			}else{
				dist[i] = cc[i-cmd.length];
			}
		}
		return dist;
	}
	
	/**
	 * 字节数组复制
	 * @param cmd
	 * @param aa
	 * @return
	 */
	public static byte[] copyTo(byte[] cmd,byte aa){
		byte[] cc = new byte[1];
		cc[0] = aa;
		return copyTo(cmd,cc);
	}
	
	/**
	 * 低字节在前，高字节在后
	 * @param i
	 * @return
	 */
	public static byte[] intToBytes(int i) {  
        byte[] targets = new byte[2];  
        targets[0] = (byte) (i & 0xFF);  
        targets[1] = (byte) (i >> 8 & 0xFF);  
        System.out.println(parseByte2HexStr(targets[0])+"*"+parseByte2HexStr(targets[1]));
        return targets;  
    }
	
	/**
	 * 低字节在前，高字节在后
	 * @param i
	 * @return
	 */
	public static byte[] intToBytes4(int i) {  
        byte[] targets = new byte[4];  
        targets[0] = (byte) (i & 0xFF);  
        targets[1] = (byte) (i >> 8 & 0xFF);  
        targets[2] = (byte) (i >> 16 & 0xFF);
        targets[3] = (byte) (i >> 24 & 0xFF);
        System.out.println(parseByte2HexStr(targets[0])+"*"+parseByte2HexStr(targets[1])+"*"+parseByte2HexStr(targets[2])+"*"+parseByte2HexStr(targets[3]));
        return targets;  
    }
	
	/**
	 * @description 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
	
	/**
	 * @description 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf) {
		StringBuffer sb = new StringBuffer();
		String hex = Integer.toHexString(buf & 0xFF);
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		sb.append(hex.toUpperCase());
		return sb.toString();
	}
	
	/**
	 * @description 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStrBlank(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase()+" ");
		}
		return sb.toString();
	}
	
	/**
	 * @description 将16进制字符串转换成二进制
	 * 
	 * @param buf
	 * @return
	 */
	public static byte[] parseHex2Byte(String content) {
		byte[] cmds = new byte[content.length()/2];
		for (int i = 0; i < content.length()/2; i++) {
			cmds[i] = (byte) HexUtils.hexStringToAlgorism(content.substring(i*2, i*2+2));
		}
		return cmds;
	}
	
	/**
     * 多个十六进制转字符串
     */
//	public static String multiHexStringToAsciiStr(String str){
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < str.length(); i+=2) {
//			sb.append(hexStringToAsciiStr(str.substring(i, i+2)));
//		}
//		return sb.toString();
//	}
	
	/**
     * 十六进制转字符串
     * 
     * @param hexString
     *            十六进制字符串
     * @param encodeType
     *            编码类型4：Unicode，2：普通编码
     * @return 字符串
     */
    public static String hexStringToAsciiStr(String hexString) {
    	int encodeType = 2;
        String result = "";
        int max = hexString.length() / encodeType;
        for (int i = 0; i < max; i++) {
            char c = (char) hexStringToAlgorism(hexString
                    .substring(i * encodeType, (i + 1) * encodeType));
            result += c;
        }
        return result;
    }
    
    /**
     * 十六进制字符串装十进制
     * 
     * @param hex
     *            十六进制字符串
     * @return 十进制数值
     */
    public static int hexStringToAlgorism(String hex) {
        hex = hex.toUpperCase();
        int max = hex.length();
        int result = 0;
        for (int i = max; i > 0; i--) {
            char c = hex.charAt(i - 1);
            int algorism = 0;
            if (c >= '0' && c <= '9') {
                algorism = c - '0';
            } else {
                algorism = c - 55;
            }
            result += Math.pow(16, max - i) * algorism;
        }
        return result;
    }
    
    /**
     * 十六进制字符串转十进制字符串
     * 
     * @param hex
     *            十六进制字符串
     * @return 十进制字符串
     */
    public static String hexStringToAlgorismStr(String hex) {
    	String str = hexStringToAlgorism(hex)+"";
    	if(str.length() == 1){
    		return "0"+str;
    	}
        return str;
    }
    
    /**
     * LSB,低位在前,高位在后
     */
    public static String lsbConvert(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = str.length()-2; i >=0 ; i-=2) {
			sb.append(str.substring(i, i+2));
		}
		return sb.toString();
    }
    
    /**
     * 数字字符串转ASCII码字符串
     * 
     * @param String
     *            字符串
     * @return ASCII字符串
     */
    public static String stringToAsciiString(String content) {
        String result = "";
        int max = content.length();
        for (int i = 0; i < max; i++) {
            char c = content.charAt(i);
            String b = Integer.toHexString(c);
            result = result + b;
        }
        return result;
    }
    
    public static byte[] getChip(String content){
    	byte[] b = new byte[16];
    	int max = content.length();
        for (int i = 0; i < max && i<b.length; i++) {
            char c = content.charAt(i);
            b[i] = (byte) c;
        }
    	return b;
    }
    
	public static String convertStringToHex(String str) {
		char[] chars = str.toCharArray();
		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			hex.append(Integer.toHexString((int) chars[i]));
		}
		return hex.toString();
	}
	
	public static String getTwoIntStr(int v){
		if(v < 10){
			return "0" + v;
		}
		return v+"";
	}
	
//	public static String getSerial(String serial){
//		if(serial.substring(14,16).equals("40")){//新规则
//			StringBuilder sb = new StringBuilder();
//			sb.append(HexUtils.hexStringToAsciiStr(serial.substring(0,4)));
//			sb.append(HexUtils.hexStringToAlgorismStr(serial.substring(4,6)));
//			int date = HexUtils.hexStringToAlgorism(serial.substring(6,10));
//			sb.append(HexUtils.getTwoIntStr(date >> 9));//年
//			sb.append(HexUtils.getTwoIntStr((date >> 5) & 0x0F));//月
//			sb.append(HexUtils.getTwoIntStr(date & 0x1F));//日
//			sb.append(HexUtils.hexStringToAlgorismStr(serial.substring(10,12)));
//			sb.append(HexUtils.hexStringToAlgorismStr(serial.substring(12,14)));
//			sb.append(HexUtils.hexStringToAsciiStr(serial.substring(14,16)));
//			serial = sb.toString();
//		}else{//老规则
//			serial = HexUtils.hexStringToAsciiStr(serial);
//			serial = StringUtils.trim(serial);
//		}
//		return serial;
//	}
	
	/**
	 * 获取站点地址设备地址
	 * @param b
	 * @return
	 */
	public static int[] getSiteDeviceAddr(byte[] b){
		String backCmd = HexUtils.parseByte2HexStr(b);
		String data = backCmd.substring(4, 12);
		String site = HexUtils.lsbConvert(data.substring(0,4));
		int siteAddr = HexUtils.hexStringToAlgorism(site);
		int deviceAddr = HexUtils.hexStringToAlgorism(data.substring(4,6));
		int csAddress = HexUtils.hexStringToAlgorism(data.substring(6,8));
		
		return new int[]{siteAddr,deviceAddr,csAddress};
	}

	
	//数据和
	public static byte  getDataAdd(byte[] bs){
		byte he = 0;
		if(bs != null){
			for (int i = 1; i < bs.length-1; i++) {
				he += bs[i];
			}
		}
		return he;
	}

    public static int getHigh4(byte data){//获取高四位
        int height;
        height = ((data & 0xf0) >> 4);
        return height;
    }

    public static int getLow4(byte data){//获取低四位
        int low;
        low = (data & 0x0f);
        return low;
    }

	public static String outStringByByte(String str, int len){
		try {
			byte[] btf = str.getBytes("GB2312");
			if(btf.length < len){
				return str;
			}
			int count = 0;
			for (int j = len - 1; j >= 0; j--) {
				if (btf[j] < 0){
					count++;
				}else{
					break;
				}
			}
			if (count % 2 == 0){
				return new String(btf, 0, len, "GB2312");
			}else{
				return new String(btf, 0, len - 1, "GB2312");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

//    public static void main(String[] args) throws IOException {
////		System.out.println(hexStringToAsciiStr("31"));
////    	System.out.println(stringToAsciiString("abcz"));
////    	System.out.println(convertStringToHex("abcz"));
////    	System.out.println(getChip("abcz"));
////    	intToBytes4(1024*1024*1024);
//		byte[] cnames = new byte[8];
//		byte[] cname = "KEK尹才量（勿用）".getBytes("GB2312");
//		for(int i=0;i<cname.length&&i<8;i++){
//			cnames[i] = cname[i];
//		}
//		System.out.println(new String(cnames, "GB2312"));
//		System.out.println(new String(outStringByByte("KEK",8).getBytes("GB2312"), "GB2312"));
//	}
}
