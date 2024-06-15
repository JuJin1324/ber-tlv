package com.ubivelox.tlv.ber;

import lombok.Getter;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2024. 6. 15.
 */

@Getter
class PrimitiveBerTLV extends BerTLV {
	private final byte[] value;

	protected PrimitiveBerTLV(Tag tag, Length length, Value value) {
		super(tag, length);
		this.value = value.getValue();
	}

	@Override
	public String toString() {
		return super.toString() + " " + HexStringUtils.byteArrayToHexString(value);
	}
}
