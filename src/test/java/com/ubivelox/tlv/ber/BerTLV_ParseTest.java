package com.ubivelox.tlv.ber;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BerTLV_ParseTest {

	@Test
	@DisplayName("빈 값이 입력된 경우 예외를 던진다")
	void whenInputNullOrEmpty_thenThrowException() {
		assertThrows(IllegalArgumentException.class,
			() -> BerTLV.parse(""));
	}

	@Test
	@DisplayName("하나의 Primitive 데이터를 입력한 경우")
	void whenInputOnePrimitiveData() {
		String hexData = "D80B01020304050607080910111213141516";
		BerTLV parsed = BerTLV.parse(hexData);
		assertEquals("D8 0B 0102030405060708091011", parsed.toString());
	}

	@Test
	@DisplayName("Constructed 데이터인데 안에 하나의 Primitive 데이터를 담은 경우")
	void whenInputConstructedIncludesOnePrimitiveData() {
		String hexData = "3F0A0DD80B01020304050607080910111213141516";
		BerTLV parsed = BerTLV.parse(hexData);
		assertEquals("3F0A 0D\n[D8 0B 0102030405060708091011]", parsed.toString());
	}

	@Test
	@DisplayName("Constructed 데이터인데 안에 두개의 Primitive 데이터를 담은 경우")
	void whenInputConstructedIncludesTwoPrimitiveData() {
		String hexData = "3F0A1AD80B0102030405060708091011D90B0102030405060708091011";
		BerTLV parsed = BerTLV.parse(hexData);
		assertEquals("3F0A 1A\n[D8 0B 0102030405060708091011, D9 0B 0102030405060708091011]", parsed.toString());
	}

	@Test
	@DisplayName("Constructed 데이터인데 안에 한개의 Constructed 데이터를 담은 경우")
	void whenInputConstructedIncludesOneConstructedIncludesOnePrimitive() {
		String hexData = "3F0A103F0B0DD80B01020304050607080910111213141516";
		BerTLV parsed = BerTLV.parse(hexData);
		assertEquals("3F0A 10\n[3F0B 0D\n[D8 0B 0102030405060708091011]]", parsed.toString());
	}
}
