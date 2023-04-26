package com.ppol.auth.util.converter;



import com.ppol.auth.util.constatnt.enums.Provider;
import com.ppol.auth.util.converter.common.CommonEnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProviderConverter extends CommonEnumConverter<Provider> {

	public ProviderConverter() {
		super(Provider.class);
	}
}
