package cn.kgc.tangcco.util.cryptography;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

public abstract class BaseCryptographyUtils {
	/**
	 * 将字符串使用ase算法加密并返回加密后字符串的十六进制字符串
	 * 
	 * @param src 欲被加密的字符串
	 * @param key 秘钥 注意:要求秘钥为长度为十六个字符的字符串
	 * @return 加密后字符串的十六进制字符串
	 */
	public static String aesEncodeHexString(String src, String key) {

		return new SymmetricCrypto(SymmetricAlgorithm.AES, new String(key).getBytes()).encryptHex(src);

	}

	/**
	 * 将使用ase算法加密后生产的十六进制字符串解密
	 * 
	 * @param encryptHex 加密后生产的十六进制字符串解密
	 * @param key        秘钥 注意:秘钥为长度为十六个字符的远秘钥字符串
	 * @return 将加密后的字符串解密回来
	 */
	public static String aesDecodeHexString(String encryptHex, String key) {
		return new SymmetricCrypto(SymmetricAlgorithm.AES, new String(key).getBytes()).decryptStr(encryptHex,CharsetUtil.CHARSET_UTF_8);
	}

	/**
	 * 将字符串使用des算法加密并返回加密后字符串的十六进制字符串
	 * 
	 * @param src 欲被加密的字符串
	 * @param key 秘钥
	 * @return 加密后字符串的十六进制字符串
	 */
	public static String desEncodeHexString(String src, String key) {
		return new SymmetricCrypto(SymmetricAlgorithm.DESede, new String(key).getBytes()).encryptHex(src);
	}

	/**
	 * 将使用des算法加密后生产的十六进制字符串解密
	 * 
	 * @param encryptHex 加密后生产的十六进制字符串解密
	 * @param key        原秘钥
	 * @return 将加密后的字符串解密回来
	 */
	public static String desDecodeHexString(String encryptHex, String key) {
		return new SymmetricCrypto(SymmetricAlgorithm.DESede, new String(key).getBytes()).decryptStr(encryptHex,CharsetUtil.CHARSET_UTF_8);
	}
	/**
	 * 使用md5加密字符串
	 * @param data	加密前的源字符串
	 * @return		加密后的字符串
	 */
	public static String digestHex(String data) {
		return new Digester(DigestAlgorithm.MD5).digestHex(data);
	}
	
	
}
