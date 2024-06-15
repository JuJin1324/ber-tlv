package com.ubivelox.tlv.ber;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Length_ParseTest {

    @Test
    @DisplayName("null 이나 빈 값이 입력된 경우 예외를 던진다")
    void whenInputNullOrEmpty_thenThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> Length.parse(null));

        assertThrows(IllegalArgumentException.class,
                () -> Length.parse(new byte[]{}));
    }

    @Test
    @DisplayName("1바이트만 파싱되는 바이트가 입력된 경우 1바이트 길이를 반환한다")
    void whenInputOneByteParsingBytes_thenReturnOneByteLength() {
        // 0x1D: 00011101
        byte[] oneByteParsingBytes = {0x1D, 0x01};
        Length length = Length.parse(oneByteParsingBytes);
        assertArrayEquals(new byte[]{0x1D}, length.getValue());
    }

    @Test
    @DisplayName("2바이트가 파싱되는 바이트가 입력된 경우 2바이트 길이를 반환한다")
    void whenInputTwoByteParsingBytes_thenReturnTwoBytesLength() {
        // 0x80: 10000000
        // 0xFD: 11111111
        byte[] twoBytesParsingBytes = {(byte) 0x80, (byte) 0xFF};
        Length length = Length.parse(twoBytesParsingBytes);
        assertArrayEquals(twoBytesParsingBytes, length.getValue());
    }
}