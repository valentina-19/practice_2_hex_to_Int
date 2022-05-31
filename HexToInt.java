package practice_2;

import java.math.BigInteger;

public class HexToInt {
	
	// функция возвращает строку без символа "0x" 
	private static String trimHexPrefix(String hexValue) {
		if (hexValue.startsWith("0x")) {
			return hexValue.substring(2);
		} else {
			return hexValue;
		}		
	}
	
	// функция подсчитывает количество байтов 
	public static int getBytesCount (String hexValue) {	
		return trimHexPrefix(hexValue).length() / 2;
	}
	
	// функция конвертирует шестнадцатеричное число, заданное в виде строки, в Little-endian
	public static BigInteger convertHexToLE (String hexValue) {
		// удаляем из входящей строки символ "0x"
		String trimHexValue = trimHexPrefix(hexValue);
		// получаем количество байт
		int bytesCount = getBytesCount(hexValue);	
		// переменная хранит исходное число, сконвертированное в Little-endian
		BigInteger hexToIntValue = new BigInteger("0");
		// используем число 256 для конвертации исходного числа в Little-endian
		BigInteger bigInt256 = new BigInteger("256");
		// переменная содержит пару символов в каждом байте в виде подстроки
		String strByte;
		// переменная содержит байты, сконвертированные в Big Integer из строкового типа 
		BigInteger biByte;		
		for (int i = 0; i < bytesCount; i++) {
			// разделяем строку на подстроки, т.е. байты, каждый из которых содержит по два символа
			strByte = trimHexValue.substring(i*2, i*2+2);
			// переводим полученные подстроки в Big Integer
			biByte = new BigInteger(strByte, 16);
			// получаем число, байты которого расположены от младшего к старшему
			hexToIntValue = hexToIntValue.add(bigInt256.pow(i).multiply(biByte));
			
		}
		// возвращаем число в Little-endian
		return hexToIntValue;		
	}
	
	// функция конвертирует шестнадцатеричное число, заданное в виде строки, в Big-endian 
	public static BigInteger convertHexToBE (String hexValue) {
		// удаляем из входящей строки символ "0x"
		String trimHexValue = trimHexPrefix(hexValue);
		// получаем количество байт
		int bytesCount = getBytesCount(hexValue);
		// переменная хранит исходное число, сконвертированное в Big-endian
		BigInteger hexToIntValue = new BigInteger("0");
		// используем число 256 для конвертации исходного числа в Big-endian
		BigInteger bigInt256 = new BigInteger("256");	
		// переменная содержит пару символов в каждом байте в виде подстроки
		String strByte;
		// переменная содержит байты, сконвертированные в Big Integer из строкового типа 
		BigInteger biByte;
		
		for (int i = 0; i < bytesCount; i++) {
			// разделяем строку на подстроки, т.е. байты, каждый из которых содержит по два символа
			strByte = trimHexValue.substring(i*2, i*2+2);
			// переводим полученные подстроки в Big Integer
			biByte = new BigInteger(strByte, 16);
			// получаем число, байты которого расположены от старшего к младшему
			hexToIntValue = hexToIntValue.add(bigInt256.pow(bytesCount-1-i).multiply(biByte));
		}
		// возвращаем число в Big-endian
		return hexToIntValue;
	}

	// функция конвертирует целое число, полученное в Little-endian, с заданным количеством байт в шестнадцатеричное
	public static String convertLEtoHex (BigInteger value, int bytesCount) {
		// используем число 256 для конвертации исходного числа в Little-endian в шестнадцатеричное
		BigInteger bigInt256 = new BigInteger("256");	
		// переменная хранит значение байта, полученное путем использования операции остатка от деления на 256
		BigInteger byteValue;
		// переменная хранит промежуточную разницу оставшейся суммы и полученного слагаемого 
		BigInteger diffValue;
		// переменная хранит число 16 
		BigInteger bigInt16 = new BigInteger("16");
		// переменная хранит значение, оставшееся после отделения младших байт 
		BigInteger divValue = new BigInteger("0");
		divValue = divValue.add(value);
		// переменная содержит шестнадцатеричное число в виде строки 
		String hexValue = "";
		// цикл работает до тех пор, пока число, полученное в результате деления,  превышает один байт
		while(divValue.compareTo(bigInt256) != -1) {
			// получаем значение младшего байта как остаток от деления на цело на 256
			byteValue = divValue.mod(bigInt256);
			// конкатенируем полученные байты в одну строку в порядке от младшего байта к старшему
			// если число меньше 16
			if (byteValue.compareTo(bigInt16) == -1) {
				// то прибавляем символ "0" для получения байта в виде двузначного символа
				hexValue = "0" + byteValue.toString(16) + hexValue;
			} else {
				// иначе символ "0" не прибавляем
			hexValue = hexValue + byteValue.toString(16);
			}
			// получаем разницу значения, оставшегося от деления на 256 и байта, полученного путем остатка от деления на 256
			diffValue = divValue.add(byteValue.negate());
			// получаем значение числа без младшего байта 
			divValue = diffValue.divide(bigInt256);		
		}
		// добавляем самый старший байт
		// если число меньше 16
		if (divValue.compareTo(bigInt16) == -1) {
			// то прибавляем символ "0" для получения байта в виде двузначного символа
			hexValue = hexValue + "0" + divValue.toString(16);
		} else {
			// иначе символ "0" не прибавляем
			hexValue = hexValue + divValue.toString(16);
		}
		
		// для получения строки, отображающей заданное количество байт, добавляем символ "0" в строку в порядке от младшего байта к старшему
		for (int i = hexValue.length(); i < bytesCount*2; i++) {
			hexValue = hexValue + "0";
		}
		 // возвращаем полученное значение числа в строковом виде с добавлением символа "0х" вначале
		return "0x" + hexValue;		
	}
	
	// функция конвертирует целое число, полученное в Big-endian, с заданным количеством байт в шестнадцатеричное
	public static String convertBEtoHex (BigInteger value, int bytesCount) {
		// используем число 256 для конвертации исходного числа в Little-endian в шестнадцатеричное
		BigInteger bigInt256 = new BigInteger("256");	
		// переменная хранит значение байта, полученное путем использования операции остатка от деления на 256
		BigInteger byteValue;
		// переменная хранит промежуточную разницу оставшейся суммы и полученного слагаемого 
		BigInteger diffValue;
		// переменная хранит число 16 
		BigInteger bigInt16 = new BigInteger("16");
		// переменная хранит значение, оставшееся после отделения младших байт 
		BigInteger divValue = new BigInteger("0");		
		divValue = divValue.add(value);
		// переменная содержит шестнадцатеричное число в виде строки 
		String hexValue = "";
		// цикл работает до тех пор, пока число, полученное в результате деления,  превышает один байт
		while(divValue.compareTo(bigInt256) != -1) {
			// получаем значение младшего байта как остаток от деления на цело на 256
			byteValue = divValue.mod(bigInt256);
			// конкатенируем полученные байты в одну строку в порядке от младшего байта к старшему
			// если число меньше 16
			if (byteValue.compareTo(bigInt16) == -1) {
				// то прибавляем символ "0" для получения байта в виде двузначного символа
				hexValue = "0" + byteValue.toString(16) + hexValue;
			} else {
				// иначе символ "0" не прибавляем
				hexValue = byteValue.toString(16) + hexValue;
			}
			// получаем разницу значения, оставшегося от деления на 256 и байта, полученного путем остатка от деления на 256
			diffValue = divValue.add(byteValue.negate());
			// уменьшаем степень числа 256
			divValue = diffValue.divide(bigInt256);		
		}
		// добавляем самый старший байт
		// если число меньше 16
		if (divValue.compareTo(bigInt16) == -1) {
			// то прибавляем символ "0" для получения байта в виде двузначного символа
			hexValue = "0" + divValue.toString(16) + hexValue;
		} else {
			// иначе символ "0" не прибавляем
			hexValue = divValue.toString(16) + hexValue;
		}
		
		// для получения строки, отображающей заданное количество байт, добавляем символ "0" в строку в порядке от старшего к младшему
		for (int i = hexValue.length(); i < bytesCount*2; i++) {
			hexValue = "0" + hexValue;
		}
		
		// возвращаем полученное значение числа в строковом виде с добавлением символа "0х" вначале
		return "0x" + hexValue;		
	}
	

}
