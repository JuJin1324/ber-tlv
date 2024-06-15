package com.ubivelox.tlv.ber;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class Value_ParseTest {

    @Test
    @DisplayName("하나의 Primitive 데이터를 입력한 경우")
    void whenInputOnePrimitiveData() {
        // Given
        byte[] bytes = HexStringUtils.hexStringToByteArray("D80B0102030405060708091011");
        Value value = new Value(bytes);
        // When
        List<BerTLV> tlvList = value.parse();
        // Then
        assertEquals(1, tlvList.size());
        assertEquals("[D8 0B 0102030405060708091011]", tlvList.toString());
    }

    @Test
    @DisplayName("두개의 Primitive 데이터를 담은 경우")
    void whenInputTwoPrimitiveData() {
        // Given
        byte[] bytes = HexStringUtils.hexStringToByteArray("D80B0102030405060708091011D90B0102030405060708091011");
        Value value = new Value(bytes);
        // When
        List<BerTLV> tlvList = value.parse();
        // Then
        assertEquals(2, tlvList.size());
        assertEquals("[D8 0B 0102030405060708091011, D9 0B 0102030405060708091011]", tlvList.toString());
    }

    @Test
    @DisplayName("한개의 Constructed 데이터를 담은 경우")
    void whenInputOneConstructedIncludesOnePrimitive() {
        // Given
        byte[] bytes = HexStringUtils.hexStringToByteArray("3F0A103F0B0DD80B0102030405060708091011");
        Value value = new Value(bytes);
        // When
        List<BerTLV> tlvList = value.parse();
        // Then
        assertEquals(1, tlvList.size());
        assertEquals("[3F0A 10\n[3F0B 0D\n[D8 0B 0102030405060708091011]]]", tlvList.toString());
    }
}
