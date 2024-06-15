package com.ubivelox.tlv.ber;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.BitSet;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
class Tag {
    private static final List<Integer> NOT_IN_USED_BYTE = List.of(0x78, 0x79, 0x7D, 0x7E);

    private final byte[] value;
    private final boolean primitive;

    public static Tag parse(final byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            throw new IllegalArgumentException("DataObject cannot be null or empty");
        }
        if (NOT_IN_USED_BYTE.contains((int) bytes[0])) {
            throw new IllegalArgumentException("Not in used byte");
        }
        if (hasExtraBytes(bytes)) {
            return new Tag(new byte[]{bytes[0], bytes[1]}, parseIsPrimitive(bytes));
        }
        return new Tag(new byte[]{bytes[0]}, parseIsPrimitive(bytes));
    }

    @Override
    public String toString() {
        return HexStringUtils.byteArrayToHexString(value);
    }

    private static boolean parseIsPrimitive(final byte[] bytes) {
        BitSet bitSet = BitSet.valueOf(bytes);
        return !bitSet.get(5);
    }

    private static boolean hasExtraBytes(final byte[] bytes) {
        BitSet bitSet = BitSet.valueOf(bytes);
        int tagNumber = bitSetToInt(bitSet.get(0, 5));
        return tagNumber >= 31;
    }

    private static int bitSetToInt(final BitSet bitSet) {
        long[] longArray = bitSet.toLongArray();
        if (longArray.length == 0) {
            return 0;
        }
        return (int) longArray[0];
    }
}
