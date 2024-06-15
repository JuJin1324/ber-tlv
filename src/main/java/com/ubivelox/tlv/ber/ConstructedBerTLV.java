package com.ubivelox.tlv.ber;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2024. 6. 15.
 */

@Getter
class ConstructedBerTLV extends BerTLV {
	private final List<BerTLV> value;

	protected ConstructedBerTLV(Tag tag, Length length, Value value) {
		super(tag, length);
		this.value = value.parse();
	}

	@Override
	public String toString() {
		return super.toString() + "\n"
			+ "[" + value.stream()
			.map(BerTLV::toString)
			.collect(Collectors.joining(", "))
			+ "]";
	}
}
