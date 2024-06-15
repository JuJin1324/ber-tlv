package com.ubivelox.tlv.ber;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.BitSet;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
class Length {
    private final byte[] value;
    private final int intValue;

    public static Length parse(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            throw new IllegalArgumentException("DataObject cannot be null");
        }

        if (hasExtraByte(new byte[]{bytes[0]})) {
            return new Length(new byte[]{bytes[0], bytes[1]}, bytes[1]);
        }
        return new Length(new byte[]{bytes[0]}, bytes[0]);
    }

    @Override
    public String toString() {
        return HexStringUtils.byteArrayToHexString(value);
    }

    private static boolean hasExtraByte(byte[] bytes) {
        BitSet bitSet = BitSet.valueOf(bytes);
        return bitSet.get(7);
    }
}
