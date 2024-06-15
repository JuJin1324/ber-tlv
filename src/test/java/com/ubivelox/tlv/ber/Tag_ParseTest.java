package com.ubivelox.tlv.ber;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Tag_ParseTest {

    @Test
    @DisplayName("null 이나 빈 값이 입력된 경우 예외를 던진다")
    void whenInputNullOrEmpty_thenThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> Tag.parse(null));

        assertThrows(IllegalArgumentException.class,
                () -> Tag.parse(new byte[]{}));
    }

    @Test
    @DisplayName("스펙에서 사욯하지 않는 바이트가 입력된 경우 예외를 던진다")
    void whenInputNotInUsedByte_thenThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> Tag.parse(new byte[]{0x78}));

        assertThrows(IllegalArgumentException.class,
                () -> Tag.parse(new byte[]{0x79}));

        assertThrows(IllegalArgumentException.class,
                () -> Tag.parse(new byte[]{0x7D}));

        assertThrows(IllegalArgumentException.class,
                () -> Tag.parse(new byte[]{0x7E}));
    }

    @Test
    @DisplayName("1바이트만 파싱되는 바이트가 입력된 경우 1바이트 태그를 반환한다")
    void whenInputOneByteParsingBytes_thenReturnOneByteTag() {
        // 0x77: 01110111
        byte[] oneByteParsingBytes = {0x77, 0x01};
        Tag tag = Tag.parse(oneByteParsingBytes);
        assertArrayEquals(new byte[]{0x77}, tag.getValue());
    }

    @Test
    @DisplayName("2바이트가 파싱되는 바이트가 입력된 경우 2바이트 태그를 반환한다")
    void whenInputTwoByteParsingBytes_thenReturnTwoBytesTag() {
        // 0x3F: 00011111
        byte[] twoBytesParsingBytes = {0x3F, 0x0A};
        Tag tag = Tag.parse(twoBytesParsingBytes);
        assertArrayEquals(twoBytesParsingBytes, tag.getValue());
    }

    @Test
    @DisplayName("Primitive 로 파싱되는 바이트가 입력된 경우 IsPrimitive 가 true 를 반환한다")
    void whenInputPrimitiveParsingBytes_thenIsPrimitiveTrue() {
        // 0x5C: 01011100
        byte[] primitiveParsingBytes = {0x5C};
        Tag tag = Tag.parse(primitiveParsingBytes);
        assertTrue(tag.isPrimitive());
    }

    @Test
    @DisplayName("Constructed 로 파싱되는 바이트가 입력된 경우 IsPrimitive 가 false 를 반환한다")
    void whenInputConstructedParsingBytes_thenIsPrimitiveFalse() {
        // 0x3D: 00111101
        byte[] constructedParsingBytes = {0x3D};
        Tag tag = Tag.parse(constructedParsingBytes);
        assertFalse(tag.isPrimitive());
    }
}