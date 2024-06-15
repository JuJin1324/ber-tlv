package com.ubivelox.tlv.ber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
class Value {
	private final byte[] value;

	public Value(byte[] bytes) {
		this.value = bytes;
	}

	public List<BerTLV> parse() {
		List<BerTLV> result = new ArrayList<>();
		if (value == null || value.length == 0) {
			return result;
		}

		int offset = 0;
		byte[] subArray = value;
		while (offset < value.length) {
			subArray = subArray(subArray, offset);
			var parsed = BerTLV.parse(subArray);
			result.add(parsed);
			offset += parsed.getDataLength();
		}
		return result;
	}

	private static byte[] subArray(byte[] byteArray, int from) {
		return Arrays.copyOfRange(byteArray, from, byteArray.length);
	}
}
