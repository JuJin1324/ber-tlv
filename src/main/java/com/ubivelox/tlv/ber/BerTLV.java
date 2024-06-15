package com.ubivelox.tlv.ber;

import java.util.Arrays;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2024. 6. 15.
 */

public abstract class BerTLV {
	private final Tag tag;
	private final Length length;

	protected BerTLV(Tag tag, Length length) {
		this.tag = tag;
		this.length = length;
	}

	public static BerTLV parse(String hexData) {
		if (hexData == null || hexData.isEmpty()) {
			throw new IllegalArgumentException("Empty hex data");
		}
		byte[] bytes = HexStringUtils.hexStringToByteArray(hexData);
		return parse(bytes);
	}

	public static BerTLV parse(byte[] bytes) {
		Tag tag = Tag.parse(bytes);

		byte[] tagDetachedBytes = subArray(bytes, tag.getValue().length);
		Length length = Length.parse(tagDetachedBytes);

		byte[] lengthDetachedBytes = subArray(tagDetachedBytes, length.getValue().length, length.getIntValue());
		Value value = new Value(lengthDetachedBytes);

		if (tag.isPrimitive()) {
			return new PrimitiveBerTLV(tag, length, value);
		}
		return new ConstructedBerTLV(tag, length, value);
	}

	protected int getDataLength() {
		return tag.getValue().length + length.getValue().length + length.getIntValue();
	}

	private static byte[] subArray(byte[] byteArray, int from) {
		return Arrays.copyOfRange(byteArray, from, byteArray.length);
	}

	private static byte[] subArray(byte[] byteArray, int from, int to) {
		return Arrays.copyOfRange(byteArray, from, to + 1);
	}

	@Override
	public String toString() {
		return tag + " " + length;
	}
}
