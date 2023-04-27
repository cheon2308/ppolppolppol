package com.ppol.user.util.converter;



import com.ppol.user.util.constatnt.enums.Provider;
import com.ppol.user.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProviderConverter extends CommonEnumConverter<Provider> {

	public ProviderConverter() {
		super(Provider.class);
	}
}
