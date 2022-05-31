package practice_2;

import java.math.BigInteger;

public class HexToInt {
	
	// ������� ���������� ������ ��� ������� "0x" 
	private static String trimHexPrefix(String hexValue) {
		if (hexValue.startsWith("0x")) {
			return hexValue.substring(2);
		} else {
			return hexValue;
		}		
	}
	
	// ������� ������������ ���������� ������ 
	public static int getBytesCount (String hexValue) {	
		return trimHexPrefix(hexValue).length() / 2;
	}
	
	// ������� ������������ ����������������� �����, �������� � ���� ������, � Little-endian
	public static BigInteger convertHexToLE (String hexValue) {
		// ������� �� �������� ������ ������ "0x"
		String trimHexValue = trimHexPrefix(hexValue);
		// �������� ���������� ����
		int bytesCount = getBytesCount(hexValue);	
		// ���������� ������ �������� �����, ����������������� � Little-endian
		BigInteger hexToIntValue = new BigInteger("0");
		// ���������� ����� 256 ��� ����������� ��������� ����� � Little-endian
		BigInteger bigInt256 = new BigInteger("256");
		// ���������� �������� ���� �������� � ������ ����� � ���� ���������
		String strByte;
		// ���������� �������� �����, ����������������� � Big Integer �� ���������� ���� 
		BigInteger biByte;		
		for (int i = 0; i < bytesCount; i++) {
			// ��������� ������ �� ���������, �.�. �����, ������ �� ������� �������� �� ��� �������
			strByte = trimHexValue.substring(i*2, i*2+2);
			// ��������� ���������� ��������� � Big Integer
			biByte = new BigInteger(strByte, 16);
			// �������� �����, ����� �������� ����������� �� �������� � ��������
			hexToIntValue = hexToIntValue.add(bigInt256.pow(i).multiply(biByte));
			
		}
		// ���������� ����� � Little-endian
		return hexToIntValue;		
	}
	
	// ������� ������������ ����������������� �����, �������� � ���� ������, � Big-endian 
	public static BigInteger convertHexToBE (String hexValue) {
		// ������� �� �������� ������ ������ "0x"
		String trimHexValue = trimHexPrefix(hexValue);
		// �������� ���������� ����
		int bytesCount = getBytesCount(hexValue);
		// ���������� ������ �������� �����, ����������������� � Big-endian
		BigInteger hexToIntValue = new BigInteger("0");
		// ���������� ����� 256 ��� ����������� ��������� ����� � Big-endian
		BigInteger bigInt256 = new BigInteger("256");	
		// ���������� �������� ���� �������� � ������ ����� � ���� ���������
		String strByte;
		// ���������� �������� �����, ����������������� � Big Integer �� ���������� ���� 
		BigInteger biByte;
		
		for (int i = 0; i < bytesCount; i++) {
			// ��������� ������ �� ���������, �.�. �����, ������ �� ������� �������� �� ��� �������
			strByte = trimHexValue.substring(i*2, i*2+2);
			// ��������� ���������� ��������� � Big Integer
			biByte = new BigInteger(strByte, 16);
			// �������� �����, ����� �������� ����������� �� �������� � ��������
			hexToIntValue = hexToIntValue.add(bigInt256.pow(bytesCount-1-i).multiply(biByte));
		}
		// ���������� ����� � Big-endian
		return hexToIntValue;
	}

	// ������� ������������ ����� �����, ���������� � Little-endian, � �������� ����������� ���� � �����������������
	public static String convertLEtoHex (BigInteger value, int bytesCount) {
		// ���������� ����� 256 ��� ����������� ��������� ����� � Little-endian � �����������������
		BigInteger bigInt256 = new BigInteger("256");	
		// ���������� ������ �������� �����, ���������� ����� ������������� �������� ������� �� ������� �� 256
		BigInteger byteValue;
		// ���������� ������ ������������� ������� ���������� ����� � ����������� ���������� 
		BigInteger diffValue;
		// ���������� ������ ����� 16 
		BigInteger bigInt16 = new BigInteger("16");
		// ���������� ������ ��������, ���������� ����� ��������� ������� ���� 
		BigInteger divValue = new BigInteger("0");
		divValue = divValue.add(value);
		// ���������� �������� ����������������� ����� � ���� ������ 
		String hexValue = "";
		// ���� �������� �� ��� ���, ���� �����, ���������� � ���������� �������,  ��������� ���� ����
		while(divValue.compareTo(bigInt256) != -1) {
			// �������� �������� �������� ����� ��� ������� �� ������� �� ���� �� 256
			byteValue = divValue.mod(bigInt256);
			// ������������� ���������� ����� � ���� ������ � ������� �� �������� ����� � ��������
			// ���� ����� ������ 16
			if (byteValue.compareTo(bigInt16) == -1) {
				// �� ���������� ������ "0" ��� ��������� ����� � ���� ����������� �������
				hexValue = "0" + byteValue.toString(16) + hexValue;
			} else {
				// ����� ������ "0" �� ����������
			hexValue = hexValue + byteValue.toString(16);
			}
			// �������� ������� ��������, ����������� �� ������� �� 256 � �����, ����������� ����� ������� �� ������� �� 256
			diffValue = divValue.add(byteValue.negate());
			// �������� �������� ����� ��� �������� ����� 
			divValue = diffValue.divide(bigInt256);		
		}
		// ��������� ����� ������� ����
		// ���� ����� ������ 16
		if (divValue.compareTo(bigInt16) == -1) {
			// �� ���������� ������ "0" ��� ��������� ����� � ���� ����������� �������
			hexValue = hexValue + "0" + divValue.toString(16);
		} else {
			// ����� ������ "0" �� ����������
			hexValue = hexValue + divValue.toString(16);
		}
		
		// ��� ��������� ������, ������������ �������� ���������� ����, ��������� ������ "0" � ������ � ������� �� �������� ����� � ��������
		for (int i = hexValue.length(); i < bytesCount*2; i++) {
			hexValue = hexValue + "0";
		}
		 // ���������� ���������� �������� ����� � ��������� ���� � ����������� ������� "0�" �������
		return "0x" + hexValue;		
	}
	
	// ������� ������������ ����� �����, ���������� � Big-endian, � �������� ����������� ���� � �����������������
	public static String convertBEtoHex (BigInteger value, int bytesCount) {
		// ���������� ����� 256 ��� ����������� ��������� ����� � Little-endian � �����������������
		BigInteger bigInt256 = new BigInteger("256");	
		// ���������� ������ �������� �����, ���������� ����� ������������� �������� ������� �� ������� �� 256
		BigInteger byteValue;
		// ���������� ������ ������������� ������� ���������� ����� � ����������� ���������� 
		BigInteger diffValue;
		// ���������� ������ ����� 16 
		BigInteger bigInt16 = new BigInteger("16");
		// ���������� ������ ��������, ���������� ����� ��������� ������� ���� 
		BigInteger divValue = new BigInteger("0");		
		divValue = divValue.add(value);
		// ���������� �������� ����������������� ����� � ���� ������ 
		String hexValue = "";
		// ���� �������� �� ��� ���, ���� �����, ���������� � ���������� �������,  ��������� ���� ����
		while(divValue.compareTo(bigInt256) != -1) {
			// �������� �������� �������� ����� ��� ������� �� ������� �� ���� �� 256
			byteValue = divValue.mod(bigInt256);
			// ������������� ���������� ����� � ���� ������ � ������� �� �������� ����� � ��������
			// ���� ����� ������ 16
			if (byteValue.compareTo(bigInt16) == -1) {
				// �� ���������� ������ "0" ��� ��������� ����� � ���� ����������� �������
				hexValue = "0" + byteValue.toString(16) + hexValue;
			} else {
				// ����� ������ "0" �� ����������
				hexValue = byteValue.toString(16) + hexValue;
			}
			// �������� ������� ��������, ����������� �� ������� �� 256 � �����, ����������� ����� ������� �� ������� �� 256
			diffValue = divValue.add(byteValue.negate());
			// ��������� ������� ����� 256
			divValue = diffValue.divide(bigInt256);		
		}
		// ��������� ����� ������� ����
		// ���� ����� ������ 16
		if (divValue.compareTo(bigInt16) == -1) {
			// �� ���������� ������ "0" ��� ��������� ����� � ���� ����������� �������
			hexValue = "0" + divValue.toString(16) + hexValue;
		} else {
			// ����� ������ "0" �� ����������
			hexValue = divValue.toString(16) + hexValue;
		}
		
		// ��� ��������� ������, ������������ �������� ���������� ����, ��������� ������ "0" � ������ � ������� �� �������� � ��������
		for (int i = hexValue.length(); i < bytesCount*2; i++) {
			hexValue = "0" + hexValue;
		}
		
		// ���������� ���������� �������� ����� � ��������� ���� � ����������� ������� "0�" �������
		return "0x" + hexValue;		
	}
	

}
